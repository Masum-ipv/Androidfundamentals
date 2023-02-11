package com.example.androidfundamentals.helper;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.androidfundamentals.model.Word;
import com.example.androidfundamentals.model.WordDao;

import java.util.List;

public class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> allWords;

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        this.wordDao = db.wordDao();
        this.allWords = wordDao.getAllWords();
    }

    LiveData<List<Word>> getAllWords() {
        return allWords;
    }

    public void insert(Word word) {
        new insertAsyncTask(wordDao).execute(word);
    }

    public void update(Word word) {
        new updateAsyncTask(wordDao).execute(word);
    }

    public void deleteWord(Word word) {
        new deleteWordAsyncTask(wordDao).execute(word);
    }

    public void deleteAll() {
        new deleteAllAsyncTask(wordDao).execute();
    }

    private class insertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao asyncTaskDao;

        public insertAsyncTask(WordDao dao) {
            this.asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            asyncTaskDao.insert(words[0]);
            return null;
        }
    }

    private class updateAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao asyncTaskDao;

        public updateAsyncTask(WordDao dao) {
            this.asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            asyncTaskDao.update(words[0]);
            return null;
        }
    }

    private class deleteWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao asyncTaskDao;

        public deleteWordAsyncTask(WordDao dao) {
            this.asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            asyncTaskDao.deleteWord(words[0]);
            return null;
        }
    }

    private class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDao asyncTaskDao;

        public deleteAllAsyncTask(WordDao dao) {
            this.asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteAll();
            return null;
        }
    }
}
