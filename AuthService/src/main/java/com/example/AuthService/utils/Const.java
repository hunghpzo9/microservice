package com.example.AuthService.utils;

import java.text.SimpleDateFormat;

public class Const {

    public static class RESPONSE_CODE {
        public static final String SUCCESS = "000";
        public static final String CREATED = "001";
        public static final String DATA_INVALID = "003";
        public static final String ERROR = "004";
        public static final String DATA_NOT_FOUND = "005";
        public static final String EMAIL_EXISTED = "006";
        public static final String EMAIL_INVALID = "007";
        public static final String PASSWORD_WEAK = "008";

    }

    public static class RESPONSE_MESSAGE {
        public static final String SUCCESS = "Success";
        public static final String SIGN_UP_SUCCESS = "SIGN_UP_SUCCESS";
        public static final String DATA_INVALID = "Data invalid";
        public static final String ERROR = "Server error";
        public static final String DATA_NOT_FOUND = "DATA_NOT_FOUND";
        public static final String EMAIL_EXISTED = "EMAIL_EXISTED";
        public static final String EMAIL_INVALID = "EMAIL_INVALID";
        public static final String PASSWORD_WEAK = "PASSWORD_WEAK";

    }

    public static class KEY_PAIR {
        public static final String PUBLIC_KEY = "PUBLIC KEY";
        public static final String PRIVATE_KEY = "PRIVATE KEY";
        public static final String PUBLIC_KEY_HEADER = "-----BEGIN PUBLIC KEY-----";
        public static final String PUBLIC_KEY_FOOTER = "-----END PUBLIC KEY-----";
        public static final String PRIVATE_KEY_HEADER = "-----BEGIN PRIVATE KEY-----";
        public static final String PRIVATE_KEY_FOOTER = "-----END PRIVATE KEY-----";
    }
    public enum PRODUCT_FIELD {
        id,
        name,
        rating,
        category_id,
    }

    public enum Status {
        OK,
        ACTIVE,
        INACTIVE
    }
    public enum TOKEN {
        ACCESS_TOKEN,
        REFRESH_TOKEN,
    }


    public enum Detail {
        BASIC,
        FULL,
        basic,
        full
    }

    public enum ORDER_BY_SQL {
        ASC,
        DESC,
        asc,
        desc
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty() || value.isBlank();
    }

    public static boolean isValidDateFormat(String dateString, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false); // This will make the validation strict
        try {
            sdf.parse(dateString);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
