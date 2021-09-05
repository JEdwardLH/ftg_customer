package com.foodtogo.user.ui.wallet.usedwallet.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.wallet.WalletBalanceResponse;

public interface UsedWalletContractor {

    interface View extends BaseView {

        void showLoadMore(WalletBalanceResponse walletBalanceResponse);

        void showLoadMoreError(String error);

        void showProgressBar();

        void hideProgressBar();

    }

    interface Presenter {

        void requestUsedWallet(int pageNo);

        void onLoadMoreUsedWallet(WalletBalanceResponse walletBalanceResponse);

        void onLoadMoreError(String error);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestUsedWallet(int pageNo);

        void close();
    }
}
