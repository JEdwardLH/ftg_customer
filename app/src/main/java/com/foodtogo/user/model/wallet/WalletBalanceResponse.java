
package com.foodtogo.user.model.wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WalletBalanceResponse {

    @SerializedName("available_balance")
    @Expose
    private String availableBalance;
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;

    @SerializedName("used_details")
    @Expose
    private ArrayList<UsedDetail> usedDetails = null;

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }


    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public ArrayList<UsedDetail> getUsedDetails() {
        return usedDetails;
    }

    public void setUsedDetails(ArrayList<UsedDetail> usedDetails) {
        this.usedDetails = usedDetails;
    }

}
