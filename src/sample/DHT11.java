package sample;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.scene.control.Label;

// Label won't work in the case of displaying temperature data from DHT11
// So, consider using TableView instead
// DHT11 class is perfect for getting temperature data through input stream

// Do not use input stream to get data
// You may lose marks for using input stream
// You should use CountdownHandler and the SerialPortDataListener

public class DHT11 extends TimerTask implements SerialPortDataListener {
    private byte n;
    private final InputStream inputStream;
    private final Timer timer;
    private  int tempValue = 0;
    String arduinoData;
    //StringBuilder humidity;




    public DHT11(byte timerDuration, Timer timer, InputStream inputStream){
        this.n = timerDuration;
        this.timer = timer;
        this.inputStream = inputStream;
    }
    @Override
    public void run(){
        try {
            if(this.n > 0){
                var data = this.inputStream.read();
                this.n = (byte)(this.n - 1);
                //this.tempValue = data;
                this.arduinoData = String.valueOf(data);
                //System.out.println(this.arduinoData);
                StringBuilder humidity = new StringBuilder(this.arduinoData);
                //System.out.println("Humidity: " + humidity.substring(1,2));
                //System.out.println(data);



            }
            else {
                // We are done reading data
                this.inputStream.close();
                this.timer.cancel();
            }
        }
        catch (IOException e)
        {
            System.out.println("Problem inside the DHT11 run method");
            e.printStackTrace();
        }


    }


    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent){
        if(serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED){
            //this.n = 10;
            //System.out.println("Data Received");
        }
    }
    public int getTempValue(){
        return this.tempValue;
    }



}
