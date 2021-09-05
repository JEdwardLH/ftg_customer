package com.foodtogo.user.ui.myrewards.mvp;


import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.wallet.RewardRequest;
import com.foodtogo.user.model.wallet.RewardsResponse;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import static com.foodtogo.user.ui.myrewards.fragment.MyRewardsFragment.EARNED;

public class TotalRewardsModel implements TotalRewardsContractor.Model {
    private TotalRewardsPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    TotalRewardsModel(TotalRewardsPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }

    @Override
    public void requestTotalRewards(int pageNo,String from) {
        RewardRequest rewardRequest=new RewardRequest();
        rewardRequest.setLang(appRepository.getLanguageCode());
        if(from.equals(EARNED)){
            rewardRequest.setPage_no(String.valueOf(pageNo));
            rewardRequest.setRewarded_page_no("1");
        }else{
            rewardRequest.setRewarded_page_no(String.valueOf(pageNo));
            rewardRequest.setPage_no("1");
        }
        disposable.add(apiInterface
                .getLoyalityHistory(rewardRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<RewardsResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<RewardsResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onLoadMoreTotalRewards(baseResponse.getData(),from);
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
    public void close() {
        disposable.dispose();
    }
}
