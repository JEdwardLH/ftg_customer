
package com.foodtogo.user.model.itemdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelatedItem {

    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_rating")
    @Expose
    private Integer itemRating;
    @SerializedName("item_image")
    @Expose
    private String itemImage;
    @SerializedName("item_content")
    @Expose
    private String itemContent;
    @SerializedName("itemt_desc")
    @Expose
    private String itemDesc;
    @SerializedName("item_type")
    @Expose
    private String itemType;
    @SerializedName("item_currency")
    @Expose
    private String itemCurrency;
    @SerializedName("item_original_price")
    @Expose
    private String itemOriginalPrice;
    @SerializedName("item_availablity")
    @Expose
    private String itemAvailablity;
    @SerializedName("item_has_discount")
    @Expose
    private String itemHasDiscount;
    @SerializedName("item_discount_price")
    @Expose
    private String itemDiscountPrice;
    @SerializedName("item_is_favourite")
    @Expose
    private String itemIsFavourite;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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

    public String getItemContent() {
        return itemContent;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemCurrency() {
        return itemCurrency;
    }

    public void setItemCurrency(String itemCurrency) {
        this.itemCurrency = itemCurrency;
    }

    public String getItemOriginalPrice() {
        return itemOriginalPrice;
    }

    public void setItemOriginalPrice(String itemOriginalPrice) {
        this.itemOriginalPrice = itemOriginalPrice;
    }

    public String getItemHasDiscount() {
        return itemHasDiscount;
    }

    public void setItemHasDiscount(String itemHasDiscount) {
        this.itemHasDiscount = itemHasDiscount;
    }

    public String getItemDiscountPrice() {
        return itemDiscountPrice;
    }

    public void setItemDiscountPrice(String itemDiscountPrice) {
        this.itemDiscountPrice = itemDiscountPrice;
    }

    public String getItemIsFavourite() {
        return itemIsFavourite;
    }

    public void setItemIsFavourite(String itemIsFavourite) {
        this.itemIsFavourite = itemIsFavourite;
    }

    public String getItemAvailablity() {
        return itemAvailablity;
    }

    public void setItemAvailablity(String itemAvailablity) {
        this.itemAvailablity = itemAvailablity;
    }


}
