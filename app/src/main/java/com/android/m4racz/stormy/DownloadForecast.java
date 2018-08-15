package com.android.m4racz.stormy;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Set;


/**
 * Download Forecast in AsyncTask
 */
public class DownloadForecast extends AsyncTask<String, Void, String> {

    private static final String TAG = DownloadForecast.class.getSimpleName();

    private TextView weatherForacast;
    private TextView weatherTemperatureCurrent;
    private TextView weatherTemperatureMax;
    private TextView weatherTemperatureMin;
    private TextView weatherWindSpeed;
    private ImageView weatherIconImage;
    private TextView weatherCurrentLocation;

    private static Hashtable weatherIcons = new Hashtable<String,String>(){{
        put("01d", "weather_sunny");
        put("01n", "weather_sunny_night");
        put("02d", "weather_few_clouds");
        put("02n", "weather_few_clouds_night");
        put("03d", "weather_scattered_clouds");
        put("03n", "weather_scattered_clouds_night");
        put("04d", "weather_broken_clouds");
        put("04n", "weather_broken_clouds_night");
        put("09d", "weather_drizzle");
        put("10d", "weather_rain");
        put("11d", "weather_thunderstorm");
        put("13d", "weather_snow");
        put("50d", "weather_atmosphere");
        put("weather_na", "weather_na");
    }};

    private Activity mainActivity;
    private Context context;

    DownloadForecast(Context context, MainActivity mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;
    }

    private OpenWeatherCurrentWeather parseJSONgson(String jsonString){
        OpenWeatherCurrentWeather openWeatherCurrentWeather = new OpenWeatherCurrentWeather();
        Gson gson = new Gson();

        openWeatherCurrentWeather = gson.fromJson(jsonString, OpenWeatherCurrentWeather.class);

        return openWeatherCurrentWeather;
    }

    @Override
    protected void onPreExecute() {

        // Set the variable txtView here, after setContentView on the dialog
        // has been called! use dialog.findViewById().

}

    @Override

    protected String doInBackground(String... urls) {
        //Variable declaration
        String forecastResult = "";
        URL url;
        HttpURLConnection myConnection;
        //try to get the weather information from openweather API
        try {
            // TODO: 15.08.2018 change URI builder to function and use append
            url = new URL(urls[0]); //get URL from Varargs in input (like an array)
            myConnection = (HttpURLConnection) url.openConnection(); //opens the connection to passed URL

            InputStream inputStream = myConnection.getInputStream(); //get the input stream

            InputStreamReader reader = new InputStreamReader(inputStream);

            int data = reader.read();
            while (data != -1){
                char current = (char) data; //get current char from the stream

                forecastResult += current; //append the char to forcast Result

                data = reader.read(); //Move to the next char until we reach end of the stream
            }

            //return final result
            Log.i(TAG, "forecastResult" + forecastResult);
            return forecastResult;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);

        //init UI objects
        weatherForacast = mainActivity.findViewById(R.id.xForecastDescription);
        weatherTemperatureCurrent = mainActivity.findViewById(R.id.xTemperatureCurrent);
        weatherTemperatureMax = mainActivity.findViewById(R.id.xTemperatureMax);
        weatherTemperatureMin = mainActivity.findViewById(R.id.xTemperatureMin);
        weatherWindSpeed = mainActivity.findViewById(R.id.xWindSpeed);
        weatherIconImage = mainActivity.findViewById(R.id.xWeatherIcon);
        weatherCurrentLocation = mainActivity.findViewById(R.id.xCurrentLocation);

        OpenWeatherCurrentWeather openWeatherCurrentWeather = parseJSONgson(result);
        if (openWeatherCurrentWeather!=null){
            Log.i(TAG, "onPostExecute openWeatherCurrentWeather:" + openWeatherCurrentWeather.toString());
        }

        if (openWeatherCurrentWeather != null) {

            //set Text Views
            weatherCurrentLocation.setText(String.format("%s, %s", openWeatherCurrentWeather.getName(), openWeatherCurrentWeather.getSys().getCountry()));
            weatherForacast.setText(openWeatherCurrentWeather.getWeather().get(0).getDescription());
            weatherTemperatureCurrent.setText(String.format("%s °C", openWeatherCurrentWeather.getMain().getTemp()));
            weatherTemperatureMax.setText(String.format("Min: %s °C", openWeatherCurrentWeather.getMain().getTemp_max()));
            weatherTemperatureMin.setText(String.format("Max: %s °C", openWeatherCurrentWeather.getMain().getTemp_min()));
            weatherWindSpeed.setText(String.format("Wind: %s m/s", openWeatherCurrentWeather.getWind().getSpeed()));



            //set Image Views
            Set keys = weatherIcons.keySet();
            String weatherIconsGson = openWeatherCurrentWeather.getWeather().get(0).getIcon();
            String weatherIconToSet = "weather_na";
            for (Object key : keys) {
                if (key.equals(weatherIconsGson)) {
                    weatherIconToSet = (String) weatherIcons.get(key);
                }
            }

            String PACKAGE_NAME = context.getPackageName();
            int imgID = context.getResources().getIdentifier(PACKAGE_NAME+"drawable/"+weatherIconToSet,null,null);
            Log.i(TAG, "imgID: " + imgID);

            weatherIconImage.setImageResource(context.getResources().getIdentifier(weatherIconToSet, "drawable", PACKAGE_NAME));


        }
    }
}