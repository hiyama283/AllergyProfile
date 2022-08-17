package com.github.distriful5061.AllergyProfile.Handlers.WebHandlers;

import com.github.distriful5061.AllergyProfile.Utils.GsonUtils;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.Header.ContentType;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpRequest;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpResponse;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Gson.Response.SimpleJsonResponse;
import com.github.distriful5061.AllergyProfile.WebServer.Http.HttpStatusCode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * BaseHandlerをabstractで一部実装した物。そのうちいろいろ肉付けされると思います。
 *
 * @since 1.1
 */
abstract public class AbstractBaseHandler implements BaseHandler {
    @Override
    public void run(InputStream inputStream, OutputStream outputStream, HttpRequest httpRequest) throws IOException {
        HttpResponse httpResponse = new HttpResponse();
        Map<String, Object> headers = HttpResponse.getDefaultHeader();

        headers.put("content-type", ContentType.APPLICATION_JSON);
        httpResponse.setStatusCode(HttpStatusCode.IMA_TEAPOT);
        httpResponse.addHeader(headers);
        httpResponse.setBody(GsonUtils.toJson(new SimpleJsonResponse(HttpStatusCode.IMA_TEAPOT, "Im a teapot.\nthis is a test handler.")));
        httpResponse.write(outputStream);
    }
}
