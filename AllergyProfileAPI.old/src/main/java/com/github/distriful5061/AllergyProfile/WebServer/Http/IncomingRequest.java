package com.github.distriful5061.AllergyProfile.WebServer.Http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.stream.Stream;

@Deprecated
public class IncomingRequest {
    public static final String CRLF = "\r\n";
    private final Socket inSocket;
    private final String header;
    private final String body;

    public IncomingRequest(Socket socket) {
        String[] parsedSocketResult = loadHeaderAndBody(socket);
        header = parsedSocketResult[0];
        body = parsedSocketResult[1];
        inSocket = socket;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public Socket getInSocket() {
        return inSocket;
    }

    public static String[] loadHeaderAndBody(Socket socket) {
        String[] result = new String[2];

        try (
                InputStream inputStream = socket.getInputStream();
        )
        {
            result[0] = readHeader(inputStream);
            result[1] = readBody(inputStream, result[0]);

        } catch (IOException ignored) {}

        return result;
    }

    public static String readHeader(InputStream in) throws IOException {
        String line = IOUtil.readLine(in);
        StringBuilder header = new StringBuilder();

        while (line != null && !line.isEmpty()) {
            header.append(line).append(CRLF);
            line = IOUtil.readLine(in);
        }

        return header.toString();
    }

    public static String readBody(InputStream in, String headerText) throws IOException {
        String result;
        if (isChunkedTransfer(headerText)) {
            result = readBodyByChunkedTransfer(in);
        } else {
            result = readBodyByContentLength(in, headerText);
        }

        return result;
    }

    public static String readBodyByChunkedTransfer(InputStream in) throws IOException {
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

    public static String readBodyByContentLength(InputStream in, String headerText) throws IOException {
        final int contentLength = getContentLength(headerText);

        if (contentLength <= 0) {
            return null;
        }

        byte[] buffer = new byte[contentLength];
        in.read(buffer);

        return IOUtil.toString(buffer);
    }

    public static int getContentLength(String headerText) {
        return Stream.of(headerText.split(CRLF))
                .filter(headerLine -> headerLine.startsWith("Content-Length"))
                .map(contentLengthHeader -> contentLengthHeader.split(":")[1].trim())
                .mapToInt(Integer::parseInt)
                .findFirst().orElse(0);
    }

    public static boolean isChunkedTransfer(String headerText) {
        return Stream.of(headerText.split(CRLF))
                .filter(headerLine -> headerLine.startsWith("Transfer-Encoding"))
                .map(transferEncoding -> transferEncoding.split(":")[1].trim())
                .anyMatch("chunked"::equals);
    }

}
