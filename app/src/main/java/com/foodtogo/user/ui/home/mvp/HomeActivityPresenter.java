package com.foodtogo.user.ui.home.mvp;

import com.foodtogo.user.data.source.AppRepository;

public class HomeActivityPresenter implements HomeActivityContractor.Presenter {

    private HomeActivityContractor.View mView;
    private HomeActivityModel model;
    private AppRepository appRepository;


    public HomeActivityPresenter(HomeActivityContractor.View view, AppRepository appRepository) {
        mView = view;
        this.appRepository = appRepository;
        model = new HomeActivityModel(this,appRepository);
    }


    @Override
    public void logout() {
        mView.showLoadingView();
        model.logout();
    }

    @Override
    public void onSuccess(String message) {
        mView.hideLoadingView();
        mView.onSuccess(message);
        appRepository.setLastShownDate("");
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
