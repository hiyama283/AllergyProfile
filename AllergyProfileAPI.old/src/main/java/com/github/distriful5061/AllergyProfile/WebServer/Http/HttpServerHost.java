package com.github.distriful5061.AllergyProfile.WebServer.Http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

        for (;;) {
           try {
               Socket socket = serverSocket.accept();

               SocketProcessor socketProcessor = new SocketProcessor(socket);
               new Thread(socketProcessor).start();

           } catch (IOException ignored) {}
       }
    }
}
