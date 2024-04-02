package com.example.learnengapp.controller;

import com.example.learnengapp.index;
import com.example.learnengapp.model.Vocab;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class CameraLayoutController implements Initializable {
    @FXML
    public HBox listVocab;
    @FXML
    private Label test;
    public Data data;
    private ArrayList<Vocab> vocabArrayList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = new Data();
        test.setText("Hello " + data.getUser().getUsername());
        listVocab.setStyle("-fx-spacing: 20");

        vocabArrayList = new ArrayList<>();
        vocabArrayList.add(new Vocab("1", "fish", "con ca", "/fish/", "imageFish", "fishFish"));
        vocabArrayList.add(new Vocab("2", "apple", "qua tao", "/apple/", "imageFish", "appleApple"));
        for (Vocab vocab : vocabArrayList){
            Pane pane = new Pane();
            pane.setId(vocab.getWord());
//            pane.setBackground(Background.fill(Paint.valueOf("#d9d9d9")));
            pane.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8");

            pane.setPrefHeight(84);
            pane.setPrefWidth(189);

            Label word = new Label(vocab.getWord());
            pane.getChildren().add(word);
            word.setLayoutX(13);
            word.setLayoutY(11);
            word.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

            Label mean = new Label(vocab.getMean());
            pane.getChildren().add(mean);
            mean.setLayoutY(45);
            mean.setLayoutX(13);
            mean.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

            Label phonetic = new Label(vocab.getPhonetic());
            pane.getChildren().add(phonetic);
            phonetic.setLayoutX(95);
            phonetic.setLayoutY(17);
            phonetic.setStyle("-fx-font-size: 14; -fx-font-weight: light");

            ImageView speaker = new ImageView();
            pane.getChildren().add(speaker);
            speaker.setFitWidth(34);
            speaker.setFitHeight(34);
            speaker.setLayoutX(96);
            speaker.setLayoutY(41);
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/learnengapp/icon/sound_max_light.png")));
            speaker.setImage(image);

            listVocab.getChildren().add(pane);

            pane.setOnMouseClicked(mouseEvent -> {
                try {
                    data.setVocab(vocab);
                    loadDetailsView(data.getStage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    void loadDetailsView(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource("wordLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Detail Word");
        stage.setScene(scene);
        stage.show();
    }
}
