package com.github.distriful5061.AllergyProfile.WebServer.Http;

public enum HttpContentType {
    TEXT_PLAIN("text/plain", "txt"),
    TEXT_HTML("text/html", "html,htm"),
    TEXT_CSS("text/css", "css"),
    TEXT_XML("text/xml", "xml"),
    APPLICATION_JAVASCRIPT("application/javascript", "js"),
    APPLICATION_JSON("application/json", "json"),
    IMAGE_JPEG("image/jpeg", "jpg,jpeg"),
    IMAGE_PNG("image/png", "png"),
    IMAGE_GIF("image/gif", "gif");

    private final String fullName;
    private final String shortName;

    HttpContentType(String fullName, String shortName) {
        this.fullName = fullName;
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return this.fullName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public static HttpContentType getByFullName(String aFullName) {
        for (HttpContentType httpContentType : HttpContentType.values()) {
            if (httpContentType.fullName.equals(aFullName.toLowerCase())) return httpContentType;
        }

        return TEXT_PLAIN;
    }

    public static HttpContentType getByShortName(String aShortName) {
        for (HttpContentType httpContentType : HttpContentType.values()) {
            for (String shortName1 : httpContentType.shortName.split(",")) {
                if (shortName1.equals(aShortName.toLowerCase())) return httpContentType;
            }
        }

        return TEXT_PLAIN;
    }
}
