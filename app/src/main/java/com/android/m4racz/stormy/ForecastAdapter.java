
package com.android.m4racz.stormy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.m4racz.stormy.CurrentWeather.Main;
import com.android.m4racz.stormy.Utils.CalcUtils;
import com.android.m4racz.stormy.Utils.WeatherUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.MyViewHolder> {

    private List forecastList;
    private Context context;

    public ForecastAdapter(List<com.android.m4racz.stormy.ForecastWeather.List> forecastlist, Context context) {
        this.forecastList = forecastlist;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView
                mForecastDescription,
                mForecastTemperature,
                mForecastDayOfWeek,
                mForecastWeatherIcon;

        public MyViewHolder(View view){
            super(view);
            mForecastWeatherIcon = (TextView) view.findViewById(R.id.forecast_icon);
            mForecastDayOfWeek = (TextView) view.findViewById(R.id.forecast_dayOfWeek);
            mForecastTemperature = (TextView) view.findViewById(R.id.forecast_temperature);
            mForecastDescription = (TextView) view.findViewById(R.id.forecast_description);

            mForecastDescription.setTypeface(MainActivity.robotoLight);
            mForecastDayOfWeek.setTypeface(MainActivity.robotoLight);
            mForecastTemperature.setTypeface(MainActivity.robotoLight);
            mForecastWeatherIcon.setTypeface(MainActivity.weatherIcon);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_item, parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //get forecast weather position
        com.android.m4racz.stormy.ForecastWeather.List list = (com.android.m4racz.stormy.ForecastWeather.List) forecastList.get(position);
        //set forecast date time

        Calendar forecastdate = WeatherUtils.convertCurrentWeatherToCorrectTimeZone(list.getDt(), FetchWeatherInfo.timeZoneId);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        df.setTimeZone(TimeZone.getTimeZone(FetchWeatherInfo.timeZoneId));
        int forecastWeatherTemp = CalcUtils.getRoundedTemperature(list.getMain().getTemp());
        String forecastWeatherTemperature = String.valueOf(forecastWeatherTemp);
        int forecastWeatherId = list.getWeather().get(0).getId();
        String PACKAGE_NAME = context.getPackageName();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String timeStamp = dateFormat.format(forecastdate.getTime());
        holder.mForecastDayOfWeek.setText(CalcUtils.getDayOfWeek(forecastdate) + " " + timeStamp);

        //set weather description
        String forecastWeatherDescription =  list.getWeather().get(0).getDescription();
        holder.mForecastDescription.setText(forecastWeatherDescription);

        //set min + max temperature
        Double forecastMin = list.getMain().getTempMin();
        Double forecastMax = list.getMain().getTempMax();

        forecastMax = Double.valueOf(CalcUtils.getRoundedTemperature(forecastMax));
        forecastMin = Double.valueOf(CalcUtils.getRoundedTemperature(forecastMax));

        holder.mForecastTemperature.setText(forecastMax + "°");

        //set weather icon
        int idCondition = list.getWeather().get(0).getId();
        String conditionIcon = WeatherUtils.getWeatherIcon(idCondition);
        holder.mForecastWeatherIcon.setText(conditionIcon);
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }
}
