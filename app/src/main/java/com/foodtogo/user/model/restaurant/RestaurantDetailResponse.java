
package com.foodtogo.user.model.restaurant;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantDetailResponse {

    @SerializedName("restaurant_info")
    @Expose
    private RestaurantInfo restaurantInfo;
    @SerializedName("category_list")
    @Expose
    private List<CategoryList> categoryList = null;
    @SerializedName("restaurant_review")
    @Expose
    private ArrayList<RestaurantReview> restaurantReview = null;
    @SerializedName("working_hours")
    @Expose
    private ArrayList<WorkingHour> workingHours = null;
    @SerializedName("item_list")
    @Expose
    private List<ItemList> itemLists = null;
    @SerializedName("preferable_time")
    @Expose
    private String preferableTime;

    public String getPreferableTime() {
        return preferableTime;
    }

    public void setPreferableTime(String preferableTime) {
        this.preferableTime = preferableTime;
    }

    public String getSelAvailbaleTime() {
        return selAvailbaleTime;
    }

    public void setSelAvailbaleTime(String selAvailbaleTime) {
        this.selAvailbaleTime = selAvailbaleTime;
    }

    @SerializedName("sel_available_time")
    @Expose
    private String selAvailbaleTime;


    public RestaurantInfo getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setRestaurantInfo(RestaurantInfo restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }

    public List<CategoryList> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryList> categoryList) {
        this.categoryList = categoryList;
    }

    public ArrayList<RestaurantReview> getRestaurantReview() {
        return restaurantReview;
    }

    public void setRestaurantReview(ArrayList<RestaurantReview> restaurantReview) {
        this.restaurantReview = restaurantReview;
    }


    public ArrayList<WorkingHour> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(ArrayList<WorkingHour> workingHours) {
        this.workingHours = workingHours;
    }

    public List<ItemList> getItemLists() {
        return itemLists;
    }

    public void setItemLists(List<ItemList> itemLists) {
        this.itemLists = itemLists;
    }


    public static CategoryList getAllCategory() {
        CategoryList categoryList = new CategoryList();
        categoryList.setMainCategoryId(0);
        categoryList.setMainCategoryName("All");
        categoryList.setSubCategoryList(new ArrayList<>());
        return categoryList;
    }

}
