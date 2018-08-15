package com.android.m4racz.stormy;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Set;
import java.util.function.ToDoubleBiFunction;

public class DownloadForecast extends AsyncTask<String, Void, String> {

    private static final String TAG = DownloadForecast.class.getSimpleName();

    private TextView weatherForacast;
    private TextView weatherTemperatureCurrent;
    private TextView weatherTemperatureMax;
    private TextView weatherTemperatureMin;
    private TextView weatherWindSpeed;
    private ImageView weatherIconImage;

    private Activity mainActivity;
    private Context context;

    DownloadForecast(Context context, MainActivity mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;
    }

    private Hashtable<String,String> parseJSONObject(String jsonString){
        // TODO: 15.08.2018 Use CLASS OpenWeather to Store data instead of stupid hashTable 
        String weatherInfo;
        String weatherMain;
        String weatherWind;
        String weatherSys;

        String weatherDescription = "";
        String weatherIcon = "";
        String weatherTemperatureCurrent;
        String weatherTemperatureMin;
        String weatherTemperatureMax;
        String weatherHumidity;
        String weatherWindSpeed;
        String weatherSunRiseUnix;
        String weatherSunSetUnix;

        JSONObject jsonObject = null;
        Hashtable<String,String> parsedWeather = new Hashtable<>();

        try {
            jsonObject = new JSONObject(jsonString);
            weatherInfo = jsonObject.getString("weather");
            weatherMain = jsonObject.getString("main");
            weatherWind = jsonObject.getString("wind");
            weatherSys = jsonObject.getString("sys");

            weatherTemperatureCurrent = new JSONObject(weatherMain).getString("temp");
            weatherTemperatureMax = new JSONObject(weatherMain).getString("temp_max");
            weatherTemperatureMin = new JSONObject(weatherMain).getString("temp_min");
            weatherHumidity = new JSONObject(weatherMain).getString("humidity");
            weatherWindSpeed = new JSONObject(weatherWind).getString("speed");
            weatherSunRiseUnix = new JSONObject(weatherSys).getString("sunrise");
            weatherSunSetUnix = new JSONObject(weatherSys).getString("sunset");
            Log.i(TAG, "weatherMain" + weatherMain);

            JSONArray jsonArray = new JSONArray(weatherInfo);
            for (int i = 0; i < jsonArray.length(); i++){

                JSONObject jsonPart = jsonArray.getJSONObject(i);
                weatherDescription = jsonPart.getString("description");
                weatherIcon = jsonPart.getString("icon");

            }

            Log.i(TAG, "weatherDescription" + weatherDescription);
            Log.i(TAG, "weatherIcon" + weatherIcon);
            Log.i(TAG, "weatherTemperatureCurrent" + weatherTemperatureCurrent);
            Log.i(TAG, "weatherSunRiseUnix" + weatherSunRiseUnix);
            Log.i(TAG, "weatherSunSetUnix" + weatherSunSetUnix);
            Log.i(TAG, "weatherHumidity " + weatherHumidity);
            Log.i(TAG, "weatherWindSpeed " + weatherWindSpeed );

            //Add Results to HASH TABLE
            parsedWeather.put("weatherDescription",weatherDescription);
            parsedWeather.put("weatherIcon",weatherIcon);
            parsedWeather.put("weatherTemperatureCurrent", weatherTemperatureCurrent);
            parsedWeather.put("weatherTemperatureMax", weatherTemperatureMax);
            parsedWeather.put("weatherTemperatureMin", weatherTemperatureMin);
            parsedWeather.put("weatherHumidity", weatherHumidity);
            parsedWeather.put("weatherWindSpeed", weatherWindSpeed);
            parsedWeather.put("weatherSunRiseUnix", weatherSunRiseUnix);
            parsedWeather.put("weatherSunSetUnix", weatherSunSetUnix);

            return parsedWeather;

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return  null;
    }

    private Hashtable<String, String> createWeatherHashTable(){
        Hashtable<String, String> weatherIcons = new Hashtable<>();
        weatherIcons.put("01d", "weather_sunny");
        weatherIcons.put("01n", "weather_sunny_night");
        weatherIcons.put("02d", "weather_few_clouds");
        weatherIcons.put("02n", "weather_few_clouds_night");
        weatherIcons.put("03d", "weather_scattered_clouds");
        weatherIcons.put("03n", "weather_scattered_clouds_night");
        weatherIcons.put("04d", "weather_broken_clouds");
        weatherIcons.put("04n", "weather_broken_clouds_night");
        weatherIcons.put("09d", "weather_drizzle");
        weatherIcons.put("10d", "weather_rain");
        weatherIcons.put("11d", "weather_thunderstorm");
        weatherIcons.put("13d", "weather_snow");
        weatherIcons.put("50d", "weather_atmosphere");
        weatherIcons.put("weather_na", "weather_na");
        return weatherIcons;
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

        Hashtable<String, String> parsedWeather = parseJSONObject(result);

        if (parsedWeather != null) {
            Log.i(TAG, "parsedWeather" + parsedWeather.toString());
        }


        Set keys = parsedWeather.keySet();
        for (Object key : keys){

            Log.i("parsedWeather", "Value of " + key + " is: " + parsedWeather.get(key));


            //SET WEATHER TEMPERATURES
            if (key.equals("weatherDescription")){
                weatherForacast.setText(parsedWeather.get(key));
            }

            if (key.equals("weatherTemperatureCurrent")){
                weatherTemperatureCurrent.setText(parsedWeather.get(key) + context.getString(R.string.celsiusDegrees));
            }

            if (key.equals("weatherTemperatureMax")){
                weatherTemperatureMax.setText(context.getString(R.string.max) + parsedWeather.get(key) + context.getString(R.string.celsiusDegrees));
            }

            if (key.equals("weatherTemperatureMin")){
                weatherTemperatureMin.setText(context.getString(R.string.min) + parsedWeather.get(key)+ context.getString(R.string.celsiusDegrees));
            }

            if (key.equals("weatherWindSpeed")){
                weatherWindSpeed.setText(context.getString(R.string.wind) + parsedWeather.get(key)+ context.getString(R.string.windSpeedUnit));
            }

            //SET IMAGE
            if (key.equals("weatherIcon")){
                String weatherIconToSet = "weather_na";
                //set forecast icon according to the code returned
                Hashtable weatherIcons = createWeatherHashTable();
                Set keys2 = weatherIcons.keySet();
                for (Object key2 : keys2){
                    Log.i("weatherIcons", "Value of key2" + key2 + " is: " + weatherIcons.get(key2));
                    if(parsedWeather.get(key).equals(key2)){
                        weatherIconToSet = (String) weatherIcons.get(key2);
                    }
                }
                Log.i(TAG, "weatherIconToSet: " + weatherIconToSet);
                String PACKAGE_NAME = context.getPackageName();
                int imgID = context.getResources().getIdentifier(PACKAGE_NAME+"drawable/"+weatherIconToSet,null,null);
                Log.i(TAG, "imgID: " + imgID);

                weatherIconImage.setImageResource(context.getResources().getIdentifier(weatherIconToSet, "drawable", PACKAGE_NAME));
            }
        }
    }
}