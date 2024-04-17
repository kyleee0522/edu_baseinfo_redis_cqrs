/************************************************************************************** 
* ICIS version 1.0
 *
 * Copyright ⓒ 2022 kt/ktds corp. All rights reserved.
 *
 * This is a proprietary software of kt corp, and you may not use this file except in compliance
 * with license agreement with kt corp. Any redistribution or use of this software, with or without
 * modification shall be strictly prohibited without prior written approval of kt corp, and the
 * copyright notice above does not evidence any actual or intended publication of such software.
 *************************************************************************************/
package com.kt.edu.common.wrapper;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.edu.common.payload.CommonHeader;
import com.kt.edu.common.utils.CommonUtil;
import com.kt.edu.common.utils.JsonUtil;
import com.kt.edu.common.utils.LogUtil;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.MDC;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Slf4j
public class RequestBodyTransformWrapper extends HttpServletRequestWrapper {

  private String transformBody;

  private ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Constructs a request object wrapping the given request.
   *
   * @param request The request to wrap
   * @throws IllegalArgumentException if the request is null
   */
  public RequestBodyTransformWrapper(HttpServletRequest request) {
    super(request);

    byte[] rawData = null;
    String modifRequestBody = "";
    String modifCommonHeader = request.getHeader("commonHeader");

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

      //LogUtil.info("[CMMN-FRWK] reqBody: {}", requestBody);
      //LogUtil.info("[CMMN-FRWK] reqBody: {}", requestBody.replaceAll("(\r\n|\r|\n|\n\r)", " "));

      String globalNo = "00000000000000000000000000000000";
      CommonHeader commonHeader = null;

      if (modifCommonHeader !=null) {
         commonHeader = JsonUtil.jsonToObject(modifCommonHeader, "", new TypeReference<CommonHeader>() {
        });
        commonHeader.setTrFlag("T");
        commonHeader.setTrDate(trDate);
        commonHeader.setTrTime(trTime);
      }

      // MDC 설정을 해야 header 가 찍힘.
      MDC.put("header", JsonUtil.toJson(commonHeader));

      if (commonHeader != null) {
         // 변경된 commonHeader 설정
          request.setAttribute("commonHeader", commonHeader);

          retVal.append(requestBody);
          globalNo = commonHeader.getGlobalNo();
          modifRequestBody = retVal.toString();

      }else{
        retVal.append(requestBody);
      }

      MDC.put("globalNo", globalNo);
      // swagger 호출시
      if(request.getRequestURI().contains("swagger-ui") || 
          request.getRequestURI().contains("system") ||
          request.getRequestURI().contains("actuator") ||
          request.getRequestURI().contains("api-docs") ) {
      }

      modifRequestBody = retVal.toString();

    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    // 모니터링 용 로그
    LogUtil.info("[CTG:MON]{}", modifRequestBody);
    this.transformBody = modifRequestBody;

  }

  @Override
  public ServletInputStream getInputStream() {
    final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(transformBody.getBytes(StandardCharsets.UTF_8));
    return new ServletInputStream() {
      @Override
      public boolean isFinished() {
        return false;
      }

      @Override
      public boolean isReady() {
        return false;
      }

      @Override
      public void setReadListener(ReadListener listener) {}

      @Override
      public int read() {
        return byteArrayInputStream.read();
      }
    };
  }

  @Override
  public BufferedReader getReader() {
    return new BufferedReader(new InputStreamReader(this.getInputStream()));
  }
}
