package com.github.distriful5061.AllergyProfile.Utils.SQL;

import com.github.distriful5061.AllergyProfile.Utils.BaseUtils;

/**
 * SQLのマスタを取得/保存するためのUtilsファイル。キャッシュ機能が含まれている(キャッシュはそのうちCDNに隔離する)。
 *
 * @since 1.0.0
 */
public class DirectConnectToSQLUtils implements BaseUtils {
    private static String sqlServerUrl;
    private static String targetSqlUserName;
    private static String targetSqlPassWord;
    private static String targetSqlTableName;

    public static String getTargetUrl() {
        return sqlServerUrl;
    }

    public static void setTargetUrl(String targetUrl) {
        sqlServerUrl = targetUrl;
    }

    public static String getTargetSqlUserName() {
        return targetSqlUserName;
    }

    public static void setTargetSqlUserName(String targetUserName) {
        targetSqlUserName = targetUserName;
    }

    public static String getTargetSqlPassWord() {
        return targetSqlPassWord;
    }

    public static void setTargetSqlPassWord(String targetPassWord) {
        targetSqlPassWord = targetPassWord;
    }

    public static String getTargetSqlTableName() {
        return targetSqlTableName;
    }

    public static void setTargetSqlTableName(String targetTableName) {
        targetSqlTableName = targetTableName;
    }


    @Override
    public int hashCode() {
        return sqlServerUrl.hashCode() ^ targetSqlUserName.hashCode() ^ targetSqlPassWord.hashCode() ^ targetSqlTableName.hashCode();
    }
}
