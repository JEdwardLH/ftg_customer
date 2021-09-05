package com.foodtogo.user.model.restaurant;


public class RequestRestaurantDetail {


    private String restaurant_id;
    private String review_page_no;
    private String lang;
    private String item_type;
    private String sort_by;
    private String search_text;
    public String getItem_type() {
        return item_type;
    }
    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getSort_by() {
        return sort_by;
    }

    public void setSort_by(String sort_by) {
        this.sort_by = sort_by;
    }


    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getReview_page_no() {
        return review_page_no;
    }

    public void setReview_page_no(String review_page_no) {
        this.review_page_no = review_page_no;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getSearch_text() {
        return search_text;
    }

    public void setSearch_text(String search_text) {
        this.search_text = search_text;
    }



}
