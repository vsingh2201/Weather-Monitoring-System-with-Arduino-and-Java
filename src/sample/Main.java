package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.aksingh.owmjapis.api.APIException;
import sample.SerialPortService;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import java.io.IOException;
import java.util.Timer;
import sample.OpenWeatherAPI;

/*
Current Goals in the Major Project
1. To get temperature data through DHT11 and Arduino from the Countdown Handler
and display it on the JavaFx label.
2. To get temperature data from the OpenWeather API and
display it on the Java Fx label.
3. If parts 1 and 2 are successfully completed do some research on
the layout/display of Java Fx
4. Find out how you can add CSS stylesheet.
5. Figure out if you can use the barometer sensor on Grove board in similiar way
6. To add additional functionality, you can consider using the TableView
from Lab I Part 3 and make a datalogger.
7. See if you can make use of Matix keypad or Serial LCD in this Major Project.
 */

public class Main extends Application {

    // How many times to run the Timer Scheduler
    public static final byte TIMER_DURATION = 10;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        /*var sp = SerialPortService.getSerialPort("COM3");
        var outputStream = sp.getOutputStream();



        // at runtime ... we want to close down nicely.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try{
                //close the data stream
                outputStream.close();
            }catch (IOException e){
                System.out.println("We had a problem shutting down the program.");
                e.printStackTrace();
            }
            // closes the serial (USB) port connection to the Arduino
            sp.closePort();
        }));
        try {
            Thread.sleep(2000);
        } catch (Exception ignored) {}

        var timer = new Timer();
        var countdown = new CountdownHandler(TIMER_DURATION, timer, outputStream);

        // Added addDataListener to this class
        sp.addDataListener(countdown);

        // run the scheduler as many times as requested
        //delay(1000);
        timer.schedule(countdown,0,1000);

        // things will run ... until they are done
*/

        /*
        You need to add the code for sp.addDataListener and the
        CountdownHandler part, timer for CountdownHandler
         */

        var pane = new BorderPane();
        var label = new Label();

        // Getting temperature data from the OpenWeather API class
        try {
            var temp = sample.OpenWeatherAPI.sendTemperature();
            String displayTemp = String.valueOf(temp);
            label.setText(displayTemp); // Displays the temperature on the Java Fx GUI

        }
        catch (APIException e){
            e.printStackTrace();
            System.out.println("Temperature not available");
        }

        //label.setText(name);

        pane.setCenter(label);
        pane.setPadding(new Insets(0, 20, 0, 20));
        //pane.setStyle("")

        var scene = new Scene(pane, 400, 200);

        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
