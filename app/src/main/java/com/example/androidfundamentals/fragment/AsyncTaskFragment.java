package com.example.androidfundamentals.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.androidfundamentals.R;
import com.example.androidfundamentals.helper.SimpleAsyncTask;

public class AsyncTaskFragment extends Fragment {
    private TextView showText;
    private Button startButton;
    private ProgressBar progressBar;
    private static final String TEXT_STATE = "currentText";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_async_task, container, false);
        showText = view.findViewById(R.id.show_text);
        startButton = view.findViewById(R.id.start_btn);
        progressBar = view.findViewById(R.id.progressBar);
        if (savedInstanceState != null) {
            showText.setText(savedInstanceState.getString(TEXT_STATE));
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showText.setText(R.string.napping);
                new SimpleAsyncTask(showText, progressBar).execute();
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_STATE, showText.getText().toString());
    }
}