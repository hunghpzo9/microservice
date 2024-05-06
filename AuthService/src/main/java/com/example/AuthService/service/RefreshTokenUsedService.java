package com.example.AuthService.service;

import com.example.AuthService.domain.dto.KeyPairDTO;
import com.example.AuthService.domain.dto.outDTO.BaseOutDTO;

public interface RefreshTokenUsedService {
    BaseOutDTO addNewUsedToken (Long userId, String refreshToken);
    BaseOutDTO deleteAllUsedToken (Long userId);

}
