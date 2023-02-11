package com.example.androidfundamentals.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfundamentals.R;
import com.example.androidfundamentals.activity.NewWordActivity;
import com.example.androidfundamentals.adapter.WordsAdapter;
import com.example.androidfundamentals.helper.WordViewModel;
import com.example.androidfundamentals.model.Word;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

public class RoomWordsSampleFragment extends Fragment {
    private WordViewModel wordViewModel;
    private RecyclerView recyclerView;
    private WordsAdapter adapter;
    int swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;

    public static final String UPDATE_WORD = "extra_word_to_be_updated";
    public static final String DATA_ID = "extra_data_id";
    public static final String REQUEST_CODE = "request_code";

    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Word word_data = new Word(result.getData().getStringExtra(NewWordActivity.EXTRA_REPLY));
                        int id = result.getData().getIntExtra(DATA_ID, -1);
                        int request_code = result.getData().getIntExtra(REQUEST_CODE, -1);
                        if (request_code == NEW_WORD_ACTIVITY_REQUEST_CODE) {
                            wordViewModel.insert(word_data);
                            Toast.makeText(getContext(), "Insert: " + word_data.getWord(), Toast.LENGTH_LONG).show();
                        } else if (request_code == UPDATE_WORD_ACTIVITY_REQUEST_CODE) {
                            Toast.makeText(getContext(), "Update: " + word_data.getWord(), Toast.LENGTH_LONG).show();
                            wordViewModel.update(new Word(id, word_data.getWord()));
                        }

                    } else {
                        Toast.makeText(getContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
                    }
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_words_sample, container, false);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new WordsAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeCard(swipeDirs);

        // Get a new or existing ViewModel from the ViewModelProvider.
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        wordViewModel.getAllWords().observe(getViewLifecycleOwner(), new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewWordActivity.class);
                mGetContent.launch(intent);
            }
        });

        adapter.setOnItemClickListener(new WordsAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Word word = adapter.getWordAtPosition(position);
                launchUpdateWordActivity(word);
            }
        });
        return view;
    }

    private void launchUpdateWordActivity(Word word) {
        Intent intent = new Intent(getContext(), NewWordActivity.class);
        intent.putExtra(RoomWordsSampleFragment.UPDATE_WORD, word.getWord());
        intent.putExtra(RoomWordsSampleFragment.DATA_ID, word.getID());
        mGetContent.launch(intent);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.delete_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            Toast.makeText(getContext(), "Clearing the data...", Toast.LENGTH_SHORT).show();
            wordViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                Collections.swap(wordViewModel.getAllWords().getValue(), from, to);
                adapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Word myWord = adapter.getWordAtPosition(position);
                Toast.makeText(getContext(), "Deleting " + myWord.getWord(), Toast.LENGTH_SHORT).show();

                // Delete the word
                wordViewModel.deleteWord(myWord);

                adapter.notifyItemRemoved(position);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}