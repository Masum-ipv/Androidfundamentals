package com.example.androidfundamentals.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidfundamentals.R;
import com.example.androidfundamentals.fragment.TwoActivitiesFragment;

public class SecondActivity extends AppCompatActivity {
    private TextView replyShowMessage;
    private EditText replyMessage;
    private Button replySend;
    public static final String EXTRA_REPLY = "TwoActivitiesFragment.extra.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        replyShowMessage = findViewById(R.id.reply_show_message);
        replyMessage = findViewById(R.id.reply_message);
        replySend = findViewById(R.id.reply_send);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        String message = getIntent().getStringExtra(TwoActivitiesFragment.EXTRA_MESSAGE);
        replyShowMessage.setText(message);

        replySend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reply = replyMessage.getText().toString();
                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_REPLY, reply);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });


    }
}