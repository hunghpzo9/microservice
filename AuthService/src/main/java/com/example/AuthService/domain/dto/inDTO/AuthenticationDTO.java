package com.example.AuthService.domain.dto.inDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDTO extends BaseRequestDTO {
    String accessToken;
    String refreshToken;
    String content;
    String nonce;
    String requestUri;
    String signature;
    String apiTimeStamp;
}
