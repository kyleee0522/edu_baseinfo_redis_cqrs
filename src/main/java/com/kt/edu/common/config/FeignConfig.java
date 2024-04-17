package com.kt.edu.common.config;

import com.kt.edu.common.feign.decoder.ResponseDecoder;
import com.kt.edu.common.feign.decoder.ResponseErrorDecoder;
import com.kt.edu.common.feign.interceptor.CustomRequestInterceptor;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    @ConditionalOnProperty(prefix = "feign", name = "flag", havingValue = "true")
    public RequestInterceptor requestInterceptor() {
        return new CustomRequestInterceptor();
    }

    @Bean
    @ConditionalOnProperty(prefix = "feign", name = "flag", havingValue = "true")
    public Decoder decoder() {
        return new ResponseDecoder();
    }

    @Bean
    @ConditionalOnProperty(prefix = "feign", name = "flag", havingValue = "true")
    public ErrorDecoder errorDecoder() {
        return new ResponseErrorDecoder();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "feign", name = "flag", havingValue = "true")
    public Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }

}