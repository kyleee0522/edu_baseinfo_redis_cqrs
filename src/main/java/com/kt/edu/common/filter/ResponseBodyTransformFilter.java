package com.kt.edu.common.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kt.edu.common.payload.CommonHeader;
import com.kt.edu.common.payload.ServiceResponse;
import com.kt.edu.common.utils.JsonUtil;
import com.kt.edu.common.utils.LogUtil;
import com.kt.edu.common.utils.ThreadUtil;
import com.kt.edu.common.wrapper.ResponseBodyTransformWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

@Slf4j
@Component
public class ResponseBodyTransformFilter implements Filter {

    @Override
    public void init(
            FilterConfig filterConfig
    ) throws ServletException {
        log.info("ResponseBodyTransformFilter init()");
    }

    @Override
    public void destroy() {
        log.info("ResponseBodyTransformFilter destroy()");
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (((HttpServletRequest) request).getRequestURI().contains("swagger-ui")||
                ((HttpServletRequest) request).getRequestURI().contains("system")||
                ((HttpServletRequest) request).getRequestURI().contains("actuator")||
                ((HttpServletRequest) request).getRequestURI().contains("api-docs")) {
            chain.doFilter(request, response);
            return;
        }

        ResponseBodyTransformWrapper responseWrapper = new ResponseBodyTransformWrapper((HttpServletResponse) response);
        chain.doFilter(request, responseWrapper);
        String responseMessage = new String(responseWrapper.getDataStream(), StandardCharsets.UTF_8);
        String transformResponseMessage = transformResponseMessageConverter((HttpServletRequest)request,(HttpServletResponse)response, responseMessage);

        //LogUtil.info("transformResponseMessage: {}", transformResponseMessage);

        MDC.clear();
        ThreadUtil.threadLocalCommonHeader.remove();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(transformResponseMessage.getBytes());
    }

    public String transformResponseMessageConverter(HttpServletRequest request,HttpServletResponse response, String transformMessage) throws JsonProcessingException {
        //super(response);

        byte[] rawData = null;
        String modifRequestBody = "";
        String modifCommonHeader = request.getHeader ("commonHeader");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmssSSS", Locale.KOREA);

        if (modifCommonHeader != null){
            modifCommonHeader = new String(Base64.getDecoder().decode(modifCommonHeader));
            //LogUtil.info("[CMMN-FRWK] reqCommonHeader: {}",modifCommonHeader);
        }

        long currentTimeMillis = System.currentTimeMillis();
        String trDate = dateFormat.format(new Date(currentTimeMillis));
        String trTime = timeFormat.format(new Date(currentTimeMillis = currentTimeMillis+100));

        try {
            InputStream inputStream = request.getInputStream();
            rawData = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        try {

            StringBuffer retVal = new StringBuffer();
            String requestBody = new String(rawData, StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();

            String globalNo = "00000000000000000000000000000000";
            CommonHeader commonHeader = null;

            if (modifCommonHeader !=null) {
                commonHeader = JsonUtil.jsonToObject(modifCommonHeader, "", new TypeReference<CommonHeader>() {
                });
            }

            if (commonHeader.getResponseType() !=null) {
                commonHeader.setTrFlag("R");
                commonHeader.setResponseType("I");
                commonHeader.setResponseTitle("성공");
                commonHeader.setResponseCode("");
                commonHeader.setResponseBasc("정상처리되었습니다.");
                commonHeader.setTrDate(trDate);
                commonHeader.setTrTime(trTime);
            }

            MDC.put("header", JsonUtil.toJson(commonHeader));
            response.addHeader("commonHeader", Base64.getEncoder().encodeToString(JsonUtil.toJson(commonHeader).getBytes()));

            //LogUtil.info("transformMessage: {}", transformMessage);
            //LogUtil.info("transformMessage commonHeader: {}", commonHeader);

            String modifyMessage = "";
            String payloadNames = "";
            if (StringUtils.isNotBlank(transformMessage)) {
                modifyMessage = transformMessage.substring(transformMessage.indexOf("{") + 1, transformMessage.lastIndexOf("}"));
                JsonNode objectPayload = objectMapper.readTree(modifyMessage);
                LogUtil.info("objectPayload: {}", objectPayload.asText());
                payloadNames = objectPayload.asText();
            }
            ServiceResponse serviceResponse = ServiceResponse.builder().commonHeader(commonHeader).build();
            JsonNode jsonNode = objectMapper.valueToTree(serviceResponse);
            String serviceResponseMessage = jsonNode.toString();
            String serviceResponseModifyMessage = serviceResponseMessage.substring(serviceResponseMessage.indexOf("{") + 1, serviceResponseMessage.lastIndexOf("}"));

            String resultValue = "";
            if (StringUtils.isNotBlank(modifyMessage)) {
                resultValue = String.format("{\"service_response\":{%s,%s}}", serviceResponseModifyMessage, modifyMessage);
            } else {
                resultValue = String.format("{\"service_response\":{%s}}", serviceResponseModifyMessage);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        // 모니터링 용 로그
        LogUtil.info("[CTG:MON]{}", transformMessage);

        //String modifyBody = resultValue;
        //LogUtil.info("transformMessage: {}", transformMessage);
        return transformMessage;
        //return modifyBody;
    }
}