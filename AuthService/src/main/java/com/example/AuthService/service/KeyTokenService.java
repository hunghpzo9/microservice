package com.example.AuthService.service;

import com.example.AuthService.domain.dto.KeyPairDTO;
import com.example.AuthService.domain.dto.outDTO.BaseOutDTO;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeyTokenService {
    BaseOutDTO createNewKeyToken (KeyPairDTO keyPair, Long userId,String refreshToken );
    KeyPairDTO getKeyPair();
    PublicKey generateJwtKeyDecryption(String jwtPublicKey);
    PrivateKey generateJwtKeyEncryption(String jwtPrivateKey);

}
