package com.foodtogo.user.model.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_phone")
    @Expose
    private String userPhone;
    @SerializedName("user_login_type")
    @Expose
    private Integer userLoginType;
    @SerializedName("user_status")
    @Expose
    private Integer userStatus;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getUserLoginType() {
        return userLoginType;
    }

    public void setUserLoginType(Integer userLoginType) {
        this.userLoginType = userLoginType;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    //Otp
    @SerializedName("otp")
    @Expose
    private Integer otp;
    @SerializedName("cus_fname")
    @Expose
    private String cusFname;
    @SerializedName("cus_email")
    @Expose
    private String cusEmail;
    @SerializedName("cus_password")
    @Expose
    private String cusPassword;
    @SerializedName("cus_phone1")
    @Expose
    private String cusPhone1;

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public String getCusFname() {
        return cusFname;
    }

    public void setCusFname(String cusFname) {
        this.cusFname = cusFname;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getCusPassword() {
        return cusPassword;
    }

    public void setCusPassword(String cusPassword) {
        this.cusPassword = cusPassword;
    }

    public String getCusPhone1() {
        return cusPhone1;
    }

    public void setCusPhone1(String cusPhone1) {
        this.cusPhone1 = cusPhone1;
    }

}
