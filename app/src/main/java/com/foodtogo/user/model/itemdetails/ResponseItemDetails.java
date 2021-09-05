
package com.foodtogo.user.model.itemdetails;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseItemDetails {

    @SerializedName("itemt_info")
    @Expose
    private ItemtInfo itemtInfo;
    @SerializedName("choices")
    @Expose
    private List<Choice> choices = null;
    @SerializedName("item_reviews")
    @Expose
    private ArrayList<ItemReviews> itemReviews = null;
    @SerializedName("related_items")
    @Expose
    private List<RelatedItem> relatedItems = null;

    @SerializedName("exist_in_cart")
    @Expose
    private String existInCart = "No";

    @SerializedName("cart_id")
    @Expose
    private String cartId;

    @SerializedName("cart_quantity")
    @Expose
    private int cartQuantity;


    @SerializedName("exist_item_cart_qty")
    @Expose
    private String existItemQuantity;

    @SerializedName("cart_amt")
    @Expose
    private String cartAmt;


    public String getExistInCart() {
        return existInCart;
    }

    public void setExistInCart(String existInCart) {
        this.existInCart = existInCart;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getCartAmt() {
        return cartAmt;
    }

    public void setCartAmt(String cartAmt) {
        this.cartAmt = cartAmt;
    }


    public String getExistItemQuantity() {
        return existItemQuantity;
    }

    public void setExistItemQuantity(String existItemQuantity) {
        this.existItemQuantity = existItemQuantity;
    }

    public ItemtInfo getItemtInfo() {
        return itemtInfo;
    }

    public void setItemtInfo(ItemtInfo itemtInfo) {
        this.itemtInfo = itemtInfo;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public ArrayList<ItemReviews> getItemReviews() {
        return itemReviews;
    }

    public void setItemReviews(ArrayList<ItemReviews> itemReviews) {
        this.itemReviews = itemReviews;
    }

    public List<RelatedItem> getRelatedItems() {
        return relatedItems;
    }

    public void setRelatedItems(List<RelatedItem> relatedItems) {
        this.relatedItems = relatedItems;
    }

}
