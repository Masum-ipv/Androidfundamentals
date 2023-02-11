package com.example.androidfundamentals.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidfundamentals.R;
import com.example.androidfundamentals.activity.CardDetailActivity;
import com.example.androidfundamentals.model.Sport;

import java.util.ArrayList;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.SportsViewHolder> {
    private ArrayList<Sport> mSportData;
    private LayoutInflater mInflater;
    private Context mContext;

    public SportsAdapter(Context context, ArrayList<Sport> mList) {
        this.mSportData = mList;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SportsAdapter.SportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.sports_list_item, parent, false);
        return new SportsAdapter.SportsViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SportsAdapter.SportsViewHolder holder, int position) {
        Sport currentSport = mSportData.get(position);
        holder.bindTo(currentSport);
    }

    @Override
    public int getItemCount() {
        return mSportData.size();
    }

    public class SportsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleText;
        private TextView mInfoText;
        private SportsAdapter mAdapter;
        private ImageView mSportsImage;

        public SportsViewHolder(@NonNull View itemView, SportsAdapter mAdapter) {
            super(itemView);
            mTitleText = itemView.findViewById(R.id.title);
            mInfoText = itemView.findViewById(R.id.subTitle);
            mSportsImage = itemView.findViewById(R.id.sportsImage);
            itemView.setOnClickListener(this);
            this.mAdapter = mAdapter;
        }

        public void bindTo(Sport currentSport) {
            mTitleText.setText(currentSport.getTitle());
            mInfoText.setText(currentSport.getInfo());
            Glide.with(mContext).load(currentSport.getImageResources()).into(mSportsImage);
        }

        @Override
        public void onClick(View view) {
            Sport currentSport = mSportData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, CardDetailActivity.class);
            detailIntent.putExtra(Sport.TITLE_KEY, currentSport.getTitle());
            detailIntent.putExtra(Sport.SUB_TITLE_KEY, currentSport.getInfo());
            detailIntent.putExtra(Sport.IMAGE_KEY, currentSport.getImageResources());
            mContext.startActivity(detailIntent);
        }
    }
}
