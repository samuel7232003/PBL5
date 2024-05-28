package com.example.learnengapp.controller;

import com.example.learnengapp.Socket.DeviceHandler;
import com.example.learnengapp.index;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.learnengapp.controller.ServerDataController.getSaveQuestion;
import static com.example.learnengapp.controller.ServerDataController.setSaveQuestion;

public class ChoseTestLayoutController implements Initializable {
    @FXML
    public ImageView btn_camera;
    @FXML
    public ImageView btn_dictionary;
    @FXML
    public ImageView btn_myNotebook;
    @FXML
    private Pane choose10words;
    @FXML
    private Pane choose15words;
    @FXML
    private Pane choose20words;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choose10words.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setSaveQuestion(10);
//                System.out.println(getSaveQuestion());
                Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                try {
                    loadView(stage, "doTestLayout.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        choose15words.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setSaveQuestion(15);
//                System.out.println(getSaveQuestion());
                Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                try {
                    loadView(stage, "doTestLayout.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        choose20words.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setSaveQuestion(20);
//                System.out.println(getSaveQuestion());
                Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                try {
                    loadView(stage, "doTestLayout.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btn_camera.setOnMouseClicked(new EventHandler<MouseEvent>() {
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

        btn_dictionary.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DeviceHandler.stopDetect();
                try {
                    Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
//                    loadMyNotebookView(stage);
                    loadView(stage, "myDictionaryLayout.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btn_myNotebook.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DeviceHandler.stopDetect();
                try {
                    Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
//                    loadMyNotebookView(stage);
                    loadView(stage, "myNotebookLayout.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
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
}
