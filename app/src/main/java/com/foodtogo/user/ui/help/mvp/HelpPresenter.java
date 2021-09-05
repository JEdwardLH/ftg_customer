package com.foodtogo.user.ui.help.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.support.Support;


public class HelpPresenter implements HelpContractor.Presenter {

    private HelpContractor.View mView;
    private HelpModel model;

    public HelpPresenter(HelpContractor.View view, AppRepository appRepository) {
        mView = view;
        model = new HelpModel(this, appRepository);

    }


    @Override
    public void requestHelpContent() {
        mView.showLoadingView();
        model.requestHelpContent();
    }

    @Override
    public void requestPrivacyPolicyContent() {
        mView.showLoadingView();
        model.requestPrivacyPolicyContent();
    }

    @Override
    public void onContent(Support support) {
        mView.hideLoadingView();
        mView.showContent(support);
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
