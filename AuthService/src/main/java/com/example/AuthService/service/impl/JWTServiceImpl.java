package com.example.AuthService.service.impl;

import com.example.AuthService.domain.dto.KeyPairDTO;
import com.example.AuthService.domain.dto.UserDTO;
import com.example.AuthService.service.AsymmetricKeyService;
import com.example.AuthService.service.JWTService;
import com.example.AuthService.service.KeyTokenService;
import com.example.AuthService.utils.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.Serializable;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.Date;

@Service
@Slf4j
public class JWTServiceImpl implements JWTService {
    @Autowired
    AsymmetricKeyService asymmetricKeyService;
    public static final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 60 * 1L;
    public static final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24L;

    @Override
    public String generateToken(String payload, KeyPairDTO keyPair, Const.TOKEN tokenEnum) {
        String token = null;
        try {
            Date expireDate = new Date(System.currentTimeMillis() + (tokenEnum == Const.TOKEN.ACCESS_TOKEN ? ACCESS_TOKEN_VALIDITY : REFRESH_TOKEN_VALIDITY));
            token = Jwts.builder()
                    .setSubject(payload)
                    .setIssuedAt(new Date())
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.RS256, asymmetricKeyService.generateJwtKeyEncryption(keyPair.getPrivateKey())).compact();

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return token;
    }

    @Override
    public UserDTO verifyJWT(String token, String publicKey) {
        UserDTO userDTO = null;
        try {
            var claims = Jwts.parser().
                    setSigningKey(asymmetricKeyService.generateJwtKeyDecryption(publicKey))
                    .parseClaimsJws(token)
                    .getBody();
            if (claims.getExpiration().compareTo(new Date()) >= 0) {
                userDTO = new UserDTO();
                userDTO.setId(Long.valueOf(claims.getSubject()));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return userDTO;
    }


}
