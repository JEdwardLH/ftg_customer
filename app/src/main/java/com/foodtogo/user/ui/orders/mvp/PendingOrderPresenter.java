package com.foodtogo.user.ui.orders.mvp;

import com.foodtogo.user.data.source.AppRepository;


public class PendingOrderPresenter implements PendingOrderContractor.Presenter {

    private PendingOrderContractor.View mView;
    private PendingOrderModel model;


    public PendingOrderPresenter(PendingOrderContractor.View view, AppRepository appRepository1) {
        mView = view;
        model = new PendingOrderModel(this, appRepository1);

    }

    @Override
    public void requestCancelOrder(int position,String orderId, String comment) {
        mView.showLoadingView();
        model.requestCancelOrder(position,orderId, comment);
    }

    @Override
    public void cancelOrderResponse(int position,String response) {
        mView.hideLoadingView();
        mView.showCancelOrderResponse(position,response);
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
