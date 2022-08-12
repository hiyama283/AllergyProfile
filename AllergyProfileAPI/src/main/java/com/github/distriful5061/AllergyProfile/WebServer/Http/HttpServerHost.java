package com.github.distriful5061.AllergyProfile.WebServer.Http;

import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServerHost implements Runnable {
    private final int PORT;
    private ExecutorService service = Executors.newCachedThreadPool();
    public HttpServerHost(int port) {
        PORT = port;
    }

    @Override
    public void run() {
        for (;;) {
            try (
                    ServerSocket serverSocket = new ServerSocket(PORT);
                    Socket socket = serverSocket.accept();
            )
            {
                service.execute(() -> {
                    try (
                            InputStream inputStream = socket.getInputStream();
                            OutputStream outputStream = socket.getOutputStream();

                    )
                    {
                        HttpRequest httpRequest = new HttpRequest(socket);
                        Map<String, String> result = new HashMap<>();


                    } catch (IOException ignored) {}
                });
            } catch (IOException ignored) {}
        }
    }
}
