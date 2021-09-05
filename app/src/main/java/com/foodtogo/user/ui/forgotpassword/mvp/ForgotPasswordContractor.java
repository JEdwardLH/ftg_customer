package com.foodtogo.user.ui.forgotpassword.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.data.rest.ApiInterface;

public interface ForgotPasswordContractor {

    interface View extends BaseView {

        void showEmailEmptyError();

        void showNotValidEmailError();

        void showForgotPasswordResponse(String message);
    }

    interface Presenter {

        void forgotPasswordClicked(String email);

        void forgotPasswordSuccess(String message);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestforgotPassword(ApiInterface apiInterface, String email);

        void close();
    }
}
