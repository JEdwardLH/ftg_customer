package com.foodtogo.user.model.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentOrders {
    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("item_order_id")
    @Expose
    private String orderId;
    @SerializedName("stem_store_id")
    @Expose
    private Integer stemStoreId;
    @SerializedName("stem_store_name")
    @Expose
    private String stemStoreName;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_rating")
    @Expose
    private Integer itemRating;
    @SerializedName("item_image")
    @Expose
    private String itemImage;
    @SerializedName("item_currency")
    @Expose
    private String itemCurrency;

    @SerializedName("item_original_price")
    @Expose
    private String itemOriginalPrice;
    @SerializedName("item_has_discount")
    @Expose
    private String itemHasDiscount;
    @SerializedName("item_discount_price")
    @Expose
    private String itemDiscountPrice;
    @SerializedName("item_category")
    @Expose
    private String itemCategory;
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemOriginalPrice() {
        return itemOriginalPrice;
    }

    public void setItemOriginalPrice(String itemOriginalPrice) {
        this.itemOriginalPrice = itemOriginalPrice;
    }

    public String getItemDiscountPrice() {
        return itemDiscountPrice;
    }

    public void setItemDiscountPrice(String itemDiscountPrice) {
        this.itemDiscountPrice = itemDiscountPrice;
    }


    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getStemStoreId() {
        return stemStoreId;
    }

    public void setStemStoreId(Integer stemStoreId) {
        this.stemStoreId = stemStoreId;
    }

    public String getStemStoreName() {
        return stemStoreName;
    }

    public void setStemStoreName(String stemStoreName) {
        this.stemStoreName = stemStoreName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemRating() {
        return itemRating;
    }

    public void setItemRating(Integer itemRating) {
        this.itemRating = itemRating;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemCurrency() {
        return itemCurrency;
    }

    public void setItemCurrency(String itemCurrency) {
        this.itemCurrency = itemCurrency;
    }


    public String getItemHasDiscount() {
        return itemHasDiscount;
    }

    public void setItemHasDiscount(String itemHasDiscount) {
        this.itemHasDiscount = itemHasDiscount;
    }



    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }
}
