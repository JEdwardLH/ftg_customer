package com.foodtogo.user.model.itemdetails;

public class RequestItemDetails {

    private String item_id;
    private String review_page_no;
    private String lang;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getReview_page_no() {
        return review_page_no;
    }

    public void setReview_page_no(String review_page_no) {
        this.review_page_no = review_page_no;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }


}
