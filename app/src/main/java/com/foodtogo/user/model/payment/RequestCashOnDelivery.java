package com.foodtogo.user.model.payment;

public class RequestCashOnDelivery {

    private String lang;
    private String cus_name;
    private String cus_last_name;
    private String cus_email;
    private String cus_phone1;
    private String cus_phone2;
    private String cus_address;
    private String cus_lat;
    private String cus_long;
    private String ord_self_pickup;
    private String use_wallet;
    private String wallet_amt;

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



    public String getCus_address1() {
        return cus_address1;
    }

    public void setCus_address1(String cus_address1) {
        this.cus_address1 = cus_address1;
    }

    private String cus_address1;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public String getCus_last_name() {
        return cus_last_name;
    }

    public void setCus_last_name(String cus_last_name) {
        this.cus_last_name = cus_last_name;
    }

    public String getCus_email() {
        return cus_email;
    }

    public void setCus_email(String cus_email) {
        this.cus_email = cus_email;
    }

    public String getCus_phone1() {
        return cus_phone1;
    }

    public void setCus_phone1(String cus_phone1) {
        this.cus_phone1 = cus_phone1;
    }

    public String getCus_phone2() {
        return cus_phone2;
    }

    public void setCus_phone2(String cus_phone2) {
        this.cus_phone2 = cus_phone2;
    }

    public String getCus_address() {
        return cus_address;
    }

    public void setCus_address(String cus_address) {
        this.cus_address = cus_address;
    }

    public String getCus_lat() {
        return cus_lat;
    }

    public void setCus_lat(String cus_lat) {
        this.cus_lat = cus_lat;
    }

    public String getCus_long() {
        return cus_long;
    }

    public void setCus_long(String cus_long) {
        this.cus_long = cus_long;
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


}
