package com.example.learnengapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = new Data();
        System.out.println(data.getUser().getUsername());

        wordVocab.setText(data.getVocab().getWord());
        meanVocab.setText(data.getVocab().getMean());
        phoneticVocab.setText(data.getVocab().getPhonetic());
        exampleVocab.setText(data.getVocab().getExample());

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/learnengapp/image/" + data.getVocab().getWord() + ".jpg")));
        imageVocab.setImage(image);
    }
}
