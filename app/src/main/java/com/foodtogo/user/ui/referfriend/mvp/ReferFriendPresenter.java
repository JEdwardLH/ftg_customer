package com.foodtogo.user.ui.referfriend.mvp;

import android.util.Patterns;

import com.foodtogo.user.data.source.AppRepository;


public class ReferFriendPresenter implements ReferFriendContractor.Presenter {

    private ReferFriendContractor.View mView;
    private ReferFriendModel model;


    public ReferFriendPresenter(ReferFriendContractor.View view, AppRepository appRepository) {
        mView = view;
        model = new ReferFriendModel(this, appRepository);
    }


    @Override
    public void requestAppOffer() {
        mView.showProgressBar();
        model.requestAppOffer();
    }

    @Override
    public void onSuccess(String message) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.onSuccess(message);
    }

    @Override
    public void requestReferFriend(String email) {
        if (email.length() == 0) {
            mView.showEmailEmptyError();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mView.showNotValidEmailError();
        } else {
            mView.showLoadingView();
            model.requestReferFriend(email);
        }
    }

    @Override
    public void apiError(String error) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void apiError(int error) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void PostReferError(String error) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.PostReferError(error);
    }

    @Override
    public void PostRefer(String message) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.PostRefer(message);
    }


    @Override
    public void close() {
        model.close();
    }
}
