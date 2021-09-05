
package com.foodtogo.user.model.itemdetails;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.foodtogo.user.model.restaurant.ItemIncudes;

public class ItemtInfo {

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
    private List<String> itemImage = null;
    @SerializedName("item_content")
    @Expose
    private String itemContent;
    @SerializedName("item_type")
    @Expose
    private String itemType;
    @SerializedName("item_desc")
    @Expose
    private String itemDesc;
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
    @SerializedName("item_discount_percent")
    @Expose
    private Integer itemDiscountPercent;
    @SerializedName("item_has_choice")
    @Expose
    private String itemHasChoice;
    @SerializedName("item_has_tax")
    @Expose
    private String itemHasTax;
    @SerializedName("item_tax")
    @Expose
    private String itemTax;
    @SerializedName("item_quantity")
    @Expose
    private Integer itemQuantity;
    @SerializedName("item_availablity")
    @Expose
    private String itemAvailablity;
    @SerializedName("item_specificatioon")
    @Expose
    private List<ItemSpecificatioon> itemSpecificatioon = null;
    @SerializedName("item_is_favourite")
    @Expose
    private String itemIsFavourite;

    @SerializedName("cart_type")
    @Expose
    private String cartType;


    @SerializedName("item_include")
    @Expose
    private ArrayList<ItemIncudes> itemApiIncludes;

    public long getDiscountRemaingTime() {
        return discountRemaingTime;
    }

    public void setDiscountRemaingTime(long discountRemaingTime) {
        this.discountRemaingTime = discountRemaingTime;
    }

    @SerializedName("discount_remaining_time")
    @Expose
    private long discountRemaingTime;

    public String getCartType() {
        return cartType;
    }

    public void setCartType(String cartType) {
        this.cartType = cartType;
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

    public Integer getItemRating() {
        return itemRating;
    }

    public void setItemRating(Integer itemRating) {
        this.itemRating = itemRating;
    }

    public List<String> getItemImage() {
        return itemImage;
    }

    public void setItemImage(List<String> itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemContent() {
        return itemContent;
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

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
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

    public Integer getItemDiscountPercent() {
        return itemDiscountPercent;
    }

    public void setItemDiscountPercent(Integer itemDiscountPercent) {
        this.itemDiscountPercent = itemDiscountPercent;
    }

    public String getItemHasChoice() {
        return itemHasChoice;
    }

    public void setItemHasChoice(String itemHasChoice) {
        this.itemHasChoice = itemHasChoice;
    }

    public String getItemHasTax() {
        return itemHasTax;
    }

    public void setItemHasTax(String itemHasTax) {
        this.itemHasTax = itemHasTax;
    }

    public String getItemTax() {
        return itemTax;
    }

    public void setItemTax(String itemTax) {
        this.itemTax = itemTax;
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

    public List<ItemSpecificatioon> getItemSpecificatioon() {
        return itemSpecificatioon;
    }

    public void setItemSpecificatioon(List<ItemSpecificatioon> itemSpecificatioon) {
        this.itemSpecificatioon = itemSpecificatioon;
    }

    public String getItemIsFavourite() {
        return itemIsFavourite;
    }

    public void setItemIsFavourite(String itemIsFavourite) {
        this.itemIsFavourite = itemIsFavourite;
    }
    public ArrayList<ItemIncudes> getItemApiIncludes() {
        return itemApiIncludes;
    }

    public void setItemApiIncludes(ArrayList<ItemIncudes> itemApiIncludes) {
        this.itemApiIncludes = itemApiIncludes;
    }

}
