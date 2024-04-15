package com.example.learnengapp.controller;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
    @FXML
    private ImageView btnDictionary;
    @FXML
    private ImageView cameraImageView;

    private ArrayList<Vocab> vocabArrayList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        vocabArrayList = new ArrayList<Vocab>();
//        vocabArrayList = ServerDataController.getData().getFullVocab();
        test.setText("Hello " + ServerDataController.getData().getUser().getUsername());
        listVocab.setStyle("-fx-spacing: 20");
        Insets marginInsets = new Insets(10, 0, 0, 0); // top, right, bottom, left
        HBox.setMargin(listVocab, marginInsets);

        if(ServerDataController.getVocabToShow() != null){
            for (Vocab vocab : ServerDataController.getVocabToShow()){
//                for (Vocab vocab : ServerDataController.getData().getFullVocab()){
                Pane pane = new Pane();
                pane.setId(vocab.getIdVocab());
                pane.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8;");

                pane.setPrefHeight(90);
                pane.setPrefWidth(175);
                pane.setMinHeight(90);
                pane.setMinWidth(175);

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

                ImageView speaker = new ImageView();
                pane.getChildren().add(speaker);
                speaker.setFitWidth(34);
                speaker.setFitHeight(34);
                speaker.setLayoutX(125);
                speaker.setLayoutY(35);
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/learnengapp/icon/speaker.png")));
                speaker.setImage(image);

                listVocab.getChildren().add(pane);

                pane.setOnMouseClicked(mouseEvent -> {
                    try {
                        ServerDataController.getData().setVocab(vocab);
                        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                        loadDetailsView(stage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                speaker.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        // Đường dẫn của file âm thanh trong thư mục resources
                        String audioPath = "/com/example/learnengapp/audio/" + transformIdVocab(vocab.getIdVocab()) + ".mp3";
                        System.out.println(transformIdVocab(vocab.getIdVocab()));

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
                    loadDictionaryView(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void loadDetailsView(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource("wordLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        ServerDataController.getData().setStage(stage);
        ServerDataController.getData().getStage().setScene(scene);
    }

    public void loadDictionaryView(Stage stage)throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource("myDictionaryLayout.fxml"));
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
        // Trả về chuỗi gốc nếu không phù hợp với định dạng "vcbxxx"
        return input;
    }
}
