
package com.foodtogo.user.model.itemdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemSpecificatioon {

    @SerializedName("specification_title")
    @Expose
    private String specificationTitle;
    @SerializedName("specification_description")
    @Expose
    private String specificationDescription;

    public String getSpecificationTitle() {
        return specificationTitle;
    }

    public void setSpecificationTitle(String specificationTitle) {
        this.specificationTitle = specificationTitle;
    }

    public String getSpecificationDescription() {
        return specificationDescription;
    }

    public void setSpecificationDescription(String specificationDescription) {
        this.specificationDescription = specificationDescription;
    }

}
