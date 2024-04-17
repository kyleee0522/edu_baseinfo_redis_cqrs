package com.kt.edu.baseinfo.comcd.exception;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.NestedRuntimeException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class DefaultNestedRuntimeExcepiton extends NestedRuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String code;

    @Nullable
    private String reason;

    @Nullable
    private Object data;


    public DefaultNestedRuntimeExcepiton(String code) {
        this(code, null, null, null);
    }

    public DefaultNestedRuntimeExcepiton(String code, @Nullable String reason) {
        this(code, reason, null, null);
    }

    public DefaultNestedRuntimeExcepiton(String code, @Nullable String reason, @Nullable Object data) {
        this(code, reason, data, null);
    }

    public DefaultNestedRuntimeExcepiton(String code, @Nullable String reason, @Nullable Throwable cause) {
        this(code, reason, null, cause);
    }

    public DefaultNestedRuntimeExcepiton(String code, @Nullable String reason, @Nullable Object data, @Nullable Throwable cause) {
        super(null, cause);

        Assert.notNull(code, "Error code is required");
        this.code = code;
//        this.code = "ICSE9999";
        this.reason = reason;
        this.data = data;
    }

    public String getCode() {
        return this.code;
    }

    public String getReason() {
        return this.reason;
    }

    public Object getData() { return this.data; }

    @Override
    public String getMessage() {
        String msg = this.code + (this.reason != null ? " \"" + this.reason + "\"" : "");
        return NestedExceptionUtils.buildMessage(msg, getCause());
    }
}