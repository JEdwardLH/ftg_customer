package com.foodtogo.user.ui.manageaddress.mvp;

import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.landing.LanguageRequest;
import com.foodtogo.user.model.shippingaddress.DeleteRequest;
import com.foodtogo.user.model.shippingaddress.MultiAddressRequest;
import com.foodtogo.user.model.shippingaddress.ShippingAddressData;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ManageAddressModel implements ManageAddressContractor.Model{

    private ManageAddressPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiInterface apiInterface;

    public ManageAddressModel(ManageAddressPresenter presenter) {
        this.mPresenter=presenter;
        this.apiInterface= ApiClient.getApiInterface();
    }

    @Override
    public void requestDeleteAddress(String lang, String id) {
        DeleteRequest deleteRequest=new DeleteRequest(lang,id);
        disposable.add(apiInterface
                .deleteAddress(deleteRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.apiSuccess(baseResponse.getMessage());
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
    public void requestToAddOrEditAddress(MultiAddressRequest addressRequest) {
        disposable.add(apiInterface
                .addAddress(addressRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.apiSuccess(baseResponse.getMessage());
                        } else {
                            mPresenter.addEditError(baseResponse.getMessage());
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
    public void requestMultiLocation(String lan) {
        LanguageRequest languageRequest = new LanguageRequest();
        languageRequest.setLang(lan);
        disposable.add(apiInterface
                .getShippingAddress(languageRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<ShippingAddressData>>() {
                    @Override
                    public void onSuccess(BaseResponse<ShippingAddressData> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onSuccessMultiLocation(baseResponse.getData().getMultiLocation());
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
