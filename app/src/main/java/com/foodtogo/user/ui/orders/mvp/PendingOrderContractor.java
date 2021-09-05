package com.foodtogo.user.ui.orders.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.orderdetails.OrderDetailResponse;

public interface PendingOrderContractor {

    interface View extends BaseView {

        void showOrderDetailResponse(OrderDetailResponse orderDetailResponse);

        void showCancelOrderResponse(int position,String response);
    }

    interface Presenter {

        void requestCancelOrder(int position,String orderId, String comment);

        void cancelOrderResponse(int position,String response);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestCancelOrder(int position,String orderId, String comment);

        void close();
    }
}
