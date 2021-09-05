package com.foodtogo.user.ui.register.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.country.CountryList;
import com.foodtogo.user.model.login.LoginResponse;
import com.foodtogo.user.model.register.RegisterResponse;

import org.json.JSONObject;

public interface RegisterContractor {

    interface View extends BaseView {

        void showNameEmptyError();

        void showEmailEmptyError();

        void showNotValidEmailError();

        void showMobileNumberEmptyError();

        void showNotValidMobileNumberError();

        void showPasswordEmptyError();

        void showNotValidPasswordError();

        void showRegisterResponse(RegisterResponse registerResponse);

        void showFacebookDialog(String facebookId, String name);

        void showLoginResponse(LoginResponse loginResponse);

        void showCountryList(CountryList countryList);

        void showOtp(RegisterResponse registerOtpResponse);

        void otpError(String error);
    }

    interface Presenter {

        void requestCountry();

        void registerButtonClicked(String name, String email, String countryCode, String mobileNumber, String password, String referralCode);

        void registerWithOtp(String name, String email, String countryCode, String mobileNumber, String password, String referralCode, String otp, String currentOtp);

        void registerSuccess(RegisterResponse registerResponse);

        void showOtp(RegisterResponse registerOtpResponse);

        void registerViaGoogle(String name, String email, String googleId);

        void registerViaFacebook(String name, String email, String facebookId);

        void registerViaFacebook(JSONObject jsonObject);

        void registerWithoutEmailFacebook(String facebookId, String name);

        void loginSuccess(LoginResponse loginResponse);

        void countryList(CountryList countryList);

        void apiError(String error);

        void otpError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestCountry();

        void requestRegister(String name, String email, String countryCode, String mobileNumber, String password, String referralCode);

        void requestRegisterWithOtp(String name, String email, String countryCode, String mobileNumber, String password, String referralCode, String otp, String currentOtp);

        void requestGoogleLogin(String name, String email, String googleId);

        void requestViaFacebook(JSONObject jsonObject);

        void requestViaFacebook(String name, String email, String facebookId);

        void close();
    }
}
