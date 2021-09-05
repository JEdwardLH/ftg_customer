
package com.foodtogo.user.model.mycart;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartResponse {

    @SerializedName("total_cart_count")
    @Expose
    private Integer totalCartCount;
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;
    @SerializedName("loyalty_info")
    @Expose
    private String loyaltyInfo;
    @SerializedName("cart_sub_total")
    @Expose
    private String cartSubTotal;

    @SerializedName("cart_tax_total")
    @Expose
    private String carTaxTotal;
    @SerializedName("delivery_fee")
    @Expose
    private String deliveryFee;
    @SerializedName("peakHourFee")
    @Expose
    private String peakHourFee;
    @SerializedName("peakHour_Info")
    @Expose
    private String peakHour_Info;
    @SerializedName("peakHourFeeStatus")
    @Expose
    private String peekHourStatus = "0";
    @SerializedName("total_cart_amount")
    @Expose
    private String totalCartAmount;
    @SerializedName("total_cart_amount_usd")
    @Expose
    private String totalCartAmountUsd;
    @SerializedName("quantity_error")
    @Expose
    private List<String> quantityError;
    @SerializedName("cart_details")
    @Expose
    private List<CartDetail> cartDetails = null;
    @SerializedName("minimum_order_error")
    @Expose
    private List<String> minimumOrderError = null;
    @SerializedName("pre_order_error")
    @Expose
    private List<String> preOrderError = null;
    @SerializedName("store_locations")
    @Expose
    private ArrayList<StoreLocations> storeLocations = null;
    public String getLoyaltyInfo() {
        return loyaltyInfo;
    }

    public void setLoyaltyInfo(String loyaltyInfo) {
        this.loyaltyInfo = loyaltyInfo;
    }

    public String getCarTaxTotal() {
        return carTaxTotal;
    }

    public void setCarTaxTotal(String carTaxTotal) {
        this.carTaxTotal = carTaxTotal;
    }
    public String getExtraFee() {
        return peakHourFee;
    }

    public String getPeakHourInfo() {
        return peakHour_Info;
    }

    public boolean isPeakHour() {
        return peekHourStatus.equals("1");
    }


    public Integer getTotalCartCount() {
        return totalCartCount;
    }

    public void setTotalCartCount(Integer totalCartCount) {
        this.totalCartCount = totalCartCount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCartSubTotal() {
        return cartSubTotal;
    }

    public void setCartSubTotal(String cartSubTotal) {
        this.cartSubTotal = cartSubTotal;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getTotalCartAmount() {
        return totalCartAmount;
    }

    public void setTotalCartAmount(String totalCartAmount) {
        this.totalCartAmount = totalCartAmount;
    }

    public List<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }


    public List<String> getMinimumOrderError() {
        return minimumOrderError;
    }

    public void setMinimumOrderError(List<String> minimumOrderError) {
        this.minimumOrderError = minimumOrderError;
    }

    public List<String> getPreOrderError() {
        return preOrderError;
    }

    public void setPreOrderError(List<String> preOrderError) {
        this.preOrderError = preOrderError;
    }

    public ArrayList<StoreLocations> getStoreLocations() {
        return storeLocations;
    }

    public void setStoreLocations(ArrayList<StoreLocations> storeLocations) {
        this.storeLocations = storeLocations;
    }

    public List<String> getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(List<String> quantityError) {
        this.quantityError = quantityError;
    }
    public String getTotalCartAmountUsd() {
        return totalCartAmountUsd;
    }

    public void setTotalCartAmountUsd(String totalCartAmountUsd) {
        this.totalCartAmountUsd = totalCartAmountUsd;
    }
}
