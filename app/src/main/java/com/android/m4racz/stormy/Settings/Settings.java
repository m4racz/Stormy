package com.android.m4racz.stormy.Settings;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.m4racz.stormy.R;

public class Settings extends AppCompatActivity {
    public Spinner spinnerTemperature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //change color of status bar
        Window window = this.getWindow();

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorStatusBar));
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }

        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.android.m4racz.stormy", MODE_PRIVATE);
        int temperatureSettings = sharedPreferences.getInt("temperature_settings", 99);


        spinnerTemperature = this.findViewById(R.id.settings_temperature_spinner);
        ArrayAdapter<CharSequence> spinnerTemperatureAdapter = ArrayAdapter.createFromResource(this, R.array.temperature_units,
                R.layout.custom_spinner);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            spinnerTemperature.setBackgroundTintList(getColorStateList(R.color.colorText));
        }
        spinnerTemperatureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTemperature.setAdapter(spinnerTemperatureAdapter);

        if(temperatureSettings != 99) spinnerTemperature.setSelection(temperatureSettings);

        spinnerTemperature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sharedPreferences.edit().putInt("temperature_settings", position).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
