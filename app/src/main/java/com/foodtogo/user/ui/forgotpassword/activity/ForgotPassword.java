package com.foodtogo.user.ui.forgotpassword.activity;

import android.os.Bundle;
import androidx.appcompat.widget.AppCompatEditText;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.ui.forgotpassword.mvp.ForgotPasswordContractor;
import com.foodtogo.user.ui.forgotpassword.mvp.ForgotPasswordPresenter;

import butterknife.BindView;
import butterknife.OnClick;


public class ForgotPassword extends BaseActivity implements ForgotPasswordContractor.View {


    @BindView(R.id.et_email)
    AppCompatEditText etEmail;

    ForgotPasswordPresenter forgotPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forgotPasswordPresenter = new ForgotPasswordPresenter(this, appRepository);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_forgot_password;
    }


    @OnClick(R.id.btn_forgot_password)
    public void setBtnForgotPassword() {
        if (isNetworkConnected())
            forgotPasswordPresenter.forgotPasswordClicked(etEmail.getText().toString().trim());
        else
            showToast(R.string.no_internet_connection);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        forgotPasswordPresenter.close();
    }


    @Override
    public void showEmailEmptyError() {
        showToast(R.string.warning_empty_email);
    }

    @Override
    public void showNotValidEmailError() {
        showToast(R.string.warning_invalid_email);
    }

    @Override
    public void showForgotPasswordResponse(String message) {
        hideKeyboard();
        showSuccessDialog(3, message);
    }


    @Override
    public void showLoadingView() {
        showProgressDialog();
    }

    @Override
    public void hideLoadingView() {
        hideProgressDialog();
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @Override
    public void showError(int message) {
        showToast(message);
    }

}
