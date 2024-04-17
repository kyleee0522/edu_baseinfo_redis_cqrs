package com.kt.edu.common.exception;


import com.kt.edu.common.payload.ResponeMessage;
import com.kt.edu.common.utils.JsonUtil;
import feign.FeignException;
import feign.Request;

public class InterfaceException extends FeignException {

    private static final long serialVersionUID = 894457625883918695L;


    public InterfaceException(int status, String message, Request request) {
        super(status, message, request);
    }

    public InterfaceException(int status, ResponeMessage responseMessage, Request request) {
        super(status, JsonUtil.toJson(responseMessage),request);
    }

    public ResponeMessage getResponseMessage() {
        return JsonUtil.readJson(this.getMessage(),ResponeMessage.class);
    }

}