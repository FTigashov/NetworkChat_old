package com.example.networkchat.server.options;

import com.example.networkchat.server.options.authentication.AuthenticationService;
import com.example.networkchat.server.options.authentication.BaseAuthentication;
import com.example.networkchat.server.options.handler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private final ServerSocket serverSocket;
    private final AuthenticationService authenticationService;
    private final List<ClientHandler> clients;

    public MyServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        authenticationService = new BaseAuthentication();
        clients = new ArrayList<>();
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

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public boolean isUsernameBusy(String username) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(username)) return true;
        }
        return false;
    }
}
