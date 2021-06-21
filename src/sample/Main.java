package sample;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.aksingh.owmjapis.api.APIException;
import sample.SerialPortService;
import javafx.geometry.Insets;
import java.io.IOException;
import java.util.Timer;
import sample.OpenWeatherAPI;
import sample.DHT11;



public class Main extends Application {


    public static void main(String[] args) {

        launch(args);
    }

    // HBox for the top part of the BorderPane Layout
    public HBox addHBox() {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("hbox");
        Text title = new Text("Outdoor Data From API");
        title.setFont(Font.font("Arial", FontWeight.NORMAL, 40));
        hbox.getChildren().add(title);
        return hbox;
    }
    // VBox to display all the options
    public VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8.0);
        vbox.setStyle("-fx-background-color: #A4EBF3");

        Text options[] = new Text[]{
                new Text("City"),
                new Text("Temperature"),
                new Text("Humidity"),
                new Text("Pressure")
        };

        for (int i = 0; i < 4; i++) {
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


            try {
                Thread.sleep(2000);
            } catch (Exception ignored) {
            }


            var timer = new Timer();// Creating a Timer Object

            // Creating a arduinoData Object to receive data from Arduino
            var arduinoData = new ArduinoData((byte) 2, timer);
            serialPort.addDataListener(arduinoData);
            timer.schedule(arduinoData, 0, 1000);

            // BorderPane Layout
            var pane = new BorderPane();
            HBox hbox = addHBox();// For top pane
            var databox = new VBox(); // For Right Pane
            databox.setPadding(new Insets(10));
            databox.setSpacing(8);
            Text cityName = new Text("Toronto");
            databox.getChildren().add(cityName);
            VBox.setMargin(cityName,new Insets(15,0,0,15));
            cityName.setFont(Font.font("Arial",FontWeight.NORMAL,32));
            cityName.setLineSpacing(4.0);

            // Getting data from the OpenWeather API and displaying on JavaFx Window
            OpenWeatherAPI weatherData = new OpenWeatherAPI();
            // Temperature Data for Toronto
            try{

                var temperature = weatherData.getTemperature();
                //System.out.println("Temperature: " + temperature);
                Text tempValue = new Text(String.valueOf(temperature) + "C");
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
                //System.out.println("Humidity: " + humidity);
                Text humidityValue = new Text(String.valueOf(humidity) + "%");
                VBox.setMargin(humidityValue,new Insets(15,0,0,15));
                humidityValue.setFont(Font.font("Arial",FontWeight.NORMAL,32));
                humidityValue.setLineSpacing(4.0);
                databox.getChildren().add(humidityValue);
            }
            catch (APIException e){
                e.printStackTrace();
                System.out.println("Humidity data not available");
            }
            // Pressure Data for Toronto

            try{
                var pressure = weatherData.getPressure();
                //System.out.println("Pressure: " + pressure);
                Text pressureValue = new Text(String.valueOf(pressure + "Pa"));
                VBox.setMargin(pressureValue,new Insets(15,0,0,15));
                pressureValue.setFont(Font.font("Arial",FontWeight.NORMAL,32));
                pressureValue.setLineSpacing(4.0);
                databox.getChildren().add(pressureValue);
            }
            catch (APIException e){
                e.printStackTrace();
                System.out.println("Pressure data not available");
            }
            // Setting up the BorderPane Layout
            pane.setTop(hbox);
            pane.setLeft(addVBox());
            pane.setRight(databox);

            pane.setPadding(new Insets(0, 20, 0, 20));


            var scene = new Scene(pane, 500, 500);
            // Adding CSS stylesheet
            scene.getStylesheets().add(Main.class.getResource("Styles.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle("EECS1021 Major Project: Indoor Vs Outdoor Weather Data");
            primaryStage.show();


        }
    }

