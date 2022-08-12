package com.github.distriful5061.AllergyProfile.WebServer.Http.Connections;

import com.github.distriful5061.AllergyProfile.WebServer.Http.ContentType;
import com.github.distriful5061.AllergyProfile.WebServer.Http.HttpStatusCode;
import com.github.distriful5061.AllergyProfile.WebServer.Http.IOUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpResponse {
    private final Map<String, String> headers = new HashMap<>();
    private HttpStatusCode statusCode;
    private String body;
    private File bodyFile;

    public static Map<String, Object> getDefaultHeader() {
        Map<String, Object> result = new HashMap<>();

        result.put("Cache-Control", "public, max-age=21600");

        return result;
    }

    public HttpResponse() {
        this.statusCode = HttpStatusCode.OK;
    }

    public HttpResponse(HttpStatusCode httpStatusCode) {
        this.statusCode = httpStatusCode;
    }

    public void addHeader(String key, Object value) {
        headers.put(key.toLowerCase(), value.toString());
    }

    public void addHeader(Map<String, Object> mapObject) {
        mapObject.forEach((key, value) -> {
            headers.put(key.toLowerCase(), value.toString());
        });
    }

    public void clearHeader() {
        headers.clear();
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setBody(File file) {
        Objects.requireNonNull(file);
        this.bodyFile = file;

        String fileName = this.bodyFile.getName();
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1);

        this.addHeader("Content-Type", ContentType.toContentType(extension));
    }

    public HttpStatusCode getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCodeNumber) {
        this.statusCode = HttpStatusCode.getByStatusCode(statusCodeNumber);
    }

    public void setStatusCode(HttpStatusCode httpStatusCode) {
        this.statusCode = httpStatusCode;
    }

    public void write(OutputStream outputStream) throws IOException {
        IOUtil.println(outputStream, "HTTP/1.1 " + statusCode.toString());

        headers.forEach((key, value) -> IOUtil.println(outputStream, "%s:%s".formatted(key, value)));

        if (this.body != null) {
            IOUtil.println(outputStream, "");
            IOUtil.println(outputStream, body);
        } else if (this.bodyFile != null) {
            IOUtil.println(outputStream, "");
            Files.copy(bodyFile.toPath(), outputStream);
        }
    }
}
