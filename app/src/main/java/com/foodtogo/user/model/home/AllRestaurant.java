
package com.foodtogo.user.model.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllRestaurant {

    @SerializedName(value = "restaurant_id", alternate = "category_id")
    @Expose
    private Integer restaurantId;
    @SerializedName(value = "restaurant_name" , alternate = "category_name")
    @Expose
    private String restaurantName;
    @SerializedName(value = "restaurant_logo", alternate = "category_image")
    @Expose
    private String restaurantLogo;

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

}
