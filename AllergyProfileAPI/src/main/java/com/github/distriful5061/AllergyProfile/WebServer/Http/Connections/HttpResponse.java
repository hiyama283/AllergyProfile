package com.github.distriful5061.AllergyProfile.WebServer.Http.Connections;

import com.github.distriful5061.AllergyProfile.WebServer.Http.ContentType;
import com.github.distriful5061.AllergyProfile.WebServer.Http.IOUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpResponse {
    private int statusCode;
    private Map<String, String> headers = new HashMap<>();
    private String body;
    private File bodyFile;

    public HttpResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    public void addHeader(String key, Object value) {
        headers.put(key, value.toString());
    }

    public void addHeader(String[] keys, Object[] values) {
        if (keys.length != values.length) return;

        for (int i = 0; i < keys.length; i++) {
            headers.put(keys[i], values[i].toString());
        }
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

    public void write(OutputStream outputStream) {
        IOUtil.println(outputStream, "HTTP/1.1"  + statusCode);

        headers.forEach((key, value) -> {
            IOUtil.println(outputStream, "%s:%s".formatted(key, value));
        });

        if (this.body != null) {
            IOUtil.println(outputStream, "");
            IOUtil.println(outputStream, body);
        } else if (this.bodyFile != null) {
            IOUtil.println(outputStream, "");
            try {
                Files.copy(bodyFile.toPath(), outputStream);
            } catch (IOException ignored) {}
        }
    }
}
