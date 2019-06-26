package com.android.m4racz.stormy;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabCurrentWeather extends Fragment {
    public TabCurrentWeather() {
        // Required empty public constructor
    }

    public final static String TAG = TabCurrentWeather.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.tab_current_weather_linear, container, false);
        Log.i(TAG, "onCreateView: called");
        return view;

    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: called");
        if(FetchWeatherInfo.forecastWeather != null) {
            Log.i(TAG, "onResume: forecastWeather" + FetchWeatherInfo.forecastWeather.toString());
        }
    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@link Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: called");
        if(FetchWeatherInfo.forecastWeather != null) {
            Log.i(TAG, "onPause: forecastWeather" + FetchWeatherInfo.forecastWeather.toString());
        }
    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: called");
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after {@link #onDestroy()}.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: called");
    }
}
