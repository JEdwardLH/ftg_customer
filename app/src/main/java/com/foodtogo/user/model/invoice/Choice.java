package com.foodtogo.user.model.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Choice {

    @SerializedName("choicename")
    @Expose
    private String choiceName;
    @SerializedName("choice_amount")
    @Expose
    private String choiceAmount;

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
