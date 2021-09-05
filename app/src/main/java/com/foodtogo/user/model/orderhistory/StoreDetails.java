package com.foodtogo.user.model.orderhistory;

public class StoreDetails {

    private String store_name;

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_location() {
        return store_location;
    }

    public void setStore_location(String store_location) {
        this.store_location = store_location;
    }

    public boolean isCanTrack() {
        return canTrack;
    }

    public void setCanTrack(boolean canTrack) {
        this.canTrack = canTrack;
    }

    private String store_id;
    private String store_location;
    private boolean canTrack;


}
