
package com.foodtogo.user.model.wishlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductWishList {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("pro_has_discount")
    @Expose
    private String proHasDiscount;
    @SerializedName("product_discount_price")
    @Expose
    private String productDiscountPrice;
    @SerializedName("product_original_price")
    @Expose
    private String productOriginalPrice;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("product_currency_code")
    @Expose
    private String productCurrencyCode;
    @SerializedName("availablity")
    @Expose
    private String availablity;

    @SerializedName("product_desc")
    @Expose
    private String productDesc;
    @SerializedName("restaurant_id")
    @Expose
    private int restaurantId;


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProHasDiscount() {
        return proHasDiscount;
    }

    public void setProHasDiscount(String proHasDiscount) {
        this.proHasDiscount = proHasDiscount;
    }

    public String getProductDiscountPrice() {
        return productDiscountPrice;
    }

    public void setProductDiscountPrice(String productDiscountPrice) {
        this.productDiscountPrice = productDiscountPrice;
    }

    public String getProductOriginalPrice() {
        return productOriginalPrice;
    }

    public void setProductOriginalPrice(String productOriginalPrice) {
        this.productOriginalPrice = productOriginalPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductCurrencyCode() {
        return productCurrencyCode;
    }

    public void setProductCurrencyCode(String productCurrencyCode) {
        this.productCurrencyCode = productCurrencyCode;
    }

    public String getAvailablity() {
        return availablity;
    }

    public void setAvailablity(String availablity) {
        this.availablity = availablity;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}
