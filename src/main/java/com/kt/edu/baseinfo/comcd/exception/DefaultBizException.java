package com.kt.edu.baseinfo.comcd.exception;

import com.kt.edu.common.utils.ResponseLogCd;
import org.springframework.core.NestedRuntimeException;
import org.springframework.lang.Nullable;

import java.util.List;

public class DefaultBizException extends NestedRuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String code;

    @Nullable
    private List<String> errMsgArr;

    @Nullable
    private String responseLogCd;

    @Nullable
    private String chgTitle;

    @Nullable
    private String chgReason;

    public DefaultBizException(String code) {
        this(code, null, null, null, null);
    }

    public DefaultBizException(String code, ResponseLogCd responseLogCd, List<String> errMsgArr) {
        this(code, responseLogCd, errMsgArr, null, null);
    }

    public DefaultBizException(String code, @Nullable ResponseLogCd responseLogCd, @Nullable List<String> errMsgArr, @Nullable String chgTitle, @Nullable String chgReason) {
        super(null);

        this.code = code;

        if(responseLogCd == null) {
            ResponseLogCd defaultResponseLogCd = ResponseLogCd.N;
            this.responseLogCd = defaultResponseLogCd.getErrorInfo();
        }
        else {
            this.responseLogCd = responseLogCd.getErrorInfo();
        }
        this.errMsgArr = errMsgArr;
        this.chgTitle = chgTitle;
        this.chgReason = chgReason;
    }

    public String getCode() {
        return this.code;
    }

    public List<String> getErrMsgArr() {
        return this.errMsgArr;
    }

    public String getResponseLogCd() {
        return this.responseLogCd;
    }

    public String getChgTitle() {
        return this.chgTitle;
    }

    public String getChgReason() {
        return this.chgReason;
    }

}