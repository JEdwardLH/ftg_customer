
package com.foodtogo.user.model.itemdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Choice {

    @SerializedName("choice_id")
    @Expose
    private Integer choiceId;
    @SerializedName("choice_name")
    @Expose
    private String choiceName;
    @SerializedName("choice_currency")
    @Expose
    private String choiceCurrency;
    @SerializedName("choice_price")
    @Expose
    private String choicePrice;

    public boolean isChoiceSelected() {
        return choiceSelected;
    }

    public void setChoiceSelected(boolean choiceSelected) {
        this.choiceSelected = choiceSelected;
    }

    private boolean choiceSelected;

    public Integer getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Integer choiceId) {
        this.choiceId = choiceId;
    }

    public String getChoiceName() {
        return choiceName;
    }

    public void setChoiceName(String choiceName) {
        this.choiceName = choiceName;
    }

    public String getChoiceCurrency() {
        return choiceCurrency;
    }

    public void setChoiceCurrency(String choiceCurrency) {
        this.choiceCurrency = choiceCurrency;
    }

    public String getChoicePrice() {
        return choicePrice;
    }

    public void setChoicePrice(String choicePrice) {
        this.choicePrice = choicePrice;
    }

}
