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

    public static int getWeatherIcon (int weatherId){
        int iconId;

        /*
         * Based on weather code data for Open Weather Map.
         */
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.weather_thunderstorm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.weather_drizzle;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.weather_rain;
        } else if (weatherId == 511) {
            return R.drawable.weather_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.weather_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.weather_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.weather_atmosphere;
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return R.drawable.weather_thunderstorm;
        } else if (weatherId == 800) {
            return R.drawable.weather_sunny;
        } else if (weatherId == 801) {
            return R.drawable.weather_scattered_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.weather_broken_clouds;
        } else if (weatherId >= 900 && weatherId <= 906) {
            return R.drawable.weather_thunderstorm;
        } else if (weatherId >= 958 && weatherId <= 962) {
            return R.drawable.weather_thunderstorm;
        } else if (weatherId >= 951 && weatherId <= 957) {
            return R.drawable.weather_sunny;
        }
        //if weatherID does not match we will return NA icon
        Log.e(TAG, "Unknown Weather: " + weatherId);
        return R.drawable.weather_na;

    }


    public static Calendar convertCurrentWeatherToCorrectTimeZone (CurrentWeather currentWeather, String timeZoneId){
        //convert current weather to currect timezone
        Integer dateInMillies = currentWeather.getDt();
        Log.i(TAG, "convertCurrentWeatherToCorrectTimeZone: dateInMillies: " + dateInMillies);

        Calendar forecastDate = CalcUtils.getDateFromMillies(dateInMillies, timeZoneId);

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone(timeZoneId));

        Log.i(TAG, "convertCurrentWeatherToCorrectTimeZone: df date " + df.format((forecastDate.getTime())).toString());

        return forecastDate;
    }

    /**
     *
     * @param context
     * @param weatherList
     * @param forecastDay
     * @param timeZoneId
     */
    public static void setWeatherForecastUI(View vforecastWeather, Context context, List weatherList, Integer forecastDay, String timeZoneId){
        //create variables for update forecast weather UI
        TextView weatherForecastDay1;
        TextView weatherForecastDay2;
        TextView weatherForecastDay3;

        TextView weatherForecastTempDay1;
        TextView weatherForecastTempDay2;
        TextView weatherForecastTempDay3;

        ImageView weatherForecastIconDay1;
        ImageView weatherForecastIconDay2;
        ImageView weatherForecastIconDay3;

        //init current weather UI objects
        weatherForecastDay1 = vforecastWeather.findViewById(R.id.xForecastDay1);
        weatherForecastDay2 = vforecastWeather.findViewById(R.id.xForecastDay2);
        weatherForecastDay3 = vforecastWeather.findViewById(R.id.xForecastDay3);

        weatherForecastTempDay1 = vforecastWeather.findViewById(R.id.xForecastTempDay1);
        weatherForecastTempDay2 = vforecastWeather.findViewById(R.id.xForecastTempDay2);
        weatherForecastTempDay3 = vforecastWeather.findViewById(R.id.xForecastTempDay3);

        weatherForecastIconDay1 = vforecastWeather.findViewById(R.id.xForecastIconDay1);
        weatherForecastIconDay2 = vforecastWeather.findViewById(R.id.xForecastIconDay2);
        weatherForecastIconDay3 = vforecastWeather.findViewById(R.id.xForecastIconDay3);

        String icon = weatherList.getWeather().get(0).getIcon();

        Log.i(TAG, "icon: " + icon);
        int currentTemperature = CalcUtils.getRoundedTemperature(weatherList.getMain().getTemp());

        Calendar day = CalcUtils.getDateFromString(weatherList.getDtTxt(),"yyyy-MM-dd HH:mm:ss", timeZoneId);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        String DisplayForecastDay =  df.format((day.getTime())).toString();

        if(forecastDay.equals(0)){

            weatherForecastDay1.setText(DisplayForecastDay);
            weatherForecastTempDay1.setText(String.format("%s °C", currentTemperature));

            //set Image Views5148
            Set keys = FetchWeatherInfo.weatherIcons.keySet();
            String weatherIconToSet = "weather_na";
            for (Object key : keys) {
                if (key.equals(icon)) {
                    weatherIconToSet = (String) FetchWeatherInfo.weatherIcons.get(key);
                }
            }
            String PACKAGE_NAME = context.getPackageName();
            int imgID = context.getResources().getIdentifier(PACKAGE_NAME+"drawable/"+weatherIconToSet,null,null);
            Log.i(TAG, "imgID: " + imgID);
            weatherForecastIconDay1.setImageResource(context.getResources().getIdentifier(weatherIconToSet, "drawable", PACKAGE_NAME));

        }

        if(forecastDay.equals(1)){

            weatherForecastDay2.setText(DisplayForecastDay);
            weatherForecastTempDay2.setText(String.format("%s °C", currentTemperature));

            //set Image Views
            Set keys = FetchWeatherInfo.weatherIcons.keySet();
            String weatherIconToSet = "weather_na";
            for (Object key : keys) {
                if (key.equals(icon)) {
                    weatherIconToSet = (String) FetchWeatherInfo.weatherIcons.get(key);
                }
            }
            String PACKAGE_NAME = context.getPackageName();
            int imgID = context.getResources().getIdentifier(PACKAGE_NAME+"drawable/"+weatherIconToSet,null,null);
            Log.i(TAG, "imgID: " + imgID);
            weatherForecastIconDay2.setImageResource(context.getResources().getIdentifier(weatherIconToSet, "drawable", PACKAGE_NAME));
        }

        if(forecastDay.equals(2)){

            weatherForecastDay3.setText(DisplayForecastDay);
            weatherForecastTempDay3.setText(String.format("%s °C", currentTemperature));

            //set Image Views
            Set keys = FetchWeatherInfo.weatherIcons.keySet();
            String weatherIconToSet = "weather_na";
            for (Object key : keys) {
                if (key.equals(icon)) {
                    weatherIconToSet = (String) FetchWeatherInfo.weatherIcons.get(key);
                }
            }
            String PACKAGE_NAME = context.getPackageName();
            int imgID = context.getResources().getIdentifier(PACKAGE_NAME+"drawable/"+weatherIconToSet,null,null);
            Log.i(TAG, "imgID: " + imgID);
            weatherForecastIconDay3.setImageResource(context.getResources().getIdentifier(weatherIconToSet, "drawable", PACKAGE_NAME));

        }

    }
}
