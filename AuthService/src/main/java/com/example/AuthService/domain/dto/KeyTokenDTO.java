package com.example.AuthService.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyTokenDTO {
    private Long id;
    private Long userId;
    private String publicKey;
    private String refreshToken;
    private String status;
}
