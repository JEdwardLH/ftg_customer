package com.foodtogo.user.model.restaurant;


public class RequestRestaurantBasedItem {

    private String lang;
    private String restaurant_id;
    private String main_category_id;
    private String sub_category_id;
    private String sort_by;
    private String search_text;
    private String page_no;
    private String item_type;
    private String all;
/*    private String search_halal;
    private String search_combo;
    private String orderBy_spl_offer;
    private String orderBy_top_offers;
    private ArrayList<String> available_time;*/


  /*  public String getSearch_halal() {
        return search_halal;
    }

    public void setSearch_halal(String search_halal) {
        this.search_halal = search_halal;
    }

    public String getSearch_combo() {
        return search_combo;
    }

    public void setSearch_combo(String search_combo) {
        this.search_combo = search_combo;
    }

    public String getOrderBy_spl_offer() {
        return orderBy_spl_offer;
    }

    public void setOrderBy_spl_offer(String orderBy_spl_offer) {
        this.orderBy_spl_offer = orderBy_spl_offer;
    }

    public String getOrderBy_top_offers() {
        return orderBy_top_offers;
    }

    public void setOrderBy_top_offers(String orderBy_top_offers) {
        this.orderBy_top_offers = orderBy_top_offers;
    }

    public ArrayList<String> getAvailable_time() {
        return available_time;
    }

    public void setAvailable_time(ArrayList<String> available_time) {
        this.available_time = available_time;
    }
*/



    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }


    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getMain_category_id() {
        return main_category_id;
    }

    public void setMain_category_id(String main_category_id) {
        this.main_category_id = main_category_id;
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(String sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getSearch_text() {
        return search_text;
    }

    public void setSearch_text(String search_text) {
        this.search_text = search_text;
    }

    public String getSort_by() {
        return sort_by;
    }

    public void setSort_by(String sort_by) {
        this.sort_by = sort_by;
    }

    public String getPage_no() {
        return page_no;
    }

    public void setPage_no(String page_no) {
        this.page_no = page_no;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

}
