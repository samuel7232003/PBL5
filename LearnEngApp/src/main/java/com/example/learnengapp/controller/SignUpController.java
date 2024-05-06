package com.example.learnengapp.controller;

import com.example.learnengapp.index;
import com.example.learnengapp.model.User;
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
import java.util.Random;
import java.util.ResourceBundle;

import static com.example.learnengapp.DAO.UserDAO.SignUp;
import static com.example.learnengapp.DAO.UserDAO.getUser;

public class SignUpController implements Initializable {
    @FXML
    private TextField tf_username;
    @FXML
    private Label lb_signUp;
    @FXML
    private Label lb_login;
    @FXML
    private TextField tf_focus;
    @FXML
    private Label lb_notify;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tf_focus.requestFocus();

        lb_signUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ArrayList<User> fullUser = ServerDataController.getData().getFullUser();
                String userName = tf_username.getText().trim();

                if(checkUserName(userName, fullUser)){
                    String randomId = null;
                    for (User userr : fullUser) {
                        randomId = "us".concat(String.format("%03d", generateRandomNumber()));

                        while (randomId.equals(userr.getIdUser())) {
                            randomId = "us".concat(String.format("%03d", generateRandomNumber()));
                        }
                    }
                    SignUp(randomId, userName);
                    lb_notify.setText("Đăng kí thành công");
                    lb_notify.setStyle("-fx-text-fill: #00ff1a; -fx-font-weight: bold");
                }else{
                    lb_notify.setText("User Name đã tồn tại");
                    lb_notify.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
                }
            }
        });

        lb_login.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource("login.fxml"));
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
    private int generateRandomNumber() {
        Random random = new Random();
        return  random.nextInt(1000);
    }

    private boolean checkUserName(String usName, ArrayList<User> fullUser){
        for (User user : fullUser) {
            if (usName.equals(user.getUsername())) {
                return false;
            }
        }
        return true;
    }
}
