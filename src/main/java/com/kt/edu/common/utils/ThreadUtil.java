package com.kt.edu.common.utils;

import com.kt.edu.common.payload.CommonHeader;

public class ThreadUtil {

    public static ThreadLocal<CommonHeader> threadLocalCommonHeader = new ThreadLocal<CommonHeader>();
}