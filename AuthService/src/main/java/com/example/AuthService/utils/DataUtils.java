package com.example.AuthService.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class DataUtils {
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty() || value.isBlank();
    }

    public static String removeHeaderKey(String key) {
        if (isNullOrEmpty(key)) return null;
        if (key.contains(Const.KEY_PAIR.PUBLIC_KEY_HEADER) && key.contains(Const.KEY_PAIR.PUBLIC_KEY_FOOTER)) {
            key = key
                    .replace(Const.KEY_PAIR.PUBLIC_KEY_HEADER, "")
                    .replace(Const.KEY_PAIR.PUBLIC_KEY_FOOTER, "")
                    .replace("\n", "")
                    .trim();
        } else if (key.contains(Const.KEY_PAIR.PRIVATE_KEY_HEADER) && key.contains(Const.KEY_PAIR.PRIVATE_KEY_FOOTER)) {
            key = key
                    .replace(Const.KEY_PAIR.PRIVATE_KEY_HEADER, "")
                    .replace(Const.KEY_PAIR.PRIVATE_KEY_FOOTER, "")
                    .replace("\n", "")
                    .trim();
        }
        key = key.replace("\r", "");
        return key;
    }
    public static String generateNonce(String identity, long timestamp){
        try {
            String data = identity + "-" + timestamp;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        }catch (Exception ex){
            return null;
        }
    }
    public static String generateMD5(String content){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(content.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        }catch (Exception ex){
            return null;
        }
    }

    public static String generateSignature(String secretKey, String stringToSign) {
        try {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);

        byte[] rawHmac = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(rawHmac);
        }catch (Exception ex){
            return null;
        }
    }


}
