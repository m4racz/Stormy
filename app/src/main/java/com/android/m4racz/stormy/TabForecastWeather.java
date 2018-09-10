package com.android.m4racz.stormy;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.m4racz.stormy.CurrentWeather.Main;

import org.w3c.dom.Text;

public class TabForecastWeather extends Fragment {
    public View forecastView;



    public static final String TAG = TabForecastWeather.class.getSimpleName();

    public TabForecastWeather() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, "onCreateView: called");
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.tab_forecast_weather, container, false);


        if(FetchWeatherInfo.forecastWeather != null) {
            Log.i(TAG, "onCreateView: forecastWeather" + FetchWeatherInfo.forecastWeather.toString());
        }

        this.forecastView = view;
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: called");
        if (FetchWeatherInfo.forecastWeather != null) {
            Log.i(TAG, "onResume: forecastWeather" + FetchWeatherInfo.forecastWeather.toString());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: called");
        if (FetchWeatherInfo.forecastWeather != null) {
            Log.i(TAG, "onPause: forecastWeather" + FetchWeatherInfo.forecastWeather.toString());
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: called");
        if (FetchWeatherInfo.forecastWeather != null) {
            Log.i(TAG, "onDestroy: forecastWeather" + FetchWeatherInfo.forecastWeather.toString());
        }
    }
}
