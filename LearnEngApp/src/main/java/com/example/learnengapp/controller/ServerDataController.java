package com.example.learnengapp.controller;

import com.example.learnengapp.Socket.SocketController;
import com.example.learnengapp.model.ServerData;
import com.example.learnengapp.model.Vocab;

import java.util.ArrayList;

public class ServerDataController {
    private static ServerData data;
    private static SocketController socketController;
    private static ArrayList<Vocab> vocabToShow;

    public static ServerData getData() {
        return data;
    }
    public static void setData(ServerData data) {
        data = data;
    }

    public static SocketController getSocketController() {
        return socketController;
    }

    public static void setSocketController(SocketController socketController) {
        socketController = socketController;
    }
    public ServerDataController() {
        data = new ServerData();
        socketController = new SocketController();
        socketController.openSocket();
    }
    public static Vocab searchVocab(String keyword){
        for(Vocab vocab : data.getFullVocab()){
            if(vocab.getWord().equals(keyword)){
                return vocab;
            }
        }
        return null;
    }
    public void setVocabToShow(ArrayList<Integer> idword){
        // chuyển mảng từ kiêu số int sang kiểu string là các id chính thức: vcb001
        ArrayList<String> idWordList = new ArrayList<>();
        for(int id : idword){
            String a;
            if (id < 10)                a = "vcb00" + id;
            else if (id>=10 && id < 99) a = "vcb0" + id;
            else                        a = "vcb" + id;
            idWordList.add(a);
        }
        // search từ vựng, lấy ra từ đó và ép vào vocabshow
        int n = 0;
        for(Vocab vocab : data.getFullVocab()){
            if(vocab.getIdVocab().equals(idWordList.get(n))){
                this.vocabToShow.add(vocab);
                n++;
            }
            if(n == idWordList.size()) break;
        }
    }

    public static ArrayList<Vocab> getVocabToShow() {
        return vocabToShow;
    }
}

