package com.example.learnengapp.controller;

import com.example.learnengapp.Socket.DeviceHandler;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.learnengapp.DAO.VocabDAO.*;
import static com.example.learnengapp.controller.ServerDataController.getSavePage;


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
    @FXML
    private ImageView myNotebookLayout;
    @FXML
    private ImageView myDictionaryLayout;
    @FXML
    private ImageView speaker;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/learnengapp/image/" + ServerDataController.getData().getVocab().getIdVocab() + ".jpg")));
        imageVocab.setImage(image);

        backToMain.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DeviceHandler.continueDetect();
                Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();

                int homeBack = getSavePage();
                FXMLLoader fxmlLoader;
                if(homeBack == 1){
                    DeviceHandler.continueDetect();
                    fxmlLoader = new FXMLLoader(index.class.getResource("cameraLayout.fxml"));
                } else if (homeBack == 2) {
                    fxmlLoader = new FXMLLoader(index.class.getResource("myDictionaryLayout.fxml"));
                }else
                    fxmlLoader = new FXMLLoader(index.class.getResource("myNotebookLayout.fxml"));

                Scene scene;
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
                DeviceHandler.continueDetect();
                try {
                    Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
//                    loadCameraView(stage);
                    loadView(stage, "cameraLayout.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        myNotebookLayout.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DeviceHandler.stopDetect();
                try {
                    Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
//                    loadCameraView(stage);
                    loadView(stage, "myNotebookLayout.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        myDictionaryLayout.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DeviceHandler.stopDetect();
                try {
                    Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
//                    loadCameraView(stage);
                    loadView(stage, "myDictionaryLayout.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        speaker.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Đường dẫn của file âm thanh trong thư mục resources
                String audioPath = "/com/example/learnengapp/audio/" + transformIdVocab(vocabID) + ".mp3";

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

    public void loadView(Stage stage, String viewURL) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource(viewURL));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        ServerDataController.getData().setStage(stage);
        ServerDataController.getData().getStage().setScene(scene);
    }

    public static String transformIdVocab(String input) {
        if (input.startsWith("vcb") && input.length() > 3) {
            return input.substring(3);
        }
        return input;
    }
}
