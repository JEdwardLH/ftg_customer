package com.foodtogo.user.ui.forgotpassword.mvp;

import android.util.Patterns;

import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;


public class ForgotPasswordPresenter implements ForgotPasswordContractor.Presenter {

    private ForgotPasswordContractor.View mView;
    private ForgotPasswordModel model;
    private ApiInterface apiInterface;


    public ForgotPasswordPresenter(ForgotPasswordContractor.View view, AppRepository appRepository1) {
        mView = view;
        model = new ForgotPasswordModel(this, appRepository1);
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void forgotPasswordClicked(String email) {
        if (email.length() == 0) {
            mView.showEmailEmptyError();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mView.showNotValidEmailError();
        } else {
            mView.showLoadingView();
            model.requestforgotPassword(apiInterface, email);
        }
    }

    @Override
    public void forgotPasswordSuccess(String message) {
        mView.hideLoadingView();
        mView.showForgotPasswordResponse(message);
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
