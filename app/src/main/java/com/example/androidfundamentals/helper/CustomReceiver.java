package com.example.androidfundamentals.helper;

import static com.example.androidfundamentals.fragment.PowerReceiverFragment.ACTION_CUSTOM_BROADCAST;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        String toastMessage = "Unknown intent action";

        if (intentAction != null) {
            if (intentAction.equals(Intent.ACTION_POWER_CONNECTED)) {
                toastMessage = "Power connected!";
            } else if (intentAction.equals(Intent.ACTION_POWER_DISCONNECTED)) {
                toastMessage = "Power disconnected!";
            } else if (intentAction.equals(ACTION_CUSTOM_BROADCAST)) {
                int random = intent.getIntExtra("random", -1);
                toastMessage = "Custom Broadcast Received\nSquare of the Random number: " + random * random;
            }
        }
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
    }
}