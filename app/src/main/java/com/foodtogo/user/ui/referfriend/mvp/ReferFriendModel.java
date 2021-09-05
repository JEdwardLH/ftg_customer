package com.foodtogo.user.ui.referfriend.mvp;


import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.landing.LandingResponse;
import com.foodtogo.user.model.landing.LanguageRequest;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ReferFriendModel implements ReferFriendContractor.Model {


    private ReferFriendPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    ReferFriendModel(ReferFriendPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void requestAppOffer() {
        LanguageRequest landingRequest = new LanguageRequest();
        landingRequest.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .getReferFriendOffer(landingRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<LandingResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<LandingResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onSuccess(baseResponse.getMessage());
                        } else {
                            mPresenter.apiError(baseResponse.getMessage());
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
    public void requestReferFriend(String email) {
        LanguageRequest landingRequest = new LanguageRequest();
        landingRequest.setLang(appRepository.getLanguageCode());
        landingRequest.setReferral_email(email);
        disposable.add(apiInterface
                .postReferFriend(landingRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<LandingResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<LandingResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.PostRefer(baseResponse.getMessage());
                        } else {
                            mPresenter.PostReferError(baseResponse.getMessage());
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
