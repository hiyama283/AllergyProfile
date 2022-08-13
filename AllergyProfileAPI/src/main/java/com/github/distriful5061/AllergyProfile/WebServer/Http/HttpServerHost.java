package com.github.distriful5061.AllergyProfile.WebServer.Http;

import com.github.distriful5061.AllergyProfile.Utils.ResourceUtils;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpMethod;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpRequest;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class HttpServerHost implements Runnable {
    private final int PORT;
    public HttpServerHost(int port) {
        PORT = port;
    }

    @Override
    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ignored) {
            System.out.println("System downed");
            return;
        }


        try {
            for (; ; ) {
                try {
                    Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();

                    Runnable socketProcessor = () -> {
                        try {
                            HttpRequest httpRequest = new HttpRequest(inputStream);
                            HttpResponse httpResponse = new HttpResponse();
                            Map<String, Object> headers = HttpResponse.getDefaultHeader();

                            boolean flag = true;

                            if (httpRequest.getHeader().getMethod() == HttpMethod.GET) {
                                switch (httpRequest.getHeader().getPath()) {
                                    case "/" -> {
                                        httpResponse.addHeader(headers);
                                        httpResponse.setBody(ResourceUtils.getFileResourcesByName("web/index.html"));
                                        httpResponse.write(outputStream);
                                        flag = false;
                                    }
                                }
                            }

                            if (flag) {
                                headers.put("content-type", ContentType.TEXT_PLAIN);
                                httpResponse.addHeader(headers);
                                httpResponse.setStatusCode(HttpStatusCode.NOT_FOUND);
                                httpResponse.setBody("404 Not Found");
                                httpResponse.write(outputStream);
                            }

                        } catch (IOException ignored) {
                            HttpResponse httpResponse = new HttpResponse();
                            Map<String, Object> headers = HttpResponse.getDefaultHeader();

                            headers.put("content-type", ContentType.TEXT_PLAIN);
                            httpResponse.setStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
                            httpResponse.addHeader(headers);
                            httpResponse.setBody("500 Internal Server Error");
                            try {
                                httpResponse.write(outputStream);
                            } catch (IOException ignored1) {}
                        } finally {
                            try {
                                socket.close();
                                inputStream.close();
                                outputStream.close();
                            } catch (IOException ignored1) {}
                        }
                    };
                    new Thread(socketProcessor).start();

                } catch (IOException ignored) {
                }
            }
        } catch (Throwable e) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }

        System.out.println("oh no system are shutdown ed");
        System.exit(-1);
    }
}
