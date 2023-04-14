package com.example.point.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class PointServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PointServiceApplication.class, args);
    }
}
