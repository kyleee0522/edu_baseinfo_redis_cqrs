package com.kt.edu.common.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.kt.edu.common.filter.RequestTransFormFilter;
import com.kt.edu.common.filter.ResponseBodyTransformFilter;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class WebFilterConfig {

    /*@Bean
    public FilterRegistrationBean<RequestTransFormFilter> filterRegistrationBean() {
        FilterRegistrationBean<RequestTransFormFilter> filterRegistrationBean = new FilterRegistrationBean<RequestTransFormFilter>();
        filterRegistrationBean.setFilter(new RequestTransFormFilter());
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<ResponseBodyTransformFilter> transformFilter() {
        FilterRegistrationBean<ResponseBodyTransformFilter> responseEncodingFilterBean = new FilterRegistrationBean<>();
        responseEncodingFilterBean.setFilter(new ResponseBodyTransformFilter());
        return responseEncodingFilterBean;
    }*/

    /*@Bean
    public Filter xssFilter() {
        return new XssFilter();
    }*/

    /*@Bean
    public MappingJackson2HttpMessageConverter converter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        CustomObjectMapper mapper = new CustomObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        converter.setObjectMapper(mapper);
        return converter;
    }*/
}