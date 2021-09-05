package com.foodtogo.user.ui.viewallreviews.mvp;


import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.itemdetails.RequestItemDetails;
import com.foodtogo.user.model.itemdetails.ResponseItemDetails;
import com.foodtogo.user.model.restaurant.RequestRestaurantDetail;
import com.foodtogo.user.model.restaurant.RestaurantDetailResponse;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class AllReviewModel implements AllReviewContractor.Model {


    private AllReviewPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;


    AllReviewModel(AllReviewPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void requestRestaurantDetail(int restaurantId, int reviewPageNo) {
        RequestRestaurantDetail restaurantDetail = new RequestRestaurantDetail();
        restaurantDetail.setLang(appRepository.getLanguageCode());
        restaurantDetail.setRestaurant_id(String.valueOf(restaurantId));
        restaurantDetail.setReview_page_no(String.valueOf(reviewPageNo));
        disposable.add(apiInterface
                .requestRestaurantDetails(restaurantDetail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<RestaurantDetailResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<RestaurantDetailResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onLoadMore(baseResponse.getData());
                        } else {
                            mPresenter.onLoadMoreError(baseResponse.getMessage());
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
    public void requestItemDetail(int itemId, int reviewPageNo) {
        RequestItemDetails requestItemDetails = new RequestItemDetails();
        requestItemDetails.setLang(appRepository.getLanguageCode());
        requestItemDetails.setItem_id(String.valueOf(itemId));
        requestItemDetails.setReview_page_no(String.valueOf(reviewPageNo));
        disposable.add(apiInterface
                .requestItemDetails(requestItemDetails)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<ResponseItemDetails>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseItemDetails> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onLoadMore(baseResponse.getData());
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
