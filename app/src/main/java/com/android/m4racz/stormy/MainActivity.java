package com.android.m4racz.stormy;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.m4racz.stormy.Utils.NetworkUtilsOpenWeather;

import java.net.URL;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public EditText mInputCity;
    public ImageView mSearchWeather;
    public ImageView mLocationWeather;

    public LocationManager locationManager;
    public LocationListener locationListener;
    public Location location;
    public Context context;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION} , 1);

        } else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

    }

    /** Stop the updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }
    //on create method that is run when application starts
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //SET MAIN SCREEN
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_constraint);

        //INIT UI
        mInputCity = this.findViewById(R.id.xInputSearch);
        mSearchWeather = this.findViewById(R.id.xSearchImage);
        mLocationWeather = this.findViewById(R.id.xLocationImage);

        //SET TOOLBAR MENU
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.xToolBar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false); //disable title in toolbar

        //get current context that will be passed to downloadTask
        context = getApplicationContext();

        //Configure Location Manager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                //Toast.makeText(MainActivity.this, "Provider enabled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(MainActivity.this, "Provider disabled, Location GPS will not work.", Toast.LENGTH_SHORT).show();
            }
        };
        //check if we have permission to access location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION} , 1);

        } else{

            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }

        //change color of status bar
        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorStatusBar));
        //Create onFocusChange listener to hide keyboard
        mInputCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyBoard();
                }
            }
        });

        mInputCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                Log.i(TAG, "onEditorAction: keyCode" + keyEvent.getKeyCode());
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    handled = true;
                    hideKeyBoard();
                    findWeather("input");

                }
                return handled;
            }
        });

        //Create on click listener for Search by string to Get Forecast
        mSearchWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard();
                findWeather("input"); //search via input string
            }
        });

        //Create on click listener for search by Location to Get Forecast
        mLocationWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findWeather("location"); //search via user location
            }
        });
        //SEARCH FOR CURRENT LOCATION
        findWeather("location");
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
     Search for weather in CurrentWeather API* @param view searchType = "location" search with GPS, searchType = "input" search via entered city
     */
    public void findWeather(String searchType)  {

        NetworkUtilsOpenWeather networkUtilsOpenWeather = new NetworkUtilsOpenWeather();
        URL[] weatherURL = networkUtilsOpenWeather.getUrl(this, searchType);

        try {

            //Example URL http://api.openweathermap.org/data/2.5/weather?q=london&appid=89fd3664a5ad45e46488b6af57b2a5cd
            FetchWeatherInfo fetchWeatherInfo = new FetchWeatherInfo(context, this);
            //get forecast via AsyncTask
            fetchWeatherInfo.execute(weatherURL[0].toString(), weatherURL[1].toString());
            Log.i(TAG, "findWeather fetchWeatherInfo.forecastWeather: " + fetchWeatherInfo);

        } catch (Exception e) {

            Toast.makeText(this, "Error during the Search" + e.toString(), Toast.LENGTH_SHORT).show();

        }
    }
}