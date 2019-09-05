package com.example.locationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LocationUtil locationUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationUtil = new LocationUtil(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Location location = locationUtil.getLocation();

        String txt = location != null
                ? String.format("位置情報: %1$.4f, %2$.4f", location.getLatitude(), location.getLongitude())
                : "位置情報：取得失敗";
        Toast toast = Toast.makeText(this, txt, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //locationUtil.locationStart();

    }
}
