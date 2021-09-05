package com.foodtogo.user.model.paymentsettings;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentSettingResponse {

    @SerializedName("stripe_status")
    @Expose
    private String stripeStatus;
    @SerializedName("stripe_clientId")
    @Expose
    private String stripeClientId;
    @SerializedName("stripe_secretId")
    @Expose
    private String stripeSecretId;
    @SerializedName("paypal_status")
    @Expose
    private String paypalStatus;
    @SerializedName("paypal_clientId")
    @Expose
    private String paypalClientId;
    @SerializedName("paypal_secretId")
    @Expose
    private String paypalSecretId;
    @SerializedName("netBanking_status")
    @Expose
    private String netBankingStatus;
    @SerializedName("netBanking_bankName")
    @Expose
    private String netBankingBankName;
    @SerializedName("netBanking_branch")
    @Expose
    private String netBankingBranch;
    @SerializedName("netBanking_accNo")
    @Expose
    private String netBankingAccNo;
    @SerializedName("netBanking_ifsc")
    @Expose
    private String netBankingIfsc;

    public String getStripeStatus() {
        return stripeStatus;
    }

    public void setStripeStatus(String stripeStatus) {
        this.stripeStatus = stripeStatus;
    }

    public String getStripeClientId() {
        return stripeClientId;
    }

    public void setStripeClientId(String stripeClientId) {
        this.stripeClientId = stripeClientId;
    }

    public String getStripeSecretId() {
        return stripeSecretId;
    }

    public void setStripeSecretId(String stripeSecretId) {
        this.stripeSecretId = stripeSecretId;
    }

    public String getPaypalStatus() {
        return paypalStatus;
    }

    public void setPaypalStatus(String paypalStatus) {
        this.paypalStatus = paypalStatus;
    }

    public String getPaypalClientId() {
        return paypalClientId;
    }

    public void setPaypalClientId(String paypalClientId) {
        this.paypalClientId = paypalClientId;
    }

    public String getPaypalSecretId() {
        return paypalSecretId;
    }

    public void setPaypalSecretId(String paypalSecretId) {
        this.paypalSecretId = paypalSecretId;
    }

    public String getNetBankingStatus() {
        return netBankingStatus;
    }

    public void setNetBankingStatus(String netBankingStatus) {
        this.netBankingStatus = netBankingStatus;
    }

    public String getNetBankingBankName() {
        return netBankingBankName;
    }

    public void setNetBankingBankName(String netBankingBankName) {
        this.netBankingBankName = netBankingBankName;
    }

    public String getNetBankingBranch() {
        return netBankingBranch;
    }

    public void setNetBankingBranch(String netBankingBranch) {
        this.netBankingBranch = netBankingBranch;
    }

    public String getNetBankingAccNo() {
        return netBankingAccNo;
    }

    public void setNetBankingAccNo(String netBankingAccNo) {
        this.netBankingAccNo = netBankingAccNo;
    }

    public String getNetBankingIfsc() {
        return netBankingIfsc;
    }

    public void setNetBankingIfsc(String netBankingIfsc) {
        this.netBankingIfsc = netBankingIfsc;
    }

}
