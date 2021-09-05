
package com.foodtogo.user.model.home;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeResponse {

    @SerializedName("featured_restaurant")
    @Expose
    private List<RestaurantDetail> featuredRestaurant = null;

    @SerializedName("category_list")
    @Expose
    private List<AllRestaurant> categoryList = null;
    @SerializedName("all_restaurant")
    @Expose
    private List<RestaurantDetail> allRestaurant = null;
    @SerializedName("all_restaurant_details")
    @Expose
    private List<AllRestaurantDetail> allRestaurantDetails = null;
    @SerializedName("offers")
    @Expose
    private Offers offers = null;

    @SerializedName("new_restaurant")
    @Expose
    private List<RestaurantDetail> newRestaurantDetail = null;

    @SerializedName("top_restaurant")
    @Expose
    private List<RestaurantDetail> topRestaurantDetail = null;

    @SerializedName("nearby_restaurant")
    @Expose
    private List<RestaurantDetail> nearByRestaurant = null;

    @SerializedName("recent_orders")
    @Expose
    private List<RecentOrders> recentOrdersList = null;

    @SerializedName("delivery_fee_status")
    @Expose
    private int deliveryFeeStatus;

    public int getDeliveryFeeStatus() {
        return deliveryFeeStatus;
    }

    public void setDeliveryFeeStatus(int deliveryFeeStatus) {
        this.deliveryFeeStatus = deliveryFeeStatus;
    }
    public List<RestaurantDetail> getNearByRestaurant() {
        return nearByRestaurant;
    }

    public void setNearByRestaurant(List<RestaurantDetail> nearByRestaurant) {
        this.nearByRestaurant = nearByRestaurant;
    }
    public List<AllRestaurant> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<AllRestaurant> categoryList) {
        this.categoryList = categoryList;
    }



    public List<RestaurantDetail> getNewRestaurantDetail() {
        return newRestaurantDetail;
    }

    public void setNewRestaurantDetail(List<RestaurantDetail> newRestaurantDetail) {
        this.newRestaurantDetail = newRestaurantDetail;
    }

    public List<RestaurantDetail> getFeaturedRestaurant() {
        return featuredRestaurant;
    }

    public void setFeaturedRestaurant(List<RestaurantDetail> featuredRestaurant) {
        this.featuredRestaurant = featuredRestaurant;
    }

    public List<RestaurantDetail> getAllRestaurant() {
        return allRestaurant;
    }

    public void setAllRestaurant(List<RestaurantDetail> allRestaurant) {
        this.allRestaurant = allRestaurant;
    }

    public List<AllRestaurantDetail> getAllRestaurantDetails() {
        return allRestaurantDetails;
    }

    public void setAllRestaurantDetails(List<AllRestaurantDetail> allRestaurantDetails) {
        this.allRestaurantDetails = allRestaurantDetails;
    }
    public List<RestaurantDetail> getTopRestaurantDetail() {
        return topRestaurantDetail;
    }

    public void setTopRestaurantDetail(List<RestaurantDetail> topRestaurantDetail) {
        this.topRestaurantDetail = topRestaurantDetail;
    }
    public List<RecentOrders> getRecentOrdersList() {
        return recentOrdersList;
    }

    public void setRecentOrdersList(List<RecentOrders> recentOrdersList) {
        this.recentOrdersList = recentOrdersList;
    }



    public Offers getOffers() {
        return offers;
    }
}
