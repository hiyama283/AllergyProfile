package com.github.distriful5061.AllergyProfile.WebServer;

import java.net.Socket;

public class SocketProcessor implements Runnable {
    private final Socket targetSocket;

    public SocketProcessor(Socket socket) {
        this.targetSocket = socket;
    }

    @Override
    public void run() {

    }
}
