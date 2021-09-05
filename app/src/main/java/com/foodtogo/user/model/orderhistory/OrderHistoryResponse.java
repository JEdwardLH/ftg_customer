
package com.foodtogo.user.model.orderhistory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistoryResponse {

    @SerializedName("orderArray")
    @Expose
    private List<OrderArray> orderArray = null;

    public List<OrderArray> getOrderArray() {
        return orderArray;
    }

    public void setOrderArray(List<OrderArray> orderArray) {
        this.orderArray = orderArray;
    }

}
