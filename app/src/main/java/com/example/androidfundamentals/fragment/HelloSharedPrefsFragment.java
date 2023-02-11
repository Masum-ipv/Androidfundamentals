package com.example.androidfundamentals.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidfundamentals.R;

public class HelloSharedPrefsFragment extends Fragment implements View.OnClickListener {
    private int count = 0;
    private int currentColor;
    private TextView countTextview;
    private Button blackButton, redButton, blueButton, greenButton, countBtn, resetBtn;
    private String COLOR_KEY = "color_key";
    private String COUNT_KEY = "count_key";
    private SharedPreferences sharedPreferences;
    private String sharedPrefFile = "com.example.androidfundamentals.fragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hello_shared_prefs, container, false);
        sharedPreferences = getContext().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);

        countTextview = view.findViewById(R.id.count_textview);
        blackButton = view.findViewById(R.id.black_button);
        redButton = view.findViewById(R.id.red_button);
        blueButton = view.findViewById(R.id.blue_button);
        greenButton = view.findViewById(R.id.green_button);
        countBtn = view.findViewById(R.id.count_button);
        resetBtn = view.findViewById(R.id.reset_button);

        redButton.setOnClickListener(this);
        blueButton.setOnClickListener(this);
        blackButton.setOnClickListener(this);
        greenButton.setOnClickListener(this);
        countBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);

        currentColor = ContextCompat.getColor(getContext(), R.color.default_background);
        // Restore preferences
        count = sharedPreferences.getInt(COUNT_KEY, 0);
        countTextview.setText(String.format("%s", count));
        currentColor = sharedPreferences.getInt(COLOR_KEY, currentColor);
        countTextview.setBackgroundColor(currentColor);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.count_button) {
            count++;
            countTextview.setText(String.valueOf(count));
        } else if (view.getId() == R.id.reset_button) {
            // Reset count
            count = 0;
            countTextview.setText(String.valueOf(count));
            // Reset color
            currentColor = ContextCompat.getColor(getContext(), R.color.default_background);
            countTextview.setBackgroundColor(currentColor);
            // Clear preferences
            SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
            preferencesEditor.clear();
            preferencesEditor.apply();
        } else if (view.getId() == R.id.red_button || view.getId() == R.id.blue_button ||
                view.getId() == R.id.black_button || view.getId() == R.id.green_button) {
            currentColor = ((ColorDrawable) view.getBackground()).getColor();
            countTextview.setBackgroundColor(currentColor);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        preferencesEditor.putInt(COUNT_KEY, count);
        preferencesEditor.putInt(COLOR_KEY, currentColor);
        preferencesEditor.apply();
    }
}