package com.github.distriful5061.AllergyProfile.Handlers.WebHandlers;

import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.Header.HttpMethod;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * handler系のベース。基本的にAbstractBaseHandlerを使用した方がいい。
 *
 * @since 1.1
 */
public interface BaseHandler {
    HttpMethod supportedMethod = HttpMethod.GET;
    void run(InputStream inputStream, OutputStream outputStream, HttpRequest httpRequest) throws IOException;

    HttpMethod getSupportedMethod();
}
