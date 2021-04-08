//Temperature and Humidity Sensor
#include "DHT.h"
#include <Arduino.h>
#include <U8x8lib.h>

 
#define DHTPIN 3     // what pin we're connected to
#define DHTTYPE DHT11   // DHT 11 
DHT dht(DHTPIN, DHTTYPE);
 
U8X8_SSD1306_128X64_NONAME_HW_I2C u8x8(/* reset=*/ U8X8_PIN_NONE);
 
void setup(void) {
  Serial.begin(9600); 
  //Serial.println("DHTxx test!");
  
  dht.begin();
  u8x8.begin();
  u8x8.setPowerSave(0);  
  u8x8.setFlipMode(1);
}
 
void loop(void) {
 
  double temp, humi;
  //int temp,humi;
  temp = dht.readTemperature();
  humi = dht.readHumidity();
  /* This part was for InputStream in Java
  Serial.write(temp);
  delay(1000);
  Serial.write(humi);
  No Longer Required using*/
  //Converting double Temperature Data to String
  String sendTemperature = String(temp,2);
  // Converting double Humidity Data to String
  String sendHumidity = String(humi,2);
  // Sending the DHT11 Sensor Data

  // Combining the Temperature and Humidity String
  sendTemperature.concat(sendHumidity);// Combined String
  Serial.println(sendTemperature);// Sending Combined Data
  //Serial.println(sendHumidity);
 
  u8x8.setFont(u8x8_font_chroma48medium8_r);
  u8x8.setCursor(0, 33);
  u8x8.print("Temp:");
  u8x8.print(temp);
  u8x8.print("C");
  u8x8.setCursor(0,50);
  u8x8.print("Humidity:");
  u8x8.print(humi);
  u8x8.print("%");
  u8x8.refreshDisplay();
  delay(200);
}
