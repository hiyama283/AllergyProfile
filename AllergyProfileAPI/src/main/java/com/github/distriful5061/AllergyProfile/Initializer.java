package com.github.distriful5061.AllergyProfile;

import com.github.distriful5061.AllergyProfile.Utils.Log.LogUtils;

import com.github.distriful5061.AllergyProfile.WebServer.Http.HttpServerHost;
import com.github.distriful5061.AllergyProfile.mixins.ConfigJson;
import com.github.distriful5061.AllergyProfile.mixins.MixinLoadConfig;
import com.github.distriful5061.AllergyProfile.mixins.MixinLoadUtils;
import com.github.distriful5061.AllergyProfile.mixins.MixinSetHandlers;


public class Initializer {
    public static ConfigJson config;
    public static void main(String[] args) {
        // Mixin
        LogUtils.println("Mixin Started");
        MixinLoadUtils.main();
        MixinSetHandlers.main();
        MixinLoadConfig.main();

        // Start Server
        LogUtils.println("Main Server Thread booted");
        HttpServerHost httpServerHost = new HttpServerHost(config.port);
        new Thread(httpServerHost).start();
    }
}
