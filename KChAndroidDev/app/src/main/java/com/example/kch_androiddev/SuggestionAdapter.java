package com.example.kch_androiddev;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.MyViewHolder> {

    Context context;
    List<Show> mShowList = new ArrayList<>();
    OnShowClickListener onShowClickListener;

    public SuggestionAdapter(Context context, List<Show> mShowList, OnShowClickListener listener) {
        this.context = context;
        this.mShowList = mShowList;
        onShowClickListener = listener;
    }


    public interface OnShowClickListener {
        void onShowClick(Show show);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.suggestion_show_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Show show = mShowList.get(position);
        holder.tvTitle2.setText(show.getTitle());
//        holder.ivThumbnail.setImageResource(show.getThumbnail());
        Glide.with(context)
                .load(show.getThumbnail())
                .placeholder(new ColorDrawable(Color.WHITE))
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(holder.ivThumbnail2);
    }

    @Override
    public int getItemCount() {
        return mShowList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivThumbnail2;
        private TextView tvTitle2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail2 = itemView.findViewById(R.id.ivThumbnail2);
            tvTitle2 = itemView.findViewById(R.id.tvTitle2);

            ivThumbnail2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShowClickListener.onShowClick(mShowList.get(getAdapterPosition()));
                }
            });
        }
    }
}
