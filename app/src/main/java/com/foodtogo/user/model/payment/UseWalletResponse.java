package com.foodtogo.user.model.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UseWalletResponse {

    @SerializedName("currency_code")
    @Expose
    private String currencyCode;
    @SerializedName("payable_amount")
    @Expose
    private String payableAmount;
    @SerializedName("used_wallet")
    @Expose
    private String usedWallet;

    @SerializedName("used_coupon")
    @Expose
    private String usedOffer;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(String payableAmount) {
        this.payableAmount = payableAmount;
    }

    public String getUsedWallet() {
        return usedWallet;
    }

    public void setUsedWallet(String usedWallet) {
        this.usedWallet = usedWallet;
    }

    public String getPayableAmtUsd() {
        return payableAmtUsd;
    }

    public void setPayableAmtUsd(String payableAmtUsd) {
        this.payableAmtUsd = payableAmtUsd;
    }

    public String getUsedOffer() {
        return usedOffer;
    }

    public void setUsedOffer(String usedOffer) {
        this.usedOffer = usedOffer;
    }

    @SerializedName("payable_amt_usd")
    @Expose
    private String payableAmtUsd;
}
