package com.example.AuthService.service;

import com.example.AuthService.domain.dto.KeyPairDTO;
import com.example.AuthService.utils.Const;

public interface JWTService {
    String generateToken(String payload, KeyPairDTO keyPair, Const.TOKEN tokenEnum);
}
