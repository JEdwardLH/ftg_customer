package com.foodtogo.user.ui.viewallreviews.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.itemdetails.ResponseItemDetails;
import com.foodtogo.user.model.restaurant.RestaurantDetailResponse;


public class AllReviewPresenter implements AllReviewContractor.Presenter {

    private AllReviewContractor.View mView;
    private AllReviewModel model;

    public AllReviewPresenter(AllReviewContractor.View view, AppRepository appRepository1) {
        mView = view;
        model = new AllReviewModel(this, appRepository1);
    }


    @Override
    public void requestItemDetail(int itemId, int reviewPageNo) {
        mView.showLoadingView();
        model.requestItemDetail(itemId, reviewPageNo);
    }

    @Override
    public void onLoadMore(ResponseItemDetails responseItemDetails) {
        mView.hideLoadingView();
        mView.showLoadMore(responseItemDetails);
    }

    @Override
    public void requestRestaurantDetail(int restaurantId, int reviewPageNo) {
        mView.showLoadingView();
        model.requestRestaurantDetail(restaurantId, reviewPageNo);
    }


    @Override
    public void onLoadMore(RestaurantDetailResponse categoryBasedItems) {
        mView.hideLoadingView();
        mView.showLoadMore(categoryBasedItems);
    }

    @Override
    public void onLoadMoreError(String error) {
        mView.hideLoadingView();
        mView.showLoadMoreError(error);
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

