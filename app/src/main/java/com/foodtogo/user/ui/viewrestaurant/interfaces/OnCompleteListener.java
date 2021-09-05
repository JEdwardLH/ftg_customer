package com.foodtogo.user.ui.viewrestaurant.interfaces;

import org.json.JSONObject;

import java.util.ArrayList;

public interface OnCompleteListener {

    void onComplete(JSONObject jsonObject, String orderBySplOffer, String orderByTopOffers, String searchCombo, String searchHalal, String itemType, ArrayList<String> availableTime);

    void onSortBy(int sortBy);
}
