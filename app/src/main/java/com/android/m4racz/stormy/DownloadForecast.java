package com.android.m4racz.stormy;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
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

public class DownloadForecast extends AsyncTask<String, Void, String> {
    TextView txtView;



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

        }
        catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
