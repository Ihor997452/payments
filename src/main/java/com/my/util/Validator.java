package com.my.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private Validator() {
    }

    public static boolean validateEmail(String email) {
        Matcher matcher = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$").matcher(email);
        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        return password.length() > 6;
    }

    public static boolean validatePassword(String password, String passwordToConfirm) {
        if (!password.equals(passwordToConfirm)) {
            return false;
        } else {
            return password.length() > 6;
        }
    }
}
