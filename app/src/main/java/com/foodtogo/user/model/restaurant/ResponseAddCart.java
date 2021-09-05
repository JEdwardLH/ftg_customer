package com.foodtogo.user.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAddCart {

    @SerializedName("cart_id")
    @Expose
    private Integer cartId;
    @SerializedName("cart_quantity")
    @Expose
    private String cartQuantity;
    @SerializedName("cart_currency")
    @Expose
    private String cartCurrency;
    @SerializedName("cart_total")
    @Expose
    private String cartTotal;
    @SerializedName("total_cart_count")
    @Expose
    private String totalCartCount;
    @SerializedName("total_cart_amount")
    @Expose
    private String totalCartAmount;

    public String getTotalCartAmount() {
        return totalCartAmount;
    }

    public void setTotalCartAmount(String totalCartAmount) {
        this.totalCartAmount = totalCartAmount;
    }




    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public String getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(String cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getCartCurrency() {
        return cartCurrency;
    }

    public void setCartCurrency(String cartCurrency) {
        this.cartCurrency = cartCurrency;
    }

    public String getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(String cartTotal) {
        this.cartTotal = cartTotal;
    }


    public String getTotalCartCount() {
        return totalCartCount;
    }

    public void setTotalCartCount(String totalCartCount) {
        this.totalCartCount = totalCartCount;
    }

}

