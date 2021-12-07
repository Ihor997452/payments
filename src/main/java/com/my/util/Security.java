package com.my.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.log4j.Logger;

public class Security {
    private static final int PIN_CODE_LENGTH = 4;
    private static final int PASSWORD_LENGTH = 12;
    private static final int CVV_LENGTH = 3;
    private static final int VERIFICATION_CODE_LENGTH = 8;
    private static final Logger log = Logger.getLogger(Security.class);

    private Security() {
    }

    public static boolean check(String expectedPassword, String actualPassword, String hexSalt) {
        byte[] salt = toByteArray(hexSalt);
        return expectedPassword.equals(encrypt(actualPassword, salt));
    }

    public static String encrypt(String password, byte[] salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt);
            byte[] bytes = messageDigest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();

            for(byte aByte : bytes) {
                stringBuilder.append(Integer.toString((aByte & 255) + 256, 16).substring(1));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            throw new IllegalStateException("No such algorithm", e);
        }
    }

    public static byte[] getSalt() {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[32];
            secureRandom.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            throw new IllegalStateException("No such algorithm", e);
        }
    }

    public static String toHexString(byte[] salt) {
        StringBuilder stringBuilder = new StringBuilder();

        for(byte aByte : salt) {
            stringBuilder.append(String.format("%02x", aByte));
        }

        return stringBuilder.toString();
    }

    public static byte[] toByteArray(String hexString) {
        byte[] bytes = new byte[hexString.length() / 2];

        for(int i = 0; i < bytes.length; ++i) {
            int index = i * 2;
            int value = Integer.parseInt(hexString.substring(index, index + 2), 16);
            bytes[i] = (byte)value;
        }

        return bytes;
    }

    public static String generateNewPassword() {
        char[] array = new char[PASSWORD_LENGTH];
        SecureRandom r = new SecureRandom();

        for(int i = 0; i < array.length; ++i) {
            array[i] = (char)(r.nextInt(26) + 97);
        }

        return new String(array);
    }

    public static int generatePinCode() {
        return randomNumberSequence(PIN_CODE_LENGTH);
    }

    public static int generateCvv() {
        return randomNumberSequence(CVV_LENGTH);
    }

    public static int generateVerificationCode() {
        return randomNumberSequence(VERIFICATION_CODE_LENGTH);
    }

    private static int randomNumberSequence(int len) {
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < len; ++i) {
            if (i == 0) {
                stringBuilder.append(random.nextInt(9) + 1);
                continue;
            }

            stringBuilder.append(random.nextInt(10));
        }

        return Integer.parseInt(stringBuilder.toString());
    }
}
