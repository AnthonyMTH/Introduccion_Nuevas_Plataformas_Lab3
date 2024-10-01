package com.example.baterypendingintentapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Pending Intent Recibido: El estado de la bateria ha cambiado", Toast.LENGTH_SHORT).show();
    }
}
