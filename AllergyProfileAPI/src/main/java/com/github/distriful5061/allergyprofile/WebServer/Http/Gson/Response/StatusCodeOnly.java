package com.github.distriful5061.AllergyProfile.WebServer.Http.Gson.Response;

import com.github.distriful5061.AllergyProfile.WebServer.Http.HttpStatusCode;

/**
 * 簡単なGsonに通せるオブジェクトのサンプル。詳しく書く
 *
 * @since 1.1
 */
public class StatusCodeOnly {
    /**
     * Jsonに使用する変数。名前がキーとなり、値がそのまま値となる。変数名それ自体がキーとなるため、わかりやすいように選んだほうがいい。
     * また、Publicかつstaticでないとできず、できればコンストラクターだけがいじるようにするといい。
     */
    public int statusCode;

    /**
     * 同上
     */
    public String extension;

    /**
     * コンストラクター。上記の変数を一括に設定するためにコンストラクターを設定したする。コンストラクターのほうが漏れが少なくて楽です。
     *
     * @param statusCode ステータスコード
     */
    public StatusCodeOnly(HttpStatusCode statusCode) {
        this.statusCode = statusCode.getStatusCode();
        this.extension = statusCode.getExtension();
    }
}
