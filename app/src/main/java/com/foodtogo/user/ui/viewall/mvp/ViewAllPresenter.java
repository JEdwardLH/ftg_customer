package com.foodtogo.user.ui.viewall.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.allrestaurant.AllRestaurantResponse;


public class ViewAllPresenter implements ViewAllContractor.Presenter {

    private ViewAllContractor.View mView;

    private ViewAllModel model;

    public ViewAllPresenter(ViewAllContractor.View view, AppRepository appRepository1) {
        mView = view;
        model = new ViewAllModel(this, appRepository1);

    }


    @Override
    public void onViewCategoryBasedItems(AllRestaurantResponse allRestaurantResponse) {
        mView.hideLoadingView();
        mView.showViewCategoryBasedItems(allRestaurantResponse);
    }

    @Override
    public void onViewCategoryBasedItemRequest(String categoryId, int pageNo,String type) {
        if (pageNo == 1)
            mView.showLoadingView();
        model.onViewCategoryBasedItemRequest(categoryId, pageNo,type);
    }

    @Override
    public void onLoadMore(AllRestaurantResponse allRestaurantResponse) {
        mView.hideLoadingView();
        mView.showLoadMore(allRestaurantResponse);
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

