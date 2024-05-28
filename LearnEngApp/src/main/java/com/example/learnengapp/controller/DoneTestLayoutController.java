package com.example.learnengapp.controller;

import com.example.learnengapp.Socket.DeviceHandler;
import com.example.learnengapp.index;
import com.example.learnengapp.model.Test;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.learnengapp.controller.ServerDataController.*;

public class DoneTestLayoutController implements Initializable {
    @FXML
    public VBox list_answer;
    @FXML
    public Label lb_done;
    @FXML
    public Label lb_sum;
    @FXML
    public ImageView btn_camera;
    @FXML
    public ImageView btn_dictionary;
    @FXML
    public ImageView btn_chooseTest;
    @FXML
    public ImageView btn_myNotebook;
    @FXML
    public ImageView btn_back;
    private int numberAnswerCorrect;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list_answer.setStyle("-fx-spacing: 20");
        Insets paddingInsets = new Insets(0, 25, 0, 25); // top, right, bottom, left
        list_answer.setPadding(paddingInsets);
        numberAnswerCorrect = 0;

        for (Test test : listQuestion){
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8;");

            pane.setPrefSize(450, 120);
            pane.setMinSize(450, 120);

            ImageView imageView = new ImageView();
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/learnengapp/image/" + test.getVocab().getIdVocab() + ".jpg")));
            imageView.setImage(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            pane.getChildren().add(imageView);
            imageView.setLayoutX(30);
            imageView.setLayoutY(11);

            Label lb_answer = new Label("Đáp án: " + test.getVocab().getWord());
            pane.getChildren().add(lb_answer);
            lb_answer.setLayoutX(150);
            lb_answer.setLayoutY(30);
            lb_answer.setStyle("-fx-font-size: 18");

            Label lb_your_answer = new Label("Câu trả lời: " + test.getAnswer());
            pane.getChildren().add(lb_your_answer);
            lb_your_answer.setLayoutX(150);
            lb_your_answer.setLayoutY(60);
            if (test.getVocab().getWord().equalsIgnoreCase(test.getAnswer().trim())){
                lb_your_answer.setStyle("-fx-text-fill: #3ecb3e; -fx-font-size: 18");
                numberAnswerCorrect++;
            }else {
                lb_your_answer.setStyle("-fx-text-fill: red; -fx-font-size: 18");
            }

            Label lb_detail = new Label("Chi tiết");
            pane.getChildren().add(lb_detail);
            lb_detail.setLayoutX(380);
            lb_detail.setLayoutY(50);
            lb_detail.setPrefSize(100, 50);
            lb_detail.setStyle("-fx-background-color: d2d2d2; " +
                    "-fx-background-radius: 8; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-size: 16; " +
                    "-fx-alignment: center; " +
                    "fx-border-color: black; " +
                    "-fx-border-radius: 8; " +
                    "-fx-background-color: white; " +
                    "-fx-text-fill: #7b7b7b");

            list_answer.getChildren().add(pane);

            lb_detail.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    ServerDataController.getData().setVocab(test.getVocab());
                    Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                    try {
                        loadView(stage, "wordLayout.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    setSavePage(4);
                }
            });
        }

        lb_sum.setText("Tổng số câu trả lời đúng là: " + numberAnswerCorrect + "/" + getSaveQuestion());

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

        btn_chooseTest.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DeviceHandler.stopDetect();
                try {
                    Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
//                    loadMyNotebookView(stage);
                    loadView(stage, "choseTestLayout.fxml");
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

        btn_back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DeviceHandler.stopDetect();
                try {
                    Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
//                    loadMyNotebookView(stage);
                    loadView(stage, "choseTestLayout.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        lb_done.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DeviceHandler.stopDetect();
                try {
                    Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
//                    loadMyNotebookView(stage);
                    loadView(stage, "choseTestLayout.fxml");
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
