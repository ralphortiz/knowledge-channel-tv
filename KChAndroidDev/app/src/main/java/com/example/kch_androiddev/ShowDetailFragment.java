package com.example.kch_androiddev;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class ShowDetailFragment extends Fragment implements SuggestionAdapter.OnShowClickListener {

    Toolbar toolBar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RelativeLayout btnWatchNow;
    RecyclerView rvSuggestion;
    TextView tvDescription, tvShowTitle, tvCatSub, tvSuggestion;
    ImageView ivCover;
    static ImageView btnFavorites;
    String title, description, category, subject, coverArt, videoFile;
    Show show;
    ArrayList<Show> suggestionLst = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_detail, container, false);
        toolBar = view.findViewById(R.id.toolBar);
        collapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout);
        btnWatchNow = view.findViewById(R.id.btnWatchNow);
        btnFavorites = view.findViewById(R.id.btnFavorites);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvCatSub = view.findViewById(R.id.tvCatSub);
        tvSuggestion = view.findViewById(R.id.tvSuggestion);
        rvSuggestion = view.findViewById(R.id.rvSuggestion);
        tvShowTitle = view.findViewById(R.id.tvShowTitle);
        ivCover = view.findViewById(R.id.ivCover);

        suggestionLst.add(new Show("Episode 1", "https://firebasestorage.googleapis.com/v0/b/kch-mobile-app.appspot.com/o/Thumbnails%2Fsciencesays_tb.png?alt=media&token=33c046c0-8d40-48cb-86a6-d1964ebfe299"));
        suggestionLst.add(new Show("Episode 2", "https://firebasestorage.googleapis.com/v0/b/kch-mobile-app.appspot.com/o/Thumbnails%2Fsciencesays_tb.png?alt=media&token=33c046c0-8d40-48cb-86a6-d1964ebfe299"));
        suggestionLst.add(new Show("Episode 3", "https://firebasestorage.googleapis.com/v0/b/kch-mobile-app.appspot.com/o/Thumbnails%2Fsciencesays_tb.png?alt=media&token=33c046c0-8d40-48cb-86a6-d1964ebfe299"));
        suggestionLst.add(new Show("Episode 4", "https://firebasestorage.googleapis.com/v0/b/kch-mobile-app.appspot.com/o/Thumbnails%2Fmusikantahan_tb.png?alt=media&token=65c8b3fd-9e24-4e3f-952f-950a73f892be"));
        suggestionLst.add(new Show("Episode 5", "https://firebasestorage.googleapis.com/v0/b/kch-mobile-app.appspot.com/o/Thumbnails%2Fmusikantahan_tb.png?alt=media&token=65c8b3fd-9e24-4e3f-952f-950a73f892be"));
        suggestionLst.add(new Show("Episode 6", "https://firebasestorage.googleapis.com/v0/b/kch-mobile-app.appspot.com/o/Thumbnails%2Fmusikantahan_tb.png?alt=media&token=65c8b3fd-9e24-4e3f-952f-950a73f892be"));
        suggestionLst.add(new Show("Episode 7", "https://firebasestorage.googleapis.com/v0/b/kch-mobile-app.appspot.com/o/Thumbnails%2Fmusikantahan_tb.png?alt=media&token=65c8b3fd-9e24-4e3f-952f-950a73f892be"));
        suggestionLst.add(new Show("Episode 8", "https://firebasestorage.googleapis.com/v0/b/kch-mobile-app.appspot.com/o/Thumbnails%2Fmusikantahan_tb.png?alt=media&token=65c8b3fd-9e24-4e3f-952f-950a73f892be"));


        rvSuggestion.setNestedScrollingEnabled(false);
        SuggestionAdapter suggestionAdapter = new SuggestionAdapter(getActivity(), suggestionLst, this);
        rvSuggestion.setLayoutManager(new GridLayoutManager(getActivity(), 2, RecyclerView.HORIZONTAL, false));
        rvSuggestion.setAdapter(suggestionAdapter);
        suggestionAdapter.notifyDataSetChanged();

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

        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("title");
            description = bundle.getString("description");
            category = bundle.getString("category");
            subject = bundle.getString("subject");
            coverArt = bundle.getString("cover_art");
            videoFile = bundle.getString("video_file");
            collapsingToolbarLayout.setTitle(title);
            tvShowTitle.setText(title.toUpperCase());
            tvCatSub.setText(category + " • " + subject);
            tvDescription.setText(description);
            tvSuggestion.setText("Watch other " + subject + " Shows");
            Glide.with(getActivity())
                    .load(coverArt)
                    .placeholder(new ColorDrawable(Color.WHITE))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(ivCover);
        }

        int titleLength = title.length();
        if (titleLength > 30) {
            tvShowTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        } else if (titleLength > 25) {
            tvShowTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        } else {
            tvShowTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }

        checkIfExist(title);

        btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFavorites();
            }
        });

        btnWatchNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayer();
            }
        });

        return view;
    }

    private void checkIfExist(String title) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child(Home.FULLNAME + "'s Favorite");
        final DatabaseReference titleRef = dbRef.child(title);
        titleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    btnFavorites.setColorFilter(getContext().getResources().getColor(R.color.darkOrange));
                } else {
                    btnFavorites.setColorFilter(getContext().getResources().getColor(R.color.white));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Error: " + error);
            }
        });
    }

    private void saveFavorites() {
        final Animation expandIn = AnimationUtils.loadAnimation(getActivity(), R.anim.favorite_animation);
        show = new Show(title, coverArt, videoFile, subject);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child(Home.FULLNAME + "'s Favorite");
        final DatabaseReference titleRef = dbRef.child(title);
        titleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(getContext(), "You have already added this", Toast.LENGTH_LONG).show();
                } else {
                    btnFavorites.startAnimation(expandIn);
                    Toast.makeText(getContext(), title + " is added to My Favorites", Toast.LENGTH_LONG).show();
                    btnFavorites.setColorFilter(getContext().getResources().getColor(R.color.darkOrange));
                    titleRef.setValue(show);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Error: " + error);
            }
        });
    }

    private void openPlayer() {
        Intent intent = new Intent(getActivity(), Exoplayer.class);
        intent.putExtra("video_file", videoFile);
        startActivity(intent);
    }

    @Override
    public void onShowClick(Show show) {

    }
}
