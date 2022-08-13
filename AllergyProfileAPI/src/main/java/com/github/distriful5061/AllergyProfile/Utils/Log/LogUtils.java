package com.github.distriful5061.AllergyProfile.Utils.Log;

import com.github.distriful5061.AllergyProfile.Utils.BaseUtils;

/**
 * ファイルとしてログを出力するために、仲介として様々なメソッドを提供するクラス
 *
 * @since 1.1
 */
public class LogUtils implements BaseUtils {
    public static void println(Object object, LogLevel logLevel) {
        System.out.printf("[%s] %s%n", logLevel.toString(), object.toString());
    }

    public static void print(Object object, LogLevel logLevel) {
        System.out.printf("[%s] %s", logLevel.toString(), object.toString());
    }

    public static void println(Object object) {
        println(object, LogLevel.INFO);
    }

    public static void print(Object object) {
        print(object, LogLevel.INFO);
    }
}
