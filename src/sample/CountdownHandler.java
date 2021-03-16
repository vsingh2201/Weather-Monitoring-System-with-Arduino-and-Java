package sample;
// CountdownHandler class will fetch Temperature Data
// from the Arduino and send it to JavaFx for display
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

public class CountdownHandler extends TimerTask implements SerialPortDataListener {

    private byte n;
    private final OutputStream outputStream;
    private final Timer timer;


    public CountdownHandler(byte timerDuration, Timer timer, OutputStream outputStream) {
        this.n = timerDuration;
        this.timer = timer;
        this.outputStream = outputStream;
    }
    @Override
    public int getListeningEvents(){
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }
    // serialEvent function is called whenever getListeningEvents function receives data
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent){
        if(serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED){
            // Reset the countdown back to initial stage
            this.n = 10;
            System.out.println("Button Pushed");
        }
    }

    @Override
    public void run(){
        try{
            if(this.n > 0){

                this.outputStream.write(this.n);
                this.n = (byte)(this.n - 1); // typecasting this.n -1 from int to byte
            }
            else{ // we are done and tell the Arduino we are done
                this.outputStream.write(-1);
                this.timer.cancel();
            }
        }
        catch (IOException e)
        {
            System.out.println("Problem inside the countdownhandler run method");
            e.printStackTrace();
        }


    }
}