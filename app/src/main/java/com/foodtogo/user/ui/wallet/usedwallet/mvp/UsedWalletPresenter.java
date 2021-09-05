package com.foodtogo.user.ui.wallet.usedwallet.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.wallet.WalletBalanceResponse;


public class UsedWalletPresenter implements UsedWalletContractor.Presenter {

    private UsedWalletContractor.View mView;
    private UsedWalletModel model;


    public UsedWalletPresenter(UsedWalletContractor.View view, AppRepository appRepository) {
        mView = view;
        model = new UsedWalletModel(this, appRepository);
    }


    @Override
    public void requestUsedWallet(int pageNo) {
        model.requestUsedWallet(pageNo);
    }

    @Override
    public void onLoadMoreUsedWallet(WalletBalanceResponse walletBalanceResponse) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showLoadMore(walletBalanceResponse);
    }

    @Override
    public void onLoadMoreError(String error) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.showLoadMoreError(error);
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
