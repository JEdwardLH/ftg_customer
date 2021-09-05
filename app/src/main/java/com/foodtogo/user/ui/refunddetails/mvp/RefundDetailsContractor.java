package com.foodtogo.user.ui.refunddetails.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.refunddetails.RefundDetailResponse;

import java.util.ArrayList;

public interface RefundDetailsContractor {

    interface View extends BaseView {

        void showRefundDetailResponse(ArrayList<RefundDetailResponse> pendingRefund,ArrayList<RefundDetailResponse> completedRefund);
    }

    interface Presenter {

        void requestRefundDetail(String orderId);

        void onRefundDetailResponse(ArrayList<RefundDetailResponse> pendingRefund,ArrayList<RefundDetailResponse> completedRefund);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestRefundDetail(String orderId);

        void close();
    }
}
