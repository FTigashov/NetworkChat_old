package com.example.networkchat.server.options.authentication;

import com.example.networkchat.server.options.models.User;

import java.util.List;

public class BaseAuthentication implements AuthenticationService {
    private static final List<User> clients = List.of(
            new User("Ivanov", "ivan", "1111"),
            new User("Bruce_Wane", "batman", "2222")
    );

    @Override
    public String getUsernameByLoginAndPassword(String login, String password) {
        for (User client : clients) {
            if (client.getLogin().equals(login) && client.getPassword().equals(password)) return client.getUsername();
        }
        return null;
    }

    @Override
    public void startAuthentication() {
        System.out.println("Начата аутентификации");
    }

    @Override
    public void endAuthentication() {
        System.out.println("Начата аутентификации");
    }
}
