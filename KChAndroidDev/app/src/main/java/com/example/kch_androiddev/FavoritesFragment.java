package com.example.kch_androiddev;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.content.ContentValues.TAG;

public class FavoritesFragment extends Fragment {

    List<Show> favoritesLst;
    RecyclerView rvMyFavorites;
    LinearLayout emptyView;
    FavoritesAdapter favoritesAdapter;
    Show show;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvMyFavorites = view.findViewById(R.id.rvMyFavorites);
        emptyView = view.findViewById(R.id.emptyView);
        loadData();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvMyFavorites);

        return view;
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        Show deletedItem = null;

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child(Home.FULLNAME + "'s Favorite");
                    final DatabaseReference itemRemoveRef = dbRef.child(favoritesLst.get(position).getTitle());
                    deletedItem = favoritesLst.get(position);
                    favoritesLst.remove(position);
                    itemRemoveRef.removeValue();
                    ShowDetailFragment.btnFavorites.setColorFilter(getActivity().getResources().getColor(R.color.white));
                    favoritesAdapter.notifyDataSetChanged();
                    favoritesAdapter.notifyItemRemoved(position);
                    Toast.makeText(getActivity(), "Items inside the List: " + favoritesAdapter.getItemCount(), Toast.LENGTH_LONG).show();

                    Snackbar.make(rvMyFavorites, deletedItem.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    show = new Show(deletedItem.getTitle(), deletedItem.getCover_art(), deletedItem.getVideo_file(), deletedItem.getSubject());
                                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child(Home.FULLNAME + "'s Favorite").child(deletedItem.getTitle());
                                    dbRef.setValue(show);
                                    ShowDetailFragment.btnFavorites.setColorFilter(getActivity().getResources().getColor(R.color.darkOrange));
                                    favoritesLst.add(position, deletedItem);
                                    favoritesAdapter.notifyItemInserted(position);
                                    Toast.makeText(getActivity(), "Items inside the List: " + favoritesLst, Toast.LENGTH_LONG).show();
                                }
                            }).show();
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.red))
                    .addSwipeLeftLabel("Delete")
                    .setSwipeLeftLabelColor(ContextCompat.getColor(getContext(), R.color.white))
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    public void loadData() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child(Home.FULLNAME + "'s Favorite");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoritesLst = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                    Log.d("TAG", postSnapshot + "ETO: " + postSnapshot.getValue().toString());
                    favoritesLst.add(new Show(postSnapshot.child("title").getValue().toString(),
                            postSnapshot.child("cover_art").getValue().toString(),
                            postSnapshot.child("video_file").getValue().toString(),
                            postSnapshot.child("subject").getValue().toString()));
                    favoritesAdapter = new FavoritesAdapter(getActivity(), favoritesLst);
                    rvMyFavorites.setAdapter(favoritesAdapter);
                    rvMyFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
                    favoritesAdapter.notifyDataSetChanged();
//                    Log.d("TAG", "My Favorite shows: " + favoritesLst);
                }

                if (favoritesLst.isEmpty()) {
                    rvMyFavorites.setVisibility(View.INVISIBLE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    rvMyFavorites.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Erro : " + error);
            }
        });
    }
}
