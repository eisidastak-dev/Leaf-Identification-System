package com.example.ca1dsa2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AutumnLeaveSystem extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AutumnLeaveSystem.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 900);
        stage.setTitle("Autumn Leave Identification System");
        stage.setScene(scene);
        stage.show();
        stage.setMaxHeight(900);
        stage.setMaxWidth(1400);
        stage.setMinHeight(900);
        stage.setMinWidth(1400);
    }

    public static void main(String[] args) {
        launch();
    }
}