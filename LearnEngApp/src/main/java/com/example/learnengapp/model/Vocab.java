package com.example.learnengapp.model;

public class Vocab {
    private String idVocab;
    private String word;
    private String mean;
    private String phonetic;
    private String image;
    private String example;

    public String getIdVocab() {
        return idVocab;
    }

    public void setIdVocab(String idVocab) {
        this.idVocab = idVocab;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public Vocab(String idVocab, String word, String mean, String phonetic, String image, String example) {
        this.idVocab = idVocab;
        this.word = word;
        this.mean = mean;
        this.phonetic = phonetic;
        this.image = image;
        this.example = example;
    }
}