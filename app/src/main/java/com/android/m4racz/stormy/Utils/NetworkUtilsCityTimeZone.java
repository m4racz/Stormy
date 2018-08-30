package com.android.m4racz.stormy.Utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.android.m4racz.stormy.FetchTimeZoneInfo;
import com.android.m4racz.stormy.MainActivity;
import com.google.common.net.UrlEscapers;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

public class NetworkUtilsCityTimeZone {
    private static final String TAG = NetworkUtilsCityTimeZone.class.getSimpleName();
    //Example URL https://maps.googleapis.com/maps/api/timezone/json?location=39.6034810,-119.6822510&timestamp=1331161200&key=AIzaSyAJhmFFgKOTN9zcL5NKUmZpa09o4p9Twsk
    private static final String DYNAMIC_TIMEZONE_URL =
            "https://maps.googleapis.com/maps/api/timezone/json";

    private static final String API_KEY =
            "AIzaSyAJhmFFgKOTN9zcL5NKUmZpa09o4p9Twsk";
    /* The units we want our API to return */
    private static final String LOCATION = "location";
    /* The format we want our API to return */
    private static final String KEY = "key";
    /* The format we want our API to return */
    private static final String TIMESTAMP = "timestamp";


    public URL getUrl (Double latitude, Double longitude) {
        
        Log.i(TAG, "getUrl: lastKnownLocation latitude: " + latitude.toString());
        Log.i(TAG, "getUrl: lastKnownLocation longitude: " + longitude.toString());


        Uri uri = buildUrlToGetLocationTimeZone(latitude, longitude);

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "url: " + url);
        return url;

    }

    private Uri buildUrlToGetLocationTimeZone(Double latitude, Double longitude) {

        String location = latitude+","+longitude;
        String encodedLocation = "";

        try {

            encodedLocation = UrlEscapers.urlFragmentEscaper().escape(location);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Uri builtURI = Uri.parse(DYNAMIC_TIMEZONE_URL)
                .buildUpon()
                .appendQueryParameter(LOCATION, encodedLocation)
                .appendQueryParameter(TIMESTAMP, String.valueOf(new Date().getTime()/1000))
                .appendQueryParameter(KEY,API_KEY)
                .build();
        return builtURI;
    }
}
