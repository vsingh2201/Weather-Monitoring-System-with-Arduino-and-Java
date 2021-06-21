//EECS1021 Major Project Code
// Indoor Vs Outdoor Weather Monitoring System

#include "DHT.h"
#include <Arduino.h>
#include "Seeed_BMP280.h"
#include <Wire.h>

BMP280 bmp280;// Pressure Sensor on the Grove Board

 
#define DHTPIN 3     // what pin we're connected to
#define DHTTYPE DHT11   // DHT 11 
DHT dht(DHTPIN, DHTTYPE);
 
 
void setup(void) {
  Serial.begin(9600); 
  bmp280.init();
  
  dht.begin();
}
 
void loop(void) {
 
  double temp; 
  double humi;
  double pressure;
  double altitude;
 // Reading the values from DHT11 sensor
  temp = dht.readTemperature();
  humi = dht.readHumidity();
  // Reading the values from BMP280 
  pressure = bmp280.getPressure();
  altitude = bmp280.calcAltitude(pressure);

  String sendTemperature = String(temp,2);//Converting double Temperature Data to String
  String sendHumidity = String(humi,2);// Converting double Humidity Data to String
  String sendPressure = String(pressure,2);// Converting double Pressure data to String
  String sendAltitude = String(altitude,2);// Converting double Altitude data to String
  

  // Combining the Temperature and Humidity String
  sendTemperature.concat(sendHumidity);
  sendTemperature.concat(sendPressure);
  sendTemperature.concat(sendAltitude);
  Serial.println(sendTemperature);// Sending Combined Data
  
}
