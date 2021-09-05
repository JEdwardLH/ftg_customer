
package com.foodtogo.user.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ItemList {


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
    @SerializedName("item_desc")
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
    @SerializedName("item_has_discount")
    @Expose
    private String itemHasDiscount;
    @SerializedName("item_discount_price")
    @Expose
    private String itemDiscountPrice;
    @SerializedName("item_has_choice")
    @Expose
    private String itemHasChoice;
    @SerializedName("item_quantity")
    @Expose
    private Integer itemQuantity;
    @SerializedName("item_availablity")
    @Expose
    private String itemAvailablity;
    @SerializedName("item_is_favourite")
    @Expose
    private String itemIsFavourite;
    @SerializedName("item_include")
    @Expose
    private ArrayList<ItemIncudes> itemApiIncludes;


/*    @SerializedName("discount_remaining_time")
    @Expose
    private long discountRemainingTime;

    @SerializedName("item_is_combo")
    @Expose
    private Integer itemCombo;
    @SerializedName("item_is_halal")
    @Expose
    private Integer itemHalal;

    @SerializedName("item_special_offers")
    @Expose
    private String itemSpecialOffer;*/

    /*public long getDiscountRemainingTime() {
        return discountRemainingTime;
    }

    public void setDiscountRemainingTime(long discountRemainingTime) {
        this.discountRemainingTime = discountRemainingTime;
    }

    public Integer getItemCombo() {
        return itemCombo;
    }

    public void setItemCombo(Integer itemCombo) {
        this.itemCombo = itemCombo;
    }

    public Integer getItemHalal() {
        return itemHalal;
    }

    public void setItemHalal(Integer itemHalal) {
        this.itemHalal = itemHalal;
    }


    public String getItemSpecialOffer() {
        return itemSpecialOffer;
    }

    public void setItemSpecialOffer(String itemSpecialOffer) {
        this.itemSpecialOffer = itemSpecialOffer;
    }*/
    public ArrayList<ItemIncudes> getItemApiIncludes() {
        return itemApiIncludes;
    }

    public void setItemApiIncludes(ArrayList<ItemIncudes> itemApiIncludes) {
        this.itemApiIncludes = itemApiIncludes;
    }



    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }


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

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
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

    public String getItemHasChoice() {
        return itemHasChoice;
    }

    public void setItemHasChoice(String itemHasChoice) {
        this.itemHasChoice = itemHasChoice;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemAvailablity() {
        return itemAvailablity;
    }

    public void setItemAvailablity(String itemAvailablity) {
        this.itemAvailablity = itemAvailablity;
    }

    public String getItemIsFavourite() {
        return itemIsFavourite;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }


    public void setItemIsFavourite(String itemIsFavourite) {
        this.itemIsFavourite = itemIsFavourite;
    }

    public Integer getItemRating() {
        return itemRating;
    }

    public void setItemRating(Integer itemRating) {
        this.itemRating = itemRating;
    }



}
