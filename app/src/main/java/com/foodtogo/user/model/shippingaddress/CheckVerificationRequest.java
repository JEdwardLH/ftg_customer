package com.foodtogo.user.model.shippingaddress;

public class CheckVerificationRequest {
    private  String lang;
    private  String cus_email;
    private  String current_otp;
    private  String otp;
    private  String addr_type;

    public CheckVerificationRequest(String lang, String cus_email, String current_otp, String otp,String addressType) {
        this.lang = lang;
        this.cus_email = cus_email;
        this.current_otp = current_otp;
        this.otp = otp;
        this.addr_type = addressType;
    }


}
