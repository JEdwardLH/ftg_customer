
package com.foodtogo.user.model.landing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerDetail {

    @SerializedName("banner_type")
    @Expose
    private String bannerType;
    @SerializedName("banner_image_android")
    @Expose
    private String bannerImageAndroid;
    @SerializedName("banner_image_ios")
    @Expose
    private String bannerImageIos;

    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
    }

    public String getBannerImageAndroid() {
        return bannerImageAndroid;
    }

    public void setBannerImageAndroid(String bannerImageAndroid) {
        this.bannerImageAndroid = bannerImageAndroid;
    }

    public String getBannerImageIos() {
        return bannerImageIos;
    }

    public void setBannerImageIos(String bannerImageIos) {
        this.bannerImageIos = bannerImageIos;
    }

}
