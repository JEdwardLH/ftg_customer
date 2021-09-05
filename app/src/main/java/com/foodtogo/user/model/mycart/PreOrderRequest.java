package com.foodtogo.user.model.mycart;

public class PreOrderRequest {

    private String lang;
    private String store_id;
    private String pre_order_date;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getPre_order_date() {
        return pre_order_date;
    }

    public void setPre_order_date(String pre_order_date) {
        this.pre_order_date = pre_order_date;
    }


}
