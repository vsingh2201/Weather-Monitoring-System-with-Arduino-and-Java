package sample;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListenerWithExceptions;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

/* Next thing you need to try is
Add a output stream in this class and send the data received from the
OpenWeatherAPI to the Arduino and display it on the LCD
* */
public class ArduinoData extends TimerTask implements SerialPortMessageListenerWithExceptions{

    private byte n;
    private final Timer timer;
    private static final byte[] DELIMITER = new byte[]{'\n'};

    public ArduinoData(byte timerDuration, Timer timer){
        this.n = timerDuration;
        this.timer = timer;
    }

    @Override
    public int getListeningEvents(){
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }
    // serialEvent function is called whenever getListeningEvents function receives data
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent){
        if(serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED) {

            if(this.n > 0) {
                var data = serialPortEvent.getReceivedData(); // get Received data
                var convertedData = new String(data);// Converting Received bytes into String
                StringBuilder receivedData = new StringBuilder(convertedData);// Creating a StringBuilder Object to separate Data values
                System.out.println("Indoor Data from the DHT11 and BMP280 Sensors");
                System.out.println("Temperature: " + receivedData.substring(0,5) + "C");// Printing Temperature
                System.out.println("Humidity: " + receivedData.substring(5,10) + "%");// Printing Humidity
                System.out.println("Pressure: " + receivedData.substring(10,18) + "Pa");// Printing Pressure
                System.out.println("Altitude: " + receivedData.substring(18,24)+ "m");// Printing Altitude
                //System.out.println("Original Data: " + convertedData);// To print the whole combined thing

            }

        }

    }
    @Override
    public void run(){

        // You may reset the countdown back to initial stage
        // Or decrease it to a certain number of times here

        this.n = (byte)(this.n - 1);
        // Decrement the counter when we receive data from Arduino
    }
    @Override
    public void catchException(Exception e){
        e.printStackTrace();
    }

    @Override
    public byte[] getMessageDelimiter(){
        return DELIMITER;
    }

    @Override
    public boolean delimiterIndicatesEndOfMessage(){
        return true;
    }

}
