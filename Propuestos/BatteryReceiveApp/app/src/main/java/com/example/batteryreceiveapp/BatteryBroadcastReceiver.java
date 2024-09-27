package com.example.batteryreceiveapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.TextView;

public class BatteryBroadcastReceiver extends BroadcastReceiver {

    private TextView batteryStatusTextView;

    public BatteryBroadcastReceiver(TextView batteryStatusTextView) {
        this.batteryStatusTextView = batteryStatusTextView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
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
    }
}
