package com.example.learnengapp.controller;

import com.example.learnengapp.Socket.DeviceHandler;
import com.example.learnengapp.Socket.SocketController;
import com.example.learnengapp.index;
import com.example.learnengapp.model.Vocab;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.learnengapp.controller.ServerDataController.setSavePage;

public class CameraLayoutController implements Initializable {
    @FXML
    public HBox listVocab;
    @FXML
    public Pane detailsPane;
    @FXML
    private Label test;
    @FXML
    private ImageView btnDictionary;
    @FXML
    private ImageView cameraImageView;
    @FXML
    private ImageView btnMyNotebook;

    @FXML
    private WebView webView;
    @FXML
    private ImageView btnChooseTest;

    private ArrayList<Vocab> vocabArrayList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        vocabArrayList = new ArrayList<Vocab>();
//        vocabArrayList = ServerDataController.getData().getFullVocab();
        test.setText("Hi, " + ServerDataController.getData().getUser().getUsername() + "!");
        listVocab.setStyle("-fx-spacing: 20");
        Insets marginInsets = new Insets(10, 0, 0, 0); // top, right, bottom, left
        HBox.setMargin(listVocab, marginInsets);

//        WebEngine engine = webView.getEngine();
//        engine.load("https://www.youtube.com/watch?v=iJb-MIUU7Y4");


        if(ServerDataController.getVocabToShow() != null){
            for (Vocab vocab : ServerDataController.getVocabToShow()){
                Pane pane = new Pane();
                pane.setId(vocab.getIdVocab());
                pane.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8;");

                pane.setPrefHeight(75);
                pane.setPrefWidth(210);
                pane.setMinHeight(75);
                pane.setMinWidth(210);

                Label word = new Label(vocab.getWord());
                pane.getChildren().add(word);
                word.setLayoutX(13);
                word.setLayoutY(11);
                word.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

                Label mean = new Label(vocab.getMean());
                pane.getChildren().add(mean);
                mean.setLayoutX(13);
                mean.setLayoutY(45);
                mean.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
//
//                Label phonetic = new Label(vocab.getPhonetic());
//                pane.getChildren().add(phonetic);
//                phonetic.setLayoutX(13);
//                phonetic.setLayoutY(40);
//                phonetic.setStyle("-fx-font-size: 14;");

                ImageView speaker = new ImageView();
                pane.getChildren().add(speaker);
                speaker.setFitWidth(34);
                speaker.setFitHeight(34);
                speaker.setLayoutX(170);
                speaker.setLayoutY(35);
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/learnengapp/icon/speaker.png")));
                speaker.setImage(image);

                listVocab.getChildren().add(pane);

                pane.setOnMouseClicked(mouseEvent -> {
                    try {
                        DeviceHandler.stopDetect();
                        ServerDataController.getData().setVocab(vocab);
                        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                        loadView(stage, "wordLayout.fxml");
                        setSavePage(1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                pane.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Pane pane = new Pane();
                        detailsPane.getChildren().add(pane);
                        pane.setLayoutX(20);
                        pane.setLayoutY(20);
                        pane.setPrefHeight(105);
                        pane.setPrefWidth(328);
                        pane.setStyle("-fx-border-color: black; -fx-border-radius: 8");

                        Label word = new Label(vocab.getWord());
                        pane.getChildren().add(word);
                        word.setLayoutX(14);
                        word.setLayoutY(7);
                        word.setPrefSize(286, 31);
                        word.setStyle("-fx-font-weight: bold; -fx-font-size: 18");

                        Label mean = new Label(vocab.getMean());
                        pane.getChildren().add(mean);
                        mean.setLayoutX(14);
                        mean.setLayoutY(73);
                        mean.setPrefSize(286, 18);
                        mean.setStyle("-fx-font-family: Inter; -fx-font-size: 14");

                        Label phonetic = new Label(vocab.getPhonetic());
                        pane.getChildren().add(phonetic);
                        phonetic.setLayoutX(14);
                        phonetic.setLayoutY(40);
                        phonetic.setPrefSize(286, 25);
                        phonetic.setStyle("-fx-font-size: 16");

                        Label cd = new Label("Cách dùng");
                        detailsPane.getChildren().add(cd);
                        cd.setLayoutX(20);
                        cd.setLayoutY(134);
                        cd.setStyle("-fx-font-weight: bold; -fx-font-size: 14");

                        Label example = new Label(vocab.getExample());
                        detailsPane.getChildren().add(example);
                        example.setLayoutX(100);
                        example.setLayoutY(131);
                        example.setPrefSize(346, 46);

                        Label imageMH = new Label("Hình ảnh minh họa");
                        detailsPane.getChildren().add(imageMH);
                        imageMH.setLayoutX(20);
                        imageMH.setLayoutY(190);
                        imageMH.setStyle("-fx-font-size: 14");

                        ImageView imageView = new ImageView();
                        detailsPane.getChildren().add(imageView);
                        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/learnengapp/image/" + vocab.getIdVocab() + ".jpg")));
                        imageView.setImage(image);
                        imageView.setFitHeight(126);
                        imageView.setFitWidth(179);
                        imageView.setLayoutX(194);
                        imageView.setLayoutY(181);
                        imageView.setPickOnBounds(true);
                        imageView.setPreserveRatio(true);
                    }
                });

                pane.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        detailsPane.getChildren().clear();
                    }
                });

                speaker.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        // Đường dẫn của file âm thanh trong thư mục resources
                        String audioPath = "/com/example/learnengapp/audio/" + transformIdVocab(vocab.getIdVocab()) + ".mp3";

                        // Lấy URL tuyệt đối của file âm thanh
                        URL url = getClass().getResource(audioPath);

                        if (url != null) {
                            // Tạo một Media object từ URL
                            Media sound = new Media(url.toExternalForm());

                            // Tạo một MediaPlayer từ Media object
                            MediaPlayer mediaPlayer = new MediaPlayer(sound);

                            // Bắt đầu phát âm thanh
                            mediaPlayer.play();
                        } else {
                            System.out.println("Không thể tìm thấy file âm thanh.");
                        }

                        mouseEvent.consume();
                    }
                });
            }
        }


        btnDictionary.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                    DeviceHandler.stopDetect();
//                    loadDictionaryView(stage);
                    loadView(stage, "myDictionaryLayout.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnChooseTest.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    DeviceHandler.stopDetect();
                    Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                    loadView(stage, "choseTestLayout.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnMyNotebook.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    DeviceHandler.stopDetect();
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

    public void reload() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource("cameraLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
//        scene.getStylesheets().add(index.class.getResource("home.css").toExternalForm());
        Stage stage1 = ServerDataController.getData().getStage();
        stage1.setScene(scene);
    }

    public static String transformIdVocab(String input) {
        if (input.startsWith("vcb") && input.length() > 3) {
            return input.substring(3);
        }
        return input;
    }
}
