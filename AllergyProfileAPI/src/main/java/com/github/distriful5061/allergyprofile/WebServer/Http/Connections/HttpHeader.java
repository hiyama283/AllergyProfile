package com.github.distriful5061.AllergyProfile.WebServer.Http.Connections;

import com.github.distriful5061.AllergyProfile.Utils.Log.LogLevel;
import com.github.distriful5061.AllergyProfile.Utils.Log.LogUtils;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.Header.HttpMethod;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpRequest.CRLF;

/**
 * Httpリクエストを送信した際に、ヘッダーから読み取れる様々な情報を提供するクラス。現在リクエストヘッダー保管庫として使用されている
 *
 * @since 1.0
 */
public class HttpHeader {
    private String startLine;
    private String header;
    private HttpMethod method;
    private String path;

    /**
     * コンストラクタ。String型のリクエストヘッダーヘッダーを受け渡すことで、様々な情報を読み取る
     *
     * @param header ヘッダー
     */
    public HttpHeader(String header) {
        // LogUtils.println("A SPLIT", LogLevel.DEBUG);
        String[] splitHeader = header.split("\r\n");
        // LogUtils.println("A SPLIT OK", LogLevel.DEBUG);

        this.startLine = splitHeader[0];
        // LogUtils.println("A START LINE OK", LogLevel.DEBUG);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < splitHeader.length; i++) {
            stringBuilder.append(splitHeader[i]).append("\r\n");
        }
        // LogUtils.println("A BUILD OK", LogLevel.DEBUG);

        this.header = stringBuilder.toString();

        String[] splitStartLine = startLine.split(" ");
        this.method = HttpMethod.get(splitStartLine[0].toUpperCase());
        // LogUtils.println("A ValueOf OK", LogLevel.DEBUG);
        this.path = URLDecoder.decode(splitStartLine[1], StandardCharsets.UTF_8);
        // LogUtils.println("A DECODE OK", LogLevel.DEBUG);
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

    /**
     * ContentLength(bodyを読み取るのに必要な情報)を取得するメソッド
     *
     * @return content-lengthの値
     */
    public int getContentLength() {
        return Stream.of(header.split(CRLF))
                .filter(headerLine -> headerLine.startsWith("Content-Length"))
                .map(contentLengthHeader -> contentLengthHeader.split(":")[1].trim())
                .mapToInt(Integer::parseInt)
                .findFirst().orElse(0);
    }

    /**
     * Chunk型のプロトコルかどうか判別するメソッド
     *
     * @return True=はい
     */
    public boolean isChunkedTransfer() {
        return Stream.of(header.split(CRLF))
                .filter(headerLine -> headerLine.startsWith("Transfer-Encoding"))
                .map(transferEncoding -> transferEncoding.split(":")[1].trim())
                .anyMatch("chunked"::equals);
    }
}
