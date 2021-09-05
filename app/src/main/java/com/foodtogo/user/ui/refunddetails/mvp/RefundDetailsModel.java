package com.foodtogo.user.ui.refunddetails.mvp;


import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.orderdetails.OrderDetailRequest;
import com.foodtogo.user.model.refunddetails.RefundDetailResponse;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import static com.foodtogo.user.base.AppConstants.PENDING;

public class RefundDetailsModel implements RefundDetailsContractor.Model {


    private RefundDetailsPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    RefundDetailsModel(RefundDetailsPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void requestRefundDetail(String orderId) {
        OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setOrder_id(orderId);
        orderDetailRequest.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .refundDetails(orderDetailRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<List<RefundDetailResponse>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<RefundDetailResponse>> baseResponse) {
                        ArrayList<RefundDetailResponse> pendingRefund = new ArrayList<>();
                        ArrayList<RefundDetailResponse> completedRefund = new ArrayList<>();
                        if (baseResponse.getCode() == 200) {
                            List<RefundDetailResponse> refundDetailResponses = baseResponse.getData();
                            for (int index = 0; index < refundDetailResponses.size(); index++) {
                                RefundDetailResponse refundDetailResponse = refundDetailResponses.get(index);
                                if (refundDetailResponse.getRefundStatus().equals(PENDING)) {
                                    pendingRefund.add(refundDetailResponse);
                                } else {
                                    completedRefund.add(refundDetailResponse);
                                }
                            }
                            mPresenter.onRefundDetailResponse(pendingRefund,completedRefund);
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
