package com.example.learnengapp.controller;

import com.example.learnengapp.DAO.UserDAO;
import com.example.learnengapp.index;
import com.example.learnengapp.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.learnengapp.DAO.UserDAO.getUser;

public class LoginController implements Initializable {
    @FXML
    public TextField tf_username;
    private ServerDataController serverDataController;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serverDataController = new ServerDataController();

        tf_username.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    loginEvent();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void login(MouseEvent event) throws IOException {
        loginEvent();
    }

    public void loadHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource("cameraLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Camera");
        stage.setScene(scene);
    }

    public void loginEvent() throws IOException {
        String username = tf_username.getText().trim();
        User user = UserDAO.getUserByName(username);
        if(user == null) {
            tf_username.setText("Ten dang nhap khong ton tai");
        }
        else {
            ServerDataController.getData().setUser(user);
            loadHome(ServerDataController.getData().getStage());
        }
    }
}
