package com.example.learnengapp.controller;

import com.example.learnengapp.index;
import com.example.learnengapp.model.ServerData;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.learnengapp.DAO.VocabDAO.*;


public class WordLayoutController implements Initializable {
//    private Data data;
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
    @FXML
    private Pane saveVocab;
    @FXML
    private Pane unsaveVocab;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        System.out.println(ServerDataController.getData().getUser().getUsername());
        String userID = ServerDataController.getData().getUser().getIdUser();
        String vocabID = ServerDataController.getData().getVocab().getIdVocab();

        if(checkIdVocabFromIdUser(userID, vocabID)){
            saveVocab.setLayoutX(750);
            saveVocab.setLayoutY(80);

            unsaveVocab.setLayoutX(520);
            unsaveVocab.setLayoutY(80);
        }else {
            saveVocab.setLayoutX(520);
            saveVocab.setLayoutY(80);

            unsaveVocab.setLayoutX(750);
            unsaveVocab.setLayoutY(80);
        }

        wordVocab.setText(ServerDataController.getData().getVocab().getWord());
        meanVocab.setText(ServerDataController.getData().getVocab().getMean());
        phoneticVocab.setText(ServerDataController.getData().getVocab().getPhonetic());
        exampleVocab.setText(ServerDataController.getData().getVocab().getExample());

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/learnengapp/image/" + ServerDataController.getData().getVocab().getImage())));
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
                ServerDataController.getData().setStage(stage);
                ServerDataController.getData().getStage().setScene(scene);
            }
        });

        saveVocab.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setVocabForUser(userID, vocabID);

                saveVocab.setLayoutX(750);
                saveVocab.setLayoutY(80);

                unsaveVocab.setLayoutX(520);
                unsaveVocab.setLayoutY(80);
            }
        });

        unsaveVocab.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                deleteVocabForUser(userID, vocabID);

                saveVocab.setLayoutX(520);
                saveVocab.setLayoutY(80);

                unsaveVocab.setLayoutX(750);
                unsaveVocab.setLayoutY(80);
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
                ServerDataController.getData().setStage(stage);
                ServerDataController.getData().getStage().setScene(scene);
            }
        });
    }

}
