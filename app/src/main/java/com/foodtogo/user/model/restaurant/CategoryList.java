
package com.foodtogo.user.model.restaurant;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryList {

    @SerializedName("main_category_id")
    @Expose
    private Integer mainCategoryId;
    @SerializedName("main_category_name")
    @Expose
    private String mainCategoryName;
    @SerializedName("sub_category_list")
    @Expose
    private List<SubCategoryList> subCategoryList = null;

    public Integer getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(Integer mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public List<SubCategoryList> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<SubCategoryList> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

}
