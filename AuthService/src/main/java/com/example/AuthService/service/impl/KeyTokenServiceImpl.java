package com.example.AuthService.service.impl;

import com.example.AuthService.domain.KeyToken;
import com.example.AuthService.domain.dto.KeyPairDTO;
import com.example.AuthService.domain.dto.KeyTokenDTO;
import com.example.AuthService.domain.dto.outDTO.BaseOutDTO;
import com.example.AuthService.repository.KeyTokenRepository;
import com.example.AuthService.service.KeyTokenService;
import com.example.AuthService.utils.Const;
import com.example.AuthService.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
@Slf4j
public class KeyTokenServiceImpl implements KeyTokenService {
    @Autowired
    private KeyTokenRepository keyTokenRepository;

    @Override
    public BaseOutDTO createNewKeyToken(KeyPairDTO keyPair, Long userId,String refreshToken) {
        BaseOutDTO outDTO = new BaseOutDTO();
        try {
            if (userId == null) {
                log.info("createNewKeyToken|userId is null or empty");
                outDTO.setCode(Const.RESPONSE_CODE.DATA_INVALID);
                outDTO.setMessage(Const.RESPONSE_MESSAGE.DATA_INVALID);
                return outDTO;
            }
            if (keyPair == null ||
                    DataUtils.isNullOrEmpty(keyPair.getPublicKey()) ||
                    DataUtils.isNullOrEmpty(keyPair.getPrivateKey())) {
                log.info("createNewKeyToken|KeyPair is null");
                outDTO.setCode(Const.RESPONSE_CODE.DATA_INVALID);
                outDTO.setMessage(Const.RESPONSE_MESSAGE.DATA_INVALID);
                return outDTO;
            }

            KeyTokenDTO keyTokenDTO = new KeyTokenDTO();
            KeyToken keyToken = keyTokenRepository.findByUserId(userId);
            if(keyToken != null){
                keyTokenDTO.setId(keyToken.getId());
                keyTokenDTO.setCreateDate(keyToken.getCreateDate());
            }
            keyTokenDTO.setUserId(userId);
            keyTokenDTO.setPublicKey(keyPair.getPublicKey());
            keyTokenDTO.setPrivateKey(keyPair.getPrivateKey());
            keyTokenDTO.setRefreshToken(refreshToken);
            keyTokenDTO.setStatus(Const.Status.ACTIVE.name());
            keyTokenRepository.save(new KeyToken(keyTokenDTO));

            outDTO.setCode(Const.RESPONSE_CODE.SUCCESS);
            outDTO.setMessage(Const.RESPONSE_MESSAGE.SUCCESS);

        } catch (Exception e) {
            outDTO.setCode(Const.RESPONSE_CODE.ERROR);
            outDTO.setMessage(Const.RESPONSE_MESSAGE.ERROR);
            return outDTO;
        }
        return outDTO;
    }

    @Override
    public KeyPairDTO getKeyPair() {
        KeyPairDTO keyPairDTO = null;
        try {
            Date startDate = new Date();
            Security.addProvider(new BouncyCastleProvider());
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
            keyPairGenerator.initialize(2048);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            StringWriter publicStringWriter = new StringWriter();
            StringWriter privateStringWriter = new StringWriter();
            PemWriter publicPemWriter = new PemWriter(publicStringWriter);
            PemWriter privatePemWriter = new PemWriter(privateStringWriter);

            PemObject publicKeyPemObject = new PemObject(Const.KEY_PAIR.PUBLIC_KEY, publicKey.getEncoded());
            PemObject privateKeyPemObject = new PemObject(Const.KEY_PAIR.PRIVATE_KEY, privateKey.getEncoded());

            publicPemWriter.writeObject(publicKeyPemObject);
            publicPemWriter.close();
            privatePemWriter.writeObject(privateKeyPemObject);
            privatePemWriter.close();

            keyPairDTO = new KeyPairDTO();
            keyPairDTO.setPublicKey(publicStringWriter.toString());
            keyPairDTO.setPrivateKey(privateStringWriter.toString());


            log.info("getKeyPair| duration:" + (new Date().getTime() - startDate.getTime()));

        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return keyPairDTO;
    }
    @Override
    public PublicKey generateJwtKeyDecryption(String jwtPublicKey) {
        try {
            jwtPublicKey=DataUtils.removeHeaderKey(jwtPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] keyBytes = Base64.getDecoder().decode(jwtPublicKey);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            return keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }
    @Override
    public PrivateKey generateJwtKeyEncryption(String jwtPrivateKey) {
        try {
            jwtPrivateKey=DataUtils.removeHeaderKey(jwtPrivateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] keyBytes = Base64.getDecoder().decode(jwtPrivateKey);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }
}
