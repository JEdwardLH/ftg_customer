package com.foodtogo.user.ui.wallet.mvp;

import android.os.Bundle;

import com.foodtogo.user.BaseView;

public interface WalletContractor {

    interface View extends BaseView {

        void showResult(String currencyCode,String walletBalance,Bundle bundleTotalWallet, Bundle bundleUsedWallet, Bundle bundleTotalRewards);

        void showProgressBar();

        void hideProgressBar();

    }

    interface Presenter {

        void requestWallet(int pageNo);

        void onResult(String currencyCode,String walletBalance,Bundle bundleTotalWallet, Bundle bundleUsedWallet, Bundle bundleTotalRewards);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestWallet(int pageNo);

        void close();
    }
}
