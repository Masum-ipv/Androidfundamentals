package com.example.androidfundamentals.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidfundamentals.R;

public class KeyboardDialPhoneFragment extends Fragment {
    EditText number_field;
    private final String TAG = this.getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.keyboard_dial_phone_fragment, container, false);
        number_field = view.findViewById(R.id.phone_number);
        if (number_field != null) {
            number_field.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    boolean handled = false;
                    if (i == EditorInfo.IME_ACTION_SEND) {
                        dialNumber();
                        handled = true;
                    }
                    return handled;
                }


            });
        }
        return view;
    }

    private void dialNumber() {
        String phoneNum = null;
        if (number_field != null)
            phoneNum = "tel:" + number_field.getText().toString();
        Log.d(TAG, "dialNumber: " + phoneNum);
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(phoneNum));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(TAG, "Implicit Intents can't handle this!");
        }

    }
}