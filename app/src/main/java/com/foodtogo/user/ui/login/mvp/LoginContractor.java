package com.foodtogo.user.ui.login.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.model.login.LoginResponse;

import org.json.JSONObject;

public interface LoginContractor {

    interface View extends BaseView {

        void showEmailEmptyError();

        void showMobileNumberError();

        void showNotValidEmailError();

        void showPasswordEmptyError();

        void showNotValidPasswordError();

        void showLoginResponse(LoginResponse loginResponse);

        void showFacebookDialog(String facebookId, String name);
    }

    interface Presenter {

        void loginButtonClicked(String email, String password);

        void loginViaGoogle(String name, String email, String googleId);

        void loginViaFacebook(String name, String email, String facebookId);

        void loginViaFacebook(JSONObject jsonObject);

        void loginWithoutEmailFacebook(String facebookId, String name);

        void loginSuccess(LoginResponse loginResponse);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestLogin(ApiInterface apiInterface, String email, String password);

        void requestGoogleLogin(ApiInterface apiInterface, String name, String email, String googleId);

        void requestViaFacebook(ApiInterface apiInterface, JSONObject jsonObject);

        void requestViaFacebook(ApiInterface apiInterface, String name, String email, String facebookId);

        void close();
    }
}
