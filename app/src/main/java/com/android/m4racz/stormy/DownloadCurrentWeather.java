package com.android.m4racz.stormy;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.m4racz.stormy.CurrentWeather.CurrentWeather;
import com.android.m4racz.stormy.Utils.MathUtils;
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
public class DownloadCurrentWeather extends AsyncTask<String, Void, String> {

    private static final String TAG = DownloadCurrentWeather.class.getSimpleName();

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

    DownloadCurrentWeather(Context context, MainActivity mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;
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
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
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

        CurrentWeather currentWeather = parseCurrentWeatherJSONgson(result);

        if (currentWeather !=null){
            Log.i(TAG, "onPostExecute currentWeather:" + currentWeather.toString());
        }

        if (currentWeather != null) {

            //Round Temperatures
            int currentTemperature = MathUtils.getRoundedTemperature(currentWeather.getMain().getTemp());
            int minTemperature = MathUtils.getRoundedTemperature(currentWeather.getMain().getTempMin());
            int maxTemperature = MathUtils.getRoundedTemperature(currentWeather.getMain().getTempMax());

            //set Text Views
            weatherCurrentLocation.setText(String.format("%s, %s", currentWeather.getName(), currentWeather.getSys().getCountry()));
            weatherForacast.setText(currentWeather.getWeather().get(0).getDescription());
            weatherTemperatureCurrent.setText(String.format("%s °C", currentTemperature));
            weatherTemperatureMax.setText(String.format("Min: %s °C", maxTemperature));
            weatherTemperatureMin.setText(String.format("Max: %s °C", minTemperature));
            weatherWindSpeed.setText(String.format("Wind: %s m/s", currentWeather.getWind().getSpeed()));

            //set Image Views
            Set keys = weatherIcons.keySet();
            String weatherIconsGson = currentWeather.getWeather().get(0).getIcon();
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

    private CurrentWeather parseCurrentWeatherJSONgson(String jsonString){
        CurrentWeather currentWeather = new CurrentWeather();
        Gson gson = new Gson();

        currentWeather = gson.fromJson(jsonString, CurrentWeather.class);

        return currentWeather;
    }
}