package com.foodtogo.user.model.itemdetails;

import java.util.ArrayList;

public class RequestToppingDetail {
    String item_id;
    String st_id;
    String lang;

    public RequestToppingDetail(String item_id, String st_id, String lang, ArrayList<Integer> choices_id) {
        this.item_id = item_id;
        this.st_id = st_id;
        this.lang = lang;
        this.choices_id = choices_id;
    }

    ArrayList<Integer> choices_id;



    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getSt_id() {
        return st_id;
    }

    public void setSt_id(String st_id) {
        this.st_id = st_id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public ArrayList<Integer> getChoices_id() {
        return choices_id;
    }

    public void setChoices_id(ArrayList<Integer> choices_id) {
        this.choices_id = choices_id;
    }


}
