package com.foodtogo.user.ui.viewrestaurant.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.restaurant.CategoryBasedItems;
import com.foodtogo.user.model.restaurant.RequestRestaurantDetail;
import com.foodtogo.user.model.restaurant.RestaurantDetailResponse;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public interface ViewRestaurantContractor {

    interface View extends BaseView {

        void onViewAll(RestaurantDetailResponse restaurantDetailResponse,boolean isFilterApplied);

        void showCategoryBasedItem(CategoryBasedItems categoryBasedItems);

        void showCategoryError(String message);

        void showLoadMore(CategoryBasedItems categoryBasedItems);

        void showLoadMoreError(String error);

        void showProgressBar();

        void hideProgressBar();

        void showSearchProgressBar();

        void hideSearchProgressBar();

    }

    interface Presenter {

        void requestRestaurantDetail(int restaurantId, int reviewPageNo);
        void requestRestaurantDetailFilter(RequestRestaurantDetail restaurantDetail);

        void onSuccess(RestaurantDetailResponse restaurantDetailResponse,boolean isFilterApplied);

        void onLoadMore(CategoryBasedItems categoryBasedItems);

        void onLoadMoreError(String error);

        void onSuccess(CategoryBasedItems categoryBasedItems);

        void restaurantCategoryError(String message);

        void requestRestaurantCategoryBased(String keywords, int restaurantId, int mainCategoryId, int subCategoryId,
                                            int pageNumber, int sortBy, String orderBySplOffer, String orderByTopOffers, String searchCombo,
                                            String searchHalal, String itemType, ArrayList<String> availableTime);

        void apiError(String error);

        void apiError(int error);

    }

    interface Model {

        void requestRestaurantDetail(int restaurantId, int reviewPageNo);
        void requestRestaurantDetailFilter(RequestRestaurantDetail restaurantDetail);

        Disposable requestRestaurantCategoryBased(String keywords, int restaurantId, int mainCategoryId, int subCategoryId,
                                                  int pageNumber, int sortBy, String orderBySplOffer, String orderByTopOffers, String searchCombo,
                                                  String searchHalal, String itemType, ArrayList<String> availableTime);

        void close();
    }

}
