package com.example.kch_androiddev;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;

import java.util.ArrayList;
import java.util.List;

public class MoreFragment extends Fragment implements AvatarAdapter.OnAvatarClickListener {

    Button btnSettings, btnHelp, btnSignOut;
    TextView tvUser;
    RoundedImageView ivAvatar;
    List<Avatar> avatarList = new ArrayList<>();
    AvatarAdapter avatarAdapter;
    public static final String SHARED_PREFS = "sharedPreferences";
    FirebaseFirestore db;
    String myAvatar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        btnSettings = view.findViewById(R.id.btnSettings);
        btnHelp = view.findViewById(R.id.btnHelp);
        btnSignOut = view.findViewById(R.id.btnSignOut);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        tvUser = view.findViewById(R.id.tvUser);

        Glide.with(getActivity())
                .load(Home.DEFAULT_AVATAR)
                .placeholder(new ColorDrawable(Color.WHITE))
//                        .apply(RequestOptions.skipMemoryCacheOf(true))
//                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(ivAvatar);


        tvUser.setText("Hi, " + Home.FULLNAME + "!");

//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
//        ivAvatar.setImageResource(sharedPreferences.getInt("drawableID", 0));
        avatarList.add(new Avatar("https://firebasestorage.googleapis.com/v0/b/kch-mobile-app.appspot.com/o/Avatars%2Favatar_ralph.png?alt=media&token=6fb97849-4d4c-4efb-9344-0dea4fb5e390"));
        avatarList.add(new Avatar("https://firebasestorage.googleapis.com/v0/b/kch-mobile-app.appspot.com/o/Avatars%2Favatar_nicole.png?alt=media&token=c1f1143d-4134-41cd-9b5f-2f95803719bf"));
        avatarList.add(new Avatar("https://firebasestorage.googleapis.com/v0/b/kch-mobile-app.appspot.com/o/Avatars%2Favatar_charles.png?alt=media&token=d74e5ab5-9c57-4d94-a529-98a000429aa0"));
        avatarList.add(new Avatar("https://firebasestorage.googleapis.com/v0/b/kch-mobile-app.appspot.com/o/Avatars%2Favatar_doris.png?alt=media&token=13276042-8af8-48cd-9b3c-9feb85722cda"));
        avatarList.add(new Avatar("https://firebasestorage.googleapis.com/v0/b/kch-mobile-app.appspot.com/o/Avatars%2Favatar_adu.png?alt=media&token=a7002b9d-8067-4cba-b830-5040c1c15458"));
//        avatarList.add(new Avatar(R.drawable.avatar_nicole));
//        avatarList.add(new Avatar(R.drawable.avatar_ralph));
//        avatarList.add(new Avatar(R.drawable.avatar_charles));
//        avatarList.add(new Avatar(R.drawable.avatar_doris));
//        avatarList.add(new Avatar(R.drawable.avatar_adu));
        avatarAdapter = new AvatarAdapter(getActivity(), avatarList, this);

        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectProfile();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSettings();
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHelp();
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }

    private void selectProfile() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_profile, null);
        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        RecyclerView rvAvatar = view.findViewById(R.id.rvAvatar);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_curved_edges);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.getWindow().setLayout(800, WindowManager.LayoutParams.WRAP_CONTENT);

        rvAvatar.setAdapter(avatarAdapter);
        rvAvatar.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        avatarAdapter.notifyDataSetChanged();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putInt("drawableID", myAvatar);
//                editor.commit();
//                ivAvatar.setImageResource(myAvatar);
                db = FirebaseFirestore.getInstance();
                db.collection("Users").document(Home.USER_ID)
                        .update("avatar", myAvatar);

                Glide.with(getActivity())
                        .load(myAvatar)
                        .placeholder(new ColorDrawable(Color.WHITE))
//                        .apply(RequestOptions.skipMemoryCacheOf(true))
//                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .into(ivAvatar);
                dialog.dismiss();
            }
        });
    }

    private void clickHelp() {
        HelpFragment fragment = new HelpFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer3, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void clickSettings() {
        SettingsFragment fragment = new SettingsFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer3, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onAvatarClick(Avatar avatar) {
        myAvatar = avatar.getAvatar();
    }
}
