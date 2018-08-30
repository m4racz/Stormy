package com.android.m4racz.stormy.Utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static String getDataFromWeb(String urlString){
        Log.i(TAG, "getDataFromWeb: urlString" + urlString);
        StringBuilder result = new StringBuilder();
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

                result.append(current); //append the char to forcast Result

                data = reader.read(); //Move to the next char until we reach end of the stream
            }

            //return final result
            Log.i(TAG, "result" + result);
            return result.toString();
        } catch (IOException e) {

            e.printStackTrace();

        }
        finally {

            if (myConnection != null) {
                myConnection.disconnect();
            }

        }
        return result.toString();
    }
}
