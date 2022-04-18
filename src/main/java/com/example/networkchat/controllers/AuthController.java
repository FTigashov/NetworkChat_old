package com.example.networkchat.controllers;

import com.example.networkchat.StartClient;
import com.example.networkchat.models.Network;
import javafx.collections.FXCollections;
import  javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AuthController {
    @FXML
    private Button enterButton;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    public void initialize() {
        enterButton.setOnAction(event -> checkAuth());
    }

    public void checkAuth() {
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();

        if (login.length() == 0 || password.length() == 0) {
            System.out.println("Необходимо ввести логин и пароль!");
            return;
        }

        String authErrorMessage = network.sendAuthMessage(login, password);

        if (authErrorMessage == null) {
            startClient.openChatDialog(login);
        } else {
            System.out.println("Ошибка авторизации!" + authErrorMessage);
        }
    }

    private Network network;
    private StartClient startClient;

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Network getNetwork() {
        return network;
    }

    public void setStartClient(StartClient startClient) {
        this.startClient = startClient;
    }
}
