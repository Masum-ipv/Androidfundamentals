package com.example.androidfundamentals.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int mID;

    @NonNull
    @ColumnInfo
    private String mWord;

    public Word(@NonNull String mWord) {
        this.mWord = mWord;
    }

    @Ignore
    public Word(int id, @NonNull String mWord) {
        this.mID = id;
        this.mWord = mWord;
    }

    public String getWord() {
        return mWord;
    }

    public int getID() {
        return mID;
    }

    public void setID(int mID) {
        this.mID = mID;
    }
}
