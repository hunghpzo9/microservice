package com.example.AuthService.utils;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Const {

    public static class RESPONSE_CODE {
        public static final String SUCCESS = "000";
        public static final String AUTHENTICATE_FAIL = "001";
        public static final String AUTHORIZE_FAIL = "002";

        public static final String CREATED = "003";

        public static final String DATA_INVALID = "004";
        public static final String ERROR = "005";
        public static final String DATA_NOT_FOUND = "006";
        public static final String EMAIL_EXISTED = "007";
        public static final String EMAIL_INVALID = "008";
        public static final String PASSWORD_WEAK = "009";

    }

    public static class RESPONSE_MESSAGE {
        public static final String SUCCESS = "Success";
        public static final String AUTHENTICATE_FAIL = "AUTHENTICATE_FAIL";
        public static final String PASSWORD_INCORRECT = "PASSWORD_INCORRECT";

        public static final String AUTHORIZE_FAIL = "AUTHORIZE_FAIL";

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
    public static class API_HEADER {
        public static final String API_KEY = "x-api-key";
        public static final String CLIENT_ID = "x-client-id";

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
    public static boolean isPasswordStrong(String password, String userName, String email) {
        if (!Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
                .matcher(password)
                .find()) {
            return false;
        }
        if (password.equals(userName) || password.equals(email)) {
            return false;
        }
        //Also can check password equal domain, appname,
        return true;
    }


}
