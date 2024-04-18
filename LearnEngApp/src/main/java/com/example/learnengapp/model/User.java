package com.example.learnengapp.model;

import java.util.ArrayList;

public class User {
    private String idUser;
    private String username;
    private ArrayList<String> listIdVocab;

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

    public ArrayList<String> getListIdVocab() {
        return listIdVocab;
    }

    public void setListIdVocab(ArrayList<String> listIdVocab) {
        this.listIdVocab = listIdVocab;
    }

    public User(String idUser, String username) {
        this.idUser = idUser;
        this.username = username;
    }

    public User(String idUser, String username, ArrayList<String> listVocab) {
        this.idUser = idUser;
        this.username = username;
        this.listIdVocab = listVocab;
    }

    public User(User user){
        this.idUser = user.getIdUser();
        this.username = user.getUsername();
        this.listIdVocab = user.getListIdVocab();
    }
}
