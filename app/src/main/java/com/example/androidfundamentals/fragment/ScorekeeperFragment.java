package com.example.androidfundamentals.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.androidfundamentals.R;

public class ScorekeeperFragment extends Fragment implements View.OnClickListener {
    ImageButton decreaseTeam1, decreaseTeam2, increaseTeam1, increaseTeam2;
    TextView score1, score2;
    int teamScore1 = 0, teamScore2 = 0;
    static final String STATE_SCORE_1 = "Team 1 Score";
    static final String STATE_SCORE_2 = "Team 2 Score";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scorekeeper, container, false);
        decreaseTeam1 = view.findViewById(R.id.decreaseTeam1);
        decreaseTeam2 = view.findViewById(R.id.decreaseTeam2);
        increaseTeam1 = view.findViewById(R.id.increaseTeam1);
        increaseTeam2 = view.findViewById(R.id.increaseTeam2);
        score1 = view.findViewById(R.id.score1);
        score2 = view.findViewById(R.id.score2);

        decreaseTeam1.setOnClickListener(this);
        decreaseTeam2.setOnClickListener(this);
        increaseTeam1.setOnClickListener(this);
        increaseTeam2.setOnClickListener(this);
        score1.setOnClickListener(this);
        score2.setOnClickListener(this);

        if (savedInstanceState != null) {
            teamScore1 = savedInstanceState.getInt(STATE_SCORE_1);
            teamScore2 = savedInstanceState.getInt(STATE_SCORE_2);

            //set the score text views.
            score1.setText(String.valueOf(teamScore1));
            score2.setText(String.valueOf(teamScore2));
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.decreaseTeam1) {
            teamScore1--;
            score1.setText(String.valueOf(teamScore1));
        } else if (view.getId() == R.id.decreaseTeam2) {
            teamScore2--;
            score2.setText(String.valueOf(teamScore2));
        } else if (view.getId() == R.id.increaseTeam1) {
            teamScore1++;
            score1.setText(String.valueOf(teamScore1));
        } else if (view.getId() == R.id.increaseTeam2) {
            teamScore2++;
            score2.setText(String.valueOf(teamScore2));
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.night_mode_menu, menu);
        // Change the label of the menu based on the state of the app.
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        } else {
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.night_mode) {
            // Get the night mode state of the app.
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            //Set the theme mode for the restarted activity.
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode
                        (AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode
                        (AppCompatDelegate.MODE_NIGHT_YES);
            }
            // Recreate the activity for the theme change to take effect.
            getActivity().recreate();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //save the scores
        outState.putInt(STATE_SCORE_1, teamScore1);
        outState.putInt(STATE_SCORE_2, teamScore2);
        super.onSaveInstanceState(outState);
    }
}