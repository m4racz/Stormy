package com.android.m4racz.stormy.Utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.android.m4racz.stormy.MainActivity;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

public class NetworkUtilities {
    private static final String TAG = NetworkUtilities.class.getSimpleName();

    private static final String DYNAMIC_WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather";

    private static final String API_KEY =
            "89fd3664a5ad45e46488b6af57b2a5cd";
    /* The format we want our API to return */
    private static final String format = "json";
    /* The units we want our API to return */
    private static final String units = "metric";
    /* The number of days we want our API to return */
    private static final int numDays = 14;

    /* The query parameter allows us to provide a location string to the API */
    private static final String APP_ID = "appid";
    private static final String QUERY_PARAM = "q";
    private static final String LAT_PARAM = "lat";
    private static final String LON_PARAM = "lon";

    /* The format parameter allows us to designate whether we want JSON or XML from our API */
    private static final String FORMAT_PARAM = "mode";
    /* The units parameter allows us to designate whether we want metric units or imperial units */
    private static final String UNITS_PARAM = "units";
    /* The days parameter allows us to designate how many days of weather data we want */

    public static URL getUrl(MainActivity mainActivity, Context context, String searchType) {


        if (searchType.equals("location")) {

            Double latitude = 0.0;
            Double longitude = 0.0;

            if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(mainActivity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION} , 1);

            } else {

                Log.i(TAG, "getUrl: PERMISSION GRANTED LETS GRAB LOCATION");

                Location lastKnownLocation = mainActivity.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                latitude = lastKnownLocation.getLatitude();
                longitude = lastKnownLocation.getLongitude();
                Log.i(TAG, "getUrl: lastKnownLocation latitude: " + latitude.toString());
                Log.i(TAG, "getUrl: lastKnownLocation longitude: " + longitude.toString());
                
            }



            Uri uri = buildUrlWithLatitudeLongitude(latitude, longitude);

        } else {
            String cityToSearch = mainActivity.mInputCity.getText().toString();
            Uri uri = buildUrlWithLocationQuery(cityToSearch);
            URL url = null;

            try {
                url = new URL(uri.toString());
            } catch (MalformedURLException e) {

                e.printStackTrace();
            }
            Log.i(TAG, "url: " + url);

            return url;
        }

        return null;
    }
    //Example URL http://api.openweathermap.org/data/2.5/weather?q=london&appid=89fd3664a5ad45e46488b6af57b2a5cd
    private static Uri buildUrlWithLocationQuery(String cityToSearch) {
        String encodedCity = "";
        //encode city to UTF-8
        try {
            encodedCity = URLEncoder.encode(cityToSearch, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Uri builtURI = Uri.parse(DYNAMIC_WEATHER_URL)
                .buildUpon()
                .appendQueryParameter(QUERY_PARAM, encodedCity)
                .appendQueryParameter(UNITS_PARAM, units)
                .appendQueryParameter(APP_ID,API_KEY)
                .build();
        return builtURI;
    }

    // TODO: 17.08.2018 imlementace GPS lokace 
    public static Uri buildUrlWithLatitudeLongitude(double Latitude, double Longitude){

        return null;
    }


}
