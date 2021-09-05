
package com.foodtogo.user.model.splash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageResponse {

    @SerializedName("splash_screen_android")
    @Expose
    private String splashScreenAndroid;
    @SerializedName("splash_screen_ios")
    @Expose
    private String splashScreenIos;
    @SerializedName("logo_android")
    @Expose
    private String logoAndroid;
    @SerializedName("login_logo_ios")
    @Expose
    private String loginLogoIos;
    @SerializedName("signup_logo_ios")
    @Expose
    private String signupLogoIos;
    @SerializedName("forgot_password_logo_ios")
    @Expose
    private String forgotPasswordLogoIos;

    public String getSplashScreenAndroid() {
        return splashScreenAndroid;
    }

    public void setSplashScreenAndroid(String splashScreenAndroid) {
        this.splashScreenAndroid = splashScreenAndroid;
    }

    public String getSplashScreenIos() {
        return splashScreenIos;
    }

    public void setSplashScreenIos(String splashScreenIos) {
        this.splashScreenIos = splashScreenIos;
    }

    public String getLogoAndroid() {
        return logoAndroid;
    }

    public void setLogoAndroid(String logoAndroid) {
        this.logoAndroid = logoAndroid;
    }

    public String getLoginLogoIos() {
        return loginLogoIos;
    }

    public void setLoginLogoIos(String loginLogoIos) {
        this.loginLogoIos = loginLogoIos;
    }

    public String getSignupLogoIos() {
        return signupLogoIos;
    }

    public void setSignupLogoIos(String signupLogoIos) {
        this.signupLogoIos = signupLogoIos;
    }

    public String getForgotPasswordLogoIos() {
        return forgotPasswordLogoIos;
    }

    public void setForgotPasswordLogoIos(String forgotPasswordLogoIos) {
        this.forgotPasswordLogoIos = forgotPasswordLogoIos;
    }

}
