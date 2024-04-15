package com.example.learnengapp.controller;

import com.example.learnengapp.Socket.SocketController;
import com.example.learnengapp.model.ServerData;
import com.example.learnengapp.model.Vocab;

import java.io.IOException;
import java.util.ArrayList;

public class ServerDataController {
    private static ServerData data;
    private static SocketController socketController;
    private static CameraLayoutController cameraLayoutController;
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

    public static CameraLayoutController getCameraLayoutController() {
        return cameraLayoutController;
    }

    public ServerDataController() {
        data = new ServerData();
        cameraLayoutController = new CameraLayoutController();
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
    public static void setVocabToShow(ArrayList<Integer> idword) throws IOException {
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
                vocabToShow.add(vocab);
                n++;
            }
            if(n == idWordList.size()) break;
        }
        getCameraLayoutController().reload();
    }

    public static ArrayList<Vocab> getVocabToShow() {
        return vocabToShow;
    }
}

