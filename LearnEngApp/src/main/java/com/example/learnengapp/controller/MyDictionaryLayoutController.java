package com.example.learnengapp.controller;

import com.example.learnengapp.Socket.DeviceHandler;
import com.example.learnengapp.index;
import com.example.learnengapp.model.ServerData;
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

public class MyDictionaryLayoutController  implements Initializable {
    @FXML
    public GridPane listVocablib;
    @FXML
    private ImageView cameraImageView;
    @FXML
    private TextField findWord;
    @FXML
    private ImageView myNotebookView;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listVocablib.setVgap(15);
        listVocablib.setHgap(15);
        listVocablib.setPadding(new Insets(10, 50, 15, 50));

        int row = 0;
        int col = 0;

        for (Vocab vocab : ServerDataController.getData().getFullVocab()) {
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
                    ServerDataController.getData().setVocab(vocab);
                    Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
//                    loadDetailsView(stage);
                    loadView(stage, "wordLayout.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        cameraImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    DeviceHandler.continueDetect();
                    Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
//                    loadCameraView(stage);
                    loadView(stage, "cameraLayout.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        myNotebookView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
//                    loadMyNotebookView(stage);
                    loadView(stage, "myNotebookLayout.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        findWord.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String keyword = stringProcess(findWord.getText());
//                System.out.println(keyword);
                Vocab vocab = ServerDataController.searchVocab(keyword);
//                System.out.println(vocab.getMean());

                if(Objects.equals(findWord.getText(), "")){
                    Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource("myDictionaryLayout.fxml"));
                    Scene scene = null;
                    try {
                        scene = new Scene(fxmlLoader.load(), 700, 500);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    ServerDataController.getData().setStage(stage);
                    ServerDataController.getData().getStage().setScene(scene);

                }
                if(Objects.equals(vocab.getWord(), keyword)){
                    ServerDataController.getData().setVocab(vocab);
                    Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource("wordLayout.fxml"));
                    Scene scene;
                    try {
                        scene = new Scene(fxmlLoader.load(), 700, 500);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    ServerDataController.getData().setStage(stage);
                    ServerDataController.getData().getStage().setScene(scene);
                }else{
                    listVocablib.getChildren().clear();
                    Label label = new Label("Không tìm thấy từ vựng");
                    listVocablib.getChildren().add(label);
                    label.setStyle("-fx-font-size: 24");
                }
            }
        });
    }

    public void loadView(Stage stage, String viewURL) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource(viewURL));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        ServerDataController.getData().setStage(stage);
        ServerDataController.getData().getStage().setScene(scene);
    }

    public static String stringProcess(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        str = str.trim();

        char firstChar = Character.toUpperCase(str.charAt(0));
        String restOfString = str.substring(1).toLowerCase();

        return firstChar + restOfString;
    }
}
