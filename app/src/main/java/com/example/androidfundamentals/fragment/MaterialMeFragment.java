package com.example.androidfundamentals.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidfundamentals.R;
import com.example.androidfundamentals.adapter.SportsAdapter;
import com.example.androidfundamentals.model.Sport;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class MaterialMeFragment extends Fragment implements View.OnClickListener {

    private RecyclerView mRecycleview;
    private SportsAdapter mAdapter;
    private FloatingActionButton fab;
    private ArrayList<Sport> mSportData = new ArrayList<>();
    private static final String SPORT_ITEMS = "items";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        int swipeDirs = gridColumnCount > 1 ? 0 : ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_material_me, container, false);
        mRecycleview = view.findViewById(R.id.recyclerview);
        fab = view.findViewById(R.id.refreshFab);
        fab.setOnClickListener(this);
        mRecycleview.setLayoutManager(new GridLayoutManager(getContext(), gridColumnCount));
        mAdapter = new SportsAdapter(getContext(), mSportData);
        mRecycleview.setAdapter(mAdapter);
        initData();
        swipeCard(swipeDirs);
        return view;
    }

    private void initData() {

        String[] sportsTitle = getResources().getStringArray(R.array.sports_titles);
        String[] sportsInfo = getResources().getStringArray(R.array.sports_info);
        TypedArray sportsImageResources = getResources().obtainTypedArray(R.array.sports_images);
        mSportData.clear();
        for (int i = 0; i < sportsInfo.length; i++) {
            mSportData.add(new Sport(sportsTitle[i], sportsInfo[i],
                    sportsImageResources.getResourceId(i, 0)));
        }
        sportsImageResources.recycle();
        mAdapter.notifyDataSetChanged();
    }

    private void swipeCard(int swipeDirs) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                swipeDirs) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(mSportData, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mSportData.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(mRecycleview);
    }

    @Override
    public void onClick(View view) {
        initData();
    }
}