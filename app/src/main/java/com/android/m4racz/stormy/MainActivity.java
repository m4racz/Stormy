package com.android.m4racz.stormy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.m4racz.stormy.Utils.NetworkUtilities;

import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();

    public EditText mInputCity;
    public ImageView mWeatherIcon;
    public ImageView mSearchWeather;
    public ImageView mLocationWeather;

    public TextView mWeatherForecast;
    public TextView mWeatherTemperatureCurrent;
    public TextView mWeatherTemperatureMin;
    public TextView mWeatherTemperatureMax;
    public TextView mWeatherWindSpeed;

    public LocationManager locationManager;
    public LocationListener locationListener;

    public Context context;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    {

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                    }

                }

            }

        }

    }


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
        getSupportActionBar().setDisplayShowTitleEnabled(false); //disable title in toolbar

        //INIT LOCATION MANAGER
        Log.i(TAG, "onCreate: INIT LOCATION MANAGER");
        Object o = this.getSystemService(LOCATION_SERVICE);
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                Log.i(TAG, "onLocationChanged: location" + location.toString());

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //init input variables
        mInputCity =  findViewById(R.id.xInputSearch);
        mSearchWeather = findViewById(R.id.xSearchImage);
        mLocationWeather = findViewById(R.id.xLocationImage);

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

        //Create on click listener for Search by string to Get Forecast
        mSearchWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard();
                findWeather(view, "input"); //search via input string
            }
        });

        //Create on click listener for search by Location to Get Forecast
        mLocationWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findWeather(view,"location"); //search via user location
            }
        });

        //
        Log.i(TAG, "onCreate: Package Manager: " + PackageManager.PERMISSION_GRANTED);
        Log.i(TAG, "onCreate: AccessFineLocation: " + ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION} , 1);

        } else {
            Log.i(TAG, "onCreate REQUEST LOCATION UPDATE");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }
   /*
     *
     * Hide Keyboard when invoked
     */
    private void hideKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(mInputCity.getWindowToken(), 0);
        }
    }


    /**
     Search for weather in OpenWeather API* @param view searchType = "location" search with GPS, searchType = "input" search via entered city
     */
    public void findWeather(View view, String searchType)  {

        URL weatherURL = NetworkUtilities.getUrl(this, context, searchType);

        //encode city to URL and Call Async OpenWeather API
        try {

            //Example URL http://api.openweathermap.org/data/2.5/weather?q=london&appid=89fd3664a5ad45e46488b6af57b2a5cd
            DownloadForecast task = new DownloadForecast(context, this);

            //get forecast via AsyncTask
            task.execute(weatherURL.toString());


        } catch (Exception e) {

            Toast.makeText(this, "Error during the Search" + e.toString(), Toast.LENGTH_SHORT).show();

        }
    }
}
