package com.foodtogo.user.model.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMethods {

    @SerializedName("cod")
    @Expose
    private Integer cod;
    @SerializedName("paypal")
    @Expose
    private Integer paypal;
    @SerializedName("stripe")
    @Expose
    private Integer stripe;

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public Integer getPaypal() {
        return paypal;
    }

    public void setPaypal(Integer paypal) {
        this.paypal = paypal;
    }

    public Integer getStripe() {
        return stripe;
    }

    public void setStripe(Integer stripe) {
        this.stripe = stripe;
    }

}
