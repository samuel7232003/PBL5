package com.example.learnengapp.controller;

import com.example.learnengapp.index;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public void login(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource("cameraLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        Stage stage = new Stage();
        stage.setTitle("Photo");
        stage.setScene(scene);
        stage.show();
    }
}
