package com.foodtogo.user.model.paymentsettings;

public class PaymentSettingUpdateRequest {

    private String stripe_status;
    private String stripe_clientId;
    private String stripe_secretId;
    private String paypal_status;
    private String paypal_clientId;
    private String paypal_secretId;
    private String netBanking_status;
    private String netBanking_bankName;
    private String netBanking_branch;
    private String netBanking_accNo;
    private String netBanking_ifsc;
    private String lang;

    public String getStripe_status() {
        return stripe_status;
    }

    public void setStripe_status(String stripe_status) {
        this.stripe_status = stripe_status;
    }

    public String getStripe_clientId() {
        return stripe_clientId;
    }

    public void setStripe_clientId(String stripe_clientId) {
        this.stripe_clientId = stripe_clientId;
    }

    public String getStripe_secretId() {
        return stripe_secretId;
    }

    public void setStripe_secretId(String stripe_secretId) {
        this.stripe_secretId = stripe_secretId;
    }

    public String getPaypal_status() {
        return paypal_status;
    }

    public void setPaypal_status(String paypal_status) {
        this.paypal_status = paypal_status;
    }

    public String getPaypal_clientId() {
        return paypal_clientId;
    }

    public void setPaypal_clientId(String paypal_clientId) {
        this.paypal_clientId = paypal_clientId;
    }

    public String getPaypal_secretId() {
        return paypal_secretId;
    }

    public void setPaypal_secretId(String paypal_secretId) {
        this.paypal_secretId = paypal_secretId;
    }

    public String getNetBanking_status() {
        return netBanking_status;
    }

    public void setNetBanking_status(String netBanking_status) {
        this.netBanking_status = netBanking_status;
    }

    public String getNetBanking_bankName() {
        return netBanking_bankName;
    }

    public void setNetBanking_bankName(String netBanking_bankName) {
        this.netBanking_bankName = netBanking_bankName;
    }

    public String getNetBanking_branch() {
        return netBanking_branch;
    }

    public void setNetBanking_branch(String netBanking_branch) {
        this.netBanking_branch = netBanking_branch;
    }

    public String getNetBanking_accNo() {
        return netBanking_accNo;
    }

    public void setNetBanking_accNo(String netBanking_accNo) {
        this.netBanking_accNo = netBanking_accNo;
    }

    public String getNetBanking_ifsc() {
        return netBanking_ifsc;
    }

    public void setNetBanking_ifsc(String netBanking_ifsc) {
        this.netBanking_ifsc = netBanking_ifsc;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }


}