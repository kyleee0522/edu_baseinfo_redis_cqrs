package com.kt.edu.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kt.edu.common.constants.Constant;
import com.kt.edu.common.payload.CommonHeader;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {

    private static ObjectMapper objectMapper;

    public static ObjectMapper getInstanceObjectMapper() {
        if (objectMapper == null) {
            log.debug("=====call new ObjectMapper()");
            objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        }
        return objectMapper;
    }

    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if( attr == null) {
            return null;
        }
        return attr.getRequest();
    }

    public static CommonHeader getCommonHeader() {
        HttpServletRequest servletRequest = getHttpServletRequest();
        return (CommonHeader) servletRequest.getAttribute("commonHeader");
    }

    public static CommonHeader getReqCommonHeader(String jsonStr) {
        return jsonToObject(jsonStr, Constant.REQUEST_COMMON_HEADER_PATH, new TypeReference<CommonHeader>() {
        });
    }

    public static CommonHeader getResCommonHeader(String jsonStr) {
        return jsonToObject(jsonStr, Constant.RESPONSE_COMMON_HEADER_PATH, new TypeReference<CommonHeader>() {
        });
    }

    public static List<String> getReqPayloadNames(String jsonStr) {
        //String payloadNames = jsonToObject(jsonStr, Constant.REQUEST_PAYLOAD_NAMES, new TypeReference<String>() {
        //});
        String payloadNames = jsonToObject(jsonStr,"" , new TypeReference<String>() {
        });
        if (payloadNames != null) {
            String[] payloadNameArr = payloadNames.split(",");
            List<String> payloadNameList = Arrays.asList(payloadNameArr);
            return payloadNameList;
        }
        return new ArrayList<String>();
    }

    public static List<String> getResPayloadNames(String jsonStr) {
        String payloadNames = jsonToObject(jsonStr, Constant.RESPONSE_PAYLOAD_NAMES, new TypeReference<String>() {
        });
        if (payloadNames != null) {
            String[] payloadNameArr = payloadNames.split(",");
            List<String> payloadNameList = Arrays.asList(payloadNameArr);
            return payloadNameList;
        }
        return new ArrayList<String>();
    }

    public static <T> T getReqPayload(String jsonStr, String key, TypeReference<T> typeReference) {
        return jsonToObject(jsonStr, Constant.REQUEST_PATH + "/" + key, typeReference);
    }

    public static <T> T jsonToObject(String jsonStr, String keyPath, TypeReference<T> typeReference) {
        ObjectMapper mapper = getInstanceObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T objT = null;
        try {
            String json = mapper.readTree(jsonStr).at(keyPath).toString();
            if (StringUtils.isNotBlank(json)) {
                objT = mapper.readValue(json, typeReference);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return objT;

    }

    public static String base64Encode(byte[] aBuffer) {
        return new String(Base64.encode(aBuffer));
    }

    public static byte[] base64Decode(String inputString) {
        return Base64.decode(inputString.getBytes());
    }

}