package com.foodtogo.user.model.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckQuantityResponse {

    @SerializedName("payment_error")
    @Expose
    private String paymentError = null;
    @SerializedName("quantity_error")
    @Expose
    private List<String> quantityError = null;

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;

    public String getPaymentError() {
        return paymentError;
    }

    public void setPaymentError(String paymentError) {
        this.paymentError = paymentError;
    }

    public List<String> getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(List<String> quantityError) {
        this.quantityError = quantityError;
    }

}
