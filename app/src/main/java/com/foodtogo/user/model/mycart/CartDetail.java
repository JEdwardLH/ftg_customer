
package com.foodtogo.user.model.mycart;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartDetail {

    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("minimum_order_amount")
    @Expose
    private String minimumOrderAmount;
    @SerializedName("store_status")
    @Expose
    private String storeStatus;
    @SerializedName("pre_order_status")
    @Expose
    private String preOrderStatus;
    @SerializedName("added_item_details")
    @Expose
    private List<AddedItemDetail> addedItemDetails = null;
    private AddedItemDetail addedItemDetail;
    private boolean showHeader;

    public boolean isShowHeader() {
        return showHeader;
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    public AddedItemDetail getAddedItemDetail() {
        return addedItemDetail;
    }

    public void setAddedItemDetail(AddedItemDetail addedItemDetail) {
        this.addedItemDetail = addedItemDetail;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public void setMinimumOrderAmount(String minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public String getPreOrderStatus() {
        return preOrderStatus;
    }

    public void setPreOrderStatus(String preOrderStatus) {
        this.preOrderStatus = preOrderStatus;
    }

    public String getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(String storeStatus) {
        this.storeStatus = storeStatus;
    }

    public List<AddedItemDetail> getAddedItemDetails() {
        return addedItemDetails;
    }

    public void setAddedItemDetails(List<AddedItemDetail> addedItemDetails) {
        this.addedItemDetails = addedItemDetails;
    }

}
