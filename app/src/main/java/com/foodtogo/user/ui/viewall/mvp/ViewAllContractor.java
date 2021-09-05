package com.foodtogo.user.ui.viewall.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.allrestaurant.AllRestaurantResponse;
import com.foodtogo.user.model.home.RestaurantDetail;

import java.util.List;

public interface ViewAllContractor {

    interface View extends BaseView {

        void onViewAll(List<RestaurantDetail> restaurantDetailList);

        void showViewCategoryBasedItems(AllRestaurantResponse allRestaurantResponse);

        void showLoadMore(AllRestaurantResponse allRestaurantResponse);

        void showLoadMoreError(String error);


    }

    interface Presenter {

        void onViewCategoryBasedItems(AllRestaurantResponse allRestaurantResponse);

        void onViewCategoryBasedItemRequest(String categoryId, int pageNo,String type);

        void onLoadMore(AllRestaurantResponse allRestaurantResponse);

        void onLoadMoreError(String error);

        void apiError(String error);

        void apiError(int error);

    }

    interface Model {

        void onViewCategoryBasedItemRequest(String categoryId, int pageNo,String type);

        void close();
    }

}
