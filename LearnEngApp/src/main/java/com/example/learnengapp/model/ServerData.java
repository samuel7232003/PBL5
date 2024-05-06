package com.example.learnengapp.model;

import com.example.learnengapp.DAO.UserDAO;
import com.example.learnengapp.DAO.VocabDAO;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Hashtable;

public class ServerData {
    private Stage stage;
    private User user;
    private ArrayList<Vocab> fullVocab;
    private ArrayList<User> fullUser;
    private Vocab vocab;
    private Hashtable<String, Integer> fullVocabDic;
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

    public ArrayList<User> getFullUser(){
        return fullUser;
    }

    public void setFullVocab(ArrayList<Vocab> fullVocab) {
        this.fullVocab = fullVocab;
    }

    public ServerData() {
        stage = new Stage();
        // get data from SQL
        fullVocab = new ArrayList<>();
        fullVocab = VocabDAO.getVocab();
        fullUser = new ArrayList<>();
        fullUser = UserDAO.getUser();
        // set data to dictionary
        fullVocabDic = new Hashtable<>();
        setFullVocabDic();


    }

    public void setUser(String idUser, String username) {
        user = new User(idUser, username);
    }
    public void setFullVocabDic(){
        for(Vocab vocab1 : this.fullVocab){
            int stt = Integer.parseInt(vocab1.getIdVocab().substring(3));
            this.fullVocabDic.put(vocab1.getWord(), stt);
        }
    }
    public Hashtable<String, Integer> getFullVocabDic(){ return  this.fullVocabDic;}
}
