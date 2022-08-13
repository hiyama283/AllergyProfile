package com.github.distriful5061.AllergyProfile.Handlers.WebHandlers;

import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpResponse;
import com.github.distriful5061.AllergyProfile.WebServer.Http.ContentType;
import com.github.distriful5061.AllergyProfile.WebServer.Http.HttpStatusCode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import static com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpRequest.CRLF;

/**
 * BaseHandlerをabstractで一部実装した物。そのうちいろいろ肉付けされると思います。
 *
 * @since 1.1
 */
abstract public class AbstractBaseHandler implements BaseHandler {
    @Override
    public void run(InputStream inputStream, OutputStream outputStream) {
        HttpResponse httpResponse = new HttpResponse();
        Map<String, Object> headers = HttpResponse.getDefaultHeader();

        headers.put("content-type", ContentType.TEXT_PLAIN);
        httpResponse.setStatusCode(HttpStatusCode.IMA_TEAPOT);
        httpResponse.addHeader(headers);
        httpResponse.setBody("Hello, Im a teapot. %s Here's test handler.".replace("%s", CRLF));
        try {
            httpResponse.write(outputStream);
        } catch (IOException ignored1) {}
    }
}
