package com.foodtogo.user.ui.invoice.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.invoice.InvoiceDetailsResponse;


public class InvoicePresenter implements InvoiceContractor.Presenter {

    private InvoiceContractor.View mView;
    private InvoiceModel model;


    public InvoicePresenter(InvoiceContractor.View view, AppRepository appRepository1) {
        mView = view;
        model = new InvoiceModel(this, appRepository1);

    }


    @Override
    public void requestInvoiceDetails(String orderId) {
        mView.showLoadingView();
        model.requestInvoiceDetails(orderId);
    }

    @Override
    public void responseInvoiceDetails(InvoiceDetailsResponse invoiceDetailsResponse) {
        mView.hideLoadingView();
        mView.showInvoiceDetails(invoiceDetailsResponse);
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
