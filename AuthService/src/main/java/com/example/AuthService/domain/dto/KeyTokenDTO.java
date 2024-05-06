package com.example.AuthService.domain.dto;

import com.example.AuthService.domain.RefreshTokenUsed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyTokenDTO {
    private Long id;
    private Long userId;
    private String publicKey;
    private String privateKey;
    private String refreshToken;
    private String status;
    private Date createDate;
}
