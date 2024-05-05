package com.example.AuthService.domain.dto.inDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInDTO {
    private String email;
    private String password;
    private Long refreshToken;
}
