package com.foodtogo.user.model.home;

import java.util.List;

public class RestaurantDataModel {

    private String restaurantName;
    private int categoryId;
    private String model;
    private int deliveryStatus;
    private List<RestaurantDetail> allFoodDetails;
    private List<RestaurantDetail> allPopularFoodDetails;
    private List<AllRestaurant> allRestaurantList;
    private List<RestaurantDetail> allFeatureRestaurantList;
    private List<RestaurantDetail> allRestaurantDetailList;
    private List<RecentOrders> allRecentOrders;

    public List<RecentOrders> getAllRecentOrders() {
        return allRecentOrders;
    }

    public void setAllRecentOrders(List<RecentOrders> allRecentOrders) {
        this.allRecentOrders = allRecentOrders;
    }

    public List<RestaurantDetail> getAllRestaurantDetailList() {
        return allRestaurantDetailList;
    }

    public void setAllRestaurantDetailList(List<RestaurantDetail> allRestaurantDetailList) {
        this.allRestaurantDetailList = allRestaurantDetailList;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<RestaurantDetail> getAllFoodDetails() {
        return allFoodDetails;
    }

    public void setAllFoodDetails(List<RestaurantDetail> allFoodDetails) {
        this.allFoodDetails = allFoodDetails;
    }

    public List<AllRestaurant> getAllRestaurantList() {
        return allRestaurantList;
    }

    public void setAllRestaurantList(List<AllRestaurant> allRestaurantList) {
        this.allRestaurantList = allRestaurantList;
    }

    public List<RestaurantDetail> getAllFeatureRestaurantList() {
        return allFeatureRestaurantList;
    }

    public void setAllFeatureRestaurantList(List<RestaurantDetail> allFeatureRestaurantList) {
        this.allFeatureRestaurantList = allFeatureRestaurantList;
    }

    public int getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public List<RestaurantDetail> getAllPopularFoodDetails() {
        return allPopularFoodDetails;
    }

    public void setAllPopularFoodDetails(List<RestaurantDetail> allPopularFoodDetails) {
        this.allPopularFoodDetails = allPopularFoodDetails;
    }

}
