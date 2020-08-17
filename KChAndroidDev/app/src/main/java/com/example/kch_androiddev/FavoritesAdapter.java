package com.example.kch_androiddev;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder> {

    Context context;
    List<Show> mFavoritesList = new ArrayList<>();

    public FavoritesAdapter(Context context, List<Show> mFavoritesList) {
        this.context = context;
        this.mFavoritesList = mFavoritesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorites_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Show show = mFavoritesList.get(position);
        holder.tvListTitle.setText(show.getTitle());
        holder.tvSubject.setText(show.getSubject());
        Glide.with(context)
                .load(show.getCover_art())
                .placeholder(new ColorDrawable(Color.WHITE))
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(holder.ivListCover);

    }

    @Override
    public int getItemCount() {
        return mFavoritesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivListCover;
        private TextView tvListTitle;
        private TextView tvSubject;
        private RelativeLayout rlFavorite;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivListCover = itemView.findViewById(R.id.ivListCover);
            tvListTitle = itemView.findViewById(R.id.tvListTitle);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            rlFavorite = itemView.findViewById(R.id.rlFavorite);

            rlFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Exoplayer.class);
                    intent.putExtra("video_file", mFavoritesList.get(getAdapterPosition()).getVideo_file());
                    context.startActivity(intent);
                }
            });
        }
    }
}
