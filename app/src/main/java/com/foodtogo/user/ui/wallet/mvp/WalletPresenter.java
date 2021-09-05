package com.foodtogo.user.ui.wallet.mvp;

import android.os.Bundle;

import com.foodtogo.user.data.source.AppRepository;


public class WalletPresenter implements WalletContractor.Presenter {

    private WalletContractor.View mView;
    private WalletModel model;


    public WalletPresenter(WalletContractor.View view, AppRepository appRepository) {
        mView = view;
        model = new WalletModel(this, appRepository);
    }


    @Override
    public void requestWallet(int pageNo) {
        mView.showProgressBar();
        model.requestWallet(pageNo);
    }

    @Override
    public void onResult(String currencyCode, String walletBalance, Bundle bundleTotalWallet, Bundle bundleUsedWallet, Bundle bundleTotalRewards) {
        mView.hideProgressBar();
        mView.showResult(currencyCode, walletBalance, bundleTotalWallet, bundleUsedWallet, bundleTotalRewards);
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
    public void close() {
        model.close();
    }
}
