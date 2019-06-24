package com.android.m4racz.stormy;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.m4racz.stormy.CurrentWeather.CurrentWeather;
import com.android.m4racz.stormy.ForecastWeather.ForecastWeather;
import com.android.m4racz.stormy.ForecastWeather.List;
import com.android.m4racz.stormy.Utils.CalcUtils;
import com.android.m4racz.stormy.Utils.NetworkUtils;
import com.android.m4racz.stormy.Utils.NetworkUtilsCityTimeZone;
import com.android.m4racz.stormy.Utils.SeparatorDecoration;
import com.android.m4racz.stormy.Utils.WeatherUtils;
import com.google.gson.Gson;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;


/**
 * Download TabForecastWeather in AsyncTask
 */
public class FetchWeatherInfo extends AsyncTask<String, Void, ArrayList<String>> {

    private static final String TAG = FetchWeatherInfo.class.getSimpleName();

    public static ForecastWeather forecastWeather;
    public static CurrentWeather currentWeather;
    private final MainActivity.ViewPagerAdapter adapter;
    //Create Progress Dialog to show that something is going on
    private ProgressDialog dialog;

    private Activity mainActivity;
    private Context context;
    public static String timeZoneId = "UTC";

    FetchWeatherInfo(Context context, MainActivity mainActivity, MainActivity.ViewPagerAdapter adapter) {
        this.context = context;
        this.mainActivity = mainActivity;
        this.dialog = new ProgressDialog(mainActivity);
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {

        // Set the variable txtView here, after setContentView on the dialog
        // has been called! use dialog.findViewById().
        dialog.setMessage(mainActivity.getString(R.string.progressMessage));
        dialog.show();
}
    @Override
    protected ArrayList<String> doInBackground(String... urls) {

        ArrayList<String> result = new ArrayList<>();
        //try to get the weather information from openweather API
        try {
            String currentWeather = NetworkUtils.getDataFromWeb(urls[0]);
            String forecastWeather = NetworkUtils.getDataFromWeb(urls[1]);
            result.add(currentWeather);
            result.add(forecastWeather);
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("Range")
    protected void onPostExecute(ArrayList<String> result){

        //Create Variables for update current weather above tabs

        TextView mCurrentWindIcon;
        TextView mCurrentWindLabel;
        TextView mCurrentWindValue;

        TextView mCurrentHumidityIcon;
        TextView mCurrentHumidityLabel;
        TextView mCurrentHumidityValue;

        TextView mCurrentPreasureIcon;
        TextView mCurrentPreasureLabel;
        TextView mCurrentPreasureValue;

        TextView mCurrentSunriseIcon;
        TextView mCurrentSunriseLabel;
        TextView mCurrentSunriseValue;

        TextView mCurrentSunsetIcon;
        TextView mCurrentSunsetLabel;
        TextView mCurrentSunsetValue;

        TextView mCurrentVisibilityIcon;
        TextView mCurrentVisibilityLabel;
        TextView mCurrentVisibilityValue;

        TextView mCurrentTemperature;
        TextView mCurrentWeatherIcon;
        TextView mCurrentLocation;
        TextView mCurrentLastUpdate;
        TextView mCurrentCondition;


        if(result!=null) {
            if (result.size() == 2) {
                try {
                    currentWeather = parseCurrentWeatherJSONgson(result.get(0));
                    forecastWeather = parseForecastWeatherJSONgson(result.get(1));
                    Log.i(TAG, "onPostExecute: currentWeather" + currentWeather);
                    Log.i(TAG, "onPostExecute: forecastWeather" + forecastWeather);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        if (forecastWeather!=null){

            //get timeZone for Location
            FetchTimeZoneInfo fetchTimeZoneInfo = new FetchTimeZoneInfo (context, (MainActivity) mainActivity);
            NetworkUtilsCityTimeZone networkUtilsCityTimeZone = new NetworkUtilsCityTimeZone();

            Double latitude = forecastWeather.getCity().getCoord().getLat();
            Double longitude = forecastWeather.getCity().getCoord().getLon();

            URL locationTimeZoneURL = networkUtilsCityTimeZone.getUrl(latitude,longitude);

            try {
                timeZoneId = fetchTimeZoneInfo.execute(locationTimeZoneURL.toString()).get();
                Log.i(TAG, "onPostExecute: timeZoneId: " + timeZoneId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            Fragment fCurrentWeather = adapter.getItem(0);
            View vCurrentWeather = fCurrentWeather.getView();

            for (int k = 0; k < 3; k++) {
                //get forecast for tomorrow
                List weatherList = forecastWeather.getList().get(k);
                String date = weatherList.getDtTxt();
                if (vCurrentWeather != null) {
                    //WeatherUtils.setWeatherForecastUI(vCurrentWeather, context, weatherList, k, timeZoneId);
                }
                Log.i(TAG, "onPostExecute: date converted " + date);
            }

            //set ForecastTab
            Fragment fForecastWeather = adapter.getItem(1);
            View vForecastWeather = fForecastWeather.getView();

            RecyclerView recyclerView;
            if (vForecastWeather != null) {
                recyclerView = vForecastWeather.findViewById(R.id.recycler_view);
                ForecastAdapter forecastAdapter = new ForecastAdapter(forecastWeather.getList(), context);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mainActivity.getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                //recyclerView.addItemDecoration(new DividerItemDecoration(context,LinearLayoutManager.VERTICAL));
                recyclerView.addItemDecoration(new SeparatorDecoration(Color.parseColor("#000e4a"),1,16,16));
                recyclerView.setAdapter(forecastAdapter);
            }
        }

        //Set update UI with current weather data
        if (currentWeather != null) {

            //recalculate currentweather data with time zone
            Calendar forecastdate = WeatherUtils.convertCurrentWeatherToCorrectTimeZone(currentWeather.getDt(), timeZoneId);
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            df.setTimeZone(TimeZone.getTimeZone(timeZoneId));
            Log.i(TAG, "convertCurrentWeatherToCorrectTimeZone: df date " + df.format((forecastdate.getTime())));

            //Round Temperatures
            int currentTemperature = CalcUtils.getRoundedTemperature(currentWeather.getMain().getTemp());
            int minTemperature = CalcUtils.getRoundedTemperature(currentWeather.getMain().getTempMin());
            int maxTemperature = CalcUtils.getRoundedTemperature(currentWeather.getMain().getTempMax());

            //get wind direction and speed

            //Update Fragments
            int fragmentCount = adapter.getCount();
            Log.i(TAG, "onPostExecute fragmentCount: "+ fragmentCount);


            //get fragment current weather
            Fragment fCurrentWeather = adapter.getItem(0);
            View vCurrentWeather = fCurrentWeather.getView();

            if (vCurrentWeather != null) {
                //setting wind section
                mCurrentWindIcon  = vCurrentWeather.findViewById(R.id.current_wind_icon);
                mCurrentWindLabel = vCurrentWeather.findViewById(R.id.current_wind_label);
                mCurrentWindValue = vCurrentWeather.findViewById(R.id.current_wind_value);

                mCurrentWindIcon.setTypeface(MainActivity.weatherIcon);
                mCurrentWindLabel.setTypeface(MainActivity.robotoLight);
                mCurrentWindValue.setTypeface(MainActivity.robotoLight);
                //setting humidity section
                mCurrentHumidityIcon  = vCurrentWeather.findViewById(R.id.current_humidity_icon);
                mCurrentHumidityLabel = vCurrentWeather.findViewById(R.id.current_humidity_label);
                mCurrentHumidityValue = vCurrentWeather.findViewById(R.id.current_humidity_value);

                mCurrentHumidityIcon.setTypeface(MainActivity.weatherIcon);
                mCurrentHumidityLabel.setTypeface(MainActivity.robotoLight);
                mCurrentHumidityValue.setTypeface(MainActivity.robotoLight);
                //setting preasure section
                mCurrentPreasureIcon  = vCurrentWeather.findViewById(R.id.current_preasure_icon);
                mCurrentPreasureLabel = vCurrentWeather.findViewById(R.id.current_preasure_label);
                mCurrentPreasureValue = vCurrentWeather.findViewById(R.id.current_preasure_value);

                mCurrentPreasureIcon.setTypeface(MainActivity.weatherIcon);
                mCurrentPreasureLabel.setTypeface(MainActivity.robotoLight);
                mCurrentPreasureValue.setTypeface(MainActivity.robotoLight);

                //setting visibility section
                mCurrentVisibilityIcon  = vCurrentWeather.findViewById(R.id.current_visibility_icon);
                mCurrentVisibilityLabel = vCurrentWeather.findViewById(R.id.current_visibility_label);
                mCurrentVisibilityValue = vCurrentWeather.findViewById(R.id.current_visibility_value);

                mCurrentVisibilityIcon.setTypeface(MainActivity.weatherIcon);
                mCurrentVisibilityLabel.setTypeface(MainActivity.robotoLight);
                mCurrentVisibilityValue.setTypeface(MainActivity.robotoLight);

                //setting sunrise section
                mCurrentSunriseIcon  = vCurrentWeather.findViewById(R.id.current_sunrise_icon);
                mCurrentSunriseLabel = vCurrentWeather.findViewById(R.id.current_sunrise_label);
                mCurrentSunriseValue = vCurrentWeather.findViewById(R.id.current_sunrise_value);

                mCurrentSunriseIcon.setTypeface(MainActivity.weatherIcon);
                mCurrentSunriseLabel.setTypeface(MainActivity.robotoLight);
                mCurrentSunriseValue.setTypeface(MainActivity.robotoLight);

                //setting sunset section
                mCurrentSunsetIcon  = vCurrentWeather.findViewById(R.id.current_sunset_icon);
                mCurrentSunsetLabel = vCurrentWeather.findViewById(R.id.current_sunset_label);
                mCurrentSunsetValue = vCurrentWeather.findViewById(R.id.current_sunset_value);

                mCurrentSunsetIcon.setTypeface(MainActivity.weatherIcon);
                mCurrentSunsetLabel.setTypeface(MainActivity.robotoLight);
                mCurrentSunsetValue.setTypeface(MainActivity.robotoLight);

                //CurrentWeather Upper Section
                mCurrentWeatherIcon = mainActivity.findViewById(R.id.current_weather_icon);
                mCurrentTemperature = mainActivity.findViewById(R.id.current_weather_temperature);
                mCurrentLocation = mainActivity.findViewById(R.id.current_weather_location);
                mCurrentLastUpdate = mainActivity.findViewById(R.id.current_weather_last_update);
                mCurrentCondition = mainActivity.findViewById(R.id.current_weather_condition);

                mCurrentTemperature.setTypeface(MainActivity.robotoLight);
                mCurrentLocation.setTypeface(MainActivity.robotoLight);
                mCurrentLastUpdate.setTypeface(MainActivity.robotoLight);
                mCurrentWeatherIcon.setTypeface(MainActivity.weatherIcon);
                mCurrentCondition.setTypeface(MainActivity.robotoLight);

                //get condition description
                String conditionDescription = currentWeather.getWeather().get(0).getDescription();

                //get weather condition id
                int idCondition = currentWeather.getWeather().get(0).getId();
                String conditionIcon = WeatherUtils.getWeatherIcon(idCondition);

                //get location
                String weatherLocation = currentWeather.getName();

                //get current date + time
                LocalDateTime currentDate = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                currentDate.format(formatter);

                //Current Weather UpperSection
                mCurrentWeatherIcon.setText(conditionIcon);
                mCurrentLocation.setText("Location: " + weatherLocation);
                mCurrentLastUpdate.setText("Update: " + currentDate.format(formatter));
                mCurrentTemperature.setText( currentTemperature + "°");
                mCurrentCondition.setText("Condition: " +conditionDescription);

                //setting Icons
                mCurrentHumidityIcon.setText("8");
                mCurrentWindIcon.setText("F");
                mCurrentPreasureIcon.setText("'");
                mCurrentSunriseIcon.setText("D");
                mCurrentSunsetIcon.setText("D");
                mCurrentVisibilityIcon.setText("(");

                //setting labels
                mCurrentHumidityLabel.setText("Humidity");
                mCurrentWindLabel.setText("Wind");
                mCurrentPreasureLabel.setText("Preasure");
                mCurrentSunriseLabel.setText("Sun Rise");
                mCurrentSunsetLabel.setText("Sun Set");
                mCurrentVisibilityLabel.setText("Visibility");

                String windDirection = WeatherUtils.getWindDirection(currentWeather.getWind().getDeg());
                String windSpeed = Double.toString(currentWeather.getWind().getSpeed());
                Double pressure = currentWeather.getMain().getPressure();
                Integer visibility = currentWeather.getVisibility();
                Integer humidity = currentWeather.getMain().getHumidity();

                int sunRiseEpoch = currentWeather.getSys().getSunrise();
                int sunSetEpoch = currentWeather.getSys().getSunset();
                String sunRise = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date (sunRiseEpoch*1000));
                String sunSet= new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date (sunSetEpoch*1000));

                //setting blind values
                mCurrentHumidityValue.setText(humidity + " %");
                mCurrentWindValue.setText( windSpeed + " m/s " + windDirection);
                mCurrentPreasureValue.setText(pressure + " hPa");
                mCurrentVisibilityValue.setText(visibility + "m");
                mCurrentSunriseValue.setText(sunRise);
                mCurrentSunsetValue.setText(sunSet);

            }
        }

        //close progress dialog
        if(dialog.isShowing()){
            dialog.dismiss();
        }

    }

    private ForecastWeather parseForecastWeatherJSONgson(String jsonString)
    {
        Gson gson = new Gson();
        forecastWeather = gson.fromJson(jsonString, ForecastWeather.class);
        return forecastWeather;
    }

    private CurrentWeather parseCurrentWeatherJSONgson(String jsonString)
    {
        Gson gson = new Gson();
        currentWeather = gson.fromJson(jsonString, CurrentWeather.class);
        return currentWeather;
    }
}