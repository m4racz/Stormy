package com.android.m4racz.stormy;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    EditText mInputCity;
    ImageView mWeatherIcon;
    TextView mWeatherForecast;

    public void findWeather(View view){

        AsyncTask<String, Void, String> findWeatherResult;
        Log.i("MYLOG","City to Search: " + mInputCity.getText().toString());

        //encode city to URL and Call Async OpenWeather API
        try {
            String encodedCity = URLEncoder.encode(mInputCity.getText().toString(), "UTF-8");
            String urlStrg = "https://api.openweathermap.org/data/2.5/weather?q=" + encodedCity + "&appid=89fd3664a5ad45e46488b6af57b2a5cd";
            Log.i("MYLOG","urlStrg: " + urlStrg);
            //Example URL http://api.openweathermap.org/data/2.5/weather?q=london&appid=89fd3664a5ad45e46488b6af57b2a5cd
            DownloadForecast task = new DownloadForecast();
            findWeatherResult = task.execute(urlStrg);

            mWeatherForecast.setText("aa");


        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Error during the Search" + e.toString(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputCity = findViewById(R.id.xCityInput);


    }
}
