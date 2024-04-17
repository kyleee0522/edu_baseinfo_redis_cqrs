package com.kt.edu.common.feign.interceptor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.edu.common.delegator.FeignDelegator;
import com.kt.edu.common.exception.BizRuntimeException;
import com.kt.edu.common.payload.CommonHeader;
import com.kt.edu.common.payload.RequestPayload;
import com.kt.edu.common.payload.ServiceRequest;
import com.kt.edu.common.utils.CommonBeanUtil;
import com.kt.edu.common.utils.CommonUtil;
import com.kt.edu.common.utils.JsonUtil;
import com.kt.edu.common.utils.LogUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;


public class CustomRequestInterceptor implements RequestInterceptor {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void apply(RequestTemplate requestTemplate) {

        LogUtil.info("[CTG:CMMN] Feign Interceptor target : {}, path : {}, url : {}", requestTemplate.feignTarget().name().toString(), requestTemplate.path(), requestTemplate.url());

        /*
        if (StringUtils.equalsAny(requestTemplate.feignTarget().name().toString(), "gwadm", "monitoring","mgr","cmmnAgent")) {
            return;
        }*/

        Collection<String> feignHeaders = requestTemplate.headers().get("feignHeader");

        LogUtil.info("[CTG:CMMN] Feign Header : {}", feignHeaders);

        CommonHeader commonHeader = CommonUtil.getCommonHeader();

        // Openfeign 사용시에는 thread local이 필요 없지만 circuitbreaker는  별도의 thread가 생성이 되기 때문에 threadlocal 사용시 필요
        if(commonHeader == null && feignHeaders!=null) {
            String feignHeader = feignHeaders.iterator().next();
            if(StringUtils.isNotBlank(feignHeader)) {
                RequestPayload rqstBdy = JsonUtil.readJson(feignHeader, RequestPayload.class);
                commonHeader = rqstBdy.getService_request().getCommonHeader();
            }
        }

        LogUtil.info("[CTG:CMMN] Feign Interceptor CommonHeader : {} : ", commonHeader);

        FeignDelegator feignDelegator = (FeignDelegator) CommonBeanUtil.getBean(FeignDelegator.class);

        String path = getUrn(requestTemplate.path());

        CommonHeader commonHeaderTarget = feignDelegator.makeCommonHeader(commonHeader, path);


        RequestPayload rqstBdy = RequestPayload.builder().service_request(ServiceRequest.builder()
                        .commonHeader(commonHeaderTarget).build()).build();

        try {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            String header = objectMapper.writeValueAsString(rqstBdy);
            LogUtil.info("[CTG:CMMN] Feign Interceptor serviceRequest header : {}", header);
            //requestTemplate.header("saHeader", Base64Utils.encodeToString(header.getBytes()));


            String serviceRequest = JsonUtil.toJson(rqstBdy.getService_request());
            if(serviceRequest!=null) {
                //requestTemplate.header("Req-Common-Header", Base64Utils.encodeToString(serviceRequest.getBytes()));
            }

            /*StringBuffer retVal = new StringBuffer();

            //////////////////////////
            String requestBody = new String(rawData, StandardCharsets.UTF_8);*/

        } catch (Exception e) {
            LogUtil.error("[CTG:CMMN]", e);
            throw new BizRuntimeException("ICSE1001", e.getMessage());
        }

        FeignCmmnSvc feignCmmnSvc = (FeignCmmnSvc) CommonBeanUtil.getBean("feignCmmnSvc");
        if (feignCmmnSvc != null) {
            feignCmmnSvc.apply(commonHeaderTarget, requestTemplate);
        }

    }

    private static String getUrn(String path) {

        if(StringUtils.isEmpty(path)) {
            return path;
        }

        if(path.startsWith("http")) {
            try {
                URL url = new URL(path);
                return url.getPath();
            } catch (MalformedURLException e) {
                LogUtil.error("[CTG:CMMN]", e);
            }
        }

        return path;
    }

}
