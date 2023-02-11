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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.androidfundamentals.R;
import com.example.androidfundamentals.helper.SourceCodeLoader;


public class WebPageFragment extends Fragment
        implements AdapterView.OnItemSelectedListener,
        View.OnClickListener, LoaderManager.LoaderCallbacks<String> {

    Spinner spinner;
    Button button;
    EditText urlText;
    TextView pageSource;
    String method = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web_page, container, false);
        spinner = view.findViewById(R.id.http_spinner);
        button = view.findViewById(R.id.source_btn);
        urlText = view.findViewById(R.id.url);
        pageSource = view.findViewById(R.id.page_source);
        spinner.setOnItemSelectedListener(this);
        button.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (getContext(), R.array.http_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (LoaderManager.getInstance(this).getLoader(0) != null) {
            LoaderManager.getInstance(this).initLoader(0, null, this);
        }

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        method = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        String baseURL = urlText.getText().toString();

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
        if (networkInfo != null && networkInfo.isConnected() && baseURL.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("searchQuery", method + baseURL);
            LoaderManager.getInstance(this).restartLoader(0, queryBundle, this);

            pageSource.setText(R.string.loading); //Show text Loading.. while fetching data
        } else {
            if (baseURL.length() == 0) {
                pageSource.setText(R.string.no_search_term);
            } else {
                pageSource.setText(R.string.no_network);
            }
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String url = "";
        if (args != null) {
            url = args.getString("searchQuery");
        }

        return new SourceCodeLoader(getContext(), url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if (data != null) {
            pageSource.setText(data);
        } else {
            pageSource.setText(R.string.no_results);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}