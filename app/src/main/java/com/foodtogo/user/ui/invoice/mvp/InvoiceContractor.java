package com.foodtogo.user.ui.invoice.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.invoice.InvoiceDetailsResponse;

public interface InvoiceContractor {

    interface View extends BaseView {

        void showInvoiceDetails(InvoiceDetailsResponse invoiceDetailsResponse);

    }

    interface Presenter {

        void requestInvoiceDetails(String orderId);

        void responseInvoiceDetails(InvoiceDetailsResponse invoiceDetailsResponse);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestInvoiceDetails(String orderId);

        void close();
    }
}
