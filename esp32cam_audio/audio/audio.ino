
#include "Arduino.h"
#include "SoftwareSerial.h"
#include "DFRobotDFPlayerMini.h"
#include "LiquidCrystal_I2C.h"
#include <String.h>

const int address = 0x27;
LiquidCrystal_I2C lcd(address, 16, 2);

SoftwareSerial mySoftwareSerial(10,11);



DFRobotDFPlayerMini myDFPlayer;
void printDetail(uint8_t type, int value);

void setup()
{
  
  Serial.begin(115200);
  mySoftwareSerial.begin(9600);
  Serial.println(F("Initializing DFPlayer ... (May take 3~5 seconds)"));

  if (!myDFPlayer.begin(mySoftwareSerial)) {  //Use softwareSerial to communicate with mp3.
    Serial.println(F("Unable to begin:"));
    while(true);
  }
  Serial.println(F("DFPlayer Mini online."));

  myDFPlayer.volume(30);  //Set volume value. From 0 to 30
  //myDFPlayer.play(1);  //Play the first mp3

  lcd.init();
  lcd.backlight();
}

int data = 1;
void loop()
{
  String vocab, mean;
  if(Serial.available()){
    String i = Serial.readStringUntil('_'); 
    data = i.toInt();
    myDFPlayer.play(data);
    vocab = Serial.readStringUntil('_'); 
    vocab = vocab.substring(0, vocab.length()-1);
    Serial.println(vocab);
    mean = Serial.readStringUntil('\n'); 
    mean = mean.substring(0, mean.length()-2);
    Serial.println(mean);
  }
  else Serial.println("-1");

  // static unsigned long timer = millis();
  // if (millis() - timer > 1000) {
  //   timer = millis();
  //   myDFPlayer.next();
  //     //Play next mp3 every 3 second.
  // }
  lcd.clear();

  lcd.setCursor(0,0);
  lcd.print(vocab);
  lcd.setCursor(0, 1);
  lcd.print(mean);

  
  // myDFPlayer.play(data);
  
  if (myDFPlayer.available()) {
    printDetail(myDFPlayer.readType(), myDFPlayer.read()); //Print the detail message from DFPlayer to handle different errors and states.
  }
  else Serial.println("False!");
  
  // data++; 
  // if(data == 60) data = 1;

  delay(1000);
}

void printDetail(uint8_t type, int value){
  switch (type) {
    case TimeOut:
      Serial.println(F("Time Out!"));
      break;
    case DFPlayerCardInserted:
      Serial.println(F("Card Inserted!"));
      break;
    case DFPlayerCardRemoved:
      Serial.println(F("Card Removed!"));
      break;
    case DFPlayerCardOnline:
      Serial.println(F("Card Online!"));
      break;
    case DFPlayerPlayFinished:
      Serial.print(F("Number:"));
      Serial.print(value);
      Serial.println(F(" Play Finished!"));
      break;
    case DFPlayerError:
      Serial.print(F("DFPlayerError:"));
      switch (value) {
        case Busy:
          Serial.println(F("Card not found"));
          break;
        case Sleeping:
          Serial.println(F("Sleeping"));
          break;
        case SerialWrongStack:
          Serial.println(F("Get Wrong Stack"));
          break;
        case CheckSumNotMatch:
          Serial.println(F("Check Sum Not Match"));
          break;
        case FileIndexOut:
          Serial.println(F("File Index Out of Bound"));
          break;
        case FileMismatch:
          Serial.println(F("Cannot Find File"));
          break;
        case Advertise:
          Serial.println(F("In Advertise"));
          break;
        default:
          break;
      }
      break;
    default:
      break;
  }
}