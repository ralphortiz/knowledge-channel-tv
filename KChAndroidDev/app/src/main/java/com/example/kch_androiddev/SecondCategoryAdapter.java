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

public class SecondCategoryAdapter extends RecyclerView.Adapter<SecondCategoryAdapter.MyViewHolder> {

    Context context;
    List<Show> mShowList = new ArrayList<>();
    OnShowClickListener onShowClickListener;

    public SecondCategoryAdapter(Context context, List<Show> mShowList, OnShowClickListener listener) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.horizontal_show_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Show show = mShowList.get(position);
        holder.tvTitle.setText(show.getTitle());
        Glide.with(context)
                .load(show.getThumbnail())
                .placeholder(new ColorDrawable(Color.WHITE))
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(holder.ivThumbnail);


    }

    @Override
    public int getItemCount() {
        return mShowList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivThumbnail;
        private TextView tvTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvTitle = itemView.findViewById(R.id.tvTitle);

            ivThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShowClickListener.onShowClick(mShowList.get(getAdapterPosition()));
                }
            });
        }
    }
}
