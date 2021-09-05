package com.foodtogo.user.ui.profile.mvp;


import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.accountdetails.User;
import com.foodtogo.user.model.country.CountryList;
import com.foodtogo.user.model.landing.LanguageRequest;
import com.foodtogo.user.model.profile.ProfileUpdateResponse;
import com.foodtogo.user.model.shippingaddress.CheckVerificationRequest;
import com.foodtogo.user.model.shippingaddress.OtpResponse;
import com.foodtogo.user.model.shippingaddress.SendEmailVerificationCodeRequest;
import com.foodtogo.user.util.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ProfileModel implements ProfileContractor.Model {


    private ProfilePresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;
    private static MediaType mediaType = MediaType.parse("text/plain");
    private static final String CUS_IMAGE = "cus_image";

    ProfileModel(ProfilePresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void requestAccountDetails() {
        LanguageRequest landingRequest = new LanguageRequest();
        landingRequest.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .requestAccountDetails(landingRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<User>>() {
                    @Override
                    public void onSuccess(BaseResponse<User> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onSuccess(baseResponse.getData());
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
    public void requestUpdateProfile(String userName, String email, String phone1, String phone2, String address,
                                     String latitude, String longitude, File file,String phoneCode1) {
        MultipartBody.Part part = null;
        if (file != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            part = MultipartBody.Part.createFormData(CUS_IMAGE, file.getName(), requestFile);
        }
        disposable.add(apiInterface
                .updateAccount(createPart(userName), createPart(email), createPart(phone1), createPart(phone2),
                        createPart(address), createPart(latitude), createPart(longitude), createPart(appRepository.getLanguageCode()),createPart(phoneCode1),
                        createPart(phoneCode1), part)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onSuccess(baseResponse.getMessage(),(ProfileUpdateResponse) baseResponse.getData());
                        } else if (baseResponse.getCode() == 201) {
                            mPresenter.onOtpPopup((ProfileUpdateResponse) baseResponse.getData(),baseResponse.getMessage());
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
    public void requestUpdateProfileWithOtp(String userName, String email, String phone1, String phone2, String address, String latitude,
                                            String longitude, File file, String otp,String currentOtp,String phoneCode1) {
        MultipartBody.Part part = null;
        if (file != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            part = MultipartBody.Part.createFormData(CUS_IMAGE, file.getName(), requestFile);
        }
        disposable.add(apiInterface
                .updateAccountWithOtp(createPart(userName), createPart(email), createPart(phone1), createPart(phone2),
                        createPart(address), createPart(latitude), createPart(longitude), createPart(appRepository.getLanguageCode()),createPart(otp),
                        createPart(currentOtp),createPart(phoneCode1),createPart(phoneCode1), part)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<ProfileUpdateResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<ProfileUpdateResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onSuccess(baseResponse.getMessage(), baseResponse.getData());
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


    private RequestBody createPart(String str) {
        return RequestBody.create(mediaType, str);
    }


    @Override
    public void close() {
        disposable.dispose();
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
    public void sendVerificationCode(SendEmailVerificationCodeRequest request) {
        disposable.add(apiInterface
                .sendVerificationCode(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<OtpResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<OtpResponse> baseResponse) {
                        if (baseResponse.getCode() == 201) {
                           mPresenter.emailVerifyOtpSuccess(String.valueOf(baseResponse.getData().getOtp()),baseResponse.getMessage());
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
    public void requestCheckVerificationCode(CheckVerificationRequest request) {
        disposable.add(apiInterface
                .checkVerificationCode(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onCodeVerificationSuccess(baseResponse.getMessage());
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
}
