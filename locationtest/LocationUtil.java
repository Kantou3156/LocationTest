package com.example.locationtest;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

public class LocationUtil implements LocationListener {

    private Activity caller;
    private LocationManager mLocationManager;
    private String bestProvider;
    public Location locationParam;

    public LocationUtil(Activity activity) {
        this.caller = activity;

        // インスタンス生成
        mLocationManager = (LocationManager) caller.getSystemService(caller.LOCATION_SERVICE);

        // 詳細設定
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setSpeedRequired(false);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
        bestProvider = mLocationManager.getBestProvider(criteria, true);

        this.locationStart();
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(caller, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(caller, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // パーミッションの許可を取得する
            ActivityCompat.requestPermissions(caller,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        }

        if (RuntimePermissionUtils.hasSelfPermissions(caller, Settings.ACTION_LOCATION_SOURCE_SETTINGS)) {
            // パーミッションの許可を取得する
        }
    }

    public void locationStart() {
        checkPermission();
        mLocationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
    }

    public void locationStop() {
        mLocationManager.removeUpdates(this);
    }

    public Location getLocation() {
        if (caller.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && caller.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location == null)
        {
            return null;
        }

        locationStop();

        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.locationParam = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }
}

