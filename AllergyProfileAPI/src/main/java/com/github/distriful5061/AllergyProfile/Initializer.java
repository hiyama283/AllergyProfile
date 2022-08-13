package com.github.distriful5061.AllergyProfile;

import com.github.distriful5061.AllergyProfile.Utils.Log.LogUtils;
import com.github.distriful5061.AllergyProfile.WebServer.Http.HttpServerHost;
import com.github.distriful5061.AllergyProfile.mixins.mixinLoadUtils;
import com.github.distriful5061.AllergyProfile.mixins.mixinSetHandlers;

public class Initializer {
    public static void main(String[] args) {
        // Mixin
        LogUtils.println("Mixin Started");
        mixinLoadUtils.main();
        mixinSetHandlers.main();

        // Start Server
        LogUtils.println("Main Server Thread booted");
        HttpServerHost httpServerHost = new HttpServerHost(8080);
        new Thread(httpServerHost).start();
    }
}
