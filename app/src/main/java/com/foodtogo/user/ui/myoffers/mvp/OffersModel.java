package com.foodtogo.user.ui.myoffers.mvp;

import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.myreviews.MyReviewRequest;
import com.foodtogo.user.model.offers.OffersResponse;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class OffersModel implements OffersContractor.Model {
    private OffersPresenter presenter;
    private AppRepository appRepository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiInterface apiInterface;

    public OffersModel(OffersPresenter offersPresenter, AppRepository mAppRepository) {
        this.presenter=offersPresenter;
        this.appRepository=mAppRepository;
        this.apiInterface= ApiClient.getApiInterface();

    }

    @Override
    public void requestTotalOffers(int pageNo) {
        MyReviewRequest offerRequest=new MyReviewRequest();
        offerRequest.setLang(appRepository.getLanguageCode());
        offerRequest.setPage_no(String.valueOf(pageNo));

        disposable.add(apiInterface
                .offersList(offerRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<OffersResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<OffersResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            presenter.onLoadMoreTotalOffers(baseResponse.getData(),pageNo);
                        } else {
                            presenter.onLoadMoreError(baseResponse.getMessage(),pageNo);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            presenter.apiError(NetworkUtils.getErrorMessage(responseBody));
                        } else if (e instanceof SocketTimeoutException) {
                            presenter.apiError(R.string.time_out_error);
                        } else if (e instanceof IOException) {
                            presenter.apiError(R.string.network_error);
                        } else {
                            presenter.apiError(e.getMessage());
                        }

                    }
                }));
    }

    @Override
    public void close() {
      disposable.dispose();
    }
}
