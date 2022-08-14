package com.github.distriful5061.AllergyProfile.Handlers.WebHandlers.GET;

import com.github.distriful5061.AllergyProfile.Handlers.WebHandlers.AbstractBaseHandler;
import com.github.distriful5061.AllergyProfile.Utils.ResourceUtils;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.Header.HttpMethod;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpRequest;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpResponse;
import com.github.distriful5061.AllergyProfile.WebServer.Http.HttpStatusCode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Rootディレクトリ用ハンドラ
 * Path: /
 *
 * @since 1.1
 * @see AbstractBaseHandler
 * @see com.github.distriful5061.AllergyProfile.WebServer.Http.HttpServerHost
 */
public class RootDirHandler extends AbstractBaseHandler {
    @Override
    public void run(InputStream inputStream, OutputStream outputStream, HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();
        Map<String, Object> headers = HttpResponse.getDefaultHeader();

        httpResponse.setStatusCode(HttpStatusCode.OK);
        httpResponse.addHeader(headers);
        httpResponse.setBody(ResourceUtils.getFileResourcesByName("web/index.html"));
        try {
            httpResponse.write(outputStream);
        } catch (IOException ignored) {}
    }

    @Override
    public HttpMethod getSupportedMethod() {
        return HttpMethod.GET;
    }
}
