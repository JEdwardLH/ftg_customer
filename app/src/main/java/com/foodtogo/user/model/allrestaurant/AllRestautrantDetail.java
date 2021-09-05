
package com.foodtogo.user.model.allrestaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllRestautrantDetail {

    @SerializedName("restaurant_id")
    @Expose
    private Integer restaurantId;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("restaurant_rating")
    @Expose
    private Integer restaurantRating;
    @SerializedName("restaurant_desc")
    @Expose
    private String restaurantDesc;
    @SerializedName("restaurant_image")
    @Expose
    private String restaurantImage;
    @SerializedName("restaurant_status")
    @Expose
    private String restaurantStatus;
    @SerializedName("today_wking_time")
    @Expose
    private String todayWkingTime;
    @SerializedName("restaurant_delivery_time")
    @Expose
    private String restaurantDeliveryTime;

    @SerializedName("restaurant_offer")
    @Expose
    private String restaurantOffer;

    @SerializedName("category_name")
    @Expose
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public String getRestaurantOffer() {
        return restaurantOffer;
    }

    public void setRestaurantOffer(String restaurantOffer) {
        this.restaurantOffer = restaurantOffer;
    }

    public String getRestaurantDeliveryTime() {
        return restaurantDeliveryTime;
    }

    public void setRestaurantDeliveryTime(String restaurantDeliveryTime) {
        this.restaurantDeliveryTime = restaurantDeliveryTime;
    }


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

    public Integer getRestaurantRating() {
        return restaurantRating;
    }

    public void setRestaurantRating(Integer restaurantRating) {
        this.restaurantRating = restaurantRating;
    }

    public String getRestaurantDesc() {
        return restaurantDesc;
    }

    public void setRestaurantDesc(String restaurantDesc) {
        this.restaurantDesc = restaurantDesc;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public String getRestaurantStatus() {
        return restaurantStatus;
    }

    public void setRestaurantStatus(String restaurantStatus) {
        this.restaurantStatus = restaurantStatus;
    }


    public String getTodayWkingTime() {
        return todayWkingTime;
    }

    public void setTodayWkingTime(String todayWkingTime) {
        this.todayWkingTime = todayWkingTime;
    }

}
