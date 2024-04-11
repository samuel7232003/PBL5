package com.example.learnengapp.controller;

import com.example.learnengapp.Socket.SocketController;
import com.example.learnengapp.model.ServerData;
import com.example.learnengapp.model.Vocab;

public class ServerDataController {
    private static ServerData data;
    private static SocketController socketController;

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
    }
    public static Vocab searchVocab(String keyword){
        for(Vocab vocab : data.getFullVocab()){
            if(vocab.getWord().equals(keyword)){
                return vocab;
            }
        }
        return null;
    }
}

