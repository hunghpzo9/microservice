package com.example.AuthService.utils;

import java.util.Random;

public class NonceUtil {
    private static final String SECRET_KEY = "nonce-secret-key"; //

    public static String generateNonce(String sessionId) {
        try {
            String dataToEncrypt =  sessionId +"-"+ generateRandomNumber();
            return DataUtils.encrypt(SECRET_KEY, dataToEncrypt,"AES");
        } catch (Exception e) {
            throw new RuntimeException("Error generating nonce", e);
        }
    }

    public static String decryptNonce(String nonce) {
        try {
            return DataUtils.decrypt(SECRET_KEY, nonce,"AES");
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting nonce", e);
        }
    }
    public static int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(5);
    }

}