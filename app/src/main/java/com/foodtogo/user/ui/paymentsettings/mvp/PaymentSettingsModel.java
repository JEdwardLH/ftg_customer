package com.foodtogo.user.ui.paymentsettings.mvp;


import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.landing.LanguageRequest;
import com.foodtogo.user.model.paymentsettings.PaymentSettingResponse;
import com.foodtogo.user.model.paymentsettings.PaymentSettingUpdateRequest;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import static com.foodtogo.user.base.AppConstants.PUBLISH;
import static com.foodtogo.user.base.AppConstants.UN_PUBLISH;

public class PaymentSettingsModel implements PaymentSettingsContractor.Model {


    private PaymentSettingsPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;
    private static MediaType mediaType = MediaType.parse("text/plain");
    private static final String CUS_IMAGE = "cus_image";

    PaymentSettingsModel(PaymentSettingsPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void requestPaymentSettings() {
        LanguageRequest languageRequest = new LanguageRequest();
        languageRequest.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .getPaymentSettings(languageRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<PaymentSettingResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<PaymentSettingResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onPaymentSettingResponse(baseResponse.getData());
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
    public void updatePaymentSetting(boolean isPayPal, boolean isCreditCard, String payPalClientId,
                                     String payPalSecretKey, String accountNumber,
                                     String bankName, String branchName, String IFSCCode) {

        PaymentSettingUpdateRequest paymentSettingUpdateRequest = new PaymentSettingUpdateRequest();
        paymentSettingUpdateRequest.setPaypal_status(isPayPal ? PUBLISH : UN_PUBLISH);
        paymentSettingUpdateRequest.setPaypal_clientId(isPayPal ? payPalClientId : "");
        paymentSettingUpdateRequest.setPaypal_secretId(isPayPal ? payPalSecretKey : "");
        paymentSettingUpdateRequest.setNetBanking_status(isCreditCard ? PUBLISH : UN_PUBLISH);
        paymentSettingUpdateRequest.setNetBanking_accNo(isCreditCard ? accountNumber : "");
        paymentSettingUpdateRequest.setNetBanking_bankName(isCreditCard ? bankName : "");
        paymentSettingUpdateRequest.setNetBanking_branch(isCreditCard ? branchName : "");
        paymentSettingUpdateRequest.setNetBanking_ifsc(isCreditCard ? IFSCCode : "");
        paymentSettingUpdateRequest.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .updatePaymentSettings(paymentSettingUpdateRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<PaymentSettingResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<PaymentSettingResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onUpdateSuccess(baseResponse.getMessage());
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
