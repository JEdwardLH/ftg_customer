package com.foodtogo.user.ui.orders.mvp;


import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.orderdetails.OrderDetailResponse;
import com.foodtogo.user.model.reviews.PostItemReview;
import com.foodtogo.user.model.reviews.PostOrderReview;
import com.foodtogo.user.model.reviews.PostStoreReview;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class FullFilledOrderModel implements FullFilledOrderContractor.Model {


    private FullFilledOrderPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    FullFilledOrderModel(FullFilledOrderPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void postStoreReview(String storeId, String rating, String comment) {
        PostStoreReview postStoreReview = new PostStoreReview();
        postStoreReview.setStore_id(storeId);
        postStoreReview.setReview_rating(rating);
        postStoreReview.setReview_comments(comment);
        postStoreReview.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .writeStoreReview(postStoreReview)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<OrderDetailResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderDetailResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.postReviewSuccess(baseResponse.getMessage());
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
    public void postOrderReview(String orderId, String storeId, String deliveryId, String rating, String comment) {
        PostOrderReview postOrderReview = new PostOrderReview();
        postOrderReview.setOrder_id(orderId);
        postOrderReview.setStore_id(storeId);
        postOrderReview.setDeliver_id(deliveryId);
        postOrderReview.setReview_rating(rating);
        postOrderReview.setReview_comments(comment);
        postOrderReview.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .writeOrderReview(postOrderReview)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<OrderDetailResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderDetailResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.postReviewSuccess(baseResponse.getMessage());
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
    public void postItemReview(String productId, String rating, String comment) {
        PostItemReview postItemReview = new PostItemReview();
        postItemReview.setProduct_id(productId);
        postItemReview.setReview_rating(rating);
        postItemReview.setReview_comments(comment);
        postItemReview.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .writeItemReview(postItemReview)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<OrderDetailResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderDetailResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.postReviewSuccess(baseResponse.getMessage());
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
