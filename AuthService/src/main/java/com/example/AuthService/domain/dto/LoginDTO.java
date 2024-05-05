package com.example.AuthService.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO {
    private Long userId;
    private String userName;
    private String email;
    private String password;
    private String accessToken;
    private String refreshToken;
}
