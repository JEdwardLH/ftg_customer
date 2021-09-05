package com.foodtogo.user.ui.track.mvp;


import android.content.Context;

import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.directions.DirectionResponse;
import com.foodtogo.user.model.track.TrackDetailResponse;
import com.foodtogo.user.model.track.TrackDetailsRequest;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class TrackModel implements TrackContractor.Model {


    private TrackPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;
    private Context mContext;

    TrackModel(TrackPresenter presenter, AppRepository appRepository1, Context context) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
        this.mContext=context;
    }


    @Override
    public Disposable requestTrackDetails(String storeId, String orderId) {
        TrackDetailsRequest trackDetailsRequest = new TrackDetailsRequest();
        trackDetailsRequest.setStore_id(storeId);
        trackDetailsRequest.setOrder_id(orderId);
        trackDetailsRequest.setLang(appRepository.getLanguageCode());
        DisposableSingleObserver disposableSingleObserver = apiInterface
                .getTrackDetails(trackDetailsRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<TrackDetailResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<TrackDetailResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onTrackDetailResponse(baseResponse.getData());
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
                });
        disposable.add(disposableSingleObserver);
        return disposableSingleObserver;
    }

    @Override
    public Disposable requestPolylineData(String originLat, String originLng, String restaurantLat, String restaurantLng, String customerLat, String customerLng) {
        String origin = originLat + "," + originLng;
        String destination = customerLat + "," + customerLng;
        String wayPoints = "optimize:true|" + restaurantLat + "," + restaurantLng + "|";

        DisposableSingleObserver disposableSingleObserver = apiInterface
                .getPolylineData(origin, destination, wayPoints, mContext.getString(R.string.google_api_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<DirectionResponse>() {
                    @Override
                    public void onSuccess(DirectionResponse directionResponse) {
                        if (directionResponse.getStatus().equals("OK")) {
                            mPresenter.onDirectionResponse(directionResponse);
                        } else {
                            mPresenter.directionAPIError(directionResponse.getStatus());
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
                });
        disposable.add(disposableSingleObserver);
        return disposableSingleObserver;
    }

    @Override
    public Disposable requestRePolylineData(String originLat, String originLng, String restaurantLat, String restaurantLng, String customerLat, String customerLng) {
        String origin = originLat + "," + originLng;
        String destination = customerLat + "," + customerLng;
        if (restaurantLat.equals("")) {
            DisposableSingleObserver disposableSingleObserver = apiInterface
                    .getPolylineData(origin, destination,  mContext.getString(R.string.google_api_key))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<DirectionResponse>() {
                        @Override
                        public void onSuccess(DirectionResponse directionResponse) {
                            if (directionResponse.getStatus().equals("OK")) {
                                mPresenter.onReDirectionResponse(directionResponse);
                            } else {
                                mPresenter.directionAPIError(directionResponse.getStatus());
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
                    });
            disposable.add(disposableSingleObserver);
            return disposableSingleObserver;
        } else {
            String wayPoints = "optimize:true|" + restaurantLat + "," + restaurantLng + "|";
            DisposableSingleObserver disposableSingleObserver = apiInterface
                    .getPolylineData(origin, destination, wayPoints,  mContext.getString(R.string.google_api_key))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<DirectionResponse>() {
                        @Override
                        public void onSuccess(DirectionResponse directionResponse) {
                            if (directionResponse.getStatus().equals("OK")) {
                                mPresenter.onReDirectionResponse(directionResponse);
                            } else {
                                mPresenter.directionAPIError(directionResponse.getStatus());
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
                    });
            disposable.add(disposableSingleObserver);
            return disposableSingleObserver;
        }


    }


    @Override
    public Disposable repeatCallTrackDetails(String storeId, String orderId, int returnType) {
        TrackDetailsRequest trackDetailsRequest = new TrackDetailsRequest();
        trackDetailsRequest.setStore_id(storeId);
        trackDetailsRequest.setOrder_id(orderId);
        trackDetailsRequest.setLang(appRepository.getLanguageCode());
        DisposableSingleObserver disposableSingleObserver = apiInterface
                .getTrackDetails(trackDetailsRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<TrackDetailResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<TrackDetailResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            if (returnType == 0) {
                                mPresenter.onRepeatTrackDetailResponse(baseResponse.getData());
                            } else {
                                mPresenter.onSendOtpResponse(baseResponse.getData());
                            }
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
                });
        disposable.add(disposableSingleObserver);
        return disposableSingleObserver;

    }


    @Override
    public void close() {
        disposable.dispose();
    }
}
