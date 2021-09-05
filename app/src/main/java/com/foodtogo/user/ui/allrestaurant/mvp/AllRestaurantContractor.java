package com.foodtogo.user.ui.allrestaurant.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.accountdetails.User;
import com.foodtogo.user.model.allrestaurant.AllRestaurantResponse;
import com.foodtogo.user.model.allrestaurant.CategoryList;

import java.util.ArrayList;

public interface AllRestaurantContractor {

    interface View extends BaseView {

        void onViewAll(AllRestaurantResponse allRestaurantResponse);

        void showLoadMore(AllRestaurantResponse allRestaurantResponse);

        void categoryBasedResponse(AllRestaurantResponse allRestaurantResponse);

        void showLoadMoreError(String error);
    }

    interface Presenter {

        void requestAllRestaurant(int pageNo);

        void requestAllRestaurant(int pageNo, String categoryId);

        void onAllRestaurant(AllRestaurantResponse allRestaurantResponse);

        void onLoadMore(AllRestaurantResponse allRestaurantResponse);

        void onViewCategoryBasedItemRequest(String type, int pageNo, ArrayList<CategoryList> categoryArrayList, String filterJSON,
                                            String searchHalal, String orderByDelivery, String orderByRating, String orderByOffers,ArrayList<String> categoryIds);

        void categoryBasedResponse(AllRestaurantResponse allRestaurantResponse);

        void showLoadMoreError(String error);

        void onSuccess(User user);

        void apiError(String error);

        void apiError(int error);

    }

    interface Model {

        void requestAllRestaurant(int pageNo);

        void requestAllRestaurant(int pageNo, String categoryId);

        void onViewCategoryBasedItemRequest(String type, int pageNo, ArrayList<CategoryList> categoryArrayList, String filterJSON,
                                            String searchHalal, String orderByDelivery, String orderByRating, String orderByOffers,ArrayList<String> categoryIds);

        void close();
    }

}
