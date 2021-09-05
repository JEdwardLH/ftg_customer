package com.foodtogo.user.ui.mycart.interfaces;

import com.foodtogo.user.model.mycart.AddedItemDetail;

public interface MyCartListener {

    void clickItem(AddedItemDetail addedItemDetail );

    void clickPlusBtn(int cartId, int quantity);

    void clickMinusBtn(int cartId, int quantity);

    void clickPreOrder(int storeId, String restaurantName);
    void clickRemovePreOrder(int storeId);

    void clickRemoveChoice(int productId, int cartId, int choiceId);

    void clickRemoveCart(int cartId);

    void showErrorMessage(String message);
}
