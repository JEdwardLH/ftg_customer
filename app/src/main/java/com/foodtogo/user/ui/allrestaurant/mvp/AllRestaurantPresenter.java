package com.foodtogo.user.ui.allrestaurant.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.accountdetails.User;
import com.foodtogo.user.model.allrestaurant.AllRestaurantResponse;
import com.foodtogo.user.model.allrestaurant.CategoryList;

import java.util.ArrayList;


public class AllRestaurantPresenter implements AllRestaurantContractor.Presenter {

    private AllRestaurantContractor.View mView;

    private AllRestaurantModel model;

    public AllRestaurantPresenter(AllRestaurantContractor.View view, AppRepository appRepository1) {
        mView = view;
        model = new AllRestaurantModel(this, appRepository1);
    }


    @Override
    public void requestAllRestaurant(int pageNo) {
        if (pageNo == 1)
            mView.showLoadingView();

        model.requestAllRestaurant(pageNo);
    }

    @Override
    public void requestAllRestaurant(int pageNo, String categoryId) {
        if (pageNo == 1)
            mView.showLoadingView();
        model.requestAllRestaurant(pageNo, categoryId);
    }

    @Override
    public void onAllRestaurant(AllRestaurantResponse allRestaurantResponse) {
        mView.hideLoadingView();
        mView.onViewAll(allRestaurantResponse);
    }

    @Override
    public void onLoadMore(AllRestaurantResponse allRestaurantResponse) {
        mView.showLoadMore(allRestaurantResponse);
    }

    @Override
    public void onViewCategoryBasedItemRequest(String type, int pageNo, ArrayList<CategoryList> categoryArrayList, String filterJSON, String searchHalal, String orderByDelivery, String orderByRating,
                                               String orderByOffers,ArrayList<String> categoryIds) {
        if (pageNo == 1)
            mView.showLoadingView();
            model.onViewCategoryBasedItemRequest(type, pageNo, categoryArrayList, filterJSON, searchHalal, orderByDelivery,
                orderByRating, orderByOffers,categoryIds);
    }


    @Override
    public void categoryBasedResponse(AllRestaurantResponse allRestaurantResponse) {
        mView.hideLoadingView();
        mView.categoryBasedResponse(allRestaurantResponse);
    }

    @Override
    public void showLoadMoreError(String error) {
        mView.showLoadMoreError(error);
    }


    @Override
    public void onSuccess(User user) {
        mView.hideLoadingView();
    }


    @Override
    public void apiError(String error) {
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void apiError(int error) {
        mView.hideLoadingView();
        mView.showError(error);
    }


}

