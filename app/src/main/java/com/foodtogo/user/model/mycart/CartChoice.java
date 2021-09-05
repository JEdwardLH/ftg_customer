package com.foodtogo.user.model.mycart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartChoice {

    @SerializedName("choice_id")
    @Expose
    private Integer choiceId;
    @SerializedName("choice_name")
    @Expose
    private String choiceName;
    @SerializedName("choice_amount")
    @Expose
    private String choiceAmount;

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

    public String getChoiceAmount() {
        return choiceAmount;
    }

    public void setChoiceAmount(String choiceAmount) {
        this.choiceAmount = choiceAmount;
    }


}
