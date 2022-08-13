package com.github.distriful5061.AllergyProfile.WebServer.Http.Connections;

/**
 * HTTPのメソッドのリスト
 *
 * @since 1.0
 */
public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

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
}