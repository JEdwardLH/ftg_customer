
package com.foodtogo.user.model.mycart;

import java.util.List;

import com.foodtogo.user.model.itemdetails.Choice;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddedItemDetail {

    @SerializedName("restaurant_id")
    @Expose
    private Integer restaurantId;
    @SerializedName("cart_id")
    @Expose
    private Integer cartId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("available_stock")
    @Expose
    private int availableStock;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("cart_quantity")
    @Expose
    private Integer cartQuantity;
    @SerializedName("cart_unit_price")
    @Expose
    private String cartUnitPrice;
    @SerializedName("cart_tax")
    @Expose
    private String cartTax;
    @SerializedName("cart_has_choice")
    @Expose
    private String cartHasChoice;
    @SerializedName("cart_spl_request")
    @Expose
    private String cartSplRequest;
    @SerializedName("cart_choices")
    @Expose
    private List<CartChoice> cartChoices = null;
    @SerializedName("item_choices")
    @Expose
    private List<Choice> itemChoices = null;
    @SerializedName("cart_sub_total")
    @Expose
    private String cartSubTotal;
    @SerializedName("cart_currency")
    @Expose
    private String cartCurrency;
    @SerializedName("cart_pre_order")
    @Expose
    private String cartPreOrder;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(Integer cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getCartUnitPrice() {
        return cartUnitPrice;
    }

    public void setCartUnitPrice(String cartUnitPrice) {
        this.cartUnitPrice = cartUnitPrice;
    }

    public String getCartTax() {
        return cartTax;
    }

    public void setCartTax(String cartTax) {
        this.cartTax = cartTax;
    }

    public String getCartHasChoice() {
        return cartHasChoice;
    }

    public void setCartHasChoice(String cartHasChoice) {
        this.cartHasChoice = cartHasChoice;
    }

    public List<CartChoice> getCartChoices() {
        return cartChoices;
    }

    public void setCartChoices(List<CartChoice> cartChoices) {
        this.cartChoices = cartChoices;
    }

    public String getCartSubTotal() {
        return cartSubTotal;
    }

    public void setCartSubTotal(String cartSubTotal) {
        this.cartSubTotal = cartSubTotal;
    }

    public String getCartCurrency() {
        return cartCurrency;
    }

    public void setCartCurrency(String cartCurrency) {
        this.cartCurrency = cartCurrency;
    }

    public String getCartPreOrder() {
        return cartPreOrder;
    }

    public void setCartPreOrder(String cartPreOrder) {
        this.cartPreOrder = cartPreOrder;
    }


    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }

    public List<Choice> getItemChoices() {
        return itemChoices;
    }

    public void setItemChoices(List<Choice> itemChoices) {
        this.itemChoices = itemChoices;
    }

    public String getCartSplRequest() {
        return cartSplRequest;
    }

    public void setCartSplRequest(String cartSplRequest) {
        this.cartSplRequest = cartSplRequest;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
}
