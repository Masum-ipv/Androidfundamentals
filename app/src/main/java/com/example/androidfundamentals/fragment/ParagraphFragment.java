package com.example.androidfundamentals.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidfundamentals.R;

public class ParagraphFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_paragraph, container, false);
        TextView article_text = view.findViewById(R.id.article);
        registerForContextMenu(article_text);

        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit_article) {
            Toast.makeText(getActivity(), "Edit choice clicked.", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.share_article) {
            Toast.makeText(getActivity(), "Share choice clicked.", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.delete_article) {
            Toast.makeText(getActivity(), "Delete choice clicked.", Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }
}