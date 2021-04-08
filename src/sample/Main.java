package sample;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.aksingh.owmjapis.api.APIException;
import sample.SerialPortService;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import java.io.IOException;
import java.util.Timer;
import sample.OpenWeatherAPI;
import sample.DHT11;

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

/*
New Ideas
1. You do not need to update the temperature sent by DHT11 dynamically.
Just get the temperature value once and exit the serialEvent method
Display the temperature value on the JavaFx window

Then, here is the deal: You can create an update button at the bottom which
will update all the values like OpenWeather API temp value and DHT11 temp value
and the other values you are thinking about like Barometer sensor

Once you figure out how to get all the values from Arduino and display them on
JavaFx window, the only thing that is left is designing the GUI interface.

Good job! Just keep thinking like this and Major Project will be done in no time

 */

public class Main extends Application {

    // How many times to run the Timer Scheduler
    public static final byte TIMER_DURATION = 10;

    public static void main(String[] args) {

        launch(args);
    }

    public HBox addHBox(){
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15,12,15,12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        Button buttonCurrent = new Button("Outdoor Temperature");
        buttonCurrent.setPrefSize(100,20);

        Button buttonProjected = new Button("Indoor Temperature");
        buttonProjected.setPrefSize(100,20);
        hbox.getChildren().addAll(buttonCurrent,buttonProjected);
        //Text temper = DataController.getTitle();
        //hbox.getChildren().addAll(temper);


        return hbox;
    }

    public VBox addVBox(){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Text title = new Text("Data");
        title.setFont(Font.font("Arial", FontWeight.BOLD,14));
        vbox.getChildren().add(title);

        Hyperlink options[] = new Hyperlink[] {
                new Hyperlink("Sales"),
                new Hyperlink("Marketing"),
                new Hyperlink("Distribution"),
                new Hyperlink("Costs")};

        for (int i=0; i<4; i++) {
            VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
            vbox.getChildren().add(options[i]);
        }

        return vbox;
    }



    @Override
    public void start(Stage primaryStage) {





        //String dht11;
        //dht11 = controller.getTemperature();


        /*
        You need to add the code for sp.addDataListener and the
        CountdownHandler part, timer for CountdownHandler
         */
        //long delayTime = System.currentTimeMillis();
        //long previousTime = 0;

        var serialPort = SerialPortService.getSerialPort("COM3");
        var outputStream = serialPort.getOutputStream();
        var inputStream = serialPort.getInputStream();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            serialPort.closePort();
        }));

        try {
            Thread.sleep(2000);
        } catch (Exception ignored) {}


        var timer = new Timer();
        /*Testing DHT11 class
        var dht11 = new DHT11(TIMER_DURATION,timer,inputStream);
         Testing for only one loop
        var dht11 = new DHT11((byte)4,timer,inputStream);

        serialPort.addDataListener(dht11);
        timer.schedule(dht11,0,1000);*/

        // Testing the Countdown Handler class
        //var countdown = new CountdownHandler(TIMER_DURATION,outputStream);
        //serialPort.addDataListener(countdown);
        //System.out.println("listen: " + countdown.getListeningEvents());
        //timer.schedule(countdown,0,1000);


        // Testing the ArduinoData class here
        var arduinoData = new ArduinoData((byte)2,timer);
        serialPort.addDataListener(arduinoData);
        timer.schedule(arduinoData,0,1000);
        // Here we are testing the Borderpane layout


        //var controller = new DataController(); // To get data from Arduino
        //serialPort.addDataListener(controller);// Used with DataController class

        var pane = new BorderPane();
        var label = new Label();
        //HBox hbox = addHBox();
        //pane.setTop(hbox);
        //pane.setCenter(addVBox());

        // Getting temperature data from the OpenWeather API class
        /* Commented temporarily to avoid unnecessary API calls
        try {
            var temp = sample.OpenWeatherAPI.sendTemperature();
            String displayTemp = String.valueOf(temp);
            label.setText(displayTemp); // Displays the temperature on the Java Fx GUI

        }
        catch (APIException e){
            e.printStackTrace();
            System.out.println("Temperature not available");
        }

         */



        //label.setText(name);



        pane.setPadding(new Insets(0, 20, 0, 20));
        //pane.setStyle("")

        // Printing Temperature data from DHT11 onto the Label
       /* var dht11 = countdown.getLabelData();
        System.out.println("Temperature: " + dht11);
        label.setText(dht11);
        //label.setText("Temperature");
        pane.setCenter(label);*/

        var scene = new Scene(pane, 400, 400);

       // primaryStage.setScene(scene);
        //primaryStage.show();


    }
}
