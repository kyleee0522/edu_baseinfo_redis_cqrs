package com.kt.edu.common.utils;

public enum ResponseLogCd {
    Y("Y"),
    N("N");

    String responseLogCd;

    ResponseLogCd(String responseLogCd) {
        this.responseLogCd = responseLogCd;
    }

    public String getErrorInfo(){
        return responseLogCd;
    }
}
