package sample;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.model.CurrentWeather;

import java.math.BigDecimal;
import java.math.RoundingMode;


// This class is used to obtain weather data from the OpenWeather API
public class OpenWeatherAPI {

    public OpenWeatherAPI(){};

    public double getTemperature() throws APIException{
        // declaring object of "OWM" class
        OWM owm = new OWM("ADD YOUR API KEY HERE");
        CurrentWeather cwd = owm.currentWeatherByCityName("Toronto");

        var currentTemp = cwd.getMainData().getTemp();
        var printTemp = currentTemp -273.5;
        // Format the printTemp to have only 2 decimal places
        // Using BigDecimal
        BigDecimal bd = BigDecimal.valueOf(printTemp);
        bd.setScale(2, RoundingMode.HALF_UP);
        var reducedTempValue = bd.intValue();// Converts it to Int

        return reducedTempValue;
    }

    public double getHumidity() throws APIException{
        // declaring object of "OWM" class
        OWM owm = new OWM("710d0b11cdff5de6ce42a0e2c369b70f");
        CurrentWeather cwd = owm.currentWeatherByCityName("Toronto");


        var humidity = cwd.getMainData().getHumidity();

        return humidity;
    }
    public double getPressure() throws APIException{
        OWM owm = new OWM("710d0b11cdff5de6ce42a0e2c369b70f");

        // getting current weather data for the "London" city
        CurrentWeather cwd = owm.currentWeatherByCityName("Toronto");
        // Getting pressure data
        var pressure = cwd.getMainData().getPressure();
        return pressure;
    }

}
