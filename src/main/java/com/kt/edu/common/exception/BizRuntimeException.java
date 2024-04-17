package com.kt.edu.common.exception;


@Deprecated
public class BizRuntimeException extends DefaultNestedRuntimeExcepiton {

    private static final long serialVersionUID = -9096806026148593245L;

    public BizRuntimeException(String code) {
        super(code);
    }

    public BizRuntimeException(String code, String reason) {
        super(code, reason);
    }

    public BizRuntimeException(String code, String reason, Object data) {
        super(code, reason, data);
    }

    public BizRuntimeException(String code, String reason, Throwable cause) {
        super(code, reason, cause);
    }

    public BizRuntimeException(String code, String reason, Object data, Throwable cause) {
        super(code, reason, data, cause);
    }

    public BizRuntimeException(String code, String reason, String basc) {
        super(code, reason, basc);
    }

}