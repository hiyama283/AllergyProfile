package com.github.distriful5061.AllergyProfile.WebServer.Http.Connections;

import com.github.distriful5061.AllergyProfile.WebServer.Http.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Httpのリクエストのヘッダ/ボディーを格納するクラス
 *
 * @since 1.0
 */
public class HttpRequest {
    /**
     * \rと\nを組み合わせたやつ。HTTP/1.1ではこれが改行になる
     */
    public static final String CRLF = "\r\n";
    private final HttpHeader httpHeader;
    private final String body;
    private final Map<String, String> headerMap = new HashMap<>();

    public HttpRequest(InputStream inputStream) throws IOException {
        httpHeader = new HttpHeader(readHeader(inputStream));
        body = readBody(inputStream);

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

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    /**
     * ヘッダーのみを読み込み。String型として返される。HttpHeaderの場合はnewでコンストラクタにこの結果を引き渡せばいい。
     *
     * @param in InputStream
     * @return ヘッダー
     * @throws IOException ヘッダーの読み込みに失敗
     */
    public String readHeader(InputStream in) throws IOException {
        String line = IOUtil.readLine(in);
        StringBuilder header = new StringBuilder();

        while (line != null && !line.isEmpty()) {
            header.append(line).append(CRLF);
            line = IOUtil.readLine(in);
        }

        return header.toString();
    }

    /**
     * Bodyを読み込む。原理はContent-LengthとTransferLengthの検知
     *
     * @param in InputStream
     * @return ボディ
     * @throws IOException ボディーの読み込みに失敗
     */
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
