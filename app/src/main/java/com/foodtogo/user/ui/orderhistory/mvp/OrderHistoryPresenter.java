package com.foodtogo.user.ui.orderhistory.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.orderhistory.OrderHistoryResponse;


public class OrderHistoryPresenter implements OrderHistoryContractor.Presenter {

    private OrderHistoryContractor.View mView;
    private OrderHistoryModel model;


    public OrderHistoryPresenter(OrderHistoryContractor.View view, AppRepository appRepository) {
        mView = view;
        model = new OrderHistoryModel(this, appRepository);
    }


    @Override
    public void requestOrderList(int pageNo) {
        if(pageNo==1)
        mView.showProgressBar();

        model.requestOrderList(pageNo);
    }

    @Override
    public void onOrderList(OrderHistoryResponse orderHistoryResponse) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showOrderList(orderHistoryResponse);
    }

    @Override
    public void onLoadMore(OrderHistoryResponse orderHistoryResponse) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showLoadMore(orderHistoryResponse);
    }

    @Override
    public void onLoadMoreError(String error) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showLoadMoreError(error);
    }

    @Override
    public void onRepeatOrder(String message) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showRepeatOrder(message);
    }

    @Override
    public void requestRepeatOrder(String orderId) {
        mView.showLoadingView();
        mView.hideProgressBar();
        model.requestRepeatOrder(orderId);
    }

    @Override
    public void apiError(String error) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showError(error);
    }

    @Override
    public void apiError(int error) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showError(error);
    }


    @Override
    public void close() {
        model.close();
    }
}
