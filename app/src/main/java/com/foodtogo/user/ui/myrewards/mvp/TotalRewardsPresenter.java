package com.foodtogo.user.ui.myrewards.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.wallet.RewardsResponse;


public class TotalRewardsPresenter implements TotalRewardsContractor.Presenter {

    private TotalRewardsContractor.View mView;
    private TotalRewardsModel model;


    public TotalRewardsPresenter(TotalRewardsContractor.View view, AppRepository appRepository) {
        mView = view;
        model = new TotalRewardsModel(this, appRepository);
    }

    @Override
    public void requestTotalRewards(int pageNo,String from) {
        model.requestTotalRewards(pageNo,from);
    }

    @Override
    public void onLoadMoreTotalRewards(RewardsResponse rewardsResponse,String from) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showTotalRewardsLoadMore(rewardsResponse,from);
    }

    @Override
    public void onLoadMoreError(String error) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.showLoadMoreError(error);
    }

    @Override
    public void apiError(String error) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void apiError(int error) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void close() {
        model.close();
    }
}
