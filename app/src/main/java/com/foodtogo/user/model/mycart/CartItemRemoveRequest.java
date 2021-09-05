package com.foodtogo.user.model.mycart;

public class CartItemRemoveRequest {

    private String cart_id;
    private String lang;

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }


}
