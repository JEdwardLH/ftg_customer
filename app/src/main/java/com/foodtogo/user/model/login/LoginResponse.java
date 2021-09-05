
package com.foodtogo.user.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("token")
    private String token;
    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_email")
    private String userEmail;
    @SerializedName("user_phone")
     private String userPhone;
    @SerializedName("user_login_type")
    private Integer userLoginType;
    @SerializedName("user_status")
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

}
