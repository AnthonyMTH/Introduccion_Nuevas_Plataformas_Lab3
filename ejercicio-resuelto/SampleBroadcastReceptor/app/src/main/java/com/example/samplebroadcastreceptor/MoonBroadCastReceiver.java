package com.example.samplebroadcastreceptor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class MoonBroadCastReceiver  extends BroadcastReceiver {
    private static final String TAG = "MoonBroadcastReceiver";
    public static final String EXTRA_MOON_PHASE = "com.example.sampleintentbroadcatreceiver. MoonBroadcastReceiver.EXTRA_MOON_PHASE";

    @Override
    public void onReceive(Context context, Intent intent) {
        String message =  intent.getStringExtra(EXTRA_MOON_PHASE);
        writeToFile (message, context);
        Log.d(TAG, message);
    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("miArchivo.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
