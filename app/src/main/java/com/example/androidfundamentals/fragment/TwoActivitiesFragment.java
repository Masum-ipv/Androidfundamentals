package com.example.androidfundamentals.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidfundamentals.R;
import com.example.androidfundamentals.activity.SecondActivity;

public class TwoActivitiesFragment extends Fragment {
    private TextView mainReceived, mainShowMessage;
    private EditText mainMessage;
    private Button mainSend;
    public static final String EXTRA_MESSAGE = "TwoActivitiesFragment.extra.MESSAGE";
    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        String reply = result.getData().getStringExtra(SecondActivity.EXTRA_REPLY);
                        mainReceived.setVisibility(View.VISIBLE);
                        mainShowMessage.setText(reply);
                        mainShowMessage.setVisibility(View.VISIBLE);
                        mainMessage.getText().clear();
                    }
                }
            }
    );


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two_activities, container, false);
        mainReceived = view.findViewById(R.id.main_received);
        mainShowMessage = view.findViewById(R.id.main_show_message);
        mainMessage = view.findViewById(R.id.main_message);
        mainSend = view.findViewById(R.id.main_send);


        mainSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SecondActivity.class);
                String message = mainMessage.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                mGetContent.launch(intent);
            }
        });

        return view;
    }


}