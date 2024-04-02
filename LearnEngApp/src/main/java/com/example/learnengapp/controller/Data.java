package com.example.learnengapp.controller;

import com.example.learnengapp.model.User;
import com.example.learnengapp.model.Vocab;
import javafx.stage.Stage;

public class Data {
    private static Stage stage;
    private static User user;
    private static Vocab vocab;

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Data.stage = stage;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Data.user = user;
    }

    public static Vocab getVocab() {
        return vocab;
    }

    public static void setVocab(Vocab vocab) {
        Data.vocab = vocab;
    }

    public Data() {
        Stage stage1 = new Stage();
        stage = stage1;
    }
}
