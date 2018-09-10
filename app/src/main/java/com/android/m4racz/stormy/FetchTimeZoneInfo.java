package com.android.m4racz.stormy;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.android.m4racz.stormy.Utils.NetworkUtils;
import com.google.gson.Gson;

import java.util.ArrayList;


/**
 * Download Forecast in AsyncTask
 */
public class FetchTimeZoneInfo extends AsyncTask<String, Void, String> {

    private static final String TAG = FetchTimeZoneInfo.class.getSimpleName();

    //Create Progress Dialog to show that something is going on
    private ProgressDialog dialog;

    private Activity mainActivity;
    private Context context;


    FetchTimeZoneInfo(Context context, MainActivity mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {

    }
    @Override
    protected String doInBackground(String... urls) {

        ArrayList<String> result = new ArrayList<String>();
        //try to get the weather information from openweather API
        try {
            String timeZone = NetworkUtils.getDataFromWeb(urls[0]);
            com.android.m4racz.stormy.TimeZone.TimeZone timeZoneObject = parseCurrentTimeZoneJSONgson(timeZone);
            if (timeZoneObject.getTimeZoneId() == null){
                return "UTC";
            }
            return timeZoneObject.getTimeZoneId();
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

    }

    private com.android.m4racz.stormy.TimeZone.TimeZone parseCurrentTimeZoneJSONgson(String jsonString) {
        com.android.m4racz.stormy.TimeZone.TimeZone timeZone;
        Gson gson = new Gson();
        timeZone = gson.fromJson(jsonString, com.android.m4racz.stormy.TimeZone.TimeZone.class);
        return timeZone;
    }
}