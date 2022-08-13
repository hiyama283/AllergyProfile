package com.github.distriful5061.AllergyProfile.Json;

import java.util.Map;

import static com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpRequest.CRLF;

public class Json {
    public static String toJsonString(Map<String, String> map) {
        StringBuilder string = new StringBuilder("{" + CRLF);

        map.forEach((key, value) -> {
            string.append("    %s: %s%s".formatted(key, value, CRLF));
        });

        string.append("}");
        return string.toString();
    }
}
