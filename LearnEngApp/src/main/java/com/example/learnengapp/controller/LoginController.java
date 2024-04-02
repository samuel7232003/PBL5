package com.example.learnengapp.controller;

import com.example.learnengapp.index;
import com.example.learnengapp.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public TextField tf_username;

    public ArrayList<User> listUser;

    public Data data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = new Data();
        listUser = new ArrayList<>();
        listUser.add(new User("1", "Huy"));
        listUser.add(new User("2", "VietThanh"));
        listUser.add(new User("3", "DucThanh"));
    }

    public void login(MouseEvent event) throws IOException {
        String username = tf_username.getText().trim();
        for(int i = 0; i < listUser.size(); i++){
            User user = listUser.get(i);
            if(!username.equals(user.getUsername())){
                tf_username.setText("Ten dang nhap khong ton tai");
            }else{
//                tf_username.setText("Dang nhap thanh cong");
                data.setUser(new User(user.getIdUser(), user.getUsername()));
                loadHome(data.getStage());
            }
        }
    }

    public void loadHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource("cameralayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Camera");
        stage.setScene(scene);
        stage.show();
    }
}
