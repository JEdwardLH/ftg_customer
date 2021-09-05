
package com.foodtogo.user.model.invoice;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailArray {

    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("store_location")
    @Expose
    private String storeLocation;
    @SerializedName("store_phone")
    @Expose
    private String storePhone;
    @SerializedName("item_lists")
    @Expose
    private List<ItemList> itemLists = null;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<ItemList> getItemLists() {
        return itemLists;
    }

    public void setItemLists(List<ItemList> itemLists) {
        this.itemLists = itemLists;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

}
