package com.github.distriful5061.AllergyProfile.mixins;

import com.github.distriful5061.AllergyProfile.Handlers.WebHandlers.RootDirHandler;
import com.github.distriful5061.AllergyProfile.WebServer.Http.HttpServerHost;

/**
 * Handler系をhandlerリストに追加するmixin
 *
 * @since 1.1
 */
public class mixinSetHandlers {
    public static void main() {
        HttpServerHost.addHandler("/", new RootDirHandler());
    }
}
