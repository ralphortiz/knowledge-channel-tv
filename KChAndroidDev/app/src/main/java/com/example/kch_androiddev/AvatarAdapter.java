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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.MyViewHolder> {

    Context context;
    List<Avatar> avatarList = new ArrayList<>();
    OnAvatarClickListener onAvatarClickListener;

    public AvatarAdapter(Context context, List<Avatar> avatarList, OnAvatarClickListener listener) {
        this.context = context;
        this.avatarList = avatarList;
        this.onAvatarClickListener = listener;
    }

    public interface OnAvatarClickListener {
        void onAvatarClick(Avatar avatar);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.avatar_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Avatar avatar = avatarList.get(position);
//        holder.ivAvatarIcon.setImageResource(avatar.getAvatar());
        Glide.with(context)
                .load(avatar.getAvatar())
                .placeholder(new ColorDrawable(ContextCompat.getColor(context, R.color.orange)))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(holder.ivAvatarIcon);
    }

    @Override
    public int getItemCount() {
        return avatarList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView ivAvatarIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatarIcon = itemView.findViewById(R.id.ivAvatarIcon);
            ivAvatarIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAvatarClickListener.onAvatarClick(avatarList.get(getAdapterPosition()));
                }
            });
        }
    }
}
