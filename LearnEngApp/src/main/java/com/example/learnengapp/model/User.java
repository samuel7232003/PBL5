package com.example.learnengapp.model;

import java.util.ArrayList;

public class User {
    private String idUser;
    private String username;
    private ArrayList<Vocab> listVocab;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Vocab> getListVocab() {
        return listVocab;
    }

    public void setListVocab(ArrayList<Vocab> listVocab) {
        this.listVocab = listVocab;
    }

    public User(String idUser, String username) {
        this.idUser = idUser;
        this.username = username;
    }

    public User(String idUser, String username, ArrayList<Vocab> listVocab) {
        this.idUser = idUser;
        this.username = username;
        this.listVocab = listVocab;
    }

    public User(User user){
        this.idUser = user.getIdUser();
        this.username = user.getUsername();
        this.listVocab = user.getListVocab();
    }
}
