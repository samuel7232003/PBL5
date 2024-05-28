package com.example.learnengapp.model;

public class Test {
    private Vocab vocab;
    private String answer;

    public Test(Vocab vocab, String answer) {
        this.vocab = vocab;
        this.answer = answer;
    }

    public Vocab getVocab() {
        return vocab;
    }

    public void setVocab(Vocab vocab) {
        this.vocab = vocab;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
