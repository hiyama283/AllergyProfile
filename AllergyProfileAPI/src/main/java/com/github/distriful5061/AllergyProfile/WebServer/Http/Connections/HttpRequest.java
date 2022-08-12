package com.github.distriful5061.AllergyProfile.WebServer.Http.Connections;

import com.github.distriful5061.AllergyProfile.WebServer.Http.HttpStatusCode;
import com.github.distriful5061.AllergyProfile.WebServer.Http.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    public static final String CRLF = "\r\n";
    private Socket inSocket;
    private HttpHeader httpHeader = null;
    private String body = null;
    private final Map<String, String> headerMap = new HashMap<>();

    public HttpRequest(Socket socket) {
        try (InputStream inputStream = socket.getInputStream()) {
            httpHeader = new HttpHeader(readHeader(inputStream));
            body = readBody(inputStream);
        } catch (IOException ignored) {}
        inSocket = socket;

        for (String str : httpHeader.getHeader().split("\r\n")) {
            String[] splitString = str.split(":");
            headerMap.put(splitString[0].trim(), splitString[1].trim());
        }
    }

    @Override
    public String toString() {
        return httpHeader.toString() + body;
    }

    public HttpHeader getHeader() {
        return httpHeader;
    }

    public String getBody() {
        return body;
    }

    public Socket getInSocket() {
        return inSocket;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public String readHeader(InputStream in) throws IOException {
        String line = IOUtil.readLine(in);
        StringBuilder header = new StringBuilder();

        while (line != null && !line.isEmpty()) {
            header.append(line).append(CRLF);
            line = IOUtil.readLine(in);
        }

        return header.toString();
    }

    public String readBody(InputStream in) throws IOException {
        String result;
        if (httpHeader.isChunkedTransfer()) {
            result = readBodyByChunkedTransfer(in);
        } else {
            result = readBodyByContentLength(in);
        }

        return result;
    }

    public String readBodyByContentLength(InputStream in) throws IOException {
        final int contentLength = httpHeader.getContentLength();

        if (contentLength <= 0) {
            return null;
        }

        byte[] buffer = new byte[contentLength];
        in.read(buffer);

        return IOUtil.toString(buffer);
    }

    public String readBodyByChunkedTransfer(InputStream in) throws IOException {
        StringBuilder body = new StringBuilder();

        int chunkSize = Integer.parseInt(IOUtil.readLine(in), 16);

        while (chunkSize != 0) {
            byte[] buffer = new byte[chunkSize];
            in.read(buffer);

            body.append(IOUtil.toString(buffer));

            IOUtil.readLine(in); // chunk-body の末尾にある CRLF を読み飛ばす
            chunkSize = Integer.parseInt(IOUtil.readLine(in), 16);
        }

        return body.toString();
    }

}
