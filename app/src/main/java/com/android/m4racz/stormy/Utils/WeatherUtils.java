package com.android.m4racz.stormy.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.m4racz.stormy.CurrentWeather.CurrentWeather;
import com.android.m4racz.stormy.FetchWeatherInfo;
import com.android.m4racz.stormy.ForecastWeather.List;
import com.android.m4racz.stormy.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;
import java.util.TimeZone;


public class WeatherUtils {


    private static String TAG = WeatherUtils.class.getSimpleName();

    public static String getWindDirection(Double degrees) {
        String windDirection = "N/A";

        if (degrees == null) {
            return windDirection;
        }

        if (degrees >= 337.5 || degrees < 22.5) {
            windDirection = "N";
        } else if (degrees >= 22.5 && degrees < 67.5) {
            windDirection = "NE";
        } else if (degrees >= 67.5 && degrees < 112.5) {
            windDirection = "E";
        } else if (degrees >= 112.5 && degrees < 157.5) {
            windDirection = "SE";
        } else if (degrees >= 157.5 && degrees < 202.5) {
            windDirection = "S";
        } else if (degrees >= 202.5 && degrees < 247.5) {
            windDirection = "SW";
        } else if (degrees >= 247.5 && degrees < 292.5) {
            windDirection = "W";
        } else if (degrees >= 292.5 && degrees < 337.5) {
            windDirection = "NW";
        }

        return windDirection;
    }


    public static String getWeatherIcon (int weatherId){
        int iconId;

        /*
         * Based on weather code data for Open Weather Map.
         */

        if (weatherId >= 200 && weatherId <= 232) {
            //thunderstorm
            return "&";
        } else if (weatherId >= 300 && weatherId <= 321) {
            //drizzle
            return "R";
        } else if (weatherId >= 500 && weatherId <= 504) {
            //rain
            return "8";
        } else if (weatherId == 511) {
            //snow
            return "#";
        } else if (weatherId >= 520 && weatherId <= 531) {
            //rain
            return "8";
        } else if (weatherId >= 600 && weatherId <= 622) {
            //snow
            return "#";
        } else if (weatherId >= 701 && weatherId <= 761) {
            //atmospfere
            return "E";
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            //thunderstorm
            return "&";
        } else if (weatherId == 800) {
            //sunny
            return "B";
        } else if (weatherId == 801) {
            //weather_scattered_clouds
            return "3";
        } else if (weatherId >= 802 && weatherId <= 804) {
            //weather_broken_clouds
            return "3";
        } else if (weatherId >= 900 && weatherId <= 906) {
            //weather_thunderstorm
            return "&";
        } else if (weatherId >= 958 && weatherId <= 962) {
            //weather_thunderstorm
            return "&";
        } else if (weatherId >= 951 && weatherId <= 957) {
            //sunny
            return "B";
        }
        //if weatherID does not match we will return NA icon
        Log.e(TAG, "Unknown Weather: " + weatherId);
        return ")";

    }

    public static Calendar convertCurrentWeatherToCorrectTimeZone (int dateInMillies, String timeZoneId){
        //convert current weather to current timezone
        Log.i(TAG, "convertCurrentWeatherToCorrectTimeZone: dateInMillies: " + dateInMillies);
        Calendar forecastDate = CalcUtils.getDateFromMillies(dateInMillies, timeZoneId);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone(timeZoneId));
        Log.i(TAG, "convertCurrentWeatherToCorrectTimeZone: df date " + df.format((forecastDate.getTime())).toString());
        return forecastDate;
    }
}
