package com.kt.edu.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@RefreshScope
public class PropertyUtil {

    @Value("${app-info.node-ip:}")
    public String nodeIp;


}