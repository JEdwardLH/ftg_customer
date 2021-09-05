
package com.foodtogo.user.model.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {

    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("specialRequest")
    @Expose
    private String specialRequest;
    @SerializedName("pdt_image")
    @Expose
    private String pdtImage;
    @SerializedName("ord_quantity")
    @Expose
    private Integer ordQuantity;
    @SerializedName("ord_unit_price")
    @Expose
    private String ordUnitPrice;
    @SerializedName("ord_tax_amt")
    @Expose
    private String ordTaxAmt;
    @SerializedName("sub_total")
    @Expose
    private String subTotal;
    @SerializedName("ord_currency")
    @Expose
    private String ordCurrency;
    @SerializedName("pre_order_date")
    @Expose
    private String preOrderDate;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    public String getPdtImage() {
        return pdtImage;
    }

    public void setPdtImage(String pdtImage) {
        this.pdtImage = pdtImage;
    }

    public Integer getOrdQuantity() {
        return ordQuantity;
    }

    public void setOrdQuantity(Integer ordQuantity) {
        this.ordQuantity = ordQuantity;
    }

    public String getOrdUnitPrice() {
        return ordUnitPrice;
    }

    public void setOrdUnitPrice(String ordUnitPrice) {
        this.ordUnitPrice = ordUnitPrice;
    }

    public String getOrdTaxAmt() {
        return ordTaxAmt;
    }

    public void setOrdTaxAmt(String ordTaxAmt) {
        this.ordTaxAmt = ordTaxAmt;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getOrdCurrency() {
        return ordCurrency;
    }

    public void setOrdCurrency(String ordCurrency) {
        this.ordCurrency = ordCurrency;
    }

    public String getPreOrderDate() {
        return preOrderDate;
    }

    public void setPreOrderDate(String preOrderDate) {
        this.preOrderDate = preOrderDate;
    }

}
