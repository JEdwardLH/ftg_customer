
package com.foodtogo.user.model.allrestaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllRestaurantResponse {

    @SerializedName("category_list")
    @Expose
    private List<CategoryList> categoryList = null;
    @SerializedName("all_restautrant_details")
    @Expose
    private List<AllRestautrantDetail> allRestautrantDetails = null;

    public List<CategoryList> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryList> categoryList) {
        this.categoryList = categoryList;
    }

    public List<AllRestautrantDetail> getAllRestautrantDetails() {
        return allRestautrantDetails;
    }

    public void setAllRestautrantDetails(List<AllRestautrantDetail> allRestautrantDetails) {
        this.allRestautrantDetails = allRestautrantDetails;
    }

}
