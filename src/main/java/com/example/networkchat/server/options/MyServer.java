package com.example.networkchat.server.options;

import com.example.networkchat.server.options.authentication.Authentication;
import com.example.networkchat.server.options.authentication.BaseAuthentication;
import com.example.networkchat.server.options.handler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

    private final ServerSocket serverSocket;
    private final Authentication auth;

    public MyServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        auth = new BaseAuthentication();
    }

    public void start() {
        System.out.println("Сервер запущен\n---------------");

        try {
            while (true) {
                waitAndProccessNewClientConnection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitAndProccessNewClientConnection() throws IOException {
        System.out.println("Ожидание клиента...");
        Socket socket = serverSocket.accept();
        System.out.println("Клиент подключился!");

        proccessClientConnection(socket);
    }

    private void proccessClientConnection(Socket socket) throws IOException {
        ClientHandler handler = new ClientHandler(this, socket);
        handler.handle();
    }
}
