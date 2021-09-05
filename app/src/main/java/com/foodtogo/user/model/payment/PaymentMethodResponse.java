package com.foodtogo.user.model.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PaymentMethodResponse {


    @SerializedName("payment_methods")
    @Expose
    private PaymentMethods paymentMethods;


    public ArrayList<CouponList> getCouponlist() {
        return couponlist;
    }

    public void setCouponlist(ArrayList<CouponList> couponlist) {
        this.couponlist = couponlist;
    }

    @SerializedName("counpon_list")
    @Expose
    private ArrayList<CouponList> couponlist;


    public PaymentMethods getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(PaymentMethods paymentMethods) {
        this.paymentMethods = paymentMethods;
    }




}