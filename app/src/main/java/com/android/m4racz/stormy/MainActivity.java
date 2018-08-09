package com.android.m4racz.stormy;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity{
    public EditText mInputCity;
    public ImageView mWeatherIcon;
    public TextView mWeatherForecast;
    public Button mSearchWeather;
    public static Context context;


    //on create method that is run when application starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init variables
        mInputCity = findViewById(R.id.xCityInput);
        mSearchWeather = findViewById(R.id.xButtonFindWeather);
        mWeatherForecast = findViewById(R.id.xForecastDescription);
        mWeatherIcon = findViewById(R.id.xWeatherIcon);
        context = getApplicationContext();

        //Create on click listener for button Get Forecast
        mSearchWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findWeather(view);
            }
        });

    }

    //Search for the weather
    public void findWeather(View view)  {

        Log.i("MYLOG","City to Search: " + mInputCity.getText().toString());

        //encode city to URL and Call Async OpenWeather API
        try {
            String encodedCity = URLEncoder.encode(mInputCity.getText().toString(), "UTF-8");
            String urlStrg = "https://api.openweathermap.org/data/2.5/weather?q=" + encodedCity + "&appid=89fd3664a5ad45e46488b6af57b2a5cd";
            Log.i("MYLOG","urlStrg: " + urlStrg);
            //Example URL http://api.openweathermap.org/data/2.5/weather?q=london&appid=89fd3664a5ad45e46488b6af57b2a5cd
            DownloadForecast task = new DownloadForecast(mWeatherForecast, mWeatherIcon, context );

            //get forecast
            task.execute(urlStrg);


        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Error during the Search" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


}
