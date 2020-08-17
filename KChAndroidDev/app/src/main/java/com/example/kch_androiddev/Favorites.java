package com.example.kch_androiddev;

import java.util.ArrayList;
import java.util.List;

public class Favorites {

    public static List<Show> getFavoritesLst() {
        return favoritesLst;
    }

    static List<Show> favoritesLst = new ArrayList<>();
}
