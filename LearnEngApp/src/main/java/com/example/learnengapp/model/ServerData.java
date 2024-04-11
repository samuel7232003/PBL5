package com.example.learnengapp.model;

import com.example.learnengapp.DAO.VocabDAO;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ServerData {
    private Stage stage;
    private User user;
    private ArrayList<Vocab> fullVocab;
    private Vocab vocab;

    public Vocab getVocab() {
        return vocab;
    }

    public void setVocab(Vocab vocab) {
        this.vocab = vocab;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Vocab> getFullVocab() {
        return fullVocab;
    }

    public void setFullVocab(ArrayList<Vocab> fullVocab) {
        this.fullVocab = fullVocab;
    }

    public ServerData() {
        stage = new Stage();
        fullVocab = new ArrayList<>();
        fullVocab = VocabDAO.getVocab();
    }

    public void setUser(String idUser, String username) {
        user = new User(idUser, username);
    }
}
