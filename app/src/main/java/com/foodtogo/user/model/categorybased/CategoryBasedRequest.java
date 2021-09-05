package com.foodtogo.user.model.categorybased;

import java.util.ArrayList;

public class CategoryBasedRequest {

    private String user_latitude;
    private String user_longitude;
    private String lang;
    private String page;
    private ArrayList<String> category_id;
    private String search_halal;
    private String orderBy_delivery;
    private String orderBy_rating;
    private String orderBy_offers;
    private ArrayList<Integer> restaurant_type;
    private ArrayList<Integer> prefer_time;
    private String featured_restaurant;

    public String getFeatured_restaurant() {
        return featured_restaurant;
    }

    public void setFeatured_restaurant(String featured_restaurant) {
        this.featured_restaurant = featured_restaurant;
    }

    public String getSearch_halal() {
        return search_halal;
    }

    public void setSearch_halal(String search_halal) {
        this.search_halal = search_halal;
    }

    public String getOrderBy_delivery() {
        return orderBy_delivery;
    }

    public void setOrderBy_delivery(String orderBy_delivery) {
        this.orderBy_delivery = orderBy_delivery;
    }

    public String getOrderBy_rating() {
        return orderBy_rating;
    }

    public void setOrderBy_rating(String orderBy_rating) {
        this.orderBy_rating = orderBy_rating;
    }

    public String getOrderBy_offers() {
        return orderBy_offers;
    }

    public void setOrderBy_offers(String orderBy_offers) {
        this.orderBy_offers = orderBy_offers;
    }

    public ArrayList<Integer> getRestaurant_type() {
        return restaurant_type;
    }

    public void setRestaurant_type(ArrayList<Integer> restaurant_type) {
        this.restaurant_type = restaurant_type;
    }

    public ArrayList<Integer> getPrefer_time() {
        return prefer_time;
    }

    public void setPrefer_time(ArrayList<Integer> prefer_time) {
        this.prefer_time = prefer_time;
    }



    public String getUser_latitude() {
        return user_latitude;
    }

    public void setUser_latitude(String user_latitude) {
        this.user_latitude = user_latitude;
    }

    public String getUser_longitude() {
        return user_longitude;
    }

    public void setUser_longitude(String user_longitude) {
        this.user_longitude = user_longitude;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public ArrayList<String> getCategory_id() {
        return category_id;
    }

    public void setCategory_id(ArrayList<String> category_id) {
        this.category_id = category_id;
    }


}
