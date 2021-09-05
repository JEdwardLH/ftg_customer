package com.foodtogo.user.ui.orderhistory.mvp;


import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.ErrorResponse;
import com.foodtogo.user.model.orderhistory.OrderHistoryRequest;
import com.foodtogo.user.model.orderhistory.OrderHistoryResponse;
import com.foodtogo.user.model.orderhistory.RepeatOrderRequest;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class OrderHistoryModel implements OrderHistoryContractor.Model {


    private OrderHistoryPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;


    OrderHistoryModel(OrderHistoryPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void requestOrderList(int pageNo) {
        OrderHistoryRequest orderHistoryRequest = new OrderHistoryRequest();
        orderHistoryRequest.setLang(appRepository.getLanguageCode());
        orderHistoryRequest.setPage_no(String.valueOf(pageNo));
        disposable.add(apiInterface
                .getOrders(orderHistoryRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<OrderHistoryResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderHistoryResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            if (pageNo == 1) {
                                mPresenter.onOrderList(baseResponse.getData());
                            } else {
                                mPresenter.onLoadMore(baseResponse.getData());
                            }
                        } else {
                            if (pageNo == 1) {
                                mPresenter.apiError(baseResponse.getMessage());
                            } else {
                                mPresenter.onLoadMoreError(baseResponse.getMessage());
                            }

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
    public void requestRepeatOrder(String orderId) {
        RepeatOrderRequest repeatOrderRequest = new RepeatOrderRequest();
        repeatOrderRequest.setLang(appRepository.getLanguageCode());
        repeatOrderRequest.setOrder_id(orderId);
        disposable.add(apiInterface
                .repeatOrder(repeatOrderRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<ErrorResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<ErrorResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            ErrorResponse response = baseResponse.getData();
                            if (response != null && response.getErrors() != null) {
                                if (response.getErrors().size() > 0) {
                                    mPresenter.apiError(response.getErrors().get(0));
                                } else {
                                    mPresenter.onRepeatOrder(baseResponse.getMessage());
                                }
                            } else {
                                mPresenter.onRepeatOrder(baseResponse.getMessage());
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
                }));
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
