package sample;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListenerWithExceptions;
import java.nio.ByteBuffer;
import javafx.scene.text.Text;



public class DataController implements SerialPortMessageListenerWithExceptions{

    private static final byte[] DELIMITER = new byte[]{'\n'};
    private String temperature;
    private static Text title;

    // Constructor
    public DataController(){
    }

    @Override
    public int getListeningEvents(){
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }
    // Get the temperature from DHT11
    public String getTemperature(){
        return temperature;
    }

    // Send temperature to JavaFx

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent){
        if(serialPortEvent.getEventType() != SerialPort.LISTENING_EVENT_DATA_RECEIVED){
            return; // No temperature Data Received
        }

        var data = serialPortEvent.getReceivedData(); // get Received data
        var convertedData = new String(data);

        //var convertedData = ByteBuffer.wrap(data).getInt(); No Byte Buffer Required for Temperature Data
        System.out.println("DHT11: " + convertedData);
        //Text title = new Text(convertedData);
        title = new Text(convertedData);

        temperature = convertedData;
        // Temperarture data should be in double
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
