
package com.foodtogo.user.model.restaurant;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantInfo {

    @SerializedName("restaurant_id")
    @Expose
    private Integer restaurantId;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("restaurant_logo")
    @Expose
    private String restaurantLogo;
    @SerializedName("restaurant_banner")
    @Expose
    private List<String> restaurantBanner = null;
    @SerializedName("restaurant_desc")
    @Expose
    private String restaurantDesc;
    @SerializedName("minimum_order")
    @Expose
    private Integer minimumOrder;
    @SerializedName("restaurant_currency")
    @Expose
    private String restaurantCurrency;
    @SerializedName("pre_order")
    @Expose
    private String preOrder;
    @SerializedName("restaurant_rating")
    @Expose
    private Integer restaurantRating;
    @SerializedName("cancellation_policy")
    @Expose
    private String cancellationPolicy;
    @SerializedName("refund_status")
    @Expose
    private String refundStatus;
    @SerializedName("cancel_status")
    @Expose
    private String cancelStatus;

    @SerializedName("restaurant_location")
    @Expose
    private String restaurantLocation;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("restaurant_status")
    @Expose
    private String restaurantStatus;

    public String getRestaurantPhone() {
        return restaurantPhone;
    }

    public void setRestaurantPhone(String restaurantPhone) {
        this.restaurantPhone = restaurantPhone;
    }

    @SerializedName("restaurant_phone")
    @Expose
    private String restaurantPhone;

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantLogo() {
        return restaurantLogo;
    }

    public void setRestaurantLogo(String restaurantLogo) {
        this.restaurantLogo = restaurantLogo;
    }

    public List<String> getRestaurantBanner() {
        return restaurantBanner;
    }

    public void setRestaurantBanner(List<String> restaurantBanner) {
        this.restaurantBanner = restaurantBanner;
    }

    public String getRestaurantDesc() {
        return restaurantDesc;
    }

    public void setRestaurantDesc(String restaurantDesc) {
        this.restaurantDesc = restaurantDesc;
    }

    public Integer getMinimumOrder() {
        return minimumOrder;
    }

    public void setMinimumOrder(Integer minimumOrder) {
        this.minimumOrder = minimumOrder;
    }

    public String getRestaurantCurrency() {
        return restaurantCurrency;
    }

    public void setRestaurantCurrency(String restaurantCurrency) {
        this.restaurantCurrency = restaurantCurrency;
    }

    public String getPreOrder() {
        return preOrder;
    }

    public void setPreOrder(String preOrder) {
        this.preOrder = preOrder;
    }

    public Integer getRestaurantRating() {
        return restaurantRating;
    }

    public void setRestaurantRating(Integer restaurantRating) {
        this.restaurantRating = restaurantRating;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(String restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getRestaurantStatus() {
        return restaurantStatus;
    }

    public void setRestaurantStatus(String restaurantStatus) {
        this.restaurantStatus = restaurantStatus;
    }

    public String getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(String cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

}
