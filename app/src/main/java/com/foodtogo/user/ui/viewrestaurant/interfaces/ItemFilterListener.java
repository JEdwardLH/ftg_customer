package com.foodtogo.user.ui.viewrestaurant.interfaces;

public interface ItemFilterListener {

    void onShowItem();

    void onUpdateFilter(String key, boolean value);

    void onUpdateFilter(String key, String value);
}
