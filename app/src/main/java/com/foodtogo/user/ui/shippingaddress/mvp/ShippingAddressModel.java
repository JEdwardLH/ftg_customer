package com.foodtogo.user.ui.shippingaddress.mvp;


import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.country.CountryList;
import com.foodtogo.user.model.landing.LanguageRequest;
import com.foodtogo.user.model.shippingaddress.CheckVerificationRequest;
import com.foodtogo.user.model.shippingaddress.OtpResponse;
import com.foodtogo.user.model.shippingaddress.SendEmailVerificationCodeRequest;
import com.foodtogo.user.model.shippingaddress.ShippingAddressData;
import com.foodtogo.user.model.shippingaddress.ShippingAddressRequest;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import static com.foodtogo.user.base.AppConstants.VERIFICATION_CHECK;

public class ShippingAddressModel implements ShippingAddressContractor.Model {


    private ShippingAddressPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    ShippingAddressModel(ShippingAddressPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void postShippingAddress(String firstName, String lastName, String emailAddress, String mobileNumber,
                                    String alternateNumber,String landmark, String postalAddress, String latitude,
                                    String longitude, String pinCode,String countryCode1,String countryCode2) {
        ShippingAddressRequest shippingAddressRequest = new ShippingAddressRequest();
        shippingAddressRequest.setSh_cus_fname(firstName);
        shippingAddressRequest.setSh_cus_lname(lastName);
        shippingAddressRequest.setSh_cus_email(emailAddress);
        shippingAddressRequest.setSh_phone1(mobileNumber);
        shippingAddressRequest.setSh_phone2(alternateNumber);
        shippingAddressRequest.setSh_location(postalAddress);
        shippingAddressRequest.setSh_latitude(latitude);
        shippingAddressRequest.setSh_longitude(longitude);
        shippingAddressRequest.setSh_zipcode(pinCode);
        shippingAddressRequest.setSh_location1(landmark);
        shippingAddressRequest.setSh_phone1_code(countryCode1);
        shippingAddressRequest.setSh_phone2_code(countryCode2);
        shippingAddressRequest.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .postShippingAddress(shippingAddressRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<OtpResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<OtpResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onSuccess(baseResponse.getMessage());
                        }else if(baseResponse.getCode()==201){
                            mPresenter.otpRequest(String.valueOf(baseResponse.getData().getOtp()),baseResponse.getMessage(),false);
                        }else if(baseResponse.getCode()==400 && baseResponse.getMessage().equals(VERIFICATION_CHECK)){
                            mPresenter.showErrorDialog(baseResponse.getMessage());
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
    public void requestShippingAddress() {
        LanguageRequest languageRequest = new LanguageRequest();
        languageRequest.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .getShippingAddress(languageRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<ShippingAddressData>>() {
                    @Override
                    public void onSuccess(BaseResponse<ShippingAddressData> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onShippingResponse(baseResponse.getData().getShippingAddress());
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

    @Override
    public void postShippingAddressWithOtp(ShippingAddressRequest shippingAddressRequest) {
        disposable.add(apiInterface
                .postAddressWithOtp(shippingAddressRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
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
    public void sendVerificationCode(SendEmailVerificationCodeRequest request) {
        disposable.add(apiInterface
                .sendVerificationCode(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<OtpResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<OtpResponse> baseResponse) {
                        if (baseResponse.getCode() == 201) {
                            mPresenter.otpRequest(String.valueOf(baseResponse.getData().getOtp()),baseResponse.getMessage(),true);
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
