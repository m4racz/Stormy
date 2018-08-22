package com.android.m4racz.stormy;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.m4racz.stormy.CurrentWeather.CurrentWeather;
import com.android.m4racz.stormy.ForecastWeather.ForecastWeather;
import com.android.m4racz.stormy.Utils.MathUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;


/**
 * Download Forecast in AsyncTask
 */
public class FetchWeatherInfo extends AsyncTask<String, Void, ArrayList<String>> {

    private static final String TAG = FetchWeatherInfo.class.getSimpleName();

    //Create Progress Dialog to show that something is going on
    private ProgressDialog dialog;

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

    FetchWeatherInfo(Context context, MainActivity mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;
        this.dialog = new ProgressDialog(mainActivity);
    }

    @Override
    protected void onPreExecute() {

        // Set the variable txtView here, after setContentView on the dialog
        // has been called! use dialog.findViewById().
        dialog.setMessage(mainActivity.getString(R.string.progressMessage));
        dialog.show();
}
    @Override
    protected ArrayList<String> doInBackground(String... urls) {

        ArrayList<String> result = new ArrayList<String>();
        //try to get the weather information from openweather API
        try {
            String currentWeather = getDataFromWeb(urls[0]);
            String forecastWeather = getDataFromWeb(urls[1]);
            result.add(currentWeather);
            result.add(forecastWeather);
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    protected void onPostExecute(ArrayList<String> result){
        //create variables for update current weather UI

        TextView weatherForacast;
        TextView weatherTemperatureCurrent;
        TextView weatherTemperatureMax;
        TextView weatherTemperatureMin;
        TextView weatherWindSpeed;
        ImageView weatherIconImage;
        TextView weatherCurrentLocation;

        //init current weather UI objects
        weatherForacast = mainActivity.findViewById(R.id.xForecastDescription);
        weatherTemperatureCurrent = mainActivity.findViewById(R.id.xTemperatureCurrent);
        weatherTemperatureMax = mainActivity.findViewById(R.id.xTemperatureMax);
        weatherTemperatureMin = mainActivity.findViewById(R.id.xTemperatureMin);
        weatherWindSpeed = mainActivity.findViewById(R.id.xWindSpeed);
        weatherIconImage = mainActivity.findViewById(R.id.xWeatherIcon);
        weatherCurrentLocation = mainActivity.findViewById(R.id.xCurrentLocation);

        //init forecast weather UI objects


        //transform json to JAVA CLASS
        CurrentWeather currentWeather = new CurrentWeather();
        ForecastWeather forecastWeather = new ForecastWeather();

        if(result!=null) {
            if (result.size() == 2) {
                try {
                    currentWeather = parseCurrentWeatherJSONgson(result.get(0));
                    forecastWeather = parseForecastWeatherJSONgson(result.get(1));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        //Set update UI with current weather data
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
        if (forecastWeather!=null){
            //create variables for update forecast weather UI
            TextView weatherForecastDay1;
            TextView weatherForecastDay2;
            TextView weatherForecastDay3;
            TextView weatherForecastTempDay1;
            TextView weatherForecastTempDay2;
            TextView weatherForecastTempDay3;
            ImageView weatherForecastIconDay1;
            ImageView weatherForecastIconDay2;
            ImageView weatherForecastIconDay3;

            //init current weather UI objects
            weatherForecastDay1 = mainActivity.findViewById(R.id.xForecastDay1);
            weatherForecastDay2 = mainActivity.findViewById(R.id.xForecastDay2);
            weatherForecastDay3 = mainActivity.findViewById(R.id.xForecastDay3);

            weatherForecastTempDay1 = mainActivity.findViewById(R.id.xForecastTempDay1);
            weatherForecastTempDay2 = mainActivity.findViewById(R.id.xForecastTempDay2);
            weatherForecastTempDay3 = mainActivity.findViewById(R.id.xForecastTempDay3);

            weatherForecastIconDay1 = mainActivity.findViewById(R.id.xForecastIconDay1);
            weatherForecastIconDay2 = mainActivity.findViewById(R.id.xForecastIconDay2);
            weatherForecastIconDay3 = mainActivity.findViewById(R.id.xForecastIconDay3);

            //round temperatures


        }
        //close progress dialog
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }
    private ForecastWeather parseForecastWeatherJSONgson(String jsonString){
        ForecastWeather forecastWeather = new ForecastWeather();
        Gson gson = new Gson();
        forecastWeather = gson.fromJson(jsonString, ForecastWeather.class);
        return forecastWeather;
    }

    private CurrentWeather parseCurrentWeatherJSONgson(String jsonString){
        CurrentWeather currentWeather = new CurrentWeather();
        Gson gson = new Gson();

        currentWeather = gson.fromJson(jsonString, CurrentWeather.class);

        return currentWeather;
    }

    private String getDataFromWeb (String urlString ){

        StringBuilder forecastResult = new StringBuilder();
        HttpURLConnection myConnection = null;
        try {
            URL url = new URL(urlString);
            //HttpURLConnection myConnection;
            myConnection = (HttpURLConnection) url.openConnection(); //opens the connection to passed URL
            InputStream inputStream = myConnection.getInputStream(); //get the input stream

            InputStreamReader reader = new InputStreamReader(inputStream);

            int data = reader.read();
            while (data != -1){
                char current = (char) data; //get current char from the stream

                forecastResult.append(current); //append the char to forcast Result

                data = reader.read(); //Move to the next char until we reach end of the stream
            }

            //return final result
            Log.i(TAG, "forecastResult" + forecastResult);
            return forecastResult.toString();
        } catch (IOException e) {

            e.printStackTrace();

        }
        finally {

            if (myConnection != null) {
                myConnection.disconnect();
            }

        }
        return forecastResult.toString();
    }
}