package com.foodtogo.user.ui.orders.mvp;

import com.foodtogo.user.BaseView;

public interface FullFilledOrderContractor {

    interface View extends BaseView {

        void postReviewSuccess(String message);
    }

    interface Presenter {

        void postStoreReview(String storeId, String rating, String comment);

        void postOrderReview(String orderId, String storeId, String deliveryId, String rating, String comment);

        void postItemReview(String productId, String rating, String comment);

        void postReviewSuccess(String message);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void postStoreReview(String storeId, String rating, String comment);

        void postOrderReview(String orderId, String storeId, String deliveryId, String rating, String comment);

        void postItemReview(String productId, String rating, String comment);

        void close();
    }
}
