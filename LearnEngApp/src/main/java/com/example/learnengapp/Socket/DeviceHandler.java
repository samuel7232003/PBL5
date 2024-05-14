package com.example.learnengapp.Socket;

import com.example.learnengapp.controller.CameraLayoutController;
import com.example.learnengapp.controller.ServerDataController;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class DeviceHandler  extends Thread{
    private String nameDevice;
    private OutputStream osHandler;
    private InputStream isHandler;
    private Socket deviceSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private static boolean checkReload = true;
//    private static String continuee = "continue";
    Scanner sc;

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public DeviceHandler(Socket deviceSocket){
        try {
            sc = new Scanner(System.in);
            this.deviceSocket = deviceSocket;
            this.osHandler = this.deviceSocket.getOutputStream();
            this.isHandler = this.deviceSocket.getInputStream();
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.osHandler, StandardCharsets.UTF_8)); // viết riêng
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.isHandler, StandardCharsets.UTF_8)); // viết riêng
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            String txt = "";
            txt = this.bufferedReader.readLine();
            System.out.println(txt);
            if(txt.contains("CAM")){
                String linkCam = this.bufferedReader.readLine();
                SocketController.setLinkCam(linkCam);
                this.nameDevice = "CAM";
                System.out.println("Đã kết nối đến camera có id: " + SocketController.getLinkCam());
            }
            else if(txt.equals("MODEL")){
                sendLinkCam();
                this.nameDevice = "MODEL";
                System.out.println("Đã kết nối đến model");
                detectByWebcam(); // khởi động camera
            }
            else{
                System.out.println("Đã kết nối đến 1 device chưa biết");
                this.nameDevice = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        TransferVocabForAudio();
        try{
            while (true){
                String header = this.bufferedReader.readLine();
                System.out.println("Header: " + header);
                if (header == null) throw new IOException();
                switch (header){
                    case "RESULT":{
                        if(checkReload == true){
                            String result = this.bufferedReader.readLine();
                            System.out.println("Kết quả được gửi về: " + result);
                            String[] kqList = result.split(";");
                            if(kqList.length !=0 ) {
                                ArrayList<Integer> idWordList = new ArrayList<>();
                                // lấy id của các kết quả
                                for (String kq : kqList) {
                                    String idkq = String.valueOf(ServerDataController.getData().getFullVocabDic().get(kq));
                                    if (idkq != "null") {
                                        int id = Integer.parseInt(idkq);
                                        idWordList.add(id);
                                    }
                                }



                                if(idWordList.size()!=0){
                                    // sắp xếp mảng theo giá trị tăng dần các id
                                    idWordList.sort((o1, o2) -> o1 - o2);
                                    idWordList = removeDuplicate(idWordList);
                                    // gửi id của từ vựng về cho camera và sound
                                    ServerDataController.setVocabToShow(idWordList);
                                    for (DeviceHandler deviceHandler : SocketController.getDevices()) {
                                        if (deviceHandler.nameDevice == "CAM") {
                                            for (int id : idWordList) {
                                                System.out.println(id);
                                                deviceHandler.getBufferedWriter().newLine();
                                                deviceHandler.getBufferedWriter().write("" + id);
                                                deviceHandler.getBufferedWriter().flush();
                                                break;
                                            }
//                                } else if (deviceHandler.nameDevice == "MODEL") {
//                                    deviceHandler.getBufferedWriter().write(continuee);
//                                    deviceHandler.getBufferedWriter().flush();
                                        }
                                    }
                                    reloadOnSocket();
                                }

                            }
                        }
                    }
                }
            }
        } catch (IOException e) {

        }
    }
    public void sendLinkCam(){
        try {

            this.bufferedWriter.write("WEBCAMLink");
            this.bufferedWriter.flush();

            this.bufferedWriter.write(SocketController.getLinkCam());
            this.bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void detectByWebcam(){
        try {
            this.bufferedWriter.write("DETECT");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void stopDetect(){
//        System.out.println("alo alo");
        checkReload = false;
////        continuee = "STOP";
//        try {
//            for(DeviceHandler deviceHandler : SocketController.getDevices()){
//                if(deviceHandler.nameDevice == "MODEL"){
////                    deviceHandler.deviceSocket.close();
//                    deviceHandler.getBufferedWriter().write("STOP");
//                    deviceHandler.getBufferedWriter().flush();
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
    public static void continueDetect(){
        checkReload = true;
    }
    public void reloadOnSocket(){
        Platform.runLater(() ->{
            try {
                ServerDataController.getCameraLayoutController().reload();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public ArrayList<Integer> removeDuplicate(ArrayList<Integer> idWord){
        ArrayList<Integer> newArrayIdWord = new ArrayList<>();
        for(int n : idWord){
            if(!newArrayIdWord.contains(n)){
                newArrayIdWord.add(n);
            }
        }
        return newArrayIdWord;
    }
}
