package com.example.androidfundamentals.fragment;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.androidfundamentals.R;
import com.example.androidfundamentals.helper.AlarmReceiver;

public class StandUpFragment extends Fragment {
    private ToggleButton alarmToggle;
    private Button nextTimeBtn;
    private NotificationManager notificationManager;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final String CHANNEL_NAME = "Stand Up Notification";
    private static final int NOTIFICATION_ID = 0;
    private static final String CHANNEL_DESCRIPTION = "Notifies every 15 minutes to stand up and walk";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stand_up, container, false);
        alarmToggle = view.findViewById(R.id.alarmToggle);
        nextTimeBtn = view.findViewById(R.id.nextTime);
        createNotificationChannel();

        Intent notifyIntent = new Intent(getContext(), AlarmReceiver.class);
        //Check if alarm is already set.
        boolean alarmUp = (PendingIntent.getBroadcast(getContext(), NOTIFICATION_ID,
                notifyIntent, PendingIntent.FLAG_NO_CREATE) != null);
        alarmToggle.setChecked(alarmUp);
        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (getContext(), NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String toastMessage = "";
                if (isChecked) {
                    long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                    long triggerTime = SystemClock.elapsedRealtime() + repeatInterval;

                    alarmManager.setInexactRepeating
                            (AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatInterval, notifyPendingIntent);
                    toastMessage = "Stand Up Alarm On!";
                } else {
                    notificationManager.cancelAll();
                    alarmManager.cancel(notifyPendingIntent);
                    toastMessage = "Stand Up Alarm Off!";
                }
                Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();
            }
        });

        nextTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alarmManager.getNextAlarmClock() != null) {
                    long nextAlarmTime = alarmManager.getNextAlarmClock().getTriggerTime();
                    Toast.makeText(getContext(), "Next Alarm Time: " + nextAlarmTime, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void createNotificationChannel() {
        notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setDescription(CHANNEL_DESCRIPTION);
            notificationManager.createNotificationChannel(channel);
        }
    }
}