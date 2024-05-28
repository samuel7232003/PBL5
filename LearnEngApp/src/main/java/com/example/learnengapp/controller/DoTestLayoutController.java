package com.example.learnengapp.controller;

import com.example.learnengapp.Socket.DeviceHandler;
import com.example.learnengapp.index;
import com.example.learnengapp.model.Test;
import com.example.learnengapp.model.Vocab;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.*;

import static com.example.learnengapp.DAO.VocabDAO.getVcbById;
import static com.example.learnengapp.DAO.VocabDAO.getVocabById;
import static com.example.learnengapp.controller.ServerDataController.getSaveQuestion;
import static com.example.learnengapp.controller.ServerDataController.listQuestion;

public class DoTestLayoutController implements Initializable {
    @FXML
    public TextField tf_answer;
    @FXML
    public Label lb_no_question;
    @FXML
    public ImageView image_question;
    @FXML
    public Label lb_next;
    @FXML
    public TextField tf_focus;
    @FXML
    public Label lb_mean;
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
    private int count;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listQuestion.clear();
        count = 0;
        for(String idVocab : randomQuestion(getSaveQuestion())){
            Vocab vocab = getVcbById(idVocab);
            Test test = new Test(vocab, "");
            listQuestion.add(test);
        }

        //Danh sách câu hỏi
//        for(Test vocab : listQuestion)
//            System.out.println(vocab.getVocab().getWord());

        //Khởi tạo câu hỏi đầu tiên
        lb_no_question.setText("Câu " + (count + 1) + "/" + getSaveQuestion());
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/learnengapp/image/" + listQuestion.get(count).getVocab().getIdVocab() + ".jpg")));
        image_question.setImage(image);
        lb_mean.setText(listQuestion.get(count).getVocab().getMean());

        tf_focus.requestFocus();

        lb_next.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (count == getSaveQuestion() - 1){
                    listQuestion.get(count).setAnswer(tf_answer.getText());
                    Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                    try {
                        loadView(stage, "doneTestLayout.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    listQuestion.get(count).setAnswer(tf_answer.getText());
                    tf_answer.setText("");
                    tf_focus.requestFocus();
                    count++;
                    if (count == getSaveQuestion() - 1){
                        lb_next.setText("Nộp bài");
                    }
                    lb_no_question.setText("Câu " + (count + 1) + "/" + getSaveQuestion());
                    lb_mean.setText(listQuestion.get(count).getVocab().getMean());
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/learnengapp/image/" + listQuestion.get(count).getVocab().getIdVocab() + ".jpg")));
                    image_question.setImage(image);
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
    }

    public static ArrayList<String> randomQuestion(int number) {
        HashSet<String> uniqueStrings = new HashSet<>();
        Random random = new Random();

        while (uniqueStrings.size() < number) {
            int randomIndex = random.nextInt(601); // random từ 0 đến 600
            String randomString = String.format("vcb%03d", randomIndex);
            uniqueStrings.add(randomString);
        }

        return new ArrayList<>(uniqueStrings);
    }

    public void loadView(Stage stage, String viewURL) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(index.class.getResource(viewURL));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        ServerDataController.getData().setStage(stage);
        ServerDataController.getData().getStage().setScene(scene);
    }
}
