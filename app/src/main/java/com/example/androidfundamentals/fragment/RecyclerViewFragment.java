package com.example.androidfundamentals.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;

import com.example.androidfundamentals.R;
import com.example.androidfundamentals.adapter.WordListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;

public class RecyclerViewFragment extends Fragment implements View.OnClickListener {

    private final LinkedList<String> mWordList = new LinkedList<>();
    private RecyclerView recyclerView;
    private WordListAdapter wordListAdapter;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        for (int i = 0; i < 5; i++) {
            mWordList.addLast("Word: " + i);
        }
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        recyclerView = view.findViewById(R.id.recyclerview_holder);
        wordListAdapter = new WordListAdapter(getContext(), mWordList);
        recyclerView.setAdapter(wordListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onClick(View view) {
        int wordListSize = mWordList.size();
        mWordList.addLast("+ Word " + wordListSize);
        recyclerView.getAdapter().notifyItemInserted(wordListSize);
        recyclerView.smoothScrollToPosition(wordListSize);
    }
}