package com.foodtogo.user.model.accountdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

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
    @SerializedName("user_phone2")
    @Expose
    private String userPhone2;
    @SerializedName("user_address")
    @Expose
    private String userAddress;
    @SerializedName("user_latitude")
    @Expose
    private String userLatitude;
    @SerializedName("user_longitude")
    @Expose
    private String userLongitude;
    @SerializedName("user_avatar")
    @Expose
    private String userAvatar;

    @SerializedName("email_verification_status")
    @Expose
    private String emailVerificationStatus;


    @SerializedName("user_ph1_no_only")
    @Expose
    private String userPhn1Only;

    @SerializedName("user_ph1_cnty_code")
    @Expose
    private String userPhn1CountryCode;


    public String getEmailVerificationStatus() {
        return emailVerificationStatus;
    }

    public void setEmailVerificationStatus(String emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
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

    public String getUserPhone2() {
        return userPhone2;
    }

    public void setUserPhone2(String userPhone2) {
        this.userPhone2 = userPhone2;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(String userLatitude) {
        this.userLatitude = userLatitude;
    }

    public String getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(String userLongitude) {
        this.userLongitude = userLongitude;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserPhn1Only() {
        return userPhn1Only;
    }

    public void setUserPhn1Only(String userPhn1Only) {
        this.userPhn1Only = userPhn1Only;
    }

    public String getUserPhn1CountryCode() {
        return userPhn1CountryCode;
    }

    public void setUserPhn1CountryCode(String userPhn1CountryCode) {
        this.userPhn1CountryCode = userPhn1CountryCode;
    }

}
