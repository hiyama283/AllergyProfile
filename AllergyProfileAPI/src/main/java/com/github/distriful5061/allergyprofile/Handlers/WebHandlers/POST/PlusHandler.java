package com.github.distriful5061.AllergyProfile.Handlers.WebHandlers.POST;

import com.github.distriful5061.AllergyProfile.Handlers.WebHandlers.AbstractBaseHandler;
import com.github.distriful5061.AllergyProfile.Utils.GsonUtils;
import com.github.distriful5061.AllergyProfile.Utils.Log.LogLevel;
import com.github.distriful5061.AllergyProfile.Utils.Log.LogUtils;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.Header.ContentType;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.Header.HttpMethod;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpHeader;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpRequest;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpResponse;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Gson.Request.JsonPlusHandlerRequest;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Gson.Response.BasicError;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Gson.Response.PlusApiResult;
import com.github.distriful5061.AllergyProfile.WebServer.Http.HttpStatusCode;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 足し算して返す単純なPOSTサンプル。
 * Path: /plus
 *
 * @since 1.1
 * @see AbstractBaseHandler
 * @see com.github.distriful5061.AllergyProfile.WebServer.Http.HttpServerHost
 */
public class PlusHandler extends AbstractBaseHandler {
    @Override
    public void run(InputStream inputStream, OutputStream outputStream, HttpRequest httpRequest) throws IOException {
        // LogUtils.println("C Run here", LogLevel.DEBUG);

        JsonPlusHandlerRequest jsonPlusHandlerRequest;
        try {
            // LogUtils.println("C Load", LogLevel.DEBUG);
            jsonPlusHandlerRequest = GsonUtils.fromJson(httpRequest.getBody(), JsonPlusHandlerRequest.class);
            // LogUtils.println("C Loaded Json", LogLevel.DEBUG);
        } catch (JsonSyntaxException ignored) {
            sendBadRequest(outputStream);
            // LogUtils.println("C Failed to Json", LogLevel.DEBUG);
            return;
        }

        HttpResponse httpResponse = new HttpResponse();
        Map<String, Object> headers = new HashMap<>();

        // LogUtils.println("C Pass", LogLevel.DEBUG);

        headers.put("content-type", ContentType.APPLICATION_JSON);
        httpResponse.addHeader(headers);
        httpResponse.setBody(GsonUtils.toJson(new PlusApiResult(HttpStatusCode.OK, jsonPlusHandlerRequest.getPlus())));
        httpResponse.write(outputStream);
        // LogUtils.println("C Write", LogLevel.DEBUG);
    }

    private void sendBadRequest(OutputStream outputStream) throws IOException {
        HttpResponse httpResponse = new HttpResponse();
        Map<String, Object> headers = new HashMap<>();

        headers.put("content-type", ContentType.APPLICATION_JSON);
        httpResponse.addHeader(headers);
        httpResponse.setBody(GsonUtils.toJson(new BasicError(HttpStatusCode.BAD_REQUEST, null, HttpMethod.GET)));
        httpResponse.write(outputStream);
    }

    @Override
    public HttpMethod getSupportedMethod() {
        return HttpMethod.POST;
    }
}
