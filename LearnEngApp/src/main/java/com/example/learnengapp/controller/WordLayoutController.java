package com.example.learnengapp.controller;

import com.example.learnengapp.index;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class WordLayoutController implements Initializable {
    private Data data;
    @FXML
    private Label wordVocab;
    @FXML
    private Label meanVocab;
    @FXML
    private Label phoneticVocab;
    @FXML
    private ImageView imageVocab;
    @FXML
    private Label exampleVocab;
    @FXML
    private ImageView backToMain;
    @FXML
    private ImageView cameraImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = new Data();
        System.out.println(data.getUser().getUsername());

        wordVocab.setText(data.getVocab().getWord());
        meanVocab.setText(data.getVocab().getMean());
        phoneticVocab.setText(data.getVocab().getPhonetic());
        exampleVocab.setText(data.getVocab().getExample());

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/learnengapp/image/" + data.getVocab().getImage())));
        imageVocab.setImage(image);

        backToMain.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource("myDictionaryLayout.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 700, 500);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                data.setStage(stage);
                data.getStage().setScene(scene);
            }
        });

        cameraImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource("cameraLayout.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 700, 500);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                data.setStage(stage);
                data.getStage().setScene(scene);
            }
        });
    }

}
