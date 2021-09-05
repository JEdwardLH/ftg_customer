package com.foodtogo.user.ui.viewallreviews.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.itemdetails.ResponseItemDetails;
import com.foodtogo.user.model.restaurant.RestaurantDetailResponse;

public interface AllReviewContractor {

    interface View extends BaseView {

        void showLoadMore(RestaurantDetailResponse restaurantDetailResponse);

        void showLoadMore(ResponseItemDetails responseItemDetails);

        void showLoadMoreError(String error);

    }

    interface Presenter {

        void requestItemDetail(int itemId, int reviewPageNo);

        void onLoadMore(ResponseItemDetails responseItemDetails);

        void requestRestaurantDetail(int restaurantId, int reviewPageNo);

        void onLoadMore(RestaurantDetailResponse restaurantDetailResponse);

        void onLoadMoreError(String error);

        void apiError(String error);

        void apiError(int error);

    }

    interface Model {

        void requestItemDetail(int itemId, int reviewPageNo);

        void requestRestaurantDetail(int restaurantId, int reviewPageNo);

        void close();
    }

}
