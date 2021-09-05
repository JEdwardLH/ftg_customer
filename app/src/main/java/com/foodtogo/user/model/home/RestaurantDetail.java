
package com.foodtogo.user.model.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RestaurantDetail implements Serializable {

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
    @SerializedName("today_wking_time")
    @Expose
    private String todayWkingTime;
    @SerializedName("restaurant_status")
    @Expose
    private String restaurantStatus;
    @SerializedName("restaurant_image" )
    @Expose
    private String restaurantImage;

    @SerializedName("restaurant_banner" )
    @Expose
    private String restaurantBanner;

    @SerializedName("restaurant_logo" )
    @Expose
    private String restaurantLogo;
    @SerializedName("restaurant_category")
    @Expose
    private String restaurantCat;

    @SerializedName("delivery_duration")
    @Expose
    private String deliveryDuration;

    public String getRestaurantBanner() {
        return restaurantBanner;
    }

    public void setRestaurantBanner(String restaurantBanner) {
        this.restaurantBanner = restaurantBanner;
    }


    public String getDeliveryDuration() {
        return deliveryDuration;
    }

    public void setDeliveryDuration(String deliveryDuration) {
        this.deliveryDuration = deliveryDuration;
    }
    public String getRestaurantLogo() {
        return restaurantLogo;
    }

    public void setRestaurantLogo(String restaurantLogo) {
        this.restaurantLogo = restaurantLogo;
    }


    public String getRestaurantCat() {
        return restaurantCat;
    }

    public void setRestaurantCat(String restaurantCat) {
        this.restaurantCat = restaurantCat;
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

    public String getTodayWkingTime() {
        return todayWkingTime;
    }

    public void setTodayWkingTime(String todayWkingTime) {
        this.todayWkingTime = todayWkingTime;
    }

    public String getRestaurantStatus() {
        return restaurantStatus;
    }

    public void setRestaurantStatus(String restaurantStatus) {
        this.restaurantStatus = restaurantStatus;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

}
