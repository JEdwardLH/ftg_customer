package com.foodtogo.user.ui.orders.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.orderdetails.OrderDetailResponse;

public interface OrderContractor {

    interface View extends BaseView {

        void showOrderDetailResponse(OrderDetailResponse orderDetailResponse);
    }

    interface Presenter {

        void requestOrderDetail(String orderId);

        void onOrderDetailResponse(OrderDetailResponse orderDetailResponse);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestOrderDetail(String orderId);

        void close();
    }
}
