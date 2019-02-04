#include <SoftwareSerial.h>

SoftwareSerial mySerial(2, 3); // RX, TX  
// Connect HM10      Arduino Uno
//     TXD          Pin 2
//     RXD          Pin 3

void setup() {  
  Serial.begin(9600);
  Serial.println("start"); 
  lcd.begin(16, 2);
  // If the baud rate of the HM-10 module has been updated,
  // you may need to change 9600 by another value
  // Once you have found the correct baudrate,
  // you can update it using AT+BAUDx command 
  // e.g. AT+BAUD0 for 9600 bauds
  mySerial.begin(9600);
  mySerial.println("start");

}

void loop() { 
  
  // Read the values from the connected sensors
  int wind = analogRead(A0);
  int wave = analogRead(A1);
  int solar = analogRead(A2);
  //  int ground = analogRead(A5);

  // Convert the values to the specified units
  float voltage_wind = wind * (5.0 / 1024.0);
  float voltage_wave = wave * (5.0 / 1024.0);
  float voltage_solar = solar * (5.0 / 1024.0);
  //  float voltage_ground = ground * (5.0 / 1024.0);
  
  if (voltage_wave <= 0.05){
    voltage_wave = 0;
  }

  //Display values on Arduino serial monitor 
  Serial.print("Wind:");    
  Serial.println(voltage_wind);
  Serial.print("Wave:");    
  Serial.println(voltage_wave);
  Serial.print("Solar:");    
  Serial.println(voltage_solar);

  // Send values through the HM-10 device connected to the mySerial pins
  // The Android application reads these values as Strings delimited by new-line characters
  mySerial.println(voltage_wind); 
  mySerial.println(voltage_wave); 
  mySerial.println(voltage_solar); 
  
  // stop the program for for <sensorValue> milliseconds: (update rate)
  delay(1000); 
}
/****************************************/
