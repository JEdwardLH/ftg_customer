
package com.foodtogo.user.model.landing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroceryIcon {

    @SerializedName("icon_status")
    @Expose
    private String iconStatus;
    @SerializedName("gr_icon")
    @Expose
    private String grIcon;

    public String getIconStatus() {
        return iconStatus;
    }

    public void setIconStatus(String iconStatus) {
        this.iconStatus = iconStatus;
    }

    public String getGrIcon() {
        return grIcon;
    }

    public void setGrIcon(String grIcon) {
        this.grIcon = grIcon;
    }

}
