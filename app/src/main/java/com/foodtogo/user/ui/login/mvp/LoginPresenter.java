package com.foodtogo.user.ui.login.mvp;

import android.util.Patterns;

import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.login.LoginResponse;

import org.json.JSONObject;


public class LoginPresenter implements LoginContractor.Presenter {

    private LoginContractor.View mView;
    private LoginModel model;
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    public LoginPresenter(LoginContractor.View view, AppRepository appRepository1) {
        mView = view;
        this.appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
        model = new LoginModel(this, appRepository);
    }


    @Override
    public void loginButtonClicked(String email, String password) {
        if (email.length() == 0) {
            mView.showEmailEmptyError();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mView.showNotValidEmailError();
        }
//        else if (!isEmail && !Patterns.PHONE.matcher(email).matches()) {
//            mView.showMobileNumberError();
//        }l
        else if (password.length() == 0) {
            mView.showPasswordEmptyError();
        } else if (password.length() < 6) {
            mView.showNotValidPasswordError();
        } else {
            mView.showLoadingView();
            model.requestLogin(apiInterface, email, password);
        }
    }

    @Override
    public void loginViaGoogle(String name, String email, String googleId) {
        mView.showLoadingView();
        model.requestGoogleLogin(apiInterface, name, email, googleId);
    }

    @Override
    public void loginViaFacebook(String name, String email, String facebookId) {
        mView.showLoadingView();
        model.requestViaFacebook(apiInterface, name, email, facebookId);
    }

    @Override
    public void loginViaFacebook(JSONObject jsonObject) {
        mView.showLoadingView();
        model.requestViaFacebook(apiInterface, jsonObject);
    }

    @Override
    public void loginWithoutEmailFacebook(String facebookId, String name) {
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
    public void apiError(String error) {
        mView.hideLoadingView();
        mView.showError(error);
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
