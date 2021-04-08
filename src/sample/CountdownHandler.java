package sample;
// CountdownHandler class will fetch Temperature Data
// from the Arduino and send it to JavaFx for display

// You do need Countdown Handler for this Project
// You cannot use the DataController from Lab I instead of Countdown Handler
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

// I do not need outputStream in this class

public class CountdownHandler extends TimerTask implements SerialPortDataListener {

    private byte n;
    private final byte timerDuration;
    private final OutputStream outputStream;

    // Constructor
    public CountdownHandler(byte timerDuration, OutputStream outputStream) {
        this.n = timerDuration;
        this.timerDuration = timerDuration;
        this.outputStream = outputStream;
    }
    // Override the run() method from TimerTask
    @Override
    public void run(){
        System.out.println("listen: " + this.getListeningEvents());

    }
    // Overide the getListeningEvents() from jSerialComm.SerialPortDataListener
    @Override
    public int getListeningEvents(){
        System.out.println("rx!!");
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }
    // Override the serialEvent() from jSerialComm.SerialPortEvent
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent){

        if (serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
           // var data = serialPortEvent.getReceivedData(); // get Received data
            //var convertedData = new String(data);


            //System.out.println("DHT11: " + convertedData);
            //this.n = timerDuration; // Reset the Countdown value
        }
    }


}