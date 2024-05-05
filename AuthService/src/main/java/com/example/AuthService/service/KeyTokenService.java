package com.example.AuthService.service;

import com.example.AuthService.domain.dto.KeyPairDTO;
import com.example.AuthService.domain.dto.outDTO.BaseOutDTO;

public interface KeyTokenService {
    BaseOutDTO createNewKeyToken (KeyPairDTO keyPair, Long userId, String refreshToken );
    BaseOutDTO deleteAllKeyToken (Long userId );

}
