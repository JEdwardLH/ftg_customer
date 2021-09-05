
package com.foodtogo.user.model.home;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllRestaurantDetail {

    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("restaurant_details")
    @Expose
    private List<RestaurantDetail> restaurantDetails = null;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<RestaurantDetail> getRestaurantDetails() {
        return restaurantDetails;
    }

    public void setRestaurantDetails(List<RestaurantDetail> restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }

}
