package com.android.m4racz.stormy;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = DownloadForecast.class.getSimpleName();
    public EditText mInputCity;
    public ImageView mWeatherIcon;
    public ImageView mSearchWeather;

    public TextView mWeatherForecast;
    public TextView mWeatherTemperatureCurrent;
    public TextView mWeatherTemperatureMin;
    public TextView mWeatherTemperatureMax;
    public TextView mWeatherWindSpeed;

    public Context context;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //on create method that is run when application starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //SET MAIN SCREEN
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SET TOOLBAR MENU
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.xToolBar);
        setSupportActionBar(toolbar);

        //SET OPTIONS MENU

        //init input variables
        mInputCity =  findViewById(R.id.xInputSearch);
        mSearchWeather = findViewById(R.id.xSearchImage);

        //init result variables
        mWeatherForecast = findViewById(R.id.xForecastDescription);
        mWeatherIcon = findViewById(R.id.xWeatherIcon);
        mWeatherTemperatureMax = findViewById(R.id.xTemperatureMax);
        mWeatherTemperatureMin = findViewById(R.id.xTemperatureMin);
        mWeatherTemperatureCurrent = findViewById(R.id.xTemperatureCurrent);
        mWeatherWindSpeed = findViewById(R.id.xWindSpeed);

        //get current context that will be passed to downloadTask
        context = getApplicationContext();

        //Create onFocusChange listener to hide keyboard
        mInputCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyBoard();
                }
            }
        });

        //Create on click listener for button Get Forecast
        mSearchWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard();
                findWeather(view);
            }
        });

    }


    /**
     * Hide Keyboard when invoked
     */
    private void hideKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(mInputCity.getWindowToken(), 0);
        }
    }


    /**
     Search for weather in OpenWeather API* @param view
     */
    public void findWeather(View view)  {

        Log.i(TAG, "City to Search: " + mInputCity.getText().toString());

        //encode city to URL and Call Async OpenWeather API
        try {

            String encodedCity = URLEncoder.encode(mInputCity.getText().toString(), "UTF-8");
            String urlStrg = "https://api.openweathermap.org/data/2.5/weather?q=" + encodedCity + "&units=metric" + "&appid=89fd3664a5ad45e46488b6af57b2a5cd";
            Log.i(TAG,"urlStrg: " + urlStrg);
            //Example URL http://api.openweathermap.org/data/2.5/weather?q=london&appid=89fd3664a5ad45e46488b6af57b2a5cd
            DownloadForecast task = new DownloadForecast(context, this);

            //get forecast via AsyncTask
            task.execute(urlStrg);


        } catch (Exception e) {
            Toast.makeText(this, "Error during the Search" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
