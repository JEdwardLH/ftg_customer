package com.foodtogo.user.ui.refunddetails.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.refunddetails.RefundDetailResponse;

import java.util.ArrayList;


public class RefundDetailsPresenter implements RefundDetailsContractor.Presenter {

    private RefundDetailsContractor.View mView;
    private RefundDetailsModel model;


    public RefundDetailsPresenter(RefundDetailsContractor.View view, AppRepository appRepository1) {
        mView = view;
        model = new RefundDetailsModel(this, appRepository1);

    }

    @Override
    public void requestRefundDetail(String orderId) {
        mView.showLoadingView();
        model.requestRefundDetail(orderId);
    }

    @Override
    public void onRefundDetailResponse(ArrayList<RefundDetailResponse> pendingRefund, ArrayList<RefundDetailResponse> completedRefund) {
        mView.hideLoadingView();
        mView.showRefundDetailResponse(pendingRefund, completedRefund);
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
