package com.foodtogo.user.model.offers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OffersResponse {
    @SerializedName("coupon_data")
    @Expose
    private List<OffersData> offersData = null;
    @SerializedName("total_offer_amount")
    @Expose
    private String totalOfferAmount;
    @SerializedName("used_offer_amount")
    @Expose
    private String usedOfferAmount;
    @SerializedName("balance_amount")
    @Expose
    private String balanceAmount;
    @SerializedName("currency")
    @Expose
    private String currency;

    public List<OffersData> getOffersData() {
        return offersData;
    }

    public void setOffersData(List<OffersData> offersData) {
        this.offersData = offersData;
    }


    public String getTotalOfferAmount() {
        return totalOfferAmount;
    }

    public void setTotalOfferAmount(String totalOfferAmount) {
        this.totalOfferAmount = totalOfferAmount;
    }

    public String getUsedOfferAmount() {
        return usedOfferAmount;
    }

    public void setUsedOfferAmount(String usedOfferAmount) {
        this.usedOfferAmount = usedOfferAmount;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
