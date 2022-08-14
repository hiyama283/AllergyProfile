package com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.Header;

import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpHeader;

import java.util.Locale;

/**
 * HTTPのメソッドのリスト
 *
 * @since 1.0
 */
public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    TEAPOT("TEAPOT");

    private final String stringType;

    /**
     * コンストラクタ。情報を格納する
     *
     * @param stringType 文字列として表した値
     */
    HttpMethod(String stringType) {
        this.stringType = stringType;
    }

    @Override
    public String toString() {
        return this.stringType;
    }

    public static HttpMethod get(String string) {
        for (HttpMethod code : HttpMethod.values()) {
            if (code.toString().toLowerCase(Locale.ROOT).equals(string.toLowerCase(Locale.ROOT))) {
                return code;
            }
        }
        return HttpMethod.TEAPOT;
    }
}