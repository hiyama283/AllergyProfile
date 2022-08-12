package com.github.distriful5061.AllergyProfile.WebServer.Http;

import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpMethod;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpRequest;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class SocketProcessor implements Runnable {
    private final Socket socket;
    public SocketProcessor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                OutputStream outputStream = socket.getOutputStream();
        )
        {
            HttpRequest httpRequest = new HttpRequest(socket);
            HttpResponse httpResponse = new HttpResponse();
            Map<String, Object> headers = HttpResponse.getDefaultHeader();

            boolean flag = true;

            if (httpRequest.getHeader().getMethod() == HttpMethod.GET) {
                switch (httpRequest.getHeader().getPath()) {
                    case "/" -> {
                        headers.put("content-type", ContentType.TEXT_PLAIN);
                        httpResponse.addHeader(headers);
                        httpResponse.setBody("Hello, World!");
                        httpResponse.write(outputStream);
                        flag = false;
                    }
                }
            }

            if (flag) {
                headers.put("content-type", ContentType.TEXT_PLAIN);
                httpResponse.addHeader(headers);
                httpResponse.setBody("404 Not Found");
                httpResponse.write(outputStream);
            }

        } catch (IOException ignored) {}
    }
}
