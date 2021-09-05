package com.foodtogo.user.ui.allrestaurant.interfaces;

import com.foodtogo.user.model.allrestaurant.CategoryList;

import java.util.ArrayList;

public interface OnApplyFilter {

    void onComplete(ArrayList<CategoryList> categoryListArrayList, String jsonObject,ArrayList<String> categoryId);

}
