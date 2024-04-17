package com.kt.edu.baseinfo.comcd.exception;

import com.kt.edu.common.utils.ResponseLogCd;

import java.util.List;

public class BizException extends DefaultBizException {

    private static final long serialVersionUID = -9096806026148593245L;

    public BizException(String code) {
        super(code);
    }

    public BizException(String code, ResponseLogCd responseLogCd, List<String> errMsgArr) {
        super(code, responseLogCd, errMsgArr);
    }

    public BizException(String code, ResponseLogCd responseLogCd, List<String> errMsgArr, String chgTitle, String chgReason) {
        super(code, responseLogCd, errMsgArr, chgTitle, chgReason);
    }

}
