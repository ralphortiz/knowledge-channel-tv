package com.example.kch_androiddev;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class ShowDetailFragment extends Fragment {

    Toolbar toolBar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fabPlay;
    TextView tvDescription;
    ImageView ivCover;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_detail, container, false);
        toolBar = view.findViewById(R.id.toolBar);
        collapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout);
        fabPlay = view.findViewById(R.id.fabPlay);
        tvDescription = view.findViewById(R.id.tvDescription);
        ivCover = view.findViewById(R.id.ivCover);

        fabPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayer();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString("title");
            String description = bundle.getString("description");
            String cover_art = bundle.getString("cover_art");
            collapsingToolbarLayout.setTitle(title);
            tvDescription.setText(description);
            Glide.with(getActivity())
                    .load(cover_art)
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(ivCover);
        }
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(255, 255, 255));

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        return view;
    }

    private void openPlayer() {
        Intent intent = new Intent(getActivity(), Exoplayer.class);
        startActivity(intent);
    }
}
