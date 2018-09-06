package com.android.m4racz.stormy.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public class WeatherContract {

    public static final String CONTENT_AUTHORITY = "com.android.m4racz.stormy";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_WEATHER = "weather";

    public static final class WeatherCurrent implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_WEATHER)
                .build();

        public static final String TABLE_NAME = "CurrentWeather";

        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_WEATHER_ID = "weather_id";
        public static final String COLUMN_MAX_TEMP = "max_temp";
        public static final String COLUMN_MIN_TEMP = "min_temp";
        public static final String COLUMN_CUR_TEMP = "cur_temp";
        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_WIND_SPEED = "wind";
        public static final String COLUMN_SUN_RISE = "sun_rise";
        public static final String COLUMN_SUN_SET = "sun_set";
        public static final String COLUMN_LOC_LAT = "loc_lat";
        public static final String COLUMN_LOC_LON = "loc_lon";
        public static final String COLUMN_LOC = "loc";

    }
    public static final class WeatherForecast implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_WEATHER)
                .build();

        public static final String TABLE_NAME = "ForecastWeather";

        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_WEATHER_ID = "weather_id";
        public static final String COLUMN_MAX_TEMP = "max_temp";
        public static final String COLUMN_MIN_TEMP = "min_temp";
        public static final String COLUMN_WIND_SPEED = "wind";

        public static final String COLUMN_LOC_LAT = "loc_lat";
        public static final String COLUMN_LOC_LON = "loc_lon";
        public static final String COLUMN_LOC = "loc";

    }
}
