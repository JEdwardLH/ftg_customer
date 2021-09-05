package com.foodtogo.user.ui.allrestaurant.interfaces;

import com.foodtogo.user.model.allrestaurant.AllRestautrantDetail;

public interface RestaurantDetailListener {

    void itemClick(AllRestautrantDetail position);

    void noItemView();

    void showItem();
}
