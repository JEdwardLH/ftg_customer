package com.foodtogo.user.ui.home.mvp;


import com.foodtogo.user.BaseApplication;
import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.login.LoginResponse;
import com.foodtogo.user.model.login.LogoutRequest;
import com.foodtogo.user.util.NetworkUtils;
import com.foodtogo.user.util.ViewUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import static com.foodtogo.user.base.AppConstants.ANDROID;

public class HomeActivityModel implements HomeActivityContractor.Model {


    private HomeActivityPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    HomeActivityModel(HomeActivityPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void logout() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setLang(appRepository.getLanguageCode());
        logoutRequest.setToken(appRepository.getOAuthKey());
        logoutRequest.setAndr_device_id(ViewUtils.getDeviceId());
        logoutRequest.setType(ANDROID);
        logoutRequest.setAndr_fcm_id(appRepository.getFCMToken());
        disposable.add(apiInterface
                .logout(logoutRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<LoginResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<LoginResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onSuccess(BaseApplication.getContext().getString(R.string.logged_out));
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
    public void close() {
        disposable.dispose();
    }
}
