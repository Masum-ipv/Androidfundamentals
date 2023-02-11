package com.example.androidfundamentals.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidfundamentals.R;

public class BatteryLevelFragment extends Fragment implements View.OnClickListener {
    private ImageView batteryImage;
    private int imageLevel = 0;
    private Button increaseButton, decreaseButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_battery_level, container, false);
        batteryImage = view.findViewById(R.id.battery_id);
        increaseButton = view.findViewById(R.id.increase);
        decreaseButton = view.findViewById(R.id.decrease);
        increaseButton.setOnClickListener(this);
        decreaseButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.increase) {
            imageLevel++;
            imageLevel = Math.min(imageLevel, 6);
            batteryImage.setImageLevel(imageLevel);
        } else if (view.getId() == R.id.decrease) {
            imageLevel--;
            imageLevel = Math.max(imageLevel, 0);
            batteryImage.setImageLevel(imageLevel);
        }
        Toast.makeText(getContext(), "ImageLevel: " + imageLevel, Toast.LENGTH_SHORT).show();
    }
}