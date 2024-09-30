package com.example.baterypendingintentapp;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private BatteryReceiver batteryReceiver;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textBatteryStatus = findViewById(R.id.textBatteryStatus);
        TextView textBatteryLevel = findViewById(R.id.textBatteryLevel);
        ProgressBar batteryProgressBar = findViewById(R.id.batteryProgressBar);

        batteryReceiver = new BatteryReceiver(textBatteryStatus, textBatteryLevel, batteryProgressBar);
    }

    @Override
    protected void onResume(){
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(batteryReceiver, filter, Context.RECEIVER_EXPORTED);
        }else{
            registerReceiver(batteryReceiver, filter);
        }
        Log.d(TAG, "BroadcastReceiver registrado con PendingIntent satisfactoriamente.");

    }

    @Override
    protected void onPause(){
        super.onPause();
        unregisterReceiver(batteryReceiver);
        Log.d(TAG, "onPause: BroadcastReceiver desregistrado satisfactoriamente");
    }
}