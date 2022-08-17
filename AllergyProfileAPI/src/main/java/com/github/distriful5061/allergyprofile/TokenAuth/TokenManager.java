package com.github.distriful5061.AllergyProfile.TokenAuth;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class TokenManager {
    public static byte[] b64Encode(byte[] src) {
        return Base64.getEncoder().encode(src);
    }

    public static byte[] b64Decode(byte[] src) {
        return Base64.getDecoder().decode(src);
    }

    public static byte[] andAllBit(byte[] src) {
        byte[] result = new byte[src.length];
        for (int i = 0; i < src.length; i++) {
            result[i] = (byte) (src[i] & 0xff);
        }
        return result;
    }

    private static final String algorithm = "HmacSHA25";
    public static byte[] getHMACHash(byte[] plainText, byte[] secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKeySpec);

        byte[] mac_bytes = mac.doFinal(plainText);
        return andAllBit(mac_bytes);
    }


}
