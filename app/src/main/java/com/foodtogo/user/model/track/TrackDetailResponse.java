
package com.foodtogo.user.model.track;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrackDetailResponse {

    @SerializedName("order_id")
    @Expose
    private String orderId;


    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("payable_amount")
    @Expose
    private String payableAmount;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @SerializedName("pay_type")
    @Expose
    private String payType;
    @SerializedName("order_otp")
    @Expose
    private Integer orderOtp;
    @SerializedName("order_status_details")
    @Expose
    private List<OrderStatusDetail> orderStatusDetails = null;
    @SerializedName("delivery_person_details")
    @Expose
    private DeliveryPersonDetails deliveryPersonDetails;
    @SerializedName("estimated_arrival_time")
    @Expose
    private String estimatedArrivalTime;

    public String getOrderFailedReason() {
        return orderFailedReason;
    }

    public void setOrderFailedReason(String orderFailedReason) {
        this.orderFailedReason = orderFailedReason;
    }

    @SerializedName("order_failed_reason")
    @Expose
    private String orderFailedReason;

    @SerializedName("restaurant_details")
    @Expose
    private RestaurantDetails restaurantDetails;
    @SerializedName("customer_details")
    @Expose
    private CustomerDetails customerDetails;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderOtp() {
        return orderOtp;
    }

    public void setOrderOtp(Integer orderOtp) {
        this.orderOtp = orderOtp;
    }

    public List<OrderStatusDetail> getOrderStatusDetails() {
        return orderStatusDetails;
    }

    public void setOrderStatusDetails(List<OrderStatusDetail> orderStatusDetails) {
        this.orderStatusDetails = orderStatusDetails;
    }

    public DeliveryPersonDetails getDeliveryPersonDetails() {
        return deliveryPersonDetails;
    }

    public void setDeliveryPersonDetails(DeliveryPersonDetails deliveryPersonDetails) {
        this.deliveryPersonDetails = deliveryPersonDetails;
    }

    public String getEstimatedArrivalTime() {
        return estimatedArrivalTime;
    }

    public void setEstimatedArrivalTime(String estimatedArrivalTime) {
        this.estimatedArrivalTime = estimatedArrivalTime;
    }

    public RestaurantDetails getRestaurantDetails() {
        return restaurantDetails;
    }

    public void setRestaurantDetails(RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }
    public String getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(String payableAmount) {
        this.payableAmount = payableAmount;
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
