package com.github.distriful5061.AllergyProfile.Json;

import java.util.Map;

import static com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpRequest.CRLF;

/**
 * Jsonを無理やり実装しようとしているクラス。Gson仕様に伴い廃止
 *
 * @since 1.0
 */
@Deprecated
public class Json {
    /**
     * Map型のリストを、1階層型のJsonに変換するメソッド
     *
     * @param map 対象のリスト(hashmapでも可)
     * @return 超単純なJson
     */
    public static String toJsonString(Map<String, String> map) {
        StringBuilder string = new StringBuilder("{" + CRLF);

        map.forEach((key, value) -> string.append("    %s: %s%s".formatted(key, value, CRLF)));

        string.append("}");
        return string.toString();
    }
}
