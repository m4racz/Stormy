package com.android.m4racz.stormy.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.m4racz.stormy.CurrentWeather.CurrentWeather;
import com.android.m4racz.stormy.FetchWeatherInfo;
import com.android.m4racz.stormy.ForecastWeather.ForecastWeather;
import com.android.m4racz.stormy.ForecastWeather.List;
import com.android.m4racz.stormy.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;


public class WeatherUtils {


    private static String TAG = WeatherUtils.class.getSimpleName();

    /**
     * @param forecastWeather object of weather forecast
     * @param timeZoneId
     * @return arraylist that contains 3 day forecast indexes to object forecastWeather
     */
    public static void convertForecastToCorrectTimeZone (ForecastWeather forecastWeather, String timeZoneId){
        //convert forecast weather to currect timezone
        for (int i = 0; i < forecastWeather.getList().size(); i++){

            List weatherList = forecastWeather.getList().get(i);

            Calendar forecastDate = CalcUtils.getDateFromString(weatherList.getDtTxt(), "yyyy-MM-dd HH:mm:ss", timeZoneId);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            df.setTimeZone(TimeZone.getTimeZone(timeZoneId));
            weatherList.setDtTxt(df.format((forecastDate.getTime())).toString());

            Log.i(TAG, "get3dayForecast: new dtTXT" + weatherList.getDtTxt()) ;
        }
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
     * WILL BE DEPRECATED
     * @param mainActivity
     * @param context
     * @param weatherList
     * @param forecastDay
     * @param timeZoneId
     */
    public static void setWeatherForecastUI(Activity mainActivity, Context context, List weatherList, Integer forecastDay, String timeZoneId){
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
        weatherForecastDay1 = mainActivity.findViewById(R.id.xForecastDay1);
        weatherForecastDay2 = mainActivity.findViewById(R.id.xForecastDay2);
        weatherForecastDay3 = mainActivity.findViewById(R.id.xForecastDay3);

        weatherForecastTempDay1 = mainActivity.findViewById(R.id.xForecastTempDay1);
        weatherForecastTempDay2 = mainActivity.findViewById(R.id.xForecastTempDay2);
        weatherForecastTempDay3 = mainActivity.findViewById(R.id.xForecastTempDay3);

        weatherForecastIconDay1 = mainActivity.findViewById(R.id.xForecastIconDay1);
        weatherForecastIconDay2 = mainActivity.findViewById(R.id.xForecastIconDay2);
        weatherForecastIconDay3 = mainActivity.findViewById(R.id.xForecastIconDay3);

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
