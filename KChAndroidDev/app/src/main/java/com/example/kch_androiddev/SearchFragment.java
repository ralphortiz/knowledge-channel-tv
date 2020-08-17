package com.example.kch_androiddev;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchAdapter.OnShowClickListener {

    EditText etSearch;
    LinearLayout searchView;
    TextView tvSearchLabel;
    RecyclerView rvSearchShow;
    FirebaseFetch firebaseFetch = new FirebaseFetch();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        etSearch = view.findViewById(R.id.etSearch);
        rvSearchShow = view.findViewById(R.id.rvSearchShow);
        searchView = view.findViewById(R.id.searchView);
        tvSearchLabel = view.findViewById(R.id.tvSearchLabel);
        firebaseFetch.getData();

        final SearchAdapter searchAdapter = new SearchAdapter(getActivity(), firebaseFetch.getSearchLst(), this);
        rvSearchShow.setAdapter(searchAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        rvSearchShow.setLayoutManager(gridLayoutManager);
        rvSearchShow.setVisibility(View.GONE);


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchAdapter.getFilter().filter(s);
                if (TextUtils.isEmpty(s)) {
                    rvSearchShow.setVisibility(View.GONE);
                } else {
                    rvSearchShow.setVisibility(View.VISIBLE);
                    if (searchAdapter.getItemCount() < 1) {
                        rvSearchShow.setVisibility(View.GONE);
                        searchView.setVisibility(View.VISIBLE);
                    } else {
                        rvSearchShow.setVisibility(View.VISIBLE);
                        searchView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void onShowClick(Show show) {
        Fragment fragment = new ShowDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", show.getTitle());
        bundle.putString("description", show.getDescription());
        bundle.putString("category", show.getCategory());
        bundle.putString("subject", show.getSubject());
        bundle.putString("cover_art", show.getCover_art());
        bundle.putString("video_file", show.getVideo_file());
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer4, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        etSearch.clearFocus();
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
    }
}
