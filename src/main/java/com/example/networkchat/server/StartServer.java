package com.example.networkchat.server;

import com.example.networkchat.server.options.MyServer;

import java.io.IOException;

public class StartServer {
    private static final int DEFAULT_PORT = 8186;
    public static void main(String[] args) {
        try {
            new MyServer(DEFAULT_PORT).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
