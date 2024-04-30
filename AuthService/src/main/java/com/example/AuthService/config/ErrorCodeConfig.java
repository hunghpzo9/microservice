package com.example.AuthService.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "error-codes")
public class ErrorCodeConfig {
    private  Map<String, Integer> errorCodes = new HashMap<>();

    public Map<String, Integer> getCodes() {
        return errorCodes;
    }
    public void setErrorCodes(Map<String, Integer> errorCodes) {
        this.errorCodes = errorCodes;
    }
}