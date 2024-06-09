import socket
import threading
from ultralytics import YOLO
from time import sleep

class socketController:
    host = ""
    port = 8001
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    connected = False
    detected = False
    message=""
    model = YOLO("../yolov8x-oiv7.pt")  # load a pretrained model (recommended for training)
    webcamLink = ""
    def ConnectToServer(self):
        self.getPCIP()
        self.client.connect((self.host, self.port))
        print("Đã connect tới server app")
        self.connected = True
        self.client.send(bytes("MODEL\r\n", 'UTF-8'))

        receive_thread = threading.Thread(target=self.receive_messages)
        receive_thread.start()

        receive_thread.join()
        # write_thread = threading.Thread(target=self.write_messages)
        # write_thread.start()

    def receive_messages(self):
        while True:
            try:
                message1 = self.client.recv(1024).decode('utf-8')
                print(message1)
                if(message1.strip('') == "WEBCAMLink"):
                    self.setWebcamLink(self.client.recv(1024).decode('utf-8'))
                    print(self.webcamLink)
                elif message1.strip('') == "DETECT":
                    self.detected = True
                    print("Set ", self.detected)
                    self.ShowResultByWebcam()
                elif message1.strip('') == "STOP":
                    self.detected = False
                    print("Set ", self.detected)

            except:
                print("An error Ocurred")
                self.client.close
                break

    def write_messages(self, message):
        self.client.send(bytes(message + "\r\n", 'UTF-8'))

    def getPCIP(self):
        host_name = socket.gethostname()
        self.host = socket.gethostbyname(host_name)

    def ShowResultByWebcam(self):
        detected_classes = []
        if(self.detected == True):
            results = self.model(source=self.webcamLink.strip(),show=True, device='0',stream=True)  # http://192.168.173.130:81/stream
            while True:
                for r in results:
                    boxes = r.boxes
                    for box in boxes:
                        c = box.cls
                        detected_classes.append(self.model.names[int(c)])
                    resultsString = self.ListToString(detected_classes)
                    sleep(1)
                    self.client.send(bytes("RESULT\r\n", 'UTF-8'))
                    self.write_messages(resultsString)
                    print(resultsString)
                    detected_classes.clear()


    def ListToString(self,detectedList):
        result = ""
        for rs in detectedList:
            result += rs + ";"
        return result

    def setWebcamLink(self, link):
        self.webcamLink = link
    def getWebcamLink(self):
        return self.webcamLink