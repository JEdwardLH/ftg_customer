package com.foodtogo.user.model.shippingaddress;

public class DeleteRequest {

    private String lang;
    private String delete_id;

    public DeleteRequest(String lang, String delete_id) {
        this.lang = lang;
        this.delete_id = delete_id;
    }

}
