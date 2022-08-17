package com.github.distriful5061.AllergyProfile;

import com.github.distriful5061.AllergyProfile.Utils.Log.LogUtils;
import com.github.distriful5061.AllergyProfile.WebServer.Http.HttpServerHost;
import com.github.distriful5061.AllergyProfile.mixins.MixinLoadConfig;
import com.github.distriful5061.AllergyProfile.mixins.MixinLoadUtils;
import com.github.distriful5061.AllergyProfile.mixins.MixinSetHandlers;
import com.github.distriful5061.AllergyProfile.mixins.gson.ConfigJson;
import com.github.distriful5061.cryptlibrary.PassWord;


public class Initializer {
    public static ConfigJson config;
    public static void main(String[] args) {
        // Mixin
        LogUtils.println("Mixin Started");
        MixinLoadConfig.main();

        MixinLoadUtils.main();
        MixinSetHandlers.main();

        // Random Seed
        if (config.tokenAuthConfig.regenRandomPassWord) {
            config.tokenAuthConfig.regenRandomPassWord = false;
            config.tokenAuthConfig.useHMACPassWord = new PassWord().getRandomString(128);

            MixinLoadConfig.saveConfig();
        }

        // Start Server
        LogUtils.println("Main Server Thread booted");
        HttpServerHost httpServerHost = new HttpServerHost(config.getHttpApiConfig().port);
        new Thread(httpServerHost).start();
    }
}
