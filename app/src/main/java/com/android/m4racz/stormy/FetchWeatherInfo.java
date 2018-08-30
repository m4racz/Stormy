package com.android.m4racz.stormy;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.m4racz.stormy.CurrentWeather.CurrentWeather;
import com.android.m4racz.stormy.ForecastWeather.ForecastWeather;
import com.android.m4racz.stormy.ForecastWeather.List;
import com.android.m4racz.stormy.Utils.CalcUtils;
import com.android.m4racz.stormy.Utils.NetworkUtils;
import com.android.m4racz.stormy.Utils.NetworkUtilsCityTimeZone;
import com.android.m4racz.stormy.Utils.WeatherUtils;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;


/**
 * Download Forecast in AsyncTask
 */
public class FetchWeatherInfo extends AsyncTask<String, Void, ArrayList<String>> {

    private static final String TAG = FetchWeatherInfo.class.getSimpleName();

    private static ForecastWeather forecastWeather;
    private static CurrentWeather currentWeather;

    //Create Progress Dialog to show that something is going on
    private ProgressDialog dialog;

    public static Hashtable weatherIcons = new Hashtable<String, String>() {{
        put("01d", "weather_sunny");
        put("01n", "weather_sunny_night");
        put("02d", "weather_few_clouds");
        put("02n", "weather_few_clouds_night");
        put("03d", "weather_scattered_clouds");
        put("03n", "weather_scattered_clouds_night");
        put("04d", "weather_broken_clouds");
        put("04n", "weather_broken_clouds_night");
        put("09d", "weather_drizzle");
        put("10d", "weather_rain");
        put("10n", "weather_rain");
        put("11d", "weather_thunderstorm");
        put("13d", "weather_snow");
        put("50d", "weather_atmosphere");
        put("weather_na", "weather_na");
    }};

    private Activity mainActivity;
    private Context context;


    FetchWeatherInfo(Context context, MainActivity mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;
        this.dialog = new ProgressDialog(mainActivity);
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

        //init current weather UI objects
        weatherCurrentForecast = mainActivity.findViewById(R.id.xForecastDescription);
        weatherTemperatureCurrent = mainActivity.findViewById(R.id.xTemperatureCurrent);
        weatherTemperatureMax = mainActivity.findViewById(R.id.xTemperatureMax);
        weatherTemperatureMin = mainActivity.findViewById(R.id.xTemperatureMin);
        weatherWindSpeed = mainActivity.findViewById(R.id.xWindSpeed);
        weatherIconImage = mainActivity.findViewById(R.id.xWeatherIcon);
        weatherCurrentLocation = mainActivity.findViewById(R.id.xCurrentLocation);
        weatherForecastDate = mainActivity.findViewById(R.id.xForecastDate);

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
        String timeZoneId = "";
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

            for (int k = 0; k < 3; k++) {
                //get forecast for tomorrow
                List weatherList = forecastWeather.getList().get(k);
                String date = weatherList.getDtTxt();
                WeatherUtils.setWeatherForecastUI(mainActivity, context, weatherList, k, timeZoneId);
                Log.i(TAG, "onPostExecute: date converted " + date);
            }
        }
        //Set update UI with current weather data
        if (currentWeather != null) {

            //recalculate currentweather data with time zone
            Calendar forecastdate = WeatherUtils.convertCurrentWeatherToCorrectTimeZone(currentWeather, timeZoneId);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            df.setTimeZone(TimeZone.getTimeZone(timeZoneId));
            Log.i(TAG, "convertCurrentWeatherToCorrectTimeZone: df date " + df.format((forecastdate.getTime())).toString());

            //Round Temperatures
            int currentTemperature = CalcUtils.getRoundedTemperature(currentWeather.getMain().getTemp());
            int minTemperature = CalcUtils.getRoundedTemperature(currentWeather.getMain().getTempMin());
            int maxTemperature = CalcUtils.getRoundedTemperature(currentWeather.getMain().getTempMax());

            //set Text Views
            weatherForecastDate.setText(df.format((forecastdate.getTime())).toString());
            weatherCurrentLocation.setText(String.format("%s, %s", currentWeather.getName(), currentWeather.getSys().getCountry()));
            weatherCurrentForecast.setText(currentWeather.getWeather().get(0).getDescription());
            weatherTemperatureCurrent.setText(String.format("%s °C", currentTemperature));
            weatherTemperatureMax.setText(String.format("Min: %s °C", maxTemperature));
            weatherTemperatureMin.setText(String.format("Max: %s °C", minTemperature));
            weatherWindSpeed.setText(String.format("Wind: %s m/s", currentWeather.getWind().getSpeed()));

            //set Image Views
            Set keys = weatherIcons.keySet();
            String weatherIconsGson = currentWeather.getWeather().get(0).getIcon();
            String weatherIconToSet = "weather_na";
            for (Object key : keys) {
                if (key.equals(weatherIconsGson)) {
                    weatherIconToSet = (String) weatherIcons.get(key);
                }
            }

            String PACKAGE_NAME = context.getPackageName();
            int imgID = context.getResources().getIdentifier(PACKAGE_NAME+"drawable/"+weatherIconToSet,null,null);
            Log.i(TAG, "imgID: " + imgID);

            weatherIconImage.setImageResource(context.getResources().getIdentifier(weatherIconToSet, "drawable", PACKAGE_NAME));


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