package com.foodtogo.user.ui.orders.mvp;

import com.foodtogo.user.data.source.AppRepository;


public class FullFilledOrderPresenter implements FullFilledOrderContractor.Presenter {

    private FullFilledOrderContractor.View mView;
    private FullFilledOrderModel model;


    public FullFilledOrderPresenter(FullFilledOrderContractor.View view, AppRepository appRepository1) {
        mView = view;
        model = new FullFilledOrderModel(this, appRepository1);

    }

    @Override
    public void postStoreReview(String storeId, String rating, String comment) {
        mView.showLoadingView();
        model.postStoreReview(storeId, rating, comment);
    }

    @Override
    public void postOrderReview(String orderId, String storeId, String deliveryId, String rating, String comment) {
        mView.showLoadingView();
        model.postOrderReview(orderId, storeId, deliveryId, rating, comment);
    }

    @Override
    public void postItemReview(String productId, String rating, String comment) {
        mView.showLoadingView();
        model.postItemReview(productId, rating, comment);
    }

    @Override
    public void postReviewSuccess(String message) {
        mView.hideLoadingView();
        mView.postReviewSuccess(message);
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

    @Override
    public void close() {
        model.close();
    }
}
