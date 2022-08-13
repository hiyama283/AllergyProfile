package com.github.distriful5061.AllergyProfile.mixins;

import com.github.distriful5061.AllergyProfile.Utils.ResourceUtils;
import com.github.distriful5061.AllergyProfile.Utils.SQL.DirectConnectToSQLUtils;

/**
 * Utilsファイルの初期設定を済ませるクラス。
 *
 * @since 1.0
 */
public class mixinLoadUtils {
    /**
     * Static型のutilsを設定する
     */
    public static void main() {
        // Setup SQL Utils
        String[] sqlSettings = ResourceUtils.getPlainTextResourcesByName("SqlSettings.txt").split("\n");
        DirectConnectToSQLUtils.setTargetUrl(sqlSettings[0]);
        DirectConnectToSQLUtils.setTargetSqlUserName(sqlSettings[1]);
        DirectConnectToSQLUtils.setTargetSqlPassWord(sqlSettings[2]);
        DirectConnectToSQLUtils.setTargetSqlTableName(sqlSettings[3]);
    }
}
