package com.example.kch_androiddev;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements FirstCategoryAdapter.OnShowClickListener, SecondCategoryAdapter.OnShowClickListener {

    RecyclerView rvFirstCategory, rvSecondCategory;
    SliderView sliderView;
    List<SliderItem> sliderItemList = new ArrayList<>();
    private FirebaseFirestore db;
    FirebaseFetch firebaseFetch = new FirebaseFetch();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FirebaseApp.initializeApp(getActivity());
        rvFirstCategory = view.findViewById(R.id.rvFirstCategory);
        rvSecondCategory = view.findViewById(R.id.rvSecondCategory);
        sliderView = view.findViewById(R.id.sliderView);

        sliderItemList.add(new SliderItem(R.drawable.cover_art1));
        sliderItemList.add(new SliderItem(R.drawable.cover_art2));
        sliderItemList.add(new SliderItem(R.drawable.cover_art3));

        firebaseFetch.getData();

        FirstCategoryAdapter firstCategoryAdapter = new FirstCategoryAdapter(getActivity(), firebaseFetch.getCategory1Lst(), this);
        rvFirstCategory.setAdapter(firstCategoryAdapter);
        rvFirstCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        firstCategoryAdapter.notifyDataSetChanged();

        SecondCategoryAdapter secondCategoryAdapter = new SecondCategoryAdapter(getActivity(), firebaseFetch.getCategory2Lst(), this);
        rvSecondCategory.setAdapter(secondCategoryAdapter);
        rvSecondCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        secondCategoryAdapter.notifyDataSetChanged();

        SliderAdapter sliderAdapter = new SliderAdapter(getActivity(), sliderItemList);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderAdapter.notifyDataSetChanged();

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(2);
        sliderView.startAutoCycle();

        return view;
    }

    @Override
    public void onShowClick(Show show) {
        Fragment fragment = new ShowDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", show.getTitle());
        bundle.putString("description", show.getDescription());
        bundle.putString("cover_art",show.getCover_art());
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        Toast.makeText(getActivity().getApplicationContext(), "This is item in position " + show.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
