package com.foodtogo.user.ui.settings.mvp;

import android.app.Activity;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.settings.MenuSettings;

import java.util.List;


public class SettingsPresenter implements SettingsContractor.Presenter {

    private SettingsContractor.View mView;
    private SettingsModel model;


    public SettingsPresenter(SettingsContractor.View view, AppRepository appRepository1) {
        mView = view;
        model = new SettingsModel(this, appRepository1);
    }



    @Override
    public void requestMenu(Activity activity) {
        mView.showLoadingView();
        model.requestMenu(activity);
    }

    @Override
    public void onSettingsMenu(List<MenuSettings> menuSettingsList) {
        mView.hideLoadingView();
        mView.showSettingsMenu(menuSettingsList);
    }

    @Override
    public void requestChangePassword(String oldPassword, String newPassword) {
        mView.showLoadingView();
        model.requestChangePassword(oldPassword,newPassword);
    }

    @Override
    public void onPasswordChangedSuccess(String message) {
        mView.hideLoadingView();
        mView.showPasswordChangedSuccess(message);
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
