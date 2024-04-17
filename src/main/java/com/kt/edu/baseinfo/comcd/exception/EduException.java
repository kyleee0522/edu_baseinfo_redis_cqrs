package com.kt.edu.baseinfo.comcd.exception;

import com.kt.edu.common.utils.ResponseLogCd;

import java.util.List;

public class EduException extends BizException {
    private static final long serialVersionUID = 7026102025430486487L;

    public EduException(String code) {
        super(code, chkLogCd(code), null);
    }

    public EduException(String code, List<String> errMsgArr) {
        super(code, chkLogCd(code), errMsgArr);
    }

    public EduException(String code, List<String> errMsgArr, String chgTitle, String chgReason) {
        super(code, chkLogCd(code), errMsgArr, chgTitle, chgReason);
    }

    private static ResponseLogCd chkLogCd(String code) {
        String regex = "^.{4}[EI].*$";
        if(code.matches(regex) && code.charAt(4) == 'I') { // 안내성 메세지 코드 설정
            return ResponseLogCd.N;
        }else {	// 이외 모든 메세지 코드는 오류 코드
            return ResponseLogCd.Y;
        }
    }


}