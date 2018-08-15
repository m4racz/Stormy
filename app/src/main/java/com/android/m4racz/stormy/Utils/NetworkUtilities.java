package com.android.m4racz.stormy.Utils;

import android.content.Context;

import java.net.URL;

public class NetworkUtilities {
    private static final String TAG = NetworkUtilities.class.getSimpleName();
    private static final String DYNAMIC_WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather";
    /* The format we want our API to return */
    private static final String format = "json";
    /* The units we want our API to return */
    private static final String units = "metric";
    /* The number of days we want our API to return */
    private static final int numDays = 14;

    /* The query parameter allows us to provide a location string to the API */
    private static final String QUERY_PARAM = "q";

    private static final String LAT_PARAM = "lat";
    private static final String LON_PARAM = "lon";

    /* The format parameter allows us to designate whether we want JSON or XML from our API */
    private static final String FORMAT_PARAM = "mode";
    /* The units parameter allows us to designate whether we want metric units or imperial units */
    private static final String UNITS_PARAM = "units";
    /* The days parameter allows us to designate how many days of weather data we want */

    // TODO: 15.08.2018 FINISH URI BUILDER 
    /*
    public static URL getUrl(Context context) {
        boolean isLocationAvaiable;
        isLocationAvaiable = true;
        if (true) {

            return buildUrlWithLatitudeLongitude(latitude, longitude);
        } else {
            String locationQuery = SunshinePreferences.getPreferredWeatherLocation(context);
            return buildUrlWithLocationQuery(locationQuery);
        }
    }
    */
}
