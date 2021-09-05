package com.foodtogo.user.ui.wallet.totalwallet.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.wallet.WalletBalanceResponse;

public interface TotalWalletContractor {

    interface View extends BaseView {

        void showTotalWalletLoadMore(WalletBalanceResponse walletBalanceResponse);

        void showLoadMoreError(String error);

        void showProgressBar();

        void hideProgressBar();

    }

    interface Presenter {

        void requestTotalWallet(int pageNo);

        void onLoadMoreTotalWallet(WalletBalanceResponse walletBalanceResponse);

        void onLoadMoreError(String error);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestTotalWallet(int pageNo);

        void close();
    }
}
