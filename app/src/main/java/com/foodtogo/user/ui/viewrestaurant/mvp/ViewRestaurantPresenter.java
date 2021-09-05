package com.foodtogo.user.ui.viewrestaurant.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.restaurant.CategoryBasedItems;
import com.foodtogo.user.model.restaurant.RequestRestaurantDetail;
import com.foodtogo.user.model.restaurant.RestaurantDetailResponse;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;


public class ViewRestaurantPresenter implements ViewRestaurantContractor.Presenter {

    private ViewRestaurantContractor.View mView;

    private ViewRestaurantModel model;
    private Disposable disposable;

    public ViewRestaurantPresenter(ViewRestaurantContractor.View view, AppRepository appRepository) {
        mView = view;
        model = new ViewRestaurantModel(this, appRepository);
    }


    @Override
    public void requestRestaurantDetail(int restaurantId, int reviewPageNo) {
        mView.showProgressBar();
        model.requestRestaurantDetail(restaurantId, reviewPageNo);
    }

    @Override
    public void requestRestaurantDetailFilter(RequestRestaurantDetail restaurantDetail) {
        mView.showProgressBar();
        model.requestRestaurantDetailFilter(restaurantDetail);
    }

    @Override
    public void onSuccess(RestaurantDetailResponse restaurantDetailResponse,boolean isFilterApplied) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.hideSearchProgressBar();
        mView.onViewAll(restaurantDetailResponse,isFilterApplied);
    }

    @Override
    public void onLoadMore(CategoryBasedItems categoryBasedItems) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.hideSearchProgressBar();
        mView.showLoadMore(categoryBasedItems);
    }

    @Override
    public void onLoadMoreError(String error) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.hideSearchProgressBar();
        mView.showLoadMoreError(error);
    }

    @Override
    public void onSuccess(CategoryBasedItems categoryBasedItems) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.hideSearchProgressBar();
        mView.showCategoryBasedItem(categoryBasedItems);
    }

    @Override
    public void restaurantCategoryError(String message) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.hideSearchProgressBar();
        mView.showCategoryError(message);
    }

    @Override
    public void requestRestaurantCategoryBased(String keywords, int restaurantId, int mainCategoryId, int subCategoryId, int pageNumber, int sortBy, String orderBySplOffer, String orderByTopOffers, String searchCombo, String searchHalal, String itemType, ArrayList<String> availableTime) {
        if (pageNumber == 1)
            mView.showSearchProgressBar();

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

            disposable = model.requestRestaurantCategoryBased(keywords, restaurantId, mainCategoryId, subCategoryId, pageNumber,
                    sortBy, orderBySplOffer, orderByTopOffers, searchCombo, searchHalal, itemType, availableTime);

    }

    @Override
    public void apiError(String error) {
        mView.hideLoadingView();
        mView.hideSearchProgressBar();
        mView.hideProgressBar();
        mView.showError(error);
    }

    @Override
    public void apiError(int error) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.hideSearchProgressBar();
        mView.showError(error);
    }

}

