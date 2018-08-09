package com.android.m4racz.stormy;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private ImageView weatherIconImage;
    private Context context;

    public DownloadForecast(TextView mWeatherForecast, ImageView mWeatherIcon, Context context) {
        this.weatherForacast = mWeatherForecast;
        this.weatherIconImage =  mWeatherIcon;
        this.context = context;
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

        try {
            String weatherInfo ="";
            String weatherDescription = "";
            String weatherIcon = "";
            String weatherIconToSet = "na";
            JSONObject jsonObject = new JSONObject(result);
            weatherInfo = jsonObject.getString("weather");

            Log.i("MYLOG", weatherInfo);

            JSONArray jsonArray = new JSONArray(weatherInfo);
            for (int i = 0; i < jsonArray.length(); i++){

                JSONObject jsonPart = jsonArray.getJSONObject(i);
                weatherDescription = jsonPart.getString("description");
                weatherIcon = jsonPart.getString("icon");

            }

            Log.i("MYLOG", "weatherDescription" + weatherDescription);
            Log.i("MYLOG", "weatherIcon" + weatherIcon);

            //set forecast text View
            weatherForacast.setText(weatherDescription);

            //set forecast icon according to the code returned
            Hashtable weatherIcons = createWeatherHashTable();
            Set keys = weatherIcons.keySet();
            for (Object key : keys){
                Log.i("weatherIcons", "Value of " + key + " is: " + weatherIcons.get(key));
                if(weatherIcon.equals(key)){
                    weatherIconToSet = (String) weatherIcons.get(key);
                }
            }
            Log.i("MYLOG", "weatherIconToSet: " + weatherIconToSet);
            String PACKAGE_NAME = context.getPackageName();
            int imgID = context.getResources().getIdentifier(PACKAGE_NAME+"drawable/"+weatherIconToSet,null,null);
            Log.i("MYLOG", "imgID: " + imgID);

            weatherIconImage.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),imgID));

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
