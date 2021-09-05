package com.foodtogo.user.ui.orderhistory.interfaces;

public interface OrderHistoryListener {

    void onClickInvoice(int position);

    void onClickRepeatOrder(String orderId);

    void onClickOrderDetails(String orderId);

    void onClickTrack(int position);

}
