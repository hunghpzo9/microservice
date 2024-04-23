package com.example.AuthService.utils;

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


}
