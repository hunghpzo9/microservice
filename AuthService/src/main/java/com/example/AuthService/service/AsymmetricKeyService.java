package com.example.AuthService.service;

import com.example.AuthService.domain.dto.KeyPairDTO;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface AsymmetricKeyService {
    KeyPairDTO getKeyPair();
    PublicKey generateJwtKeyDecryption(String jwtPublicKey);
    PrivateKey generateJwtKeyEncryption(String jwtPrivateKey);
}
