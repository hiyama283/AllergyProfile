package com.github.distriful5061.AllergyProfile.Utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PassWordUtils implements BaseUtils {
    @Override
    public int hashCode() {
        return 1234617091;
    }

    public static String getHash(String content, String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] cipher_byte = messageDigest.digest(content.getBytes());

            return String.format("%040x", new BigInteger(1, cipher_byte));
        } catch (NoSuchAlgorithmException ignored) {
            return null;
        }
    }

    public static String getSHA256Hash(String content) {
        return getHash(content, "SHA-256");
    }
}
