package com.example.networkchat;

import com.example.networkchat.controllers.ChatController;
import com.example.networkchat.models.Network;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartClient extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartClient.class.getResource("chat-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Чат");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        Network network = new Network();
        ChatController chatController = fxmlLoader.getController();

        chatController.setNetwork(network);

        network.connect();
        network.waitMessage(chatController);
    }

    public static void main(String[] args) {
        launch();
    }
}