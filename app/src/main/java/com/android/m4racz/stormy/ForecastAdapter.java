package com.android.m4racz.stormy;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;



public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.MyViewHolder> {
    private List forecastList;

    public ForecastAdapter(List<com.android.m4racz.stormy.ForecastWeather.List> forecastlist) {
        this.forecastList = forecastlist;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View view){
            super(view);
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
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }
}
