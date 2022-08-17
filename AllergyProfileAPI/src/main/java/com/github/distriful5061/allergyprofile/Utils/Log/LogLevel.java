package com.github.distriful5061.AllergyProfile.Utils.Log;

public enum LogLevel {
    /**
     * 情報。実行時の何らかの注目すべき事象（開始や終了など）。コンソール等に即時出力することを想定。従ってメッセージ内容は簡潔に止めるべき
     */
    INFO("INFO"),
    /**
     * 警告。廃要素となったAPIの使用、APIの不適切な使用、エラーに近い事象など、実行時に生じた異常とは言い切れないが正常とも異なる何らかの予期しない問題。コンソール等に即時出力することを想定
     */
    WARN("WARN"),
    /**
     * エラー。予期しないその他の実行時エラー。コンソール等に即時出力することを想定
     */
    ERROR("ERROR"),
    /**
     * 致命的なエラー。プログラムの異常終了を伴うようなもの。コンソール等に即時出力することを想定
     */
    FATAL("FATAL"),
    /**
     * デバッグ用の情報。システムの動作状況に関する詳細な情報。コンソールではなくログ上にだけ出力することを想定
     */
    DEBUG("DEBUG"),
    /**
     * トレース情報。更に詳細な情報。コンソールではなくログ上にだけ出力することを想定
     */
    TRACE("TRACE");

    private final String stringType;

    LogLevel(String stringType) {
        this.stringType = stringType;
    }

    @Override
    public String toString() {
        return this.stringType;
    }
}
