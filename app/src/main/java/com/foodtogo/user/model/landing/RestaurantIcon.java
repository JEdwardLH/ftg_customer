
package com.foodtogo.user.model.landing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantIcon {

    @SerializedName("icon_status")
    @Expose
    private String iconStatus;
    @SerializedName("res_icon")
    @Expose
    private String resIcon;

    public String getIconStatus() {
        return iconStatus;
    }

    public void setIconStatus(String iconStatus) {
        this.iconStatus = iconStatus;
    }

    public String getResIcon() {
        return resIcon;
    }

    public void setResIcon(String resIcon) {
        this.resIcon = resIcon;
    }

}
