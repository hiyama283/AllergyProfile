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

/**
 * Httpのレスポンスの作成を簡単にするクラス。
 *
 * @since 1.0
 */
public class HttpResponse {
    private final Map<String, String> headers = new HashMap<>();
    private HttpStatusCode statusCode;
    private String body;
    private File bodyFile;

    /**
     * Map型でヘッダーを作成する際に呼び出すメソッド。これにより最低限必要なヘッダーが手に入る
     *
     * @return 初期ヘッダー
     */
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
        if (headers.containsKey(key)) {
            headers.replace(key.toLowerCase(), value.toString());
        } else headers.put(key.toLowerCase(), value.toString());
    }

    /**
     * Map型のリストでheaderに追加するメソッド。addHeader(String key...)とかいうやつよりもこれのほうがわかりやすい気がする。
     *
     * @param mapObject 追加するためのマップ
     */
    public void addHeader(Map<String, Object> mapObject) {
        mapObject.forEach((key, value) -> {
            if (headers.containsKey(key)) {
                headers.replace(key.toLowerCase(), value.toString());
            } else headers.put(key.toLowerCase(), value.toString());
        });
    }

    public void clearHeader() {
        headers.clear();
    }

    /**
     * 文字列型のbodyを設定するメソッド。nullを入れることで途中からでもFile投げ込むようにできる。
     *
     * @param body 文字列型のボディ
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * ファイル形式のbodyを設定するメソッド。ResourceUtilsとか活用するとやりやすいぞ。注意点として、勝手にcontent-typeが追加されるから、そこんとこよろしく
     *
     * @param file ファイル
     */
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

    /**
     * 指定されたoutputStreamに書き込むメソッド。このインスタンス自体の繰り返しはこれを活用することでできる
     *
     * @param outputStream outputStream
     * @throws IOException 送信に失敗しました
     */
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
