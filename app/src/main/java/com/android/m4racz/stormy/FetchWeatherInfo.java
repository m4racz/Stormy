package com.android.m4racz.stormy;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;


/**
 * Download TabForecastWeather in AsyncTask
 */
public class FetchWeatherInfo extends AsyncTask<String, Void, ArrayList<String>> {

    private static final String TAG = FetchWeatherInfo.class.getSimpleName();

    public static ForecastWeather forecastWeather;
    private static CurrentWeather currentWeather;
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


    @SuppressLint("Range")
    protected void onPostExecute(ArrayList<String> result){

        //create variables for update current weather UI
        TextView weatherCurrentForecast;
        TextView weatherTemperatureCurrent;
        TextView weatherTemperatureMax;
        TextView weatherTemperatureMin;
        TextView weatherWindSpeed;
        ImageView weatherIconImage;
        TextView weatherCurrentLocation;
        TextView weatherForecastDate;
        TextView weatherInput;
        if(result!=null) {
            if (result.size() == 2) {
                try {
                    currentWeather = parseCurrentWeatherJSONgson(result.get(0));
                    forecastWeather = parseForecastWeatherJSONgson(result.get(1));
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
                    WeatherUtils.setWeatherForecastUI(vCurrentWeather, context, weatherList, k, timeZoneId);
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
                recyclerView.addItemDecoration(new SeparatorDecoration(Color.parseColor("#5cffffff"),1,32,32));
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
            String windDirection = WeatherUtils.getWindDirection(currentWeather.getWind().getDeg());
            String windSpeed = Double.toString(currentWeather.getWind().getSpeed());

            //Update Fragments
            int fragmentCount = adapter.getCount();
            Log.i(TAG, "onPostExecute fragmentCount: "+ fragmentCount);

            Fragment fCurrentWeather = adapter.getItem(0);
            View vCurrentWeather = fCurrentWeather.getView();

            if (vCurrentWeather != null) {

                weatherCurrentForecast = vCurrentWeather.findViewById(R.id.xForecastDescription);
                weatherTemperatureCurrent = vCurrentWeather.findViewById(R.id.xTemperatureCurrent);
                weatherTemperatureMax = vCurrentWeather.findViewById(R.id.xTemperatureMax);
                weatherTemperatureMin = vCurrentWeather.findViewById(R.id.xTemperatureMin);
                weatherWindSpeed = vCurrentWeather.findViewById(R.id.xWindSpeed);
                weatherIconImage = vCurrentWeather.findViewById(R.id.xWeatherIcon);
                weatherCurrentLocation = vCurrentWeather.findViewById(R.id.xCurrentLocation);
                weatherForecastDate = vCurrentWeather.findViewById(R.id.xForecastDate);

                weatherCurrentForecast.setText(currentWeather.getWeather().get(0).getDescription());
                weatherForecastDate.setText(df.format((forecastdate.getTime())));
                weatherCurrentLocation.setText(String.format("%s, %s", currentWeather.getName(), currentWeather.getSys().getCountry()));

                weatherTemperatureCurrent.setText(String.format("%s °C", currentTemperature));
                weatherTemperatureMax.setText(String.format("Min: %s °C", maxTemperature));
                weatherTemperatureMin.setText(String.format("Max: %s °C", minTemperature));
                weatherWindSpeed.setText(String.format("Wind: %1$s m/s %2$s", windSpeed, windDirection));

                //set Image Views

                int weatherIconsGson = currentWeather.getWeather().get(0).getId();
                int weatherIconID = WeatherUtils.getWeatherIcon(weatherIconsGson);

                String PACKAGE_NAME = context.getPackageName();
                int imgID = context.getResources().getIdentifier(String.valueOf(weatherIconID), null, null);
                Log.i(TAG, "imgID: " + imgID);

                weatherIconImage.setImageResource(context.getResources().getIdentifier(String.valueOf(imgID), "drawable", PACKAGE_NAME));

                //reset text field location to empty
                weatherInput = mainActivity.findViewById(R.id.xInputSearch);
                weatherInput.setText("");

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