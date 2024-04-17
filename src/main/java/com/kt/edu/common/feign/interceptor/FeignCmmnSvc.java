package com.kt.edu.common.feign.interceptor;

import com.kt.edu.common.payload.CommonHeader;
import feign.RequestTemplate;

public interface FeignCmmnSvc {

    public void apply(CommonHeader commonHeader, RequestTemplate requestTemplate);

}