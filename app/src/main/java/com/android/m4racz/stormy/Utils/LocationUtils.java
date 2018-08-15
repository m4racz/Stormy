package com.android.m4racz.stormy.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class LocationUtils {
    private Context context;
    private ActivityCompat activityCompat;
    private Activity activity;
    private static final String TAG = LocationUtils.class.getSimpleName();
}
