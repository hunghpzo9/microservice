package com.example.AuthService.domain.dto.inDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRequestDTO {
    String requestId = UUID.randomUUID().toString();
    long timestamp = System.currentTimeMillis();
    Long userId;
}
