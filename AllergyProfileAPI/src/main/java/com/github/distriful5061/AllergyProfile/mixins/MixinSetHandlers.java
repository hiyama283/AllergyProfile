package com.github.distriful5061.AllergyProfile.mixins;

import com.github.distriful5061.AllergyProfile.Handlers.WebHandlers.AbstractBaseHandler;
import com.github.distriful5061.AllergyProfile.Handlers.WebHandlers.GET.RootDirHandler;
import com.github.distriful5061.AllergyProfile.Handlers.WebHandlers.POST.PlusHandler;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.Header.HttpMethod;
import com.github.distriful5061.AllergyProfile.WebServer.Http.HttpServerHost;

/**
 * Handler系をhandlerリストに追加するmixin
 *
 * @since 1.1
 */
public class MixinSetHandlers {
    public static void main() {
        HttpServerHost.addHandler("/", new RootDirHandler());
        HttpServerHost.addHandler("/teapot", new AbstractBaseHandler() {
            @Override
            public HttpMethod getSupportedMethod() {
                return HttpMethod.GET;
            }
        });
        HttpServerHost.addHandler("/plus", new PlusHandler());
    }
}
