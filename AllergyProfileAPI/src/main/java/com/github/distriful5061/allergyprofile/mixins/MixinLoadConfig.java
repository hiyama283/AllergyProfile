package com.github.distriful5061.AllergyProfile.mixins;

import com.github.distriful5061.AllergyProfile.Initializer;
import com.github.distriful5061.AllergyProfile.Utils.GsonUtils;
import com.github.distriful5061.AllergyProfile.Utils.Log.LogLevel;
import com.github.distriful5061.AllergyProfile.Utils.Log.LogUtils;
import com.github.distriful5061.AllergyProfile.Utils.ResourceUtils;
import com.github.distriful5061.AllergyProfile.mixins.gson.ConfigJson;

import java.io.IOException;

public class MixinLoadConfig {
    public static void main() {
        String config = ResourceUtils.getPlainTextResourcesByName("config.json");
        Initializer.config = GsonUtils.fromJson(config, ConfigJson.class);
    }

    public static void saveConfig() {
        try {
            ResourceUtils.setPlainTextResourcesByName("config.json", GsonUtils.toJson(Initializer.config));
        } catch (IOException e) {
            LogUtils.println("SaveConfig Error", LogLevel.ERROR);
            LogUtils.println(e.getMessage(), LogLevel.TRACE);
        }
    }
}
