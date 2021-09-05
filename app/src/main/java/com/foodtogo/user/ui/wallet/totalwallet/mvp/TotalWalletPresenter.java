package com.foodtogo.user.ui.wallet.totalwallet.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.wallet.WalletBalanceResponse;


public class TotalWalletPresenter implements TotalWalletContractor.Presenter {

    private TotalWalletContractor.View mView;
    private TotalWalletModel model;


    public TotalWalletPresenter(TotalWalletContractor.View view, AppRepository appRepository) {
        mView = view;
        model = new TotalWalletModel(this, appRepository);
    }

    @Override
    public void requestTotalWallet(int pageNo) {
        model.requestTotalWallet(pageNo);
    }

    @Override
    public void onLoadMoreTotalWallet(WalletBalanceResponse walletBalanceResponse) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showTotalWalletLoadMore(walletBalanceResponse);
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
