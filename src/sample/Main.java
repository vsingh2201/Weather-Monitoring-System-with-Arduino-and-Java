package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.SerialPortService;
import javafx.scene.control.Label;
import javafx.geometry.Insets;

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

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        var sp = SerialPortService.getSerialPort("COM3");
        var outputStream = sp.getOutputStream();

        /*
        You need to add the code for sp.addDataListener and the
        CountdownHandler part, timer for CountdownHandler
         */

        var pane = new BorderPane();
        var label = new Label();

        pane.setCenter(label);
        pane.setPadding(new Insets(0, 20, 0, 20));
        //pane.setStyle("")

        var scene = new Scene(pane, 400, 200);

        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
