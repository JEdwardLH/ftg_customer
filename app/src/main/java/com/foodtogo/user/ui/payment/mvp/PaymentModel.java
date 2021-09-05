package com.foodtogo.user.ui.payment.mvp;


import android.util.Log;

import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.landing.LanguageRequest;
import com.foodtogo.user.model.payment.CheckQuantityResponse;
import com.foodtogo.user.model.payment.PaymentMethodResponse;
import com.foodtogo.user.model.payment.PaymentResult;
import com.foodtogo.user.model.payment.RequestCashOnDelivery;
import com.foodtogo.user.model.payment.RequestPayPal;
import com.foodtogo.user.model.payment.RequestStripe;
import com.foodtogo.user.model.payment.UseWallet;
import com.foodtogo.user.model.payment.UseWalletResponse;
import com.foodtogo.user.model.wallet.WalletBalanceResponse;
import com.foodtogo.user.ui.payment.enums.OfferType;
import com.foodtogo.user.util.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class PaymentModel implements PaymentContractor.Model {


    private PaymentPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;
    private final static String RESPONSE = "response";
    private final static String ID = "id";
    private final static String STATE = "state";

    PaymentModel(PaymentPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void requestWalletBalance(String pageNo) {
        LanguageRequest languageRequest = new LanguageRequest();
        languageRequest.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .getWalletBalance(languageRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<WalletBalanceResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<WalletBalanceResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onWalletResult(baseResponse.getData());
                        } else {
                            mPresenter.onWalletError(baseResponse.getMessage());
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
    public void requestPaymentMethod() {
        LanguageRequest languageRequest = new LanguageRequest();
        languageRequest.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .checkQtyPayment(languageRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<CheckQuantityResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<CheckQuantityResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            getPaymentMethods(baseResponse.getCode(), baseResponse.getData());
                        } else {
                            getPaymentMethods(baseResponse.getCode(), baseResponse.getData());
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

    private void getPaymentMethods(int code, CheckQuantityResponse checkQuantityResponse) {
        LanguageRequest languageRequest = new LanguageRequest();
        languageRequest.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .getPaymentMethod(languageRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<PaymentMethodResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<PaymentMethodResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.paymentMethodResult(code, checkQuantityResponse, baseResponse.getData());
                        } else {
                            mPresenter.paymentMethodResultError(code, checkQuantityResponse, baseResponse.getMessage());
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
    public void paymentCashOnDelivery(String firstName, String lastName, String emailId, String mobileNumber,
                                      String alternateNumber, String postalAddress, String landMark,String latitude, String longitude,
                                      String ordSelfPickup, String useWallet, String walletAmount, String useCoupon, String couponId, String couponAmount) {
        RequestCashOnDelivery requestCashOnDelivery = new RequestCashOnDelivery();
        requestCashOnDelivery.setLang(appRepository.getLanguageCode());
        requestCashOnDelivery.setCus_name(firstName);
        requestCashOnDelivery.setCus_last_name(lastName);
        requestCashOnDelivery.setCus_email(emailId);
        requestCashOnDelivery.setCus_phone1(mobileNumber);
        requestCashOnDelivery.setCus_phone2(alternateNumber);
        requestCashOnDelivery.setCus_address(postalAddress);
        requestCashOnDelivery.setCus_lat(latitude);
        requestCashOnDelivery.setCus_long(longitude);
        requestCashOnDelivery.setOrd_self_pickup(ordSelfPickup);
        requestCashOnDelivery.setUse_wallet(useWallet);
        requestCashOnDelivery.setWallet_amt(walletAmount);
        requestCashOnDelivery.setCus_address1(landMark);
        requestCashOnDelivery.setUse_coupon(useCoupon);
        requestCashOnDelivery.setCoupon_amount(couponAmount);
        requestCashOnDelivery.setCoupon_id(couponId);
        disposable.add(apiInterface
                .checkOutCOD(requestCashOnDelivery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<List<PaymentResult>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<PaymentResult>> baseResponse) {
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
    public void paymentPayPal(String payPalResponse, String firstName, String lastName, String emailId,
                              String mobileNumber, String alternateNumber, String postalAddress,String landMark, String latitude,
                              String longitude, String ordSelfPickup, String useWallet, String walletAmount,
                              String useCoupon, String couponId, String couponAmount) {
        try {
            JSONObject jsonObject = new JSONObject(payPalResponse);
            JSONObject jsonDetails = jsonObject.getJSONObject(RESPONSE);
            String id = jsonDetails.getString(ID);
            String state = jsonDetails.getString(STATE);
            RequestPayPal requestPayPal = new RequestPayPal();
            requestPayPal.setLang(appRepository.getLanguageCode());
            requestPayPal.setCus_name(firstName);
            requestPayPal.setCus_last_name(lastName);
            requestPayPal.setCus_email(emailId);
            requestPayPal.setCus_phone1(mobileNumber);
            requestPayPal.setCus_phone2(alternateNumber);
            requestPayPal.setCus_address(postalAddress);
            requestPayPal.setCus_lat(latitude);
            requestPayPal.setCus_long(longitude);
            requestPayPal.setOrd_self_pickup(ordSelfPickup);
            requestPayPal.setUse_wallet(useWallet);
            requestPayPal.setWallet_amt(walletAmount);
            requestPayPal.setTransaction_id(id);
            requestPayPal.setCus_address1(landMark);
            requestPayPal.setUse_coupon(useCoupon);
            requestPayPal.setCoupon_amount(couponAmount);
            requestPayPal.setCoupon_id(couponId);
            Log.i(STATE, state);
            disposable.add(apiInterface
                    .checkOutPayPal(requestPayPal)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<BaseResponse<List<PaymentResult>>>() {
                        @Override
                        public void onSuccess(BaseResponse<List<PaymentResult>> baseResponse) {
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
        } catch (JSONException e) {
            mPresenter.apiError(e.getMessage());
        }
    }

    @Override
    public void paymentStripe(String cardNumber, String cardMonth, String cardYear, String cvv, String firstName,
                              String lastName, String emailId, String mobileNumber, String alternateNumber,
                              String postalAddress, String landMark,String latitude, String longitude, String ordSelfPickup,
                              String useWallet, String walletAmount, String useCoupon, String couponId, String couponAmount) {
        RequestStripe requestStripe = new RequestStripe();
        requestStripe.setCard_no(cardNumber);
        requestStripe.setCcExpiryMonth(cardMonth);
        requestStripe.setCcExpiryYear(cardYear);
        requestStripe.setCvvNumber(cvv);
        requestStripe.setLang(appRepository.getLanguageCode());
        requestStripe.setCus_name(firstName);
        requestStripe.setCus_last_name(lastName);
        requestStripe.setCus_email(emailId);
        requestStripe.setCus_phone1(mobileNumber);
        requestStripe.setCus_phone2(alternateNumber);
        requestStripe.setCus_address(postalAddress);
        requestStripe.setCus_lat(latitude);
        requestStripe.setCus_long(longitude);
        requestStripe.setOrd_self_pickup(ordSelfPickup);
        requestStripe.setUse_wallet(useWallet);
        requestStripe.setWallet_amt(walletAmount);
        requestStripe.setCus_address1(landMark);
        requestStripe.setUse_coupon(useCoupon);
        requestStripe.setCoupon_amount(couponAmount);
        requestStripe.setCoupon_id(couponId);
        disposable.add(apiInterface
                .checkOutStripe(requestStripe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<List<PaymentResult>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<PaymentResult>> baseResponse) {
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
    public void paymentWallet(String firstName, String lastName, String emailId, String mobileNumber,
                              String alternateNumber, String postalAddress, String landMark,String latitude, String longitude,
                              String ordSelfPickup, String useWallet, String walletAmount,String useCoupon,String couponId, String couponAmount) {
        RequestCashOnDelivery requestCashOnDelivery = new RequestCashOnDelivery();
        requestCashOnDelivery.setLang(appRepository.getLanguageCode());
        requestCashOnDelivery.setCus_name(firstName);
        requestCashOnDelivery.setCus_last_name(lastName);
        requestCashOnDelivery.setCus_email(emailId);
        requestCashOnDelivery.setCus_phone1(mobileNumber);
        requestCashOnDelivery.setCus_phone2(alternateNumber);
        requestCashOnDelivery.setCus_address(postalAddress);
        requestCashOnDelivery.setCus_lat(latitude);
        requestCashOnDelivery.setCus_long(longitude);
        requestCashOnDelivery.setOrd_self_pickup(ordSelfPickup);
        requestCashOnDelivery.setUse_wallet(useWallet);
        requestCashOnDelivery.setWallet_amt(walletAmount);
        requestCashOnDelivery.setCus_address1(landMark);
        requestCashOnDelivery.setUse_coupon(useCoupon);
        requestCashOnDelivery.setCoupon_amount(couponAmount);
        requestCashOnDelivery.setCoupon_id(couponId);


        disposable.add(apiInterface
                .checkOutWallet(requestCashOnDelivery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<List<PaymentResult>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<PaymentResult>> baseResponse) {
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
    public void useWallet(OfferType offerType, String ordSelfPickup, String useWallet, String walletAmt, String deliveryFee,
                          String useCoupon, String couponId, String couponAmount) {
        UseWallet useWallet1 = new UseWallet();
        useWallet1.setOrd_self_pickup(ordSelfPickup);
        useWallet1.setWallet_amt(walletAmt);
        useWallet1.setUse_wallet(useWallet);
        useWallet1.setDelivery_fee(deliveryFee);
        useWallet1.setUse_coupon(useCoupon);
        useWallet1.setCoupon_id(couponId);
        useWallet1.setCoupon_amount(couponAmount);
        useWallet1.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .useWallet(useWallet1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<UseWalletResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<UseWalletResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onSuccess(offerType, baseResponse.getData(), baseResponse.getMessage());
                        } else {
                            mPresenter.usedWalletError(baseResponse.getMessage());
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
    public void useOffer(OfferType offerType, String ordSelfPickup, String useWallet, String walletAmt, String deliveryFee, String useCoupon, String couponId, String couponAmount) {

        UseWallet useWallet1 = new UseWallet();
        useWallet1.setOrd_self_pickup(ordSelfPickup);
        useWallet1.setWallet_amt(useWallet.equals("0")?"0":walletAmt);
        useWallet1.setUse_wallet(useWallet);
        useWallet1.setDelivery_fee(deliveryFee);
        useWallet1.setUse_coupon(useCoupon);
        useWallet1.setCoupon_id(couponId);
        useWallet1.setCoupon_amount(useCoupon.equals("0")?"0":couponAmount);
        useWallet1.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .useOffer(useWallet1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<UseWalletResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<UseWalletResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onSuccess(offerType, baseResponse.getData(), baseResponse.getMessage());
                        } else {
                            mPresenter.onOfferError(baseResponse.getMessage());
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
