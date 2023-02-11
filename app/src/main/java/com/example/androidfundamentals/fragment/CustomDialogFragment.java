package com.example.androidfundamentals.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidfundamentals.R;

public class CustomDialogFragment extends Fragment implements View.OnClickListener {

    Button alertButton, dateButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment, container, false);
        alertButton = view.findViewById(R.id.alert_btn);
        dateButton = view.findViewById(R.id.date_btn);
        alertButton.setOnClickListener(this);
        dateButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.alert_btn) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Alert");
            builder.setMessage("Click OK to continue, or Cancel to stop:");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getActivity(), "Pressed OK",
                            Toast.LENGTH_SHORT).show();

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getActivity(), "Pressed Cancel",
                            Toast.LENGTH_SHORT).show();

                }
            });
            builder.show();
        } else if (view.getId() == R.id.date_btn) {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
        }

    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (day_string +
                "/" + month_string + "/" + year_string);
        Toast.makeText(getActivity(), "Date: " + dateMessage,
                Toast.LENGTH_SHORT).show();

    }
}