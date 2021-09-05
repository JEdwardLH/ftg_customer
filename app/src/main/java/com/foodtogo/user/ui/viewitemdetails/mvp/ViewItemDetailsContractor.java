package com.foodtogo.user.ui.viewitemdetails.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.itemdetails.ResponseItemDetails;
import com.foodtogo.user.model.restaurant.ResponseAddCart;

import java.util.ArrayList;
import java.util.List;

public interface ViewItemDetailsContractor {

    interface View extends BaseView {

        void showItemDetails(ResponseItemDetails responseItemDetails);

        void showAddCartResponse(ResponseAddCart responseAddCart, String message);

        void onFavouriteResult(int position, String message);

        void addCartError(String error);

        void showProgressBar();

        void hideProgressBar();
        void updateToppings(String quantity);
    }

    interface Presenter {

        void requestItemDetail(int itemId, int reviewPageNo);

        void onSuccess(ResponseItemDetails responseItemDetails);

        void onSuccess(ResponseAddCart responseAddCart, String message);

        void addFavourites(int position, String itemId);

        void onFavouriteResult(int position, String message);

        void requestAddCart(int storeId, int itemId, String quantity, List<Integer> choiceItem, String specialNotes);

        void apiError(String error);

        void addCartError(String error);

        void apiError(int error);

        void updateCartQuantity(int cartId, int quantity);
        void requestToppingsApi(String storeId, String itemId, ArrayList<Integer> choiceId);
        void updateToppings(String quantity);

    }

    interface Model {

        void requestItemDetail(int itemId, int reviewPageNo);

        void updateCartQuantity(int cartId, int quantity);

        void addFavourites(int position, String itemId);

        void requestAddCart(int storeId, int itemId, String quantity, List<Integer> choiceItem, String specialNotes);
        void requestToppings(String storeId, String itemId, ArrayList<Integer> choiceId);

        void close();
    }

}
