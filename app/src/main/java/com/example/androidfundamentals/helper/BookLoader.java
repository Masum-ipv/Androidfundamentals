package com.example.androidfundamentals.helper;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class BookLoader extends AsyncTaskLoader<String> {

    private String mSearchQuery;

    public BookLoader(@NonNull Context context, String searchQuery) {
        super(context);
        this.mSearchQuery = searchQuery;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getBookInfo(mSearchQuery);
    }
}
