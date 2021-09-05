
package com.foodtogo.user.model.invoice;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoiceDetailsResponse {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_date")
    @Expose
    private String orderDate;

    public String getSelfPickup() {
        return selfPickup;
    }

    public void setSelfPickup(String selfPickup) {
        this.selfPickup = selfPickup;
    }

    @SerializedName("self_pickup")
    @Expose
    private String selfPickup;

    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;

    @SerializedName("customerDetailArray")
    @Expose
    private CustomerDetailArray customerDetailArray;
    @SerializedName("order_detailArray")
    @Expose
    private List<OrderDetailArray> orderDetailArray = null;
    @SerializedName("paytype")
    @Expose
    private String paytype;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("grand_sub_total")
    @Expose
    private String grandSubTotal;
    @SerializedName("delivery_fee")
    @Expose
    private String deliveryFee;
    @SerializedName("wallet_used")
    @Expose
    private String walletUsed;
    @SerializedName("coupon_used")
    @Expose
    private String offerUsed;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;

    @SerializedName("grand_tax_total")
    @Expose
    private String grandTaxTotal;

    @SerializedName("cancelled_item_amount")
    @Expose
    private String cancelledItemTotal;

    public String getOfferUsed() {
        return offerUsed;
    }

    public void setOfferUsed(String offerUsed) {
        this.offerUsed = offerUsed;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public CustomerDetailArray getCustomerDetailArray() {
        return customerDetailArray;
    }

    public void setCustomerDetailArray(CustomerDetailArray customerDetailArray) {
        this.customerDetailArray = customerDetailArray;
    }

    public List<OrderDetailArray> getOrderDetailArray() {
        return orderDetailArray;
    }

    public void setOrderDetailArray(List<OrderDetailArray> orderDetailArray) {
        this.orderDetailArray = orderDetailArray;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getGrandSubTotal() {
        return grandSubTotal;
    }

    public void setGrandSubTotal(String grandSubTotal) {
        this.grandSubTotal = grandSubTotal;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getWalletUsed() {
        return walletUsed;
    }

    public void setWalletUsed(String walletUsed) {
        this.walletUsed = walletUsed;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getGrandTaxTotal() {
        return grandTaxTotal;
    }

    public void setGrandTaxTotal(String grandTaxTotal) {
        this.grandTaxTotal = grandTaxTotal;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCancelledItemTotal() {
        return cancelledItemTotal;
    }

    public void setCancelledItemTotal(String cancelledItemTotal) {
        this.cancelledItemTotal = cancelledItemTotal;
    }
}
