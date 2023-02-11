package com.example.androidfundamentals.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidfundamentals.R;

public class CountFragment extends Fragment {

    private int count = 0;
    Button toastBtn, countBtn, zeroBtn;
    TextView counterView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        toastBtn = view.findViewById(R.id.toast_btn);
        countBtn = view.findViewById(R.id.count_btn);
        zeroBtn = view.findViewById(R.id.zero_btn);
        counterView = view.findViewById(R.id.counter);
        counterView.setText(Integer.toString(count));

        toastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Count: " + count, Toast.LENGTH_SHORT).show();
            }
        });

        countBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                counterView.setText(Integer.toString(count));
                if (count % 2 == 0) {
                    setEvenColor();
                } else {
                    setOddColor();
                }
            }
        });
        zeroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                counterView.setText(Integer.toString(count));
                setOddColor();
            }
        });
        return view;
    }

    private void setOddColor() {
        countBtn.setBackgroundColor(getResources().getColor(R.color.purple_700));
        zeroBtn.setBackgroundColor(getResources().getColor(R.color.gray));

    }

    private void setEvenColor() {
        countBtn.setBackgroundColor(getResources().getColor(R.color.green));
        zeroBtn.setBackgroundColor(getResources().getColor(R.color.pink));
    }
}