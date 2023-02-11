package com.example.androidfundamentals.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidfundamentals.R;
import com.example.androidfundamentals.helper.BookLoader;

import org.json.JSONArray;
import org.json.JSONObject;

public class WhoWroteItFragment extends Fragment
        implements View.OnClickListener, LoaderManager.LoaderCallbacks<String> {

    private EditText mBookInput;
    private TextView mTitleText, mAuthorText;
    private Button mSearchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_who_wrote_it, container, false);
        mBookInput = view.findViewById(R.id.bookInput);
        mTitleText = view.findViewById(R.id.titleText);
        mAuthorText = view.findViewById(R.id.authorText);
        mSearchButton = view.findViewById(R.id.searchButton);
        mSearchButton.setOnClickListener(this);

        if (LoaderManager.getInstance(this).getLoader(0) != null) {
            LoaderManager.getInstance(this).initLoader(0, null, this);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        String searchQuery = mBookInput.getText().toString();

        // Hide the keyboard when the button is pushed.
        InputMethodManager inputManager = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // Check the status of the network connection.
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        // If the network is available, connected, and the search field
        // is not empty, start a FetchBook AsyncTask.
        if (networkInfo != null && networkInfo.isConnected() && searchQuery.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("searchQuery", searchQuery);
            LoaderManager.getInstance(this).restartLoader(0, queryBundle, this);

            mAuthorText.setText("");
            mTitleText.setText(R.string.loading); //Show text Loading.. while fetching data
        } else {
            if (searchQuery.length() == 0) {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_search_term);
            } else {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_network);
            }
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String searchQuery = "";

        if (args != null) {
            searchQuery = args.getString("searchQuery");
        }

        return new BookLoader(getContext(), searchQuery);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            int i = 0;
            String title = null;
            String authors = null;

            while (i < jsonArray.length() && authors == null && title == null) {
                JSONObject book = jsonArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            }
            if (title != null && authors != null) {
                mTitleText.setText(title);
                mAuthorText.setText(authors);
            } else {
                mTitleText.setText(R.string.no_results);
                mAuthorText.setText("");
            }

        } catch (Exception e) {
            mTitleText.setText(R.string.no_results);
            mAuthorText.setText("");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}