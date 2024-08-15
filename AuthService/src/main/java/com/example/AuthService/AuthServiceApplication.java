package com.example.AuthService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.xerial.snappy.Snappy;

import java.util.Date;

@SpringBootApplication
@EntityScan(basePackages = "com.example.AuthService.domain")

public class AuthServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
