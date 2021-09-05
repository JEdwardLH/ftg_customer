
package com.foodtogo.user.model.myreviews;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyReviewResponse {

    @SerializedName("rest_review_list")
    @Expose
    private ArrayList<RestReviewList> restReviewList = null;
    @SerializedName("item_review_list")
    @Expose
    private ArrayList<ItemReviewList> itemReviewList = null;
    @SerializedName("order_review_list")
    @Expose
    private ArrayList<OrderReviewList> orderReviewList = null;

    public ArrayList<RestReviewList> getRestReviewList() {
        return restReviewList;
    }

    public void setRestReviewList(ArrayList<RestReviewList> restReviewList) {
        this.restReviewList = restReviewList;
    }

    public ArrayList<ItemReviewList> getItemReviewList() {
        return itemReviewList;
    }

    public void setItemReviewList(ArrayList<ItemReviewList> itemReviewList) {
        this.itemReviewList = itemReviewList;
    }

    public ArrayList<OrderReviewList> getOrderReviewList() {
        return orderReviewList;
    }

    public void setOrderReviewList(ArrayList<OrderReviewList> orderReviewList) {
        this.orderReviewList = orderReviewList;
    }

}
