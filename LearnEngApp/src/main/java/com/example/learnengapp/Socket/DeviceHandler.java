package com.example.learnengapp.Socket;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DeviceHandler  extends Thread{
    private OutputStream osHandler;
    private InputStream isHandler;
    private Socket deviceSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
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

                SocketController.addIdDevice("CAMSOUND");
                System.out.println("Đã kết nối đến camera có id: " + SocketController.getLinkCam());
            }
            else if(txt.equals("MODEL")){
                sendLinkCam();
                SocketController.addIdDevice("MODEL");
                System.out.println("Đã kết nối đến model");
                detectByWebcam(); // khởi động camera
            }
            else{
                System.out.println("Đã kết nối đến 1 device chưa biết");
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
                        while (true){
                            String result = this.bufferedReader.readLine();
                            System.out.println("Kết quả được gửi về: " + result);
                            String[] kqList = result.split(";");

//                            int cnt = 0;
//                            for (String idDV : SocketController.getIdDevices()){
//                                if(idDV.equals("CAMSOUND")){
//                                    for (String kq : kqList){
//                                        System.out.println(SocketController.getVocabTest().get(kq));
//                                        SocketController.getDevices().get(cnt).getBufferedWriter().newLine();
//                                        SocketController.getDevices().get(cnt).getBufferedWriter().write("" + SocketController.getVocabTest().get(kq));
//                                        SocketController.getDevices().get(cnt).getBufferedWriter().flush();
//                                    }
//                                }
//                                cnt++;
//                            }

                            for (DeviceHandler deviceHandler :  SocketController.getDevices()){
                                for(String kq : kqList){
                                    String kqt = String.valueOf(SocketController.getVocabTest().get(kq));
                                    if(kqt!="null") {
                                        System.out.println(kqt);
                                        deviceHandler.getBufferedWriter().newLine();
                                        deviceHandler.getBufferedWriter().write("" + kqt);
                                        deviceHandler.getBufferedWriter().flush();
                                    }
                                }
                                break;
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
//
//            this.bufferedWriter.write(SocketController.getLinkCam());
//            this.bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void TransferVocabForAudio(){
        try {
            int n = 1;
            while (true){
                try {
                    Thread.sleep(3000); // 1000 milliseconds = 1 giây
                } catch (InterruptedException e) {
                    // Xử lý ngoại lệ nếu cần
                }
                this.bufferedWriter.newLine();
                this.bufferedWriter.write("" + n);
                this.bufferedWriter.flush();
                n+=2;
                if (n == 199) n = 1;
            }
//            for(int i = 1; i < 20; i+=2) {
//                this.bufferedWriter.newLine();
//                this.bufferedWriter.write("" + i);
//                this.bufferedWriter.flush();
//            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void TransferVocabForAudio(String vocab){
        // viết hàm get data để xuwr lý chuỗi
        try {
            this.bufferedWriter.newLine();
            this.bufferedWriter.write(vocab);
            this.bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
