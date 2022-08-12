package com.github.distriful5061.AllergyProfile.WebServer.Http.Connections;

import java.util.stream.Stream;

import static com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpRequest.CRLF;

public class HttpHeader {
    private String startLine;
    private String header;
    private HttpMethod method;
    private String path;

    public HttpHeader(String header) {
        String[] splitHeader = header.split("\r\n");

        this.startLine = splitHeader[0];

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < splitHeader.length; i++) {
            stringBuilder.append(splitHeader[i]).append("\r\n");
        }

        this.header = stringBuilder.toString();

        String[] splitStartLine = startLine.split(" ");
        this.method = HttpMethod.valueOf(splitStartLine[0].toUpperCase());
        this.path = splitStartLine[1];
    }

    public String getStartLine() {
        return startLine;
    }

    public String getHeader() {
        return header;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return header;
    }

    public int getContentLength() {
        return Stream.of(header.split(CRLF))
                .filter(headerLine -> headerLine.startsWith("Content-Length"))
                .map(contentLengthHeader -> contentLengthHeader.split(":")[1].trim())
                .mapToInt(Integer::parseInt)
                .findFirst().orElse(0);
    }

    public boolean isChunkedTransfer() {
        return Stream.of(header.split(CRLF))
                .filter(headerLine -> headerLine.startsWith("Transfer-Encoding"))
                .map(transferEncoding -> transferEncoding.split(":")[1].trim())
                .anyMatch("chunked"::equals);
    }
}
