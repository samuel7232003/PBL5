package com.example.learnengapp.Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

public class SocketController {
    private ServerSocket serverSocket;
    static ArrayList<DeviceHandler> devices; // Quản lý
    static ArrayList<String> idDevices;
    static ArrayList<String> results;
    static String linkCam = "0";
    static public Hashtable<String, Integer> vocabTest;

    public static String getLinkCam() {
        return linkCam;
    }

    public static void setLinkCam(String linkCam) {
        SocketController.linkCam = linkCam;
    }

    public static ArrayList<DeviceHandler> getDevices() {
        return devices;
    }

    public static void setDevices(ArrayList<DeviceHandler> devices) {
        SocketController.devices = devices;
    }

    public static ArrayList<String> getIdDevices() {
        return idDevices;
    }

    public static ArrayList<String> getResults() {
        return results;
    }

    public static void setResults(ArrayList<String> results) {
        SocketController.results = results;
    }

    public static void setIdDevice(ArrayList<String> idDevices) {
        SocketController.idDevices = idDevices;
    }
    public static void addDevice(DeviceHandler device){devices.add(device);}
    public static void addIdDevice(String idDevice) {idDevices.add(idDevice);}
    public SocketController() {
        idDevices = new ArrayList<>();
        devices = new ArrayList<>();
        results = new ArrayList<>();
        vocabTest = new Hashtable<>();
        vocabTest.put("Boy", 63);
        vocabTest.put("Bottle", 57);
        vocabTest.put("Book", 54);
        vocabTest.put("Building", 70);
        vocabTest.put("Computer keyboard", 127);
        vocabTest.put("Clothing", 115);
        vocabTest.put("Desk",153);
        vocabTest.put("Door", 164);
    }

    public static Hashtable<String, Integer> getVocabTest() {
        return vocabTest;
    }

    public void openSocket(){
        try {
            serverSocket = new ServerSocket(8001);
            new Thread(() -> {
                try {
                    do {
                        System.out.println("Waiting for device");
                        // accept client chờ kết nối
                        Socket deviceSocket = serverSocket.accept();
                        // Tạo một luồng mới
                        DeviceHandler deviceHandler = new DeviceHandler(deviceSocket);
                        addDevice(deviceHandler);
                        // start thread
                        System.out.println("Đã kết nối đến 1 device");
                        deviceHandler.start();
                    } while (serverSocket != null && !serverSocket.isClosed());
                } catch (IOException e) {
                    System.out.println("Server or client socket closed");
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
