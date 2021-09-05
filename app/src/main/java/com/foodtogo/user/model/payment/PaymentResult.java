
package com.foodtogo.user.model.payment;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentResult {

    @SerializedName("shipping_details")
    @Expose
    private ShippingDetails shippingDetails;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("order_details")
    @Expose
    private List<OrderDetail> orderDetails = null;
    @SerializedName("grand_sub_total")
    @Expose
    private String grandSubTotal;
    @SerializedName("delivery_fee")
    @Expose
    private String deliveryFee;
    @SerializedName("wallet_used")
    @Expose
    private String walletUsed;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;

    public ShippingDetails getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(ShippingDetails shippingDetails) {
        this.shippingDetails = shippingDetails;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
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

}
