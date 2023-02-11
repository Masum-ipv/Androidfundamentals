package com.example.androidfundamentals.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.androidfundamentals.fragment.Tab_Fragment1;
import com.example.androidfundamentals.fragment.Tab_Fragment2;
import com.example.androidfundamentals.fragment.Tab_Fragment3;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(@NonNull FragmentManager fm, int NumOfTabs) {

        super(fm);
        this.mNumOfTabs = NumOfTabs;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Tab_Fragment1();
            case 1:
                return new Tab_Fragment2();
            case 2:
                return new Tab_Fragment3();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
