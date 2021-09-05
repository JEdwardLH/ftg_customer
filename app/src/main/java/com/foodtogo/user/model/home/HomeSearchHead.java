package com.foodtogo.user.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeSearchHead {
    String store_name;
    Integer store_id;
    @SerializedName("item_list")
    List<HomeSearchChild> homeSearchChildList;

    public String getStore_name() {
        return store_name;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public List<HomeSearchChild> getHomeSearchChildList() {
        return homeSearchChildList;
    }
}
