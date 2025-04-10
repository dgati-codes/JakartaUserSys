package com.tech11.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    public static String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public static boolean validatePassword(String inputPassword, String storedEncryptedPassword) throws NoSuchAlgorithmException {
        String encryptedInput = encryptPassword(inputPassword);
        return encryptedInput.equals(storedEncryptedPassword);
    }

}
