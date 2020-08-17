package com.example.kch_androiddev;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FirebaseFetch {

    private List<Show> category1Lst = new ArrayList<>();
    private List<Show> category2Lst = new ArrayList<>();
    private List<Show> category3Lst = new ArrayList<>();
    private static List<Show> searchLst = new ArrayList<>();
    private FirebaseFirestore db;

    public List<Show> getCategory1Lst() {
        return category1Lst;
    }

    public void setCategory1Lst(List<Show> category1Lst) {
        this.category1Lst = category1Lst;
    }

    public List<Show> getCategory2Lst() {
        return category2Lst;
    }

    public void setCategory2Lst(List<Show> category2Lst) {
        this.category2Lst = category2Lst;
    }

    public List<Show> getCategory3Lst() { return category3Lst; }

    public void setCategory3Lst(List<Show> category3Lst) { this.category3Lst = category3Lst; }

    public static List<Show> getSearchLst() {
        return searchLst;
    }

    public void setSearchLst(List<Show> searchLst) {
        this.searchLst = searchLst;
    }

    public void getData() {
        db = FirebaseFirestore.getInstance();
        db.collection("Shows")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                searchLst.add(new Show(document.getData().get("title").toString(),
                                        document.getData().get("description").toString(),
                                        document.getData().get("thumbnail").toString(),
                                        document.getData().get("category").toString(),
                                        document.getData().get("subject").toString(),
                                        document.getData().get("cover_art").toString(),
                                        document.getData().get("video_file").toString()
                                ));
                                if (String.valueOf(document.getData().get("subject")).equals("Mathematics")) {
                                    category1Lst.add(new Show(document.getData().get("title").toString(),
                                            document.getData().get("description").toString(),
                                            document.getData().get("thumbnail").toString(),
                                            document.getData().get("category").toString(),
                                            document.getData().get("subject").toString(),
                                            document.getData().get("cover_art").toString(),
                                            document.getData().get("video_file").toString()
                                    ));
                                    Log.d(TAG, "My cat1List" + category1Lst);
                                } else if (String.valueOf(document.getData().get("subject")).equals("Science")) {
                                    category2Lst.add(new Show(document.getData().get("title").toString(),
                                            document.getData().get("description").toString(),
                                            document.getData().get("thumbnail").toString(),
                                            document.getData().get("category").toString(),
                                            document.getData().get("subject").toString(),
                                            document.getData().get("cover_art").toString(),
                                            document.getData().get("video_file").toString()
                                    ));
                                    Log.d(TAG, "My cat2List" + category2Lst);
                                } else if (String.valueOf(document.getData().get("subject")).equals("Filipino")) {
                                    category3Lst.add(new Show(document.getData().get("title").toString(),
                                            document.getData().get("description").toString(),
                                            document.getData().get("thumbnail").toString(),
                                            document.getData().get("category").toString(),
                                            document.getData().get("subject").toString(),
                                            document.getData().get("cover_art").toString(),
                                            document.getData().get("video_file").toString()
                                    ));
                                    Log.d(TAG, "My cat3List" + category3Lst);
                                } else {
                                    Log.d(TAG, "Data not received");
                                }
                                Log.d(TAG, "My SearchList" + searchLst);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
