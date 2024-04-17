package com.kt.edu.common.exception;

public class RedisOperatorException extends DefaultNestedRuntimeExcepiton{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RedisOperatorException(String code) {
        super(code);
    }

    public RedisOperatorException(String code, String reason) {
        super(code, reason);
    }

    public RedisOperatorException(String code, String reason, Object data) {
        super(code, reason, data);
    }

    public RedisOperatorException(String code, String reason, Throwable cause) {
        super(code, reason, cause);
    }

    public RedisOperatorException(String code, String reason, Object data, Throwable cause) {
        super(code, reason, data, cause);
    }
}