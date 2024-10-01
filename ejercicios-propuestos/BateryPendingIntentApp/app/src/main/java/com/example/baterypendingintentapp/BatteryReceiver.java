package com.example.baterypendingintentapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
public class BatteryReceiver extends BroadcastReceiver {
    private static final String TAG = "BatteryReceiver";
    private static final String CHANNEL_ID = "CANAL";
    private static final int NOTIFICATION_ID = 1;
    private TextView batteryStatusTextView;
    private TextView batteryLevelTextView;
    private ProgressBar batteryProgressBar;

    public BatteryReceiver( TextView batteryStatusTextView, TextView batteryLevelTextView, ProgressBar batteryProgressBar){
        this.batteryStatusTextView = batteryStatusTextView;
        this.batteryLevelTextView = batteryLevelTextView;
        this.batteryProgressBar = batteryProgressBar;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //porcentaje
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level * 100 / (float) scale;

        //estado
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

        // Actualizar los TextView
        batteryStatusTextView.setText("Estado de la batería: " + statusString);
        batteryLevelTextView.setText("Nivel de batería: " + batteryPct + "%");

        batteryProgressBar.setProgress((int) batteryPct);

        Log.d(TAG, "Estado de la batería: " + statusString + ", Nivel de batería: " + batteryPct + "%");
        showNotification(context);

    }

    private void showNotification(Context context) {
        // Crear el intent para cuando el usuario haga clic en la notificación
        Intent notificationIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Crear el NotificationManager
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Crear el NotificationChannel para Android 8.0 y superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Canal de Batería";
            String description = "Canal para notificaciones de estado de batería";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }

        // Crear la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Icono pequeño de la notificación
                .setContentTitle("Ha cambiado el Estado de la Batería! ")
                .setContentText("Haz clic para ver el estado de la batería")
                .setAutoCancel(true) // La notificación se descarta cuando se pulsa
                .setContentIntent(pendingIntent); // Intent que se dispara al hacer clic en la notificación

        // Mostrar la notificación
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}
