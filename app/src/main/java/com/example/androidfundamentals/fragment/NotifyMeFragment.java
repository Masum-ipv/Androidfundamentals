package com.example.androidfundamentals.fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.androidfundamentals.R;
import com.example.androidfundamentals.activity.DrawerLayoutActivity;

import java.util.Objects;

public class NotifyMeFragment extends Fragment implements View.OnClickListener {

    private Button notifyBtn, updateBtn, cancelBtn;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final String CHANNEL_NAME = "Winter Notification";
    private static final String CHANNEL_DESCRIPTION = "Notification from Winter";
    private static final String NOTIFICATION_TITLE = "You've been notified!";
    private static final String NOTIFICATION_DESCRIPTION = "This is your notification text.";
    private static final String NOTIFICATION_TITLE_UPDATED = "Notification Updated!";
    private static final int NOTIFICATION_ID = 0;
    private static final String UPDATE_NOTIFICATION = "Update Notification";
    private static final String ACTION_UPDATE_NOTIFICATION =
            "com.example.androidfundamentals.fragment.ACTION_UPDATE_NOTIFICATION";
    private static final String ACTION_DISMISS_NOTIFICATION =
            "com.example.androidfundamentals.fragment.ACTION_DISMISS_NOTIFICATION";
    private NotificationManager notificationManager;
    private NotificationReceiver notificationReceiver = new NotificationReceiver();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notify_me, container, false);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_NOTIFICATION);
        intentFilter.addAction(ACTION_DISMISS_NOTIFICATION);
        getContext().registerReceiver(notificationReceiver, intentFilter);

        createNotificationChannel();

        notifyBtn = view.findViewById(R.id.notify);
        updateBtn = view.findViewById(R.id.update);
        cancelBtn = view.findViewById(R.id.cancel);
        notifyBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        setNotificationButtonState(true, false, false);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.notify) {

            Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
            PendingIntent updatePendingIntent = PendingIntent.getBroadcast
                    (getContext(), NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
            notifyBuilder.addAction(R.drawable.ic_update, UPDATE_NOTIFICATION, updatePendingIntent);

            notificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());
            setNotificationButtonState(false, true, true);
        } else if (view.getId() == R.id.update) {
            updateNotification();
        } else if (view.getId() == R.id.cancel) {
            notificationManager.cancel(NOTIFICATION_ID);
            setNotificationButtonState(true, false, false);
        }
    }

    private void updateNotification() {
        Bitmap androidImage = BitmapFactory
                .decodeResource(getResources(), R.drawable.mascot_1);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle(NOTIFICATION_TITLE_UPDATED));
        notificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());
        setNotificationButtonState(false, false, true);
    }

    public void createNotificationChannel() {
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

    private NotificationCompat.Builder getNotificationBuilder() {
        Intent notificationIntent = new Intent(getContext(), DrawerLayoutActivity.class); //Parent class of all fragment

        PendingIntent pendingIntent = PendingIntent.getActivity
                (getContext(), NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent dismissIntent = new Intent(ACTION_DISMISS_NOTIFICATION);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast
                (getContext(), NOTIFICATION_ID, dismissIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(getContext(), PRIMARY_CHANNEL_ID)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(NOTIFICATION_DESCRIPTION)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setDeleteIntent(dismissPendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_android);
        return nBuilder;
    }

    private void setNotificationButtonState(
            Boolean isNotifyEnabled, Boolean isUpdateEnabled, Boolean isCancelEnabled) {
        notifyBtn.setEnabled(isNotifyEnabled);
        updateBtn.setEnabled(isUpdateEnabled);
        cancelBtn.setEnabled(isCancelEnabled);
    }

    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(notificationReceiver);
        super.onDestroy();
    }

    public class NotificationReceiver extends BroadcastReceiver {
        public NotificationReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("onReceive", intent.getAction());
            if (Objects.equals(intent.getAction(), ACTION_UPDATE_NOTIFICATION)){
                updateNotification();
            }else if (Objects.equals(intent.getAction(), ACTION_DISMISS_NOTIFICATION)){
                setNotificationButtonState(true, false, false);
            }

        }
    }
}