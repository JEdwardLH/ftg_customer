package com.foodtogo.user.ui.address.mvp;


import android.content.Context;
import android.util.Log;

import com.foodtogo.user.BaseApplication;
import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.accountdetails.User;
import com.foodtogo.user.model.geocodeaddress.GeoCodeAddress;
import com.foodtogo.user.model.landing.LanguageRequest;
import com.foodtogo.user.model.shippingaddress.AddressRequest;
import com.foodtogo.user.model.shippingaddress.ShippingAddressData;
import com.foodtogo.user.util.NetworkUtils;
import com.foodtogo.user.util.PreferenceUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import static com.foodtogo.user.base.AppConstants.LAND_MARK;

public class AddAddressModel implements AddAddressContractor.Model {

    private AddAddressPresenter mPresenter;
    private AppRepository appRepository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiInterface apiInterface;
    private Context context;

    AddAddressModel(AddAddressPresenter presenter, AppRepository appRepository1,Context mContext) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
        this.context=mContext;
    }


    @Override
    public void requestAddress(String latitude, String longitude) {
        String location = latitude + "," + longitude;
        disposable.add(apiInterface
                .getAddress(location,context.getString(R.string.google_api_key) )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<GeoCodeAddress>() {
                    @Override
                    public void onSuccess(GeoCodeAddress geoCodeAddress) {
                        Log.d("dsadadsad","SUCCESS");
                        if (geoCodeAddress.getStatus().equals("OK")) {
                            mPresenter.onGeoCodeAddress(geoCodeAddress);
                        } else {
                            mPresenter.onGeoCodeAddressError(geoCodeAddress.getStatus());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("dsadadsad",e.getMessage());
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
                            requestSaveShippingAddress(baseResponse.getData());
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
    public void requestSaveShippingAddress(User user) {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setLang(appRepository.getLanguageCode());
        addressRequest.setLocation(appRepository.getSearchLocation());
        addressRequest.setSearch_latitude(appRepository.getLatitude());
        addressRequest.setSearch_longitude(appRepository.getLongitude());
        addressRequest.setZipcode(appRepository.getSearchPinCode());
        addressRequest.setAddress(PreferenceUtils.readString(BaseApplication.getContext(),LAND_MARK,""));
        disposable.add(apiInterface
                .saveShippingAddress(addressRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onSuccess(user);
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
    public void requestMultiLocation(String lat) {
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


    @Override
    public void close() {
        disposable.dispose();
    }
}
