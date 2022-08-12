package com.github.distriful5061.AllergyProfile;

import com.github.distriful5061.AllergyProfile.WebServer.Http.HttpServerHost;
import com.github.distriful5061.AllergyProfile.mixins.mixinLoadUtils;

public class Initializer {
    public static void main(String[] args) {
        // Mixin
        mixinLoadUtils.main();

        // Start Server
        HttpServerHost httpServerHost = new HttpServerHost(80);
        new Thread(httpServerHost).start();
    }
}
