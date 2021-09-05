
package com.foodtogo.user.model.orderdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChoiceList {

    @SerializedName("choicename")
    @Expose
    private String choicename;

    public String getChoicename() {
        return choicename;
    }

    public void setChoicename(String choicename) {
        this.choicename = choicename;
    }

}
