package com.foodtogo.user.model.forgotpassword;

public class ForgotPasswordRequest {

    private String lang;
    private String cus_email;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCus_email() {
        return cus_email;
    }

    public void setCus_email(String cus_email) {
        this.cus_email = cus_email;
    }

}
