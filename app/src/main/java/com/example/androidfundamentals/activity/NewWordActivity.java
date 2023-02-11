package com.example.androidfundamentals.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidfundamentals.R;
import com.example.androidfundamentals.fragment.RoomWordsSampleFragment;

public class NewWordActivity extends AppCompatActivity {

    private EditText editWord;
    private Button saveButton;
    private int position;
    public static final String EXTRA_REPLY =
            "com.example.androidfundamentals.activity.REPLY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        editWord = findViewById(R.id.edit_word);
        saveButton = findViewById(R.id.save_word);

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String existingWord = extras.getString(RoomWordsSampleFragment.UPDATE_WORD, "");
            position = extras.getInt(RoomWordsSampleFragment.DATA_ID, -1);
            editWord.setText(existingWord);
            editWord.setSelection(existingWord.length());
            editWord.requestFocus();
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(editWord.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String word = editWord.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    if (extras != null) {//Update
                        replyIntent.putExtra(RoomWordsSampleFragment.DATA_ID, position);
                        replyIntent.putExtra(RoomWordsSampleFragment.REQUEST_CODE, RoomWordsSampleFragment.UPDATE_WORD_ACTIVITY_REQUEST_CODE);
                    } else {//Insert
                        replyIntent.putExtra(RoomWordsSampleFragment.REQUEST_CODE, RoomWordsSampleFragment.NEW_WORD_ACTIVITY_REQUEST_CODE);
                    }
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}