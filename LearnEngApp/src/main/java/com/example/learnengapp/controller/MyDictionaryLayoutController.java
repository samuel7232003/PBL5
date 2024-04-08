package com.example.learnengapp.controller;

import com.example.learnengapp.index;
import com.example.learnengapp.model.Vocab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.learnengapp.DAO.VocabDAO.getVocab;
import static com.example.learnengapp.DAO.VocabDAO.getVocabByWord;

public class MyDictionaryLayoutController  implements Initializable {
    Data data = new Data();
    private ArrayList<Vocab> listVocab;
    @FXML
    public GridPane listVocablib;
    @FXML
    private ImageView cameraImageView;
    @FXML
    private TextField findWord;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listVocab = new ArrayList<>();
        listVocab = getVocab();

        listVocablib.setVgap(15);
        listVocablib.setHgap(15);
        listVocablib.setPadding(new Insets(10, 50, 15, 50));

        int row = 0;
        int col = 0;

        for (Vocab vocab : listVocab) {
            Pane pane = new Pane();
            pane.setId(vocab.getIdVocab());
            pane.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8;");

            pane.setPrefHeight(90);
            pane.setPrefWidth(160);
            pane.setMinSize(160, 90);
            pane.setMaxSize(160, 90);

            Label word = new Label(vocab.getWord());
            pane.getChildren().add(word);
            word.setLayoutX(13);
            word.setLayoutY(11);
            word.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

            Label mean = new Label(vocab.getMean());
            pane.getChildren().add(mean);
            mean.setLayoutX(13);
            mean.setLayoutY(60);
            mean.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

            Label phonetic = new Label(vocab.getPhonetic());
            pane.getChildren().add(phonetic);
            phonetic.setLayoutX(13);
            phonetic.setLayoutY(40);
            phonetic.setStyle("-fx-font-size: 14;");

            listVocablib.add(pane, col, row);

            // Di chuyển tới cột tiếp theo
            col++;

            // Nếu đã đến cột cuối cùng, chuyển sang hàng mới
            if (col == 2) {
                col = 0;
                row++;
            }

            pane.setOnMouseClicked(mouseEvent -> {
                try {
                    data.setVocab(vocab);
                    Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                    loadDetailsView(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        cameraImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                    loadCameraView(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

//        findWord.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                String keyword = findWord.getText();
//                keyword = stringProcess(keyword);
//                System.out.println(keyword);
//                Vocab vocab = new Vocab(getVocabByWord(keyword));
//                if(vocab.getIdVocab() != ""){
//                    if(vocab.getWord().equals(keyword)){
//                        System.out.println(vocab.getMean());
//                        listVocablib.getChildren().clear();
//
//                        Pane pane = new Pane();
//                        pane.setId(vocab.getIdVocab());
//                        pane.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8;");
//
//                        pane.setPrefHeight(90);
//                        pane.setPrefWidth(160);
//                        pane.setMinSize(160, 90);
//                        pane.setMaxSize(160, 90);
//
//                        Label word = new Label(vocab.getWord());
//                        pane.getChildren().add(word);
//                        word.setLayoutX(13);
//                        word.setLayoutY(11);
//                        word.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
//
//                        Label mean = new Label(vocab.getMean());
//                        pane.getChildren().add(mean);
//                        mean.setLayoutX(13);
//                        mean.setLayoutY(60);
//                        mean.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
//
//                        Label phonetic = new Label(vocab.getPhonetic());
//                        pane.getChildren().add(phonetic);
//                        phonetic.setLayoutX(13);
//                        phonetic.setLayoutY(40);
//                        phonetic.setStyle("-fx-font-size: 14;");
//
//                        listVocablib.add(pane, 0, 0);
//                    }else{
//                        listVocablib.getChildren().clear();
//                    }
//
//                }else if(vocab.getIdVocab() == ""){
//                    int roww = 0;
//                    int coll = 0;
//
//                    for (Vocab vocabb : listVocab) {
//                        Pane pane = new Pane();
//                        pane.setId(vocabb.getIdVocab());
//                        pane.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8;");
//
//                        pane.setPrefHeight(90);
//                        pane.setPrefWidth(160);
//                        pane.setMinSize(160, 90);
//                        pane.setMaxSize(160, 90);
//
//                        Label word = new Label(vocabb.getWord());
//                        pane.getChildren().add(word);
//                        word.setLayoutX(13);
//                        word.setLayoutY(11);
//                        word.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
//
//                        Label mean = new Label(vocabb.getMean());
//                        pane.getChildren().add(mean);
//                        mean.setLayoutX(13);
//                        mean.setLayoutY(60);
//                        mean.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
//
//                        Label phonetic = new Label(vocabb.getPhonetic());
//                        pane.getChildren().add(phonetic);
//                        phonetic.setLayoutX(13);
//                        phonetic.setLayoutY(40);
//                        phonetic.setStyle("-fx-font-size: 14;");
//
//                        listVocablib.add(pane, coll, roww);
//
//                        // Di chuyển tới cột tiếp theo
//                        coll++;
//
//                        // Nếu đã đến cột cuối cùng, chuyển sang hàng mới
//                        if (coll == 2) {
//                            coll = 0;
//                            roww++;
//                        }
//
//                        pane.setOnMouseClicked(mouseEvent -> {
//                            try {
//                                data.setVocab(vocab);
//                                Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
//                                loadDetailsView(stage);
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                        });
//                    }
//                }
//            }
//        });
    }
    public void loadDetailsView(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource("wordLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        data.setStage(stage);
        data.getStage().setScene(scene);
    }

    public void loadCameraView(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource("cameraLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        data.setStage(stage);
        data.getStage().setScene(scene);
    }

    public static String stringProcess(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        // Xóa khoảng trắng ở cả hai đầu
        str = str.trim();

        char firstChar = Character.toUpperCase(str.charAt(0));
        String restOfString = str.substring(1).toLowerCase();

        return firstChar + restOfString;
    }
}
