package com.example.AuthService.service.impl;

import com.example.AuthService.domain.dto.KeyPairDTO;
import com.example.AuthService.service.AsymmetricKeyService;
import com.example.AuthService.utils.Const;
import com.example.AuthService.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
@Slf4j
public class AsymmetricKeyServiceImpl implements AsymmetricKeyService {
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
            jwtPublicKey= DataUtils.removeHeaderKey(jwtPublicKey);
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
