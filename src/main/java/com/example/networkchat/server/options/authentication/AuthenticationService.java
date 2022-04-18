package com.example.networkchat.server.options.authentication;

public interface AuthenticationService {
    String getUsernameByLoginAndPassword(String login, String password);

    void startAuthentication();
    void endAuthentication();
}
