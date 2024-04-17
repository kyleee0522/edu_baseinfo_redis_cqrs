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


import com.kt.edu.common.filter.FilterServletOutputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResponseBodyTransformWrapper extends HttpServletResponseWrapper {
  ByteArrayOutputStream output;
  FilterServletOutputStream filterOutput;

  /**
   * Constructs a response adaptor wrapping the given response.
   *
   * @param response The response to be wrapped
   * @throws IllegalArgumentException if the response is null
   */
  public ResponseBodyTransformWrapper(HttpServletResponse response) {
      super(response);
      output = new ByteArrayOutputStream();
  }

  @Override
  public ServletOutputStream getOutputStream() throws IOException {
      if (filterOutput == null) {
          filterOutput = new FilterServletOutputStream(output);
      }
      return filterOutput;
  }

  public byte[] getDataStream() {
      return output.toByteArray();
  }
}