package com.foodtogo.user.ui.mycart.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.mycart.CartResponse;

import java.util.List;

public interface MyCartContractor {

    interface View extends BaseView {

        void showCartResponse(CartResponse cartResponse);

        void showProgressBar();

        void hideProgressBar();

    }

    interface Presenter {

        void requestCart();

        void removeCartItem(int cartId);

        void removeItemChoice(int productId, int cartId, List<Integer> choice);

        void updateCartChoice(int productId, int cartId, List<Integer> choice, String specialRequest);

        void updatePreOrder(int storeId, String preOrderDate);

        void updateCartQuantity(int cartId, int quantity);

        void onCartResponse(CartResponse cartResponse);

        void apiError(String error);

        void apiError(int error);

        void close();

        void removePreOrder(String storeId);

    }

    interface Model {

        void requestCart();

        void removeCartItem(int cartId);

        void updateCartQuantity(int cartId, int quantity);
        void requestRemovePreOrder(String storeId);

        void updatePreOrder(int storeId, String preOrderDate);

        void updateCartChoice(int productId, int cartId, List<Integer> choice, String specialRequest);

        void removeItemChoice(int productId, int cartId, List<Integer> choice);

        void close();
    }
}
