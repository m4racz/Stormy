package com.android.m4racz.stormy;

import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadForecast extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        String forecastResult = null;
        URL url;
        HttpURLConnection myConnection;


        try {
            url = new URL(urls[0]); //get URL from Varargs in input (like an array)
            myConnection = (HttpURLConnection) url.openConnection(); //opens the connection to passed URL

            InputStream inputStream = myConnection.getInputStream(); //get the input stream

            InputStreamReader reader = new InputStreamReader(inputStream);


            int data = reader.read();

            while (data !=1){
                char current = (char) data; //get current char from the stream

                forecastResult += current; //append the char to forcastResult

                data = reader.read(); //Move to the next char until we reach end of the stream
            }

            return forecastResult;


        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
