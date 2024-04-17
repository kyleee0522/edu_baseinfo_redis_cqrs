package com.kt.edu.common.feign.decoder;

import com.kt.edu.common.exception.InterfaceException;
import com.kt.edu.common.payload.CommonHeader;
import com.kt.edu.common.payload.ResponeMessage;
import com.kt.edu.common.utils.CommonUtil;
import com.kt.edu.common.utils.LogUtil;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

import java.io.IOException;

public class ResponseErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        ResponeMessage responseMessage = null;

        try {

            if (response.body() != null) {
                String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
                LogUtil.info("[CTG:CMMN] {} call fail. status : {}, body : {}", methodKey, response.status(), bodyStr);
                if (500 <= response.status()) {
                    CommonHeader commonHeader = CommonUtil.getResCommonHeader(bodyStr);
                    if (commonHeader != null) {
                        responseMessage = ResponeMessage.builder().responseBasc(commonHeader.getResponseBasc())
                                .responseCode(commonHeader.getResponseCode())
                                .responseDtal(commonHeader.getResponseDtal())
                                .responseLogcd(commonHeader.getResponseLogcd())
                                .responseTitle(commonHeader.getResponseTitle())
                                .responseType(commonHeader.getResponseType()).build();
                        return new InterfaceException(response.status(), responseMessage, response.request());
                    }

                }
            }

            responseMessage = ResponeMessage.builder().responseBasc("").responseCode("ICSE1001")
                    .responseDtal(String.format("status : %s, body : %s", response.status(), response.body()))
                    .responseLogcd("").responseTitle(String.format("%s 요청이 성공하지 못했습니다.(JsonParseException)", methodKey))
                    .responseType("E").build();

            return new InterfaceException(response.status(), responseMessage, response.request());

        } catch (IOException e) {
            LogUtil.error("[CTG:CMMN]", e);
            responseMessage = ResponeMessage.builder().responseBasc("").responseCode("ICSE1001")
                    .responseDtal(String.format("status : %s, body : %s", response.status(), response.body()))
                    .responseLogcd("").responseTitle(String.format("%s 요청이 성공하지 못했습니다.", methodKey)).responseType("E")
                    .build();
        }

        return new InterfaceException(response.status(), responseMessage, response.request());
    }

}