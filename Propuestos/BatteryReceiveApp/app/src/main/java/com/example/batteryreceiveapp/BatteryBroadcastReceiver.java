package com.example.batteryreceiveapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BatteryBroadcastReceiver extends BroadcastReceiver {

    private TextView batteryStatusTextView;
    private TextView batteryLevelTextView;
    private ProgressBar batteryProgressBar;

    public BatteryBroadcastReceiver(TextView batteryStatusTextView, TextView batteryLevelTextView, ProgressBar batteryProgressBar){
        this.batteryStatusTextView = batteryStatusTextView;
        this.batteryLevelTextView = batteryLevelTextView;
        this.batteryProgressBar = batteryProgressBar;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level * 100 / (float) scale;

        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        String statusString = "";

        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                statusString = "Cargando";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                statusString = "Descargando";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                statusString = "Batería llena";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                statusString = "No cargando";
                break;
            default:
                statusString = "Estado desconocido";
                break;
        }

        batteryStatusTextView.setText("Estado de la batería: " + statusString);
        batteryLevelTextView.setText("Nivel de batería: " + batteryPct + "%");

        batteryProgressBar.setProgress((int) batteryPct);

        Log.d("BatteryReceiver", "Estado de la batería: " + statusString + ", Nivel de batería: " + batteryPct + "%");
    }
}
