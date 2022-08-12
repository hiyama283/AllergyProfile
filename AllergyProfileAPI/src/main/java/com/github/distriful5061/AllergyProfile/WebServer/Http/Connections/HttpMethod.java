package com.github.distriful5061.AllergyProfile.WebServer.Http.Connections;

public enum HttpMethod {
    GET,
    HEAD,
    POST,
    OPTIONS,
    PUT,
    DELETE,
    UNSUPPORTED_METHOD;

    public static String toStringMethodName(HttpMethod httpMethod) {
        switch (httpMethod) {
            case GET -> {
                return "GET";
            }
            case HEAD -> {
                return "HEAD";
            }
            case POST -> {
                return "POST";
            }
            case OPTIONS -> {
                return "OPTIONS";
            }
            case PUT -> {
                return "PUT";
            }
            case DELETE -> {
                return "DELETE";
            }
        }

        return "UNSUPPORTED_METHOD";
    }

    public static HttpMethod toEnumHttpMethod(String method) {
        switch (method.toUpperCase()) {
            case "GET" -> {
                return HttpMethod.GET;
            }
            case "HEAD" -> {
                return HttpMethod.HEAD;
            }
            case "POST" -> {
                return HttpMethod.POST;
            }
            case "OPTIONS" -> {
                return HttpMethod.OPTIONS;
            }
            case "PUT" -> {
                return HttpMethod.PUT;
            }
            case "DELETE" -> {
                return HttpMethod.DELETE;
            }
        }

        return HttpMethod.UNSUPPORTED_METHOD;
    }
}
