package com.foodtogo.user.ui.orders.interfaces;

public interface PendingListener {

    void cancelOrder(int position, int orderId, String storeName, String itemName);

    void showCancellationPolicy(String restaurantName,String description,String info);

    void trackOrder(int position);

}
