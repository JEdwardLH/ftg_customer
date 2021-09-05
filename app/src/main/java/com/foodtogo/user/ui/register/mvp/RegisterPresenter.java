package com.foodtogo.user.ui.register.mvp;

import android.util.Patterns;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.country.CountryList;
import com.foodtogo.user.model.login.LoginResponse;
import com.foodtogo.user.model.register.RegisterResponse;

import org.json.JSONObject;


public class RegisterPresenter implements RegisterContractor.Presenter {

    private RegisterContractor.View mView;
    private RegisterModel model;
    private AppRepository appRepository;


    public RegisterPresenter(RegisterContractor.View view, AppRepository appRepository1) {
        mView = view;
        this.appRepository = appRepository1;
        model = new RegisterModel(this, appRepository);

    }


    @Override
    public void requestCountry() {
        mView.showLoadingView();
        model.requestCountry();
    }

    @Override
    public void registerButtonClicked(String name, String email, String countryCode, String mobileNumber, String password, String referralCode) {
        if (name.length() == 0) {
            mView.showNameEmptyError();
        } else if (email.length() == 0) {
            mView.showEmailEmptyError();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mView.showNotValidEmailError();
        } else if (mobileNumber.length() == 0) {
            mView.showMobileNumberEmptyError();
        } else if (password.length() == 0) {
            mView.showPasswordEmptyError();
        } else if (password.length() < 6) {
            mView.showNotValidPasswordError();
        } else {
            mView.showLoadingView();
            model.requestRegister(name, email, countryCode, mobileNumber, password, referralCode);
        }
    }

    @Override
    public void registerWithOtp(String name, String email, String countryCode, String mobileNumber, String password, String referralCode, String otp,String currentOtp) {
        mView.showLoadingView();
        model.requestRegisterWithOtp(name, email, countryCode, mobileNumber, password, referralCode, otp,currentOtp);
    }


    @Override
    public void registerSuccess(RegisterResponse registerResponse) {
        mView.hideLoadingView();
        appRepository.saveIsLoggedIn(true);
        appRepository.setOAuthKey(registerResponse.getToken());
        mView.showRegisterResponse(registerResponse);
    }

    @Override
    public void showOtp(RegisterResponse registerOtpResponse) {
        mView.hideLoadingView();
        mView.showOtp(registerOtpResponse);
    }

    @Override
    public void registerViaGoogle(String name, String email, String googleId) {
        mView.showLoadingView();
        model.requestGoogleLogin(name, email, googleId);
    }

    @Override
    public void registerViaFacebook(String name, String email, String facebookId) {
        mView.showLoadingView();
        model.requestViaFacebook(name, email, facebookId);
    }

    @Override
    public void registerViaFacebook(JSONObject jsonObject) {
        mView.showLoadingView();
        model.requestViaFacebook(jsonObject);
    }

    @Override
    public void registerWithoutEmailFacebook(String facebookId, String name) {
        mView.hideLoadingView();
        mView.showFacebookDialog(facebookId, name);
    }

    @Override
    public void loginSuccess(LoginResponse loginResponse) {
        mView.hideLoadingView();
        appRepository.saveIsLoggedIn(true);
        appRepository.setOAuthKey(loginResponse.getToken());
        appRepository.setUserId(loginResponse.getUserId());
        mView.showLoginResponse(loginResponse);
    }

    @Override
    public void countryList(CountryList countryList) {
        mView.hideLoadingView();
        mView.showCountryList(countryList);
    }


    @Override
    public void apiError(String error) {
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void otpError(String error) {
        mView.hideLoadingView();
        mView.otpError(error);
    }

    @Override
    public void apiError(int error) {
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void close() {
        model.close();
    }
}
