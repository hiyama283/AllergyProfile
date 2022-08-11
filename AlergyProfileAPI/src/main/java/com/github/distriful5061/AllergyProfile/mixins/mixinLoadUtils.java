package com.github.distriful5061.AllergyProfile.mixins;

import com.github.distriful5061.AllergyProfile.Utils.ResourceUtils;
import com.github.distriful5061.AllergyProfile.Utils.SQL.SQLUtils;

public class mixinLoadUtils {
    public static void main() {
        // Setup SQL Utils
        String[] sqlSettings = ResourceUtils.getPlainTextResourcesByName("SqlSettings.txt").split("\n");
        SQLUtils.setTargetUrl(sqlSettings[0]);
        SQLUtils.setTargetSqlUserName(sqlSettings[1]);
        SQLUtils.setTargetSqlPassWord(sqlSettings[2]);
        SQLUtils.setTargetSqlTableName(sqlSettings[3]);
    }
}
