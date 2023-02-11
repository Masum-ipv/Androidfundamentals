package com.example.androidfundamentals.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.androidfundamentals.BuildConfig;
import com.example.androidfundamentals.R;
import com.example.androidfundamentals.helper.CustomReceiver;

import java.util.Random;

public class PowerReceiverFragment extends Fragment implements View.OnClickListener {

    private CustomReceiver mReceiver = new CustomReceiver();
    public static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";
    Button sendBroadcast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_power_receiver, container, false);

        sendBroadcast = view.findViewById(R.id.sendBroadcast);
        sendBroadcast.setOnClickListener(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_HEADSET_PLUG);

        getContext().registerReceiver(mReceiver, filter);
        LocalBroadcastManager.getInstance(getContext()).
                registerReceiver(mReceiver, new IntentFilter(ACTION_CUSTOM_BROADCAST));

        return view;
    }

    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(mReceiver);
        LocalBroadcastManager.getInstance(getContext()).
                unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);
        int value = new Random().nextInt(21);
        customBroadcastIntent.putExtra("random", value);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(customBroadcastIntent);
    }
}