package com.foodtogo.user.ui.wallet.usedwallet.mvp;


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

public class UsedWalletModel implements UsedWalletContractor.Model {


    private UsedWalletPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    UsedWalletModel(UsedWalletPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
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
                            mPresenter.onLoadMoreUsedWallet(baseResponse.getData());
                        } else {
                            if (pageNo == 1) {
                                mPresenter.apiError(baseResponse.getMessage());
                            } else {
                                mPresenter.onLoadMoreError(baseResponse.getMessage());
                            }
                        }
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
