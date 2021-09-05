package com.foodtogo.user.ui.invoice.interfaces;

import com.foodtogo.user.model.invoice.ItemList;

public interface ClickStoreListener {

    void showStoreLocation(String storeTitle, String Location,String phone);

    void showItems(ItemList itemList);
}
