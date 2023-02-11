package com.example.androidfundamentals.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidfundamentals.activity.OrderActivity;
import com.example.androidfundamentals.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClickableImagesFragment extends Fragment implements View.OnClickListener {

    ImageView donut, iceCream, froyo;
    FloatingActionButton fab;
    String mOrderMessage = "Nothing Selected!";
    public static final String EXTRA_MESSAGE =
            "com.example.android.droidcafe.extra.MESSAGE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_clickable_images_fragment, container, false);
        donut = view.findViewById(R.id.donut);
        iceCream = view.findViewById(R.id.ice_cream);
        froyo = view.findViewById(R.id.froyo);
        fab = view.findViewById(R.id.fab);
        donut.setOnClickListener(this);
        iceCream.setOnClickListener(this);
        froyo.setOnClickListener(this);
        fab.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.donut) {
            mOrderMessage = getString(R.string.donut_order_message);
            displayToast(mOrderMessage);
        } else if (view.getId() == R.id.ice_cream) {
            mOrderMessage = getString(R.string.ice_cream_order_message);
            displayToast(mOrderMessage);
        } else if (view.getId() == R.id.froyo) {
            mOrderMessage = getString(R.string.froyo_order_message);
            displayToast(mOrderMessage);
        } else if (view.getId() == R.id.fab) {
            Intent intent = new Intent(getActivity(), OrderActivity.class);
            intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
            startActivity(intent);
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_SHORT).show();
    }
}