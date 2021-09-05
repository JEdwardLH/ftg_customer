package com.foodtogo.user.model.wallet;

public class RewardRequest {
    private String lang;
    private String page_no;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPage_no() {
        return page_no;
    }

    public void setPage_no(String page_no) {
        this.page_no = page_no;
    }

    public String getRewarded_page_no() {
        return rewarded_page_no;
    }

    public void setRewarded_page_no(String rewarded_page_no) {
        this.rewarded_page_no = rewarded_page_no;
    }

    private String rewarded_page_no;
}
