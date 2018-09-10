package com.android.m4racz.stormy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.m4racz.stormy.Utils.CalcUtils;
import com.android.m4racz.stormy.Utils.WeatherUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

        public TextView weatherDescription, weatherDate, weatherTemp, weatherDayOfWeek;
        public ImageView weatherIcon;

        public MyViewHolder(View view){
            super(view);
            weatherDescription = (TextView) view.findViewById(R.id.xForecastItemDescription);
            weatherDate = (TextView) view.findViewById(R.id.xForecastItemDateTime);
            weatherTemp = (TextView) view.findViewById(R.id.xForecastItemTemperature);
            weatherIcon = (ImageView) view.findViewById(R.id.xForecastItemIcon);
            weatherDayOfWeek = (TextView) view.findViewById(R.id.xForecastItemDayofWeek);
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
        com.android.m4racz.stormy.ForecastWeather.List list = (com.android.m4racz.stormy.ForecastWeather.List) forecastList.get(position);
        String forecastWeatherDescription =  list.getWeather().get(0).getDescription();
        String forecastWeatherDate = list.getDtTxt();

        Calendar forecastdate = WeatherUtils.convertCurrentWeatherToCorrectTimeZone(list.getDt(), FetchWeatherInfo.timeZoneId);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        df.setTimeZone(TimeZone.getTimeZone(FetchWeatherInfo.timeZoneId));



        int forecastWeatherTemp = CalcUtils.getRoundedTemperature(list.getMain().getTemp());
        String forecastWeatherTemperature = String.valueOf(forecastWeatherTemp);


        int forecastWeatherId = list.getWeather().get(0).getId();
        int iconID = WeatherUtils.getWeatherIcon(forecastWeatherId);


        String PACKAGE_NAME = context.getPackageName();
        int imgID = context.getResources().getIdentifier(String.valueOf(iconID), null, null);


        holder.weatherIcon.setImageResource(context.getResources().getIdentifier(String.valueOf(imgID), "drawable", PACKAGE_NAME));

        holder.weatherDescription.setText(forecastWeatherDescription);
        holder.weatherDate.setText(df.format((forecastdate.getTime())));
        holder.weatherTemp.setText(forecastWeatherTemperature);
        holder.weatherDayOfWeek.setText(CalcUtils.getDayOfWeek(forecastdate));
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }
}
