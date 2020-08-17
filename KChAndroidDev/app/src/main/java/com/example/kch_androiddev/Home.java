package com.example.kch_androiddev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomNav;
    final Fragment fragment1 = new HomeFragment();
    final Fragment fragment2 = new SearchFragment();
    final Fragment fragment3 = new FavoritesFragment();
    final Fragment fragment4 = new MoreFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    static String FULLNAME;
    static String DEFAULT_AVATAR;
    static String USER_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        FirebaseApp.initializeApp(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            FULLNAME = extras.getString("fullname");
            DEFAULT_AVATAR = extras.getString("avatar");
            USER_ID = extras.getString("userID");
        }

        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        fm.beginTransaction().add(R.id.fragmentContainer, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.fragmentContainer, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.fragmentContainer, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragmentContainer, fragment1, "1").commit();

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().
//                    beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
//        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    Fragment selectedFragment = null;
//
//                    switch (item.getItemId()) {
//                        case R.id.nav_home:
//                            selectedFragment = new HomeFragment();
//                            break;
//                        case R.id.nav_search:
//                            selectedFragment = new SearchFragment();
//                            break;
//                        case R.id.nav_download:
//                            selectedFragment = new FavoritesFragment();
//                            break;
//                        case R.id.nav_more:
//                            selectedFragment = new MoreFragment();
//                            break;
//                    }
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
//                    return true;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            fm.beginTransaction().hide(active).show(fragment1).commit();
                            active = fragment1;
                            return true;
                        case R.id.nav_search:
                            fm.beginTransaction().hide(active).show(fragment2).commit();
                            active = fragment2;
                            return true;
                        case R.id.nav_favorites:
                            fm.beginTransaction().hide(active).show(fragment3).commit();
                            active = fragment3;
                            return true;
                        case R.id.nav_more:
                            fm.beginTransaction().hide(active).show(fragment4).commit();
                            active = fragment4;
                            return true;
                    }
                    return false;
                }
            };

}