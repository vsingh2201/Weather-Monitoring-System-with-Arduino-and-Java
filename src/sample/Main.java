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



public class Main extends Application {

    // How many times to run the Timer Scheduler
    public static final byte TIMER_DURATION = 10;

    public static void main(String[] args) {

        launch(args);
    }

    public HBox addHBox() {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("hbox");
        Text title = new Text("Indoor Vs Outdoor Data");
        title.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        hbox.getChildren().add(title);
        return hbox;
    }

    public VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8.0);

        Text options[] = new Text[]{
                new Text("Temperature"),
                new Text("Humidity"),
                new Text("Pressure")
        };

        for (int i = 0; i < 3; i++) {
            VBox.setMargin(options[i], new Insets(15, 0, 0, 15));
            options[i].setFont(Font.font("Arial", FontWeight.NORMAL, 32));
            options[i].setLineSpacing(4.0);
            vbox.getChildren().add(options[i]);
        }

            return vbox;
        }


        @Override
        public void start (Stage primaryStage){


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
            } catch (Exception ignored) {
            }


            var timer = new Timer();





            // Testing the ArduinoData class here
            var arduinoData = new ArduinoData((byte) 2, timer);
            serialPort.addDataListener(arduinoData);
            timer.schedule(arduinoData, 0, 1000);
            // Here we are testing the Borderpane layout

            var pane = new BorderPane();
            HBox hbox = addHBox();
            var databox = new VBox();
            databox.setPadding(new Insets(10));
            databox.setSpacing(8);
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

            // Getting data from the OpenWeather API and displaying on JavaFx Window
            OpenWeatherAPI weatherData = new OpenWeatherAPI();
            // Temperature Data for Toronto
            try{

                var temperature = weatherData.getTemperature();
                System.out.println("Temperature: " + temperature);
                Text tempValue = new Text(String.valueOf(temperature));
                VBox.setMargin(tempValue,new Insets(15,0,0,15));
                tempValue.setFont(Font.font("Arial",FontWeight.NORMAL,32));
                tempValue.setLineSpacing(4.0);
                databox.getChildren().add(tempValue);

            }
            catch (APIException e){
                e.printStackTrace();
                System.out.println("Weather Data not available");


            }
            // Humidity data for Toronto

            try{
                var humidity = weatherData.getHumidity();
                System.out.println("Humidity: " + humidity);
                Text humidityValue = new Text(String.valueOf(humidity));
                VBox.setMargin(humidityValue,new Insets(15,0,0,15));
                humidityValue.setFont(Font.font("Arial",FontWeight.NORMAL,32));
                humidityValue.setLineSpacing(4.0);
                databox.getChildren().add(humidityValue);
            }
            catch (APIException e){
                e.printStackTrace();
                System.out.println("Humidity data not available");
            }
            // Pressure Data

            try{
                var pressure = weatherData.getPressure();
                System.out.println("Pressure: " + pressure);
                Text pressureValue = new Text(String.valueOf(pressure));
                VBox.setMargin(pressureValue,new Insets(15,0,0,15));
                pressureValue.setFont(Font.font("Arial",FontWeight.NORMAL,32));
                pressureValue.setLineSpacing(4.0);
                databox.getChildren().add(pressureValue);
            }
            catch (APIException e){
                e.printStackTrace();
                System.out.println("Pressure data not available");
            }

            pane.setTop(hbox);
            pane.setLeft(addVBox());
            pane.setRight(databox);


            pane.setPadding(new Insets(0, 20, 0, 20));
            //pane.setStyle("")


            var scene = new Scene(pane, 400, 400);
            // Adding CSS stylesheet
            scene.getStylesheets().add(Main.class.getResource("Styles.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle("EECS1021 Major Project");
            primaryStage.show();


        }
    }

