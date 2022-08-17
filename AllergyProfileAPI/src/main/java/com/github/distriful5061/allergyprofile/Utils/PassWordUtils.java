package com.github.distriful5061.AllergyProfile.Utils;

import com.github.distriful5061.cryptlibrary.NewPassWord;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * パスワードを扱う際に必要となるメソッドをカプセル化したクラス。
 *
 * @since 1.0
 */
public class PassWordUtils implements BaseUtils {
    @Override
    public int hashCode() {
        return 1234617091;
    }

    /**
     * よくわからんけどシード値とかを生成してくれます
     *
     * @return シード値
     */
    public static long getSeed() {
        return new NewPassWord().generateMultipliedRandomLong(5, System.nanoTime());
    }

    /**
     * ハッシュ値を求めるメソッド。内容とハッシュアルゴリズム名が必要となる
     *
     * @param content ハッシュに通す文字列
     * @param algorithm 使用するアルゴリズム
     * @param scrambleContent 元の文字列を、initVecに基づいた乱数でランダムにかき混ぜるか
     * @param initVec scrambleContentに使用するランダムのシード値
     * @return ハッシュ化された文字列
     */
    public static String getHash(String content, HashEnum algorithm, boolean scrambleContent, long initVec) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm.toString());

            if (scrambleContent) {
                NewPassWord newPassWord = new NewPassWord();
                content = new String(newPassWord.passwordPadding(content.toCharArray(), initVec, 16, 128));
            }

            byte[] cipher_byte = messageDigest.digest(content.getBytes());

            return String.format(algorithm.getStringFormat(), new BigInteger(1, cipher_byte));
        } catch (NoSuchAlgorithmException ignored) {
            return null;
        }
    }

    /**
     * SHA-256のハッシュを求めるメソッド。
     *
     * @param content ハッシュに通す文字列
     * @param useScramble 元の文字列をかき混ぜるかどうか
     * @param initVec かき混ぜる際に使用するランダムのシード値
     * @return ハッシュ化された文字列
     */
    public static String getSHA256Hash(String content, boolean useScramble, long initVec) {
        return getHash(content, HashEnum.SHA_256, useScramble, initVec);
    }

    /**
     * SHA-256のハッシュを求めるメソッド。
     *
     * @param content ハッシュに通す文字列
     * @return ハッシュ化された文字列
     */
    public static String getSHA256Hash(String content) {
        return getHash(content, HashEnum.SHA_256, false, 1L);
    }
}
