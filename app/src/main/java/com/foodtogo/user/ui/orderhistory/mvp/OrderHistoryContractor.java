package com.foodtogo.user.ui.orderhistory.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.orderhistory.OrderHistoryResponse;

public interface OrderHistoryContractor {

    interface View extends BaseView {

        void showOrderList(OrderHistoryResponse orderHistoryResponse);

        void showLoadMore(OrderHistoryResponse orderHistoryResponse);

        void showLoadMoreError(String error);

        void showRepeatOrder(String message);

        void showProgressBar();

        void hideProgressBar();

    }

    interface Presenter {

        void requestOrderList(int pageNo);

        void onOrderList(OrderHistoryResponse orderHistoryResponse);

        void onLoadMore(OrderHistoryResponse orderHistoryResponse);

        void onLoadMoreError(String error);

        void onRepeatOrder(String message);

        void requestRepeatOrder(String orderId);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestOrderList(int pageNo);

        void requestRepeatOrder(String orderId);

        void close();
    }
}
