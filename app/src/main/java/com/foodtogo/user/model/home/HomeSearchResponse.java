package com.foodtogo.user.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeSearchResponse {
    @SerializedName("search_list")
    List<HomeSearchHead> homeSearchHeadList;

    public List<HomeSearchHead> getHomeSearchHeadList() {
        return homeSearchHeadList;
    }
}
