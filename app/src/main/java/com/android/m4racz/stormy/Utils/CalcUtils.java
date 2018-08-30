package com.android.m4racz.stormy.Utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public final class CalcUtils {

    private static final String TAG = CalcUtils.class.getSimpleName();

    public static  int getRoundedTemperature(double temperature) {
        return (int) Math.round(temperature);
    }

    public static Calendar getDateFromString(String dateToConvert, String formatToConvert, String timeZoneId){
        Date forecastDate;
        try {

            TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
            Calendar nowTimeZoned = new GregorianCalendar(timeZone);

            forecastDate = new SimpleDateFormat(formatToConvert).parse(dateToConvert);

            nowTimeZoned.setTime(forecastDate);

            return  nowTimeZoned;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Calendar getDateFromMillies(Integer dateInMillies, String timeZoneId) {
        Date date = null;

        date = new Date((long)dateInMillies*1000);

        TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
        Calendar nowTimeZoned = new GregorianCalendar(timeZone);

        nowTimeZoned.setTime(date);

        return nowTimeZoned;
    }

    public static String getDayOfWeek(Date day) {
        String dayOfWeek = "";
        Calendar forecastDay = Calendar.getInstance();
        forecastDay.setTime(day);
        int dayNumber = forecastDay.get(Calendar.DAY_OF_WEEK);
        switch (dayNumber) {
            case 1:
                dayOfWeek = "Sunday";
                break;
            case 2:
                dayOfWeek = "Monday";
                break;
            case 3:
                dayOfWeek = "Tuesday";
                break;
            case 4:
                dayOfWeek = "Wednesday";
                break;
            case 5:
                dayOfWeek = "Thursday";
                break;
            case 6:
                dayOfWeek = "Friday";
                break;
            case 7:
                dayOfWeek = "Saturday";
        }
        return dayOfWeek;
    }
}
