package com.foodtogo.user.ui.allrestaurant.interfaces;

public interface SelectionListener {

    void onUpdateKeyValue(String key, String value);

    void onUpdateKeyValue(String key, boolean value);

    void onUpdateKeyValue(int position, boolean value,String categoryId);
}
