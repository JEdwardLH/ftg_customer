package com.foodtogo.user.ui.invoice.mvp;


import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.invoice.InvoiceDetailsResponse;
import com.foodtogo.user.model.invoice.RequestInvoice;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class InvoiceModel implements InvoiceContractor.Model {


    private InvoicePresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    InvoiceModel(InvoicePresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void requestInvoiceDetails(String orderId) {
        RequestInvoice requestInvoice = new RequestInvoice();
        requestInvoice.setOrder_id(orderId);
        requestInvoice.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .invoiceDetails(requestInvoice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<InvoiceDetailsResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<InvoiceDetailsResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.responseInvoiceDetails(baseResponse.getData());
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
