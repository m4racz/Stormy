package com.android.m4racz.stormy.Utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.android.m4racz.stormy.MainActivity;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtilsOpenWeather {
    private static final String TAG = NetworkUtilsOpenWeather.class.getSimpleName();

    private static final String DYNAMIC_WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather";

    private static final String DYNAMIC_FORECAST_URL =
            "https://api.openweathermap.org/data/2.5/forecast";


    private static final String API_KEY =
            "89fd3664a5ad45e46488b6af57b2a5cd";
    /* The units we want our API to return */
    private static final String units = "metric";
    /* The query parameter allows us to provide a location string to the API */
    private static final String APP_ID = "appid";
    private static final String QUERY_PARAM = "q";
    private static final String LAT_PARAM = "lat";
    private static final String LON_PARAM = "lon";

    /* The units parameter allows us to designate whether we want metric units or imperial units */
    private static final String UNITS_PARAM = "units";
    /* The days parameter allows us to designate how many days of weather data we want */

    private URL url = null;
    private URL urlForecast = null;
    public URL[] getUrl(MainActivity mainActivity, String searchType) {

        if (searchType.equals("location")) {

            Double latitude = 0.0;
            Double longitude = 0.0;

            if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(mainActivity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION} , 1);

            } else {

                Log.i(TAG, "getUrl: PERMISSION GRANTED LETS GRAB LOCATION");
                //INIT LOCATION MANAGER


                latitude = mainActivity.location.getLatitude();
                longitude = mainActivity.location.getLongitude();

                Log.i(TAG, "getUrl: lastKnownLocation latitude: " + latitude.toString());
                Log.i(TAG, "getUrl: lastKnownLocation longitude: " + longitude.toString());
                
            }

            Uri uri = buildUrlWithLatitudeLongitude(latitude, longitude);
            Uri uriForecast = buildUrlWithLatitudeLongitudeForecast(latitude, longitude);
            url = null;
            urlForecast = null;
            try {
                urlForecast= new URL(uriForecast.toString());
                url = new URL(uri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "url: " + url);
            return new URL[]{url,urlForecast};
        }
        else if (searchType.equals("input")) {
            // TODO: 11.09.2018 fix me due change to spinner
            String cityToSearch = "Prague";
            Uri uri = buildUrlWithLocationQuery(cityToSearch);
            Uri uriForecast = buildUrlWithLocationQueryForecast(cityToSearch);
            try {
                url = new URL(uri.toString());
                urlForecast = new URL(uriForecast.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "url: " + url);
            return new URL[]{url, urlForecast};
        }
        //nothing else is valid
        return null;
    }

    //Example URL http://api.openweathermap.org/data/2.5/weather?q=london&appid=89fd3664a5ad45e46488b6af57b2a5cd
    private static Uri buildUrlWithLocationQuery(String cityToSearch) {
        String encodedCity = "";
        //encode city to UTF-8
        //encodedCity = UrlEscapers.urlFragmentEscaper().escape(cityToSearch);

        Uri builtURI = Uri.parse(DYNAMIC_WEATHER_URL)
                .buildUpon()
                .appendQueryParameter(QUERY_PARAM, cityToSearch)
                .appendQueryParameter(UNITS_PARAM, units)
                .appendQueryParameter(APP_ID,API_KEY)
                .build();
        return builtURI;
    }

    //Example URL http://api.openweathermap.org/data/2.5/forecast?q=london&appid=89fd3664a5ad45e46488b6af57b2a5cd
    private static Uri buildUrlWithLocationQueryForecast(String cityToSearch) {
        String encodedCity = "";
        //encode city to UTF-8
        //encodedCity = UrlEscapers.urlFragmentEscaper().escape(cityToSearch);

        Uri builtURI = Uri.parse(DYNAMIC_FORECAST_URL)
                .buildUpon()
                .appendQueryParameter(QUERY_PARAM, cityToSearch)
                .appendQueryParameter(UNITS_PARAM, units)
                .appendQueryParameter(APP_ID,API_KEY)
                .build();
        return builtURI;
    }

    //build Uri with location for current weather
    public static Uri buildUrlWithLatitudeLongitude(double latitude, double longitude){
        Uri builtURI = Uri.parse(DYNAMIC_WEATHER_URL)
                .buildUpon()
                .appendQueryParameter(LAT_PARAM, String.valueOf(latitude))
                .appendQueryParameter(LON_PARAM, String.valueOf(longitude))
                .appendQueryParameter(UNITS_PARAM,units)
                .appendQueryParameter(APP_ID,API_KEY)
                .build();
        return builtURI;
    }

    //build Uri with location for forecast weather
    public static Uri buildUrlWithLatitudeLongitudeForecast(double latitude, double longitude){
        Uri builtURI = Uri.parse(DYNAMIC_FORECAST_URL)
                .buildUpon()
                .appendQueryParameter(LAT_PARAM, String.valueOf(latitude))
                .appendQueryParameter(LON_PARAM, String.valueOf(longitude))
                .appendQueryParameter(UNITS_PARAM,units)
                .appendQueryParameter(APP_ID,API_KEY)
                .build();
        return builtURI;
    }
}
