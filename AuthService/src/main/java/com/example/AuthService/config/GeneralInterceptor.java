package com.example.AuthService.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@Slf4j
public class GeneralInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getHeader("X-Forwarded-For") != null){
            String clientIpAddress = request.getHeader("X-Forwarded-For");
            int clientPort = Integer.parseInt(request.getHeader("X-Forwarded-Port"));
            log.info("AUTH SERVICE |Client IP Address: " + clientIpAddress);
            log.info("AUTH SERVICE |Client IP port: " + clientPort);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private String getRemoteAddr(HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }
}
