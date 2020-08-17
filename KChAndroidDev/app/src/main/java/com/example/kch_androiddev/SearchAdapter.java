package com.example.kch_androiddev;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable {

    Context context;
    List<Show> mShowList = new ArrayList<>();
    List<Show> mShowFilter = new ArrayList<>();
    OnShowClickListener onShowClickListener;

    public SearchAdapter(Context context, List<Show> mShowList) {
        this.context = context;
        this.mShowList = mShowList;
        this.mShowFilter = mShowList;
    }

    public SearchAdapter(Context context, List<Show> mShowList, SearchAdapter.OnShowClickListener listener) {
        this.context = context;
        this.mShowList = mShowList;
        onShowClickListener = listener;
        this.mShowFilter = mShowList;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();
                if (Key.isEmpty()) {
                    mShowFilter = mShowList;
                } else {
                    List<Show> filteredLst = new ArrayList<>();
                    for (Show show : mShowList) {
                        if (show.getTitle().toLowerCase().contains(Key.toLowerCase())) {
                            filteredLst.add(show);
                        }
                    }
                    mShowFilter = filteredLst;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mShowFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mShowFilter = (List<Show>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnShowClickListener {
        void onShowClick(Show show);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_show_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Show show = mShowFilter.get(position);
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
        return mShowFilter.size();
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
