package com.foodtogo.user.ui.about.mvp;

import android.content.Context;

import com.foodtogo.user.data.source.AppRepository;


public class AboutPresenter implements AboutContractor.Presenter {

    private AboutContractor.View mView;
    private AboutModel model;


    public AboutPresenter(AboutContractor.View view, AppRepository appRepository) {
        mView = view;
        model = new AboutModel(this, appRepository);
    }


    @Override
    public void requestAppVersion(Context context) {
        model.requestAppVersion(context);
    }

    @Override
    public void responseVersion(String version) {
        mView.showVersion(version);
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
