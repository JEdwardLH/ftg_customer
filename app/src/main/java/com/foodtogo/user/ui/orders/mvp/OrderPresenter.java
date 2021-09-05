package com.foodtogo.user.ui.orders.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.orderdetails.OrderDetailResponse;


public class OrderPresenter implements OrderContractor.Presenter {

    private OrderContractor.View mView;
    private OrderModel model;


    public OrderPresenter(OrderContractor.View view, AppRepository appRepository1) {
        mView = view;
        model = new OrderModel(this, appRepository1);

    }

    @Override
    public void requestOrderDetail(String orderId) {
        mView.showLoadingView();
        model.requestOrderDetail(orderId);
    }

    @Override
    public void onOrderDetailResponse(OrderDetailResponse orderDetailResponse) {
        mView.hideLoadingView();
        mView.showOrderDetailResponse(orderDetailResponse);
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
