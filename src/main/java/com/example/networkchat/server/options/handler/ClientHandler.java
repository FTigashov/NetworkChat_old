package com.example.networkchat.server.options.handler;

import com.example.networkchat.server.options.MyServer;
import com.example.networkchat.server.options.authentication.AuthenticationService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private static final String AUTH_CMD_PREFIX = "/auth"; // + login + password
    private static final String AUTHOK_CMD_PREFIX = "/authok"; // + username
    private static final String AUTHERR_CMD_PREFIX = "/autherr"; // + error message
    private static final String CLIENT_MSG_CMD_PREFIX = "/cMsg"; // + msg
    private static final String SERVER_MSG_CMD_PREFIX = "/sMsg"; // + msg
    private static final String PRIVATE_MSG_CMD_PREFIX = "/pMsg"; // + msg
    private static final String STOP_SERVER_CMD_PREFIX = "/stop";
    private static final String END_CLIENT_CMD_PREFIX = "/end";

    private MyServer myServer;
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;
    private String username;

    public ClientHandler(MyServer myServer, Socket socket) {
        this.myServer = myServer;
        clientSocket = socket;
    }

    public void handle() throws IOException {
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new DataInputStream(clientSocket.getInputStream());

        new Thread(() -> {
            try {
                authentication();
                readMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void authentication() throws IOException {
        while (true) {
            String message = in.readUTF();
            if (message.startsWith(AUTH_CMD_PREFIX)) {
//                out.writeUTF("Accepted");
//                System.out.println(message);
                boolean isSuccessAuth = proccessAuthentication(message);
                if (isSuccessAuth) {
                    System.out.println("Авторизация успешна! " + username);
                    break;
                }
            } else {
                out.writeUTF(AUTHERR_CMD_PREFIX + " ошибка аутентификации");
                System.out.println("Неудачная попытка аутентификации");
            }
        }
    }

    private boolean proccessAuthentication(String message) throws IOException {
        String[] parts = message.split("\\s+", 3);
        if (parts.length != 3) out.writeUTF(AUTHERR_CMD_PREFIX + " ошибка аутентификации");

        String login = parts[1];
        String password = parts[2];

        AuthenticationService auth = myServer.getAuthenticationService();

        username = auth.getUsernameByLoginAndPassword(login, password);

        if (username != null) {
            if (myServer.isUsernameBusy(username)) {
                out.writeUTF(AUTHERR_CMD_PREFIX + " данный логин уже используется");
                return false;
            }

            out.writeUTF(AUTHOK_CMD_PREFIX + " " + username);
            myServer.subscribe(this);
            return true;
        } else {
            out.writeUTF(AUTHERR_CMD_PREFIX + " логин или пароль не соответствуют действительности");
            return false;
        }
    }

    private void readMessage() {

    }

    public String getUsername() {
        return username;
    }
}
