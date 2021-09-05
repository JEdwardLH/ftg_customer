package com.foodtogo.user.model.shippingaddress;

public class SendEmailVerificationCodeRequest {

    private String lang;
    private String cus_email;

    public SendEmailVerificationCodeRequest(String lang, String cus_email) {
        this.lang = lang;
        this.cus_email = cus_email;
    }

}
