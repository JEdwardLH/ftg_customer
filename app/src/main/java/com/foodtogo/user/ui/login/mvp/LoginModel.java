package com.foodtogo.user.ui.login.mvp;


import com.foodtogo.user.BaseApplication;
import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.login.LoginFacebookRequest;
import com.foodtogo.user.model.login.LoginGoogleRequest;
import com.foodtogo.user.model.login.LoginRequest;
import com.foodtogo.user.model.login.LoginResponse;
import com.foodtogo.user.util.NetworkUtils;
import com.foodtogo.user.util.ViewUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import static com.foodtogo.user.base.AppConstants.ANDROID;

public class LoginModel implements LoginContractor.Model {

    private final static String EMAIL = "email";
    private final static String NAME = "name";
    private final static String ID = "id";

    private LoginPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;

    LoginModel(LoginPresenter presenter, AppRepository _appRepository) {
        mPresenter = presenter;
        appRepository = _appRepository;
    }


    @Override
    public void requestLogin(ApiInterface apiInterface, String email, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setCus_password(password);
        loginRequest.setLogin_id(email);
        loginRequest.setLang(appRepository.getLanguageCode());
        loginRequest.setType(ANDROID);
        loginRequest.setAndr_fcm_id(appRepository.getFCMToken());
        loginRequest.setAndr_device_id(ViewUtils.getDeviceId());
        disposable.add(apiInterface
                .signIn(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<LoginResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<LoginResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.loginSuccess(baseResponse.getData());
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
    public void requestGoogleLogin(ApiInterface apiInterface, String name, String email, String googleId) {
        LoginGoogleRequest loginGoogleRequest = new LoginGoogleRequest();
        loginGoogleRequest.setEmail(email);
        loginGoogleRequest.setName(name);
        loginGoogleRequest.setGoogle_id(googleId);
        loginGoogleRequest.setLang(appRepository.getLanguageCode());
        loginGoogleRequest.setType(ANDROID);
        loginGoogleRequest.setAndr_fcm_id(appRepository.getFCMToken());
        loginGoogleRequest.setAndr_device_id(ViewUtils.getDeviceId());
        try {
            disposable.add(apiInterface
                    .googleLogin(loginGoogleRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<BaseResponse<LoginResponse>>() {
                        @Override
                        public void onSuccess(BaseResponse<LoginResponse> baseResponse) {
                            if (baseResponse.getCode() == 200) {
                                mPresenter.loginSuccess(baseResponse.getData());
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
        } catch (Exception e) {
            mPresenter.apiError(BaseApplication.getContext().getResources().getString(R.string.server_error));
        }
    }

    @Override
    public void requestViaFacebook(ApiInterface apiInterface, JSONObject jsonObject) {
        try {
            if (jsonObject.has(EMAIL)) {
                String email = jsonObject.getString(EMAIL);
                String name = jsonObject.getString(NAME);
                String faceBookId = jsonObject.getString(ID);
                LoginFacebookRequest loginFacebookRequest = new LoginFacebookRequest();
                loginFacebookRequest.setEmail(email);
                loginFacebookRequest.setName(name);
                loginFacebookRequest.setFacebook_id(faceBookId);
                loginFacebookRequest.setLang(appRepository.getLanguageCode());
                loginFacebookRequest.setType(ANDROID);
                loginFacebookRequest.setAndr_fcm_id(appRepository.getFCMToken());
                loginFacebookRequest.setAndr_device_id(ViewUtils.getDeviceId());
                disposable.add(apiInterface
                        .facebookLogin(loginFacebookRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<BaseResponse<LoginResponse>>() {
                            @Override
                            public void onSuccess(BaseResponse<LoginResponse> baseResponse) {
                                if (baseResponse.getCode() == 200) {
                                    mPresenter.loginSuccess(baseResponse.getData());
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
            } else if (!jsonObject.has(EMAIL)) {
                String name = jsonObject.getString(NAME);
                String faceBookId = jsonObject.getString(ID);
                mPresenter.loginWithoutEmailFacebook(faceBookId, name);
            }
        } catch (Exception e) {
            mPresenter.apiError(e.getMessage());
        }
    }

    @Override
    public void requestViaFacebook(ApiInterface apiInterface, String name, String email, String facebookId) {
        LoginFacebookRequest loginFacebookRequest = new LoginFacebookRequest();
        loginFacebookRequest.setEmail(email);
        loginFacebookRequest.setName(name);
        loginFacebookRequest.setFacebook_id(facebookId);
        loginFacebookRequest.setLang(appRepository.getLanguageCode());
        loginFacebookRequest.setType(ANDROID);
        loginFacebookRequest.setAndr_fcm_id(appRepository.getFCMToken());
        loginFacebookRequest.setAndr_device_id(ViewUtils.getDeviceId());
        disposable.add(apiInterface
                .facebookLogin(loginFacebookRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<LoginResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<LoginResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.loginSuccess(baseResponse.getData());
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
