package com.foodtogo.user.model.home;

public class HomeSearchRequest {

    String lang;
    String search_key;

    public HomeSearchRequest(String lang, String search_key) {
        this.lang = lang;
        this.search_key = search_key;
    }
}
