package com.android.m4racz.stormy;
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

public class DownloadForecast extends AsyncTask<String, Void, String> {
    private TextView weatherForacast;
    private TextView weatherTemperatureCurrent;
    private TextView weatherTemperatureMax;
    private TextView weatherTemperatureMin;
    private TextView weatherWindSpeed;
    private ImageView weatherIconImage;


    private Context context;

    DownloadForecast(TextView mWeatherForecast, ImageView mWeatherIcon, TextView mWeatherTemperatureCurrent, TextView mWeatherTemperatureMin, TextView mWeatherTemperatureMax, TextView mWeatherWindspeed, Context context) {
        this.weatherForacast = mWeatherForecast;
        this.weatherIconImage =  mWeatherIcon;
        this.weatherTemperatureCurrent = mWeatherTemperatureCurrent;
        this.weatherTemperatureMax = mWeatherTemperatureMax;
        this.weatherTemperatureMin = mWeatherTemperatureMin;
        this.weatherWindSpeed = mWeatherWindspeed;
        this.context = context;
    }

    private Hashtable<String,String> parseJSONObject(String jsonString){

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

        Log.i("parseJSONObject", "parseJSONObject starts");

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

            Log.i("MYLOG", "weatherInfo" + weatherInfo);
            Log.i("MYLOG", "weatherMain" + weatherMain);

            JSONArray jsonArray = new JSONArray(weatherInfo);
            for (int i = 0; i < jsonArray.length(); i++){

                JSONObject jsonPart = jsonArray.getJSONObject(i);
                weatherDescription = jsonPart.getString("description");
                weatherIcon = jsonPart.getString("icon");

            }

            Log.i("MYLOG", "weatherDescription" + weatherDescription);
            Log.i("MYLOG", "weatherIcon" + weatherIcon);
            Log.i("MYLOG", "weatherTemperatureCurrent" + weatherTemperatureCurrent);
            Log.i("MYLOG", "weatherSunRiseUnix" + weatherSunRiseUnix);
            Log.i("MYLOG", "weatherSunSetUnix" + weatherSunSetUnix);
            Log.i("MYLOG", "weatherHumidity " + weatherHumidity);
            Log.i("MYLOG", "weatherWindSpeed " + weatherWindSpeed );

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
        weatherIcons.put("01d", "sunny");
        weatherIcons.put("01n", "sunny_night");
        weatherIcons.put("02d", "few_clouds");
        weatherIcons.put("02n", "few_clouds_night");
        weatherIcons.put("03d", "scattered_clouds");
        weatherIcons.put("03n", "scattered_clouds_night");
        weatherIcons.put("04d", "broken_clouds");
        weatherIcons.put("04n", "broken_clouds_night");
        weatherIcons.put("09d", "drizzle");
        weatherIcons.put("10d", "rain");
        weatherIcons.put("11d", "thunderstorm");
        weatherIcons.put("13d", "snow");
        weatherIcons.put("50d", "atmosphere");
        weatherIcons.put("na", "na");
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
            Log.i("MYLOG", "forecastResult" + forecastResult);
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

        Hashtable<String, String> parsedWeather = parseJSONObject(result);

        if (parsedWeather != null) {
            Log.i("MYLOG", "parsedWeather" + parsedWeather.toString());
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
                String weatherIconToSet = "na";
                //set forecast icon according to the code returned
                Hashtable weatherIcons = createWeatherHashTable();
                Set keys2 = weatherIcons.keySet();
                for (Object key2 : keys2){
                    Log.i("weatherIcons", "Value of key2" + key2 + " is: " + weatherIcons.get(key2));
                    if(parsedWeather.get(key).equals(key2)){
                        weatherIconToSet = (String) weatherIcons.get(key2);
                    }
                }
                Log.i("MYLOG", "weatherIconToSet: " + weatherIconToSet);
                String PACKAGE_NAME = context.getPackageName();
                int imgID = context.getResources().getIdentifier(PACKAGE_NAME+"drawable/"+weatherIconToSet,null,null);
                Log.i("MYLOG", "imgID: " + imgID);

                weatherIconImage.setImageResource(context.getResources().getIdentifier(weatherIconToSet, "drawable", PACKAGE_NAME));
            }
        }
    }
}