package com.foodtogo.user.model.landing;

public class LanguageRequest {

    private String lang;
    private String type;
    private String referral_email;
    private String page_no;

    public String getPage_no() {
        return page_no;
    }

    public void setPage_no(String page_no) {
        this.page_no = page_no;
    }

    public String getReferral_email() {
        return referral_email;
    }

    public void setReferral_email(String referral_email) {
        this.referral_email = referral_email;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
