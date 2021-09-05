package com.foodtogo.user.model.mycart;

import java.util.List;

public class ItemChoiceRemoveRequest {

    private String product_id;
    private String cart_id;
    private List<Integer> choice_id;
    private String lang;

    public String getSpecial_request() {
        return special_request;
    }

    public void setSpecial_request(String special_request) {
        this.special_request = special_request;
    }

    private String special_request;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public List<Integer> getChoice_id() {
        return choice_id;
    }

    public void setChoice_id(List<Integer> choice_id) {
        this.choice_id = choice_id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }


}
