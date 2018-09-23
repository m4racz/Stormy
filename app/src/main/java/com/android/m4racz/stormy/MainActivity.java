package com.android.m4racz.stormy;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.m4racz.stormy.About.About;
import com.android.m4racz.stormy.Data.WeatherDbHelper;
import com.android.m4racz.stormy.Settings.SavedLocations;
import com.android.m4racz.stormy.Settings.Settings;
import com.android.m4racz.stormy.Utils.NetworkUtilsOpenWeather;
import com.google.gson.Gson;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public Spinner mInputLocation;
    public ImageView mSearchWeather;
    public ImageView mLocationWeather;
    public LocationManager locationManager;
    public LocationListener locationListener;
    public Location location;
    public Context context;
    TabLayout tabLayout;
    public ViewPager viewPager;
    public ViewPagerAdapter adapter;
    public int []tabIcons = {
            R.drawable.tabcurrent,
            R.drawable.tabforecast,
            R.drawable.tabmap
    };


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
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.main_menu_settings:
                Log.i(TAG, "onOptionsItemSelected: settings clicked");
                Intent settingsActivity = new Intent(getApplicationContext(), Settings.class);
                this.startActivity(settingsActivity);
                return true;
            case R.id.main_menu_about:
                Log.i(TAG, "onOptionsItemSelected: about clicked");
                Intent aboutActivity = new Intent(getApplicationContext(), About.class);
                this.startActivity(aboutActivity);
                return true;
            default:
                return false;
        }
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
        setContentView(R.layout.activity_main);

        //SET TOOLBAR MENU
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false); //disable title in toolbar

        //INIT DBS
        WeatherDbHelper db = new WeatherDbHelper(this);

        //INIT TABS
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFragment(new TabCurrentWeather(), "Current");
        adapter.addFragment(new TabForecastWeather(), "Forecast");
        //adapter.addFragment(new TabMapWeather(), "Map");
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        //FORCE KEYBOARD OVERLAY to prevent screen adjust during entering search
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //INIT UI PARTS
        mInputLocation = this.findViewById(R.id.xSpinnerSearch);
        mSearchWeather = this.findViewById(R.id.xSearchImage);
        mLocationWeather = this.findViewById(R.id.xLocationImage);

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
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.primaryDarkColor));

        /*
        //Create onFocusChange listener to hide keyboard and show dropdown
        mInputLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyBoard();
                }
            }
        });

        mInputLocation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        */


        //SEARCH FOR CURRENT LOCATION
        findWeather("location");

        //INSERT LOCATION TO PREFERENCES
        String savedLocationsPrefsTag = "com.android.m4racz.stormy.SavedLocations";
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(savedLocationsPrefsTag, MODE_PRIVATE);
        Gson gson = new Gson();
        com.android.m4racz.stormy.Settings.Location locationToSave = new com.android.m4racz.stormy.Settings.Location();
        locationToSave.setCity("Prague");
        locationToSave.setCountry("CZ");
        locationToSave.setLat(15);
        locationToSave.setLon(50);

        SavedLocations savedLocations = new SavedLocations();
        savedLocations.setLocations(Collections.singletonList(locationToSave));

        String json = gson.toJson(savedLocations);
        Log.i(TAG, "onCreate: " + json);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(savedLocationsPrefsTag,json);
        editor.apply();
        editor.commit();

        String savedLocation = sharedPreferences.getString(savedLocationsPrefsTag,"");
        Log.i(TAG, "onCreate: "+ savedLocation);

        //GET LOCATION FROM PREFERENCES
        String getLocationFromPreferences = sharedPreferences.getString(savedLocationsPrefsTag, null);
        Log.i(TAG, "onCreate: getLocationFromPreferences " + getLocationFromPreferences);
        SavedLocations getSavedLocation = gson.fromJson(getLocationFromPreferences, SavedLocations.class);

        String city = getSavedLocation.getLocations().get(0).getCity();
        Log.i(TAG, "onCreate: city " + city);
    }
    /*
     *
     * Hide Keyboard when invoked
     */
    private void hideKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(mInputLocation.getWindowToken(), 0);
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
            FetchWeatherInfo fetchWeatherInfo = new FetchWeatherInfo(context, this, adapter);
            //get forecast via AsyncTask
            fetchWeatherInfo.execute(weatherURL[0].toString(), weatherURL[1].toString());
            Log.i(TAG, "findWeather fetchWeatherInfo.forecastWeather: " + fetchWeatherInfo);

        } catch (Exception e) {

            Toast.makeText(this, "Error during the Search" + e.toString(), Toast.LENGTH_SHORT).show();

        }
    }

    //setup icons for tabs
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        //tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    // Adapter for the viewpager using FragmentPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //in order to hide tab name we return null
            return null;
        }
    }
}