package sample;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.model.CurrentWeather;

public class OpenWeatherAPI {

    public OpenWeatherAPI(){};

    public double getTemperature() throws APIException{
        // declaring object of "OWM" class
        OWM owm = new OWM("710d0b11cdff5de6ce42a0e2c369b70f");

        // getting current weather data for the "London" city
        CurrentWeather cwd = owm.currentWeatherByCityName("Toronto");

        //printing city name from the retrieved data
        System.out.println("City: " + cwd.getCityName());

        // printing the max./min. temperature
        System.out.println("Temperature: " + cwd.getMainData().getTempMax()
                + "/" + cwd.getMainData().getTempMin() + "\'K");

        var currentTemp = cwd.getMainData().getTemp();
        var printTemp = currentTemp -273.5;

        //System.out.println("Current Temperature: " + printTemp + " C");

        return printTemp;
    }

    public double getHumidity() throws APIException{
        // declaring object of "OWM" class
        OWM owm = new OWM("710d0b11cdff5de6ce42a0e2c369b70f");

        // getting current weather data for the "London" city
        CurrentWeather cwd = owm.currentWeatherByCityName("Toronto");

        //printing city name from the retrieved data
        //System.out.println("City: " + cwd.getCityName());

        // printing the max./min. temperature
        //System.out.println("Temperature: " + cwd.getMainData().getTempMax()
        //  + "/" + cwd.getMainData().getTempMin() + "\'K");


        var humidity = cwd.getMainData().getHumidity();// Added today



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


    // Consider using Wind Data or other Things instead of altitude
}