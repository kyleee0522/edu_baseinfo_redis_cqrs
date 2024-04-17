package com.kt.edu.common.feign.decoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.kt.edu.common.exception.InterfaceException;
import com.kt.edu.common.payload.CommonHeader;
import com.kt.edu.common.utils.CommonUtil;
import com.kt.edu.common.utils.JsonUtil;
import com.kt.edu.common.utils.LogUtil;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;


public class ResponseDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type)
            throws IOException, DecodeException, FeignException, InterfaceException {

        String url = response.request().url();
        LogUtil.info("[CTG:CMMN] Feign Decoder Url : {}, type : {}", url, type);

        // B-MON monitoring 인 경우 Feign Interceptor에서 제외시킨다. 2022-04-25
        //if (StringUtils.equalsAny(response.request().url(),"mgr", "gwadm", "monitoring")) {
        if (StringUtils.contains(url, "//gwadm") || StringUtils.contains(url, "//bmon") || StringUtils.contains(url, "//mgr") || StringUtils.contains(url, "//agent")) {
            if (response.status() == 404 || response.status() == 204) {
                return Util.emptyValueOf(type);
            }
            if (response.body() == null) {
                return null;
            }
            if (byte[].class.equals(type)) {
                return Util.toByteArray(response.body().asInputStream());
            }
            if (String.class.equals(type)) {
                return Util.toString(response.body().asReader(Util.UTF_8));
            }
            else {
                String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
                return getObject(bodyStr, type);
            }

        }

        if (response.body() == null) {
            throw new DecodeException(response.status(), "No valid data returned", response.request());
        }

        ObjectMapper mapper = JsonUtil.getObjectMapper();
        String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
        CommonHeader resCommonHeader = CommonUtil.getResCommonHeader(bodyStr);

        LogUtil.info("[CTG:CMMN] Feign Decoder CommonHeader : {}", resCommonHeader);

        if(resCommonHeader==null) {
            return getObject(bodyStr, type);
        }

        JsonNode rootNode = mapper.readTree(bodyStr).at("/service_response");
        Iterator<String> fieldNames = rootNode.fieldNames();

        StringBuffer retVal = new StringBuffer();
        int payloadCnt = 0;
        retVal.append("{");
        while(fieldNames.hasNext()){
            String fieldName = fieldNames.next();
            if("commonHeader".equals(fieldName)==false&&"bizHeader".equals(fieldName)==false&&"saSecurity".equals(fieldName)==false) {

                Object obj = mapper.readTree(bodyStr).at("/service_response/" + fieldName);
                try {
                    if(obj==null) {
                        continue;
                    }
                    if(payloadCnt > 0) {
                        retVal.append(",");
                    }
                    retVal.append(String.format("\"%s\":%s", fieldName, mapper.writeValueAsString(obj)));
                    payloadCnt++;
                } catch (JsonProcessingException e) {
                    LogUtil.error("[CTG:CMMN]", e);
                }
            }
        }
        retVal.append("}");

        LogUtil.info("[CTG:CMMN] Feign Decoder retVal : {},{}", retVal, type);

        if("".equals(retVal.toString())) {
            return getObject(bodyStr, type);
        }

        JavaType javaType = TypeFactory.defaultInstance().constructType(type);
        Object obj = mapper.readValue(String.valueOf(retVal), javaType);

        return obj;
    }

    private Object getObject(String bodyStr, Type type)
            throws IOException, JsonProcessingException, JsonMappingException {
        ObjectMapper mapper = JsonUtil.getObjectMapper();
        JavaType javaType = TypeFactory.defaultInstance().constructType(type);
        Object obj = mapper.readValue(String.valueOf(bodyStr), javaType);
        return obj;
    }

}
