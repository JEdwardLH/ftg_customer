package com.foodtogo.user.ui.wallet.mvp;


import android.os.Bundle;

import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.landing.LanguageRequest;
import com.foodtogo.user.model.wallet.WalletBalanceResponse;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import static com.foodtogo.user.base.AppConstants.CODE;
import static com.foodtogo.user.base.AppConstants.MESSAGE;
import static com.foodtogo.user.base.AppConstants.TOTAL_WALLET;
import static com.foodtogo.user.base.AppConstants.USED_WALLET;

public class WalletModel implements WalletContractor.Model {


    private WalletPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    private Bundle bundleTotalWallet = new Bundle();
    private Bundle bundleUsedWallet = new Bundle();
    private Bundle bundleTotalRewards = new Bundle();
    private String walletBalance = "0.0";
    private String currencyCode = "0.0";

    WalletModel(WalletPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }

    @Override
    public void requestWallet(int pageNo) {
        LanguageRequest languageRequest = new LanguageRequest();
        languageRequest.setLang(appRepository.getLanguageCode());
        languageRequest.setPage_no(String.valueOf(pageNo));
        disposable.add(apiInterface
                .getWalletBalance(languageRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<WalletBalanceResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<WalletBalanceResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            currencyCode = baseResponse.getData().getCurrencyCode();
                            walletBalance = baseResponse.getData().getAvailableBalance();
                            bundleTotalWallet.putParcelableArrayList(TOTAL_WALLET, baseResponse.getData().getUsedDetails());
                            bundleTotalWallet.putInt(CODE, baseResponse.getCode());
                            bundleTotalWallet.putString(MESSAGE, baseResponse.getMessage());
                        } else {
                            bundleTotalWallet.putParcelableArrayList(TOTAL_WALLET, baseResponse.getData().getUsedDetails());
                            bundleTotalWallet.putInt(CODE, baseResponse.getCode());
                            bundleTotalWallet.putString(MESSAGE, baseResponse.getMessage());
                        }
                        requestUsedWallet(pageNo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            mPresenter.apiError(NetworkUtils.getErrorMessage(responseBody));
                        } else if (e instanceof SocketTimeoutException) {
                            mPresenter.apiError(R.string.time_out_error);
                        } else if (e instanceof IOException) {
                            mPresenter.apiError(R.string.network_error);
                        } else {
                            mPresenter.apiError(e.getMessage());
                        }

                    }
                }));
    }


    public void requestUsedWallet(int pageNo) {
        LanguageRequest languageRequest = new LanguageRequest();
        languageRequest.setLang(appRepository.getLanguageCode());
        languageRequest.setPage_no(String.valueOf(pageNo));
        disposable.add(apiInterface
                .getUsedWallet(languageRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<WalletBalanceResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<WalletBalanceResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            bundleUsedWallet.putParcelableArrayList(USED_WALLET, baseResponse.getData().getUsedDetails());
                            bundleUsedWallet.putInt(CODE, baseResponse.getCode());
                            bundleUsedWallet.putString(MESSAGE, baseResponse.getMessage());
                        } else {
                            bundleUsedWallet.putParcelableArrayList(USED_WALLET, baseResponse.getData().getUsedDetails());
                            bundleUsedWallet.putInt(CODE, baseResponse.getCode());
                            bundleUsedWallet.putString(MESSAGE, baseResponse.getMessage());
                        }
                        mPresenter.onResult(currencyCode,walletBalance, bundleTotalWallet, bundleUsedWallet, bundleTotalRewards);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            mPresenter.apiError(NetworkUtils.getErrorMessage(responseBody));
                        } else if (e instanceof SocketTimeoutException) {
                            mPresenter.apiError(R.string.time_out_error);
                        } else if (e instanceof IOException) {
                            mPresenter.apiError(R.string.network_error);
                        } else {
                            mPresenter.apiError(e.getMessage());
                        }

                    }
                }));
    }


    @Override
    public void close() {
        disposable.dispose();
    }
}
