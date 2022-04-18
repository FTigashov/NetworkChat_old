package com.example.networkchat;

import com.example.networkchat.controllers.AuthController;
import com.example.networkchat.controllers.ChatController;
import com.example.networkchat.models.Network;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StartClient extends Application {

    private Network network;
    private Stage primaryStage;
    private Stage authStage;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;

        network = new Network();
        network.connect();

        openAuthDialog();
        createChatDialog();

        /*FXMLLoader fxmlLoader = new FXMLLoader(StartClient.class.getResource("chat-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Чат");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        ChatController chatController = fxmlLoader.getController();
        Network network = new Network();

        chatController.setNetwork(network);

        network.connect();
        network.waitMessage(chatController);*/
    }

    private void openAuthDialog() throws IOException {
        FXMLLoader authLoader = new FXMLLoader(StartClient.class.getResource("auth-view.fxml"));
        Scene scene = new Scene(authLoader.load());

        authStage = new Stage();
        authStage.setTitle("Аутентификация");
        authStage.setResizable(false);
        authStage.setScene(scene);
        authStage.initModality(Modality.WINDOW_MODAL);
        authStage.initOwner(primaryStage);
        authStage.show();

        AuthController authController = authLoader.getController();
        authController.setNetwork(network);
        authController.setStartClient(this);
    }

    private void createChatDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartClient.class.getResource("chat-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Чат");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
//        stage.show();

        ChatController chatController = fxmlLoader.getController();
        chatController.setNetwork(network);
    }

    public static void main(String[] args) {
        launch();
    }
}