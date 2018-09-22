package com.android.m4racz.stormy.Settings;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.android.m4racz.stormy.R;

public class Settings extends AppCompatActivity {

    public Switch mSwitchTemperature_units;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Settings");

        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.android.m4racz.stormy", MODE_PRIVATE);
        boolean temperatureSettings = sharedPreferences.getBoolean("temperature_settings", true);

        mSwitchTemperature_units = this.findViewById(R.id.settings_temparature_units);

        if(temperatureSettings == true){
            mSwitchTemperature_units.setChecked(true);
        }
        else{
            mSwitchTemperature_units.setChecked(false);
        }

        mSwitchTemperature_units.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sharedPreferences.edit().putBoolean("temperature_settings", true).apply();
                }
                else{
                    sharedPreferences.edit().putBoolean("temperature_settings", false).apply();
                }
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
