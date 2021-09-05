package com.foodtogo.user.model.payment;

public class UseWallet {

    private String lang;
    private String ord_self_pickup;
    private String use_wallet;
    private String wallet_amt;
    private String delivery_fee;
    private String use_coupon;
    private String coupon_id;
    private String coupon_amount;
    public String getUse_coupon() {
        return use_coupon;
    }

    public void setUse_coupon(String use_coupon) {
        this.use_coupon = use_coupon;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(String coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getOrd_self_pickup() {
        return ord_self_pickup;
    }

    public void setOrd_self_pickup(String ord_self_pickup) {
        this.ord_self_pickup = ord_self_pickup;
    }

    public String getUse_wallet() {
        return use_wallet;
    }

    public void setUse_wallet(String use_wallet) {
        this.use_wallet = use_wallet;
    }

    public String getWallet_amt() {
        return wallet_amt;
    }

    public void setWallet_amt(String wallet_amt) {
        this.wallet_amt = wallet_amt;
    }

    public String getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(String delivery_fee) {
        this.delivery_fee = delivery_fee;
    }
}
