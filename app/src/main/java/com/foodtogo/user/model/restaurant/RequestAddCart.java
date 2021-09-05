package com.foodtogo.user.model.restaurant;

import java.util.List;

public class RequestAddCart {

    private String item_id;
    private String st_id;
    private String quantity;
    private String lang;
    private List<Integer> choices_id;
    private String special_notes;


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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<Integer> getChoices_id() {
        return choices_id;
    }

    public void setChoices_id(List<Integer> choices_id) {
        this.choices_id = choices_id;
    }

    public String getSpecial_notes() {
        return special_notes;
    }

    public void setSpecial_notes(String special_notes) {
        this.special_notes = special_notes;
    }


}
