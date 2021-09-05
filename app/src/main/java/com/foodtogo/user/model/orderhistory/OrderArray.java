
package com.foodtogo.user.model.orderhistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderArray {

    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("orderAmount")
    @Expose
    private String orderAmount;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("ordCurrency")
    @Expose
    private String ordCurrency;
    @SerializedName("store_details")
    @Expose
    private List<StoreDetails> storeDetails;
    @SerializedName("orderTrack")
    @Expose
    private boolean orderTrack;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrdCurrency() {
        return ordCurrency;
    }

    public void setOrdCurrency(String ordCurrency) {
        this.ordCurrency = ordCurrency;
    }

    public List<StoreDetails> getStoreDetails() {
        return storeDetails;
    }

    public void setStoreDetails(List<StoreDetails> storeDetails) {
        this.storeDetails = storeDetails;
    }

    public boolean getOrderTrack() {
        return orderTrack;
    }

    public void setOrderTrack(boolean orderTrack) {
        this.orderTrack = orderTrack;
    }



}
