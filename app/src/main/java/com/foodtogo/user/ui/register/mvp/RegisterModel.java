package com.foodtogo.user.ui.register.mvp;


import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.country.CountryList;
import com.foodtogo.user.model.landing.LanguageRequest;
import com.foodtogo.user.model.login.LoginFacebookRequest;
import com.foodtogo.user.model.login.LoginGoogleRequest;
import com.foodtogo.user.model.login.LoginResponse;
import com.foodtogo.user.model.register.RegisterRequest;
import com.foodtogo.user.model.register.RegisterResponse;
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

public class RegisterModel implements RegisterContractor.Model {

    private final static String EMAIL = "email";
    private final static String NAME = "name";
    private final static String ID = "id";

    private RegisterPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    RegisterModel(RegisterPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void requestRegister(String name, String email, String countryCode, String mobileNumber, String password, String referralCode) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setCus_fname(name);
        registerRequest.setCus_email(email);
        registerRequest.setCus_phone1(mobileNumber);
        registerRequest.setCus_password(password);
        registerRequest.setReferral_code(referralCode);
        registerRequest.setLang(appRepository.getLanguageCode());
        registerRequest.setType(ANDROID);
        registerRequest.setAndr_fcm_id(appRepository.getFCMToken());
        registerRequest.setAndr_device_id(ViewUtils.getDeviceId());
        registerRequest.setCus_phone1_code(countryCode);
        disposable.add(apiInterface
                .register(registerRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<RegisterResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<RegisterResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.registerSuccess(baseResponse.getData());
                        } else if (baseResponse.getCode() == 201) {
                            mPresenter.showOtp(baseResponse.getData());
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
    public void requestRegisterWithOtp(String name, String email, String countryCode, String mobileNumber, String password, String referralCode, String otp, String currentOtp) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setCus_fname(name);
        registerRequest.setCus_email(email);
        registerRequest.setCus_phone1(mobileNumber);
        registerRequest.setCus_password(password);
        registerRequest.setReferral_code(referralCode);
        registerRequest.setLang(appRepository.getLanguageCode());
        registerRequest.setType(ANDROID);
        registerRequest.setAndr_fcm_id(appRepository.getFCMToken());
        registerRequest.setOtp(otp);
        registerRequest.setCurrent_otp(currentOtp);
        registerRequest.setAndr_device_id(ViewUtils.getDeviceId());
        registerRequest.setCus_phone1_code(countryCode);
        disposable.add(apiInterface
                .registerWithOtp(registerRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.registerSuccess((RegisterResponse) baseResponse.getData());
                        } else {
                            mPresenter.otpError(baseResponse.getMessage());
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
    public void requestGoogleLogin(String name, String email, String googleId) {
        LoginGoogleRequest loginGoogleRequest = new LoginGoogleRequest();
        loginGoogleRequest.setEmail(email);
        loginGoogleRequest.setName(name);
        loginGoogleRequest.setGoogle_id(googleId);
        loginGoogleRequest.setLang(appRepository.getLanguageCode());
        loginGoogleRequest.setType(ANDROID);
        loginGoogleRequest.setAndr_fcm_id(appRepository.getFCMToken());
        loginGoogleRequest.setAndr_device_id(ViewUtils.getDeviceId());
        disposable.add(apiInterface
                .googleLogin(loginGoogleRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.loginSuccess((LoginResponse) baseResponse.getData());
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
    public void requestViaFacebook(JSONObject jsonObject) {
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
                        .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse baseResponse) {
                                if (baseResponse.getCode() == 200) {
                                    mPresenter.loginSuccess((LoginResponse) baseResponse.getData());
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
                mPresenter.registerWithoutEmailFacebook(faceBookId, name);
            }
        } catch (Exception e) {
            mPresenter.apiError(e.getMessage());
        }
    }

    @Override
    public void requestViaFacebook(String name, String email, String facebookId) {
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
    public void requestCountry() {
        LanguageRequest languageRequest = new LanguageRequest();
        languageRequest.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .getCountryList(languageRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<CountryList>>() {
                    @Override
                    public void onSuccess(BaseResponse<CountryList> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.countryList(baseResponse.getData());
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
