package com.example.learnengapp.controller;

import com.example.learnengapp.DAO.UserDAO;
import com.example.learnengapp.index;
import com.example.learnengapp.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.learnengapp.DAO.UserDAO.getUser;

public class LoginController implements Initializable {
    @FXML
    private TextField tf_username;
    @FXML
    private Label signUp;
    @FXML
    private TextField tf_focus;
    @FXML
    private ServerDataController serverDataController;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serverDataController = new ServerDataController();
        tf_focus.requestFocus();

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

        signUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource("signUp.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 700, 500);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ServerDataController.getData().setStage(stage);
                ServerDataController.getData().getStage().setScene(scene);
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
//        stage.show();
    }

    public void loginEvent() throws IOException {
        String username = tf_username.getText().trim();
        User user = UserDAO.getUserByName(username);
        if(Objects.equals(user.getUsername(), "")) {
            tf_focus.requestFocus();
            tf_username.clear();
            tf_username.setPromptText("Ten dang nhap khong ton tai");
            tf_username.setStyle("-fx-prompt-text-fill: red; -fx-font-weight: bold");
        }
        else {
            ServerDataController.getData().setUser(user);
            loadHome(ServerDataController.getData().getStage());
        }
    }
}
