package com.example.androidfundamentals.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.androidfundamentals.R;

public class CheckBoxesFragment extends Fragment implements View.OnClickListener {
    private String result = "Toppings: ";
    Button show;
    CheckBox syrupBtn, sprinklesBtn, nutsBtn, cherriesBtn, orioBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check_boxes, container, false);
        show = view.findViewById(R.id.show_toast_btn);
        syrupBtn = view.findViewById(R.id.syrup);
        sprinklesBtn = view.findViewById(R.id.sprinkles);
        nutsBtn = view.findViewById(R.id.nuts);
        cherriesBtn = view.findViewById(R.id.cherries);
        orioBtn = view.findViewById(R.id.orio);

        syrupBtn.setOnClickListener(this);
        sprinklesBtn.setOnClickListener(this);
        nutsBtn.setOnClickListener(this);
        cherriesBtn.setOnClickListener(this);
        orioBtn.setOnClickListener(this);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.syrup && syrupBtn.isChecked()) {
            result = result + " " + ((CheckBox) view).getText().toString();
        }
        if (view.getId() == R.id.sprinkles && sprinklesBtn.isChecked()) {
            result = result + " " + ((CheckBox) view).getText().toString();
        }
        if (view.getId() == R.id.nuts && nutsBtn.isChecked()) {
            result = result + " " + ((CheckBox) view).getText().toString();
        }
        if (view.getId() == R.id.cherries && cherriesBtn.isChecked()) {
            result = result + " " + ((CheckBox) view).getText().toString();
        }
        if (view.getId() == R.id.orio && orioBtn.isChecked()) {
            result = result + " " + ((CheckBox) view).getText().toString();
        }
    }
}