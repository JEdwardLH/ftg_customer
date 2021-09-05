package com.foodtogo.user.ui.home.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.home.HomeSearchRequest;
import com.foodtogo.user.model.home.HomeSearchResponse;
import com.foodtogo.user.model.home.Offers;
import com.foodtogo.user.model.home.RestaurantDataModel;

import java.util.List;

import io.reactivex.disposables.Disposable;

public interface HomeContractor {

    interface View extends BaseView {

        void showHomeTopResponse(List<RestaurantDataModel> topRestaurantDataModelList);

        void showHomeResponse(List<RestaurantDataModel> restaurantDataModelList, Offers offers);

        void onSuccessHomeSearchResponse(HomeSearchResponse homeSearchResponse);

        void showHomeSearchError(String message);

        void showHomeSearchError(int message);
        void showRepeatOrder(String message);
    }

    interface Presenter {

        void homeSearch(String query);

        void disposeHomeSearch();

        void onReceiveHomeSearch(HomeSearchResponse homeSearchResponse);
        void errorHomeSearchResponse(int error);

        void errorHomeSearchResponse(String error);

        void requestRestaurant();

        void apiError(String error);

        void apiError(int error);

        void showHomeResponse(List<RestaurantDataModel> topRestaurantDataModelList, List<RestaurantDataModel> restaurantDataModelList, Offers offers);

        void close();

       ;
        void onRepeatOrder(String message);

        void requestRepeatOrder(String orderId);

    }

    interface Model {

        Disposable search(HomeSearchRequest homeSearchRequest);

        void requestRestaurant();

        void requestRepeatOrder(String orderId);

        void close();
    }
}
