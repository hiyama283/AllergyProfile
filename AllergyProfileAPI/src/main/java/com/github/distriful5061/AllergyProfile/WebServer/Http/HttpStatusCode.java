package com.github.distriful5061.AllergyProfile.WebServer.Http;

/**
 * HTTPステータスコード列挙型
 *
 * @since 1.0
 */
public enum HttpStatusCode {

    /**
     * 継続 クライアントはリクエストを継続できる。
     */
    CONTINUE(100, "Continue"),

    /**
     * プロトコル切り替え サーバはリクエストを理解し、遂行のためにプロトコルの切り替えを要求している
     */
    SWITCHING_PROTOCOLS(101, "Switching Protocols"),

    /**
     * 処理中 WebDAVの拡張ステータスコード。処理が継続して行われていることを示す。
     */
    PROCESSING(102, "Processing"),

    /**
     * OK リクエストは成功し、レスポンスとともに要求に応じた情報が返される。
     */
    OK(200, "OK"),

    /**
     * 作成 リクエストは完了し、新たに作成されたリソースのURIが返される。
     */
    CREATED(201, "Created"),

    /**
     * 受理 リクエストは受理されたが、処理は完了していない。
     */
    ACCEPTED(202, "Accepted"),

    /**
     * 信頼できない情報 オリジナルのデータではなく、ローカルやプロキシ等からの情報であることを示す。
     */
    NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),

    /**
     * 内容なし リクエストを受理したが、返すべきレスポンスエンティティが存在しない場合に返される。
     */
    NO_CONTENT(204, "No Content"),

    /**
     * 内容のリセット リクエストを受理し、ユーザエージェントの画面をリセットする場合に返される。
     */
    RESET_CONTENT(205, "Reset Content"),

    /**
     * 部分的内容 部分的GETリクエストを受理したときに、返される。
     */
    PARTIAL_CONTENT(206, "Partial Content"),

    /**
     * 複数のステータス WebDAVの拡張ステータスコード。
     */
    MULTI_STATUS(207, "Multi-Status"),

    /**
     * IM使用 Delta encoding in HTTPの拡張ステータスコード。
     */
    IM_USED(226, "IM Used"),

    /**
     * 複数の選択 リクエストしたリソースが複数存在し、ユーザやユーザーエージェントに選択肢を提示するときに返される。
     */
    MULTIPLE_CHOICES(300, "Multiple Choice"),

    /**
     * 恒久的に移動した リクエストしたリソースが恒久的に移動されているときに返される。Location:ヘッダに移動先のURLが示されている。
     */
    MOVED_PERMANENTLY(301, "Moved Permanently"),

    /**
     * 発見した リクエストしたリソースが一時的に移動されているときに返される。Location:ヘッダに移動先のURLが示されている。
     */
    FOUND(302, "Found"),

    /**
     * 他を参照せよ リクエストに対するレスポンスが他のURLに存在するときに返される。Location:ヘッダに移動先のURLが示されている。
     */
    SEE_OTHER(303, "See Other"),

    /**
     * 未更新 リクエストしたリソースは更新されていないことを示す。
     */
    NOT_MODIFIED(304, "Not Modified"),

    /**
     * プロキシを使用せよ レスポンスのLocation:ヘッダに示されるプロキシを使用してリクエストを行わなければならないことを示す。
     */
    USE_PROXY(305, "Use Proxy"),

    /**
     * 将来のために予約されている。ステータスコードは前のバージョンの仕様書では使われていたが、もはや使われておらず、将来のために予約されているとされる。
     */
    UNUSED(306, "unused"),

    /**
     * 一時的リダイレクト リクエストしたリソースは一時的に移動されているときに返される。Location:ヘッダに移動先のURLが示されている。
     */
    TEMPORARY_REDIRECT(307, "Temporary Redirect"),

    /**
     * リクエストが不正である 定義されていないメソッドを使うなど、クライアントのリクエストがおかしい場合に返される。
     */
    BAD_REQUEST(400, "Bad Request"),

    /**
     * 認証が必要である Basic認証やDigest認証などを行うときに使用される。
     */
    UNAUTHORIZED(401, "Unauthorized"),

    /**
     * 支払いが必要である 現在は実装されておらず、将来のために予約されているとされる。
     */
    PAYMENT_REQUIRED(402, "Payment Required"),

    /**
     * 禁止されている リソースにアクセスすることを拒否された。
     */
    FORBIDDEN(403, "Forbidden"),

    /**
     * 未検出 リソースが見つからなかった。
     */
    NOT_FOUND(404, "Not Found"),

    /**
     * 許可されていないメソッド 許可されていないメソッドを使用しようとした。
     */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    /**
     * 受理できない Accept関連のヘッダに受理できない内容が含まれている場合に返される。
     */
    NOT_ACCEPTABLE(406, "Not Acceptable"),

    /**
     * プロキシ認証が必要である プロキシの認証が必要な場合に返される。
     */
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),

    /**
     * リクエストタイムアウト リクエストが時間以内に完了していない場合に返される。
     */
    REQUEST_TIME_OUT(408, "Request Timeout"),

    /**
     * 矛盾 要求は現在のリソースと矛盾するので完了できない。
     */
    CONFLICT(409, "Request Timeout"),

    /**
     * 消滅した。ファイルは恒久的に移動した。
     */
    GONE(410, "Gone"),

    /**
     * 長さが必要 Content-Lengthヘッダがないのでサーバーがアクセスを拒否した場合に返される。
     */
    LENGTH_REQUIRED(411, "Length Required"),

    /**
     * 前提条件で失敗した 前提条件が偽だった場合に返される。
     */
    PRECONDITION_FAILED(412, "Precondition Failed"),

    /**
     * リクエストエンティティが大きすぎる リクエストエンティティがサーバの許容範囲を超えている場合に返す。
     */
    REQUEST_ENTITY_TOO_LARGE(413, "Payload Too Large"),

    /**
     * リクエストURIが大きすぎる URIが長過ぎるのでサーバが処理を拒否した場合に返す。
     */
    REQUEST_URI_TOO_LONG(414, "URI Too Long"),

    /**
     * サポートしていないメディアタイプ 指定されたメディアタイプがサーバでサポートされていない場合に返す。
     */
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),

    /**
     * リクエストしたレンジは範囲外にある 実ファイルのサイズを超えるデータを要求した。
     */
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Range Not Satisfiable"),

    /**
     * Expectヘッダによる拡張が失敗 その拡張はレスポンスできない。またはプロキシサーバは、次に到達するサーバがレスポンスできないと判断している。
     */
    EXPECTATION_FAILED(417, "Expectation Failed"),

    /**
     * 私はティーポット HTCPCP/1.0の拡張ステータスコード。
     */
    IMA_TEAPOT(418, "I'm a teapot"),

    /**
     * 処理できないエンティティ WebDAVの拡張ステータスコード。
     */
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),

    /**
     * ロックされている WebDAVの拡張ステータスコード。リクエストしたリソースがロックされている場合に返す。
     */
    LOCKED(423, "Locked"),

    /**
     * 依存関係で失敗 WebDAVの拡張ステータスコード。
     */
    FAILED_DEPENDENCY(424, "Failed Dependency"),

    /**
     * アップグレード要求 Upgrading to TLS Within HTTP/1.1の拡張ステータスコード。
     */
    UPGRADE_REQUIRED(426, "Upgrade Required"),

    /**
     * サーバ内部エラー サーバ内部にエラーが発生した場合に返される。
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    /**
     * 実装されていない 実装されていないメソッドを使用した。
     */
    NOT_IMPLEMENTED(501, "Not Implemented"),

    /**
     * 不正なゲートウェイ ゲートウェイ・プロキシサーバは不正な要求を受け取り、これを拒否した。
     */
    BAD_GATEWAY(502, "Bad Gateway"),

    /**
     * サービス利用不可 サービスが一時的に過負荷やメンテナンスで使用不可能である。
     */
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),

    /**
     * ゲートウェイタイムアウト ゲートウェイ・プロキシサーバはURIから推測されるサーバからの適切なレスポンスがなくタイムアウトした。
     */
    GATEWAY_TIME_OUT(504, "Gateway Timeout"),

    /**
     * サポートしていないHTTPバージョン リクエストがサポートされていないHTTPバージョンである場合に返される。
     */
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported"),

    /**
     * Transparent Content Negotiation in HTTPで定義されている拡張ステータスコード。
     */
    VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates"),

    /**
     * 容量不足 WebDAVの拡張ステータスコード。リクエストを処理するために必要なストレージの容量が足りない場合に返される。
     */
    INSUFFICIENT_STORAGE(507, "Insufficient Storage"),

    /**
     * 帯域幅制限超過 そのサーバに設定されている帯域幅（転送量）を使い切った場合に返される。
     */
    BAND_WIDTH_LIMIT_EXCEEDED(509, "Loop Detected"),

    /**
     * 拡張できない An HTTP Extension Frameworkで定義されている拡張ステータスコード。
     */
    NOT_EXTENDED(510, "Not Extended"),

    /**
     * デフォルト
     */
    DEFAULT(0, "NONE");

    private final int statusCode;
    private final String extension;

    /**
     * コンストラクタ
     *
     * @param statusCode HTTPステータスコード
     */
    HttpStatusCode(int statusCode, String extension) {
        this.statusCode = statusCode;
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "%d %s".formatted(statusCode, extension);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getExtension() {
        return extension;
    }

    /**
     * HTTPステータスコードから取得
     *
     * @param statusCode HTTPステータスコード
     * @return HTTPステータスコード列挙型
     */
    public static HttpStatusCode getByStatusCode(int statusCode) {
        for (HttpStatusCode code: HttpStatusCode.values()) {
            if (code.statusCode == statusCode)
                return code;
        }
        return DEFAULT;
    }
}