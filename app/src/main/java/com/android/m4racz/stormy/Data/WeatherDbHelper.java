package com.android.m4racz.stormy.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.android.m4racz.stormy.Data.WeatherContract.WeatherCurrent;
import com.android.m4racz.stormy.Data.WeatherContract.WeatherForecast;

public class WeatherDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 1;


    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_WEATHER_TABLE =
                "CREATE TABLE " + WeatherCurrent.TABLE_NAME + "( " +
                        WeatherCurrent._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"    +
                        WeatherCurrent.COLUMN_DATE + " INTEGER NOT NULL,"             +
                        WeatherCurrent.COLUMN_CUR_TEMP + " REAL NOT NULL,"            +
                        WeatherCurrent.COLUMN_LOC + " TEXT NOT NULL,"                 +
                        WeatherCurrent.COLUMN_LOC_LAT + " REAL NOT NULL,"          +
                        WeatherCurrent.COLUMN_LOC_LON + " REAL NOT NULL,"          +
                        WeatherCurrent.COLUMN_MAX_TEMP + " REAL NOT NULL,"            +
                        WeatherCurrent.COLUMN_MIN_TEMP + " REAL NOT NULL,"            +
                        WeatherCurrent.COLUMN_PRESSURE + " REAL NOT NULL,"            +
                        WeatherCurrent.COLUMN_SUN_RISE + " INTEGER NOT NULL,"         +
                        WeatherCurrent.COLUMN_SUN_SET + " INTEGER NOT NULL,"          +
                        WeatherCurrent.COLUMN_WIND_SPEED + " REAL NOT NULL,"          +

                        " UNIQUE (" + WeatherCurrent.COLUMN_DATE + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);

        final String SQL_CREATE_WEATHER_FORECAST_TABLE =
                "CREATE TABLE " + WeatherForecast.TABLE_NAME + "( " +
                        WeatherForecast._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"    +
                        WeatherForecast.COLUMN_DATE + " INTEGER NOT NULL,"             +

                        WeatherForecast.COLUMN_LOC + " TEXT NOT NULL,"                 +
                        WeatherForecast.COLUMN_LOC_LAT + " REAL NOT NULL,"          +
                        WeatherForecast.COLUMN_LOC_LON + " REAL NOT NULL,"          +
                        WeatherCurrent.COLUMN_MAX_TEMP + " REAL NOT NULL,"            +
                        WeatherCurrent.COLUMN_MIN_TEMP + " REAL NOT NULL,"            +
                        WeatherCurrent.COLUMN_PRESSURE + " REAL NOT NULL,"            +
                        WeatherCurrent.COLUMN_WIND_SPEED + " REAL NOT NULL,"          +

                        " UNIQUE (" + WeatherCurrent.COLUMN_DATE + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_FORECAST_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeatherCurrent.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeatherForecast.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
