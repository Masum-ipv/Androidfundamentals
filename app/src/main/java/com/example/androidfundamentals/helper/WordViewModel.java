package com.example.androidfundamentals.helper;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.androidfundamentals.model.Word;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordRepository wordRepository;
    private LiveData<List<Word>> allWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
        allWords = wordRepository.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return allWords;
    }

    public void insert(Word word) {
        wordRepository.insert(word);
    }

    public void update(Word word) {
        wordRepository.update(word);
    }

    public void deleteAll() {
        wordRepository.deleteAll();
    }

    public void deleteWord(Word word) {
        wordRepository.deleteWord(word);
    }

}
