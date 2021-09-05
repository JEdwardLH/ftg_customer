package com.foodtogo.user.ui.myrewards.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.wallet.RewardsResponse;

public class RewardsPresenter implements RewardsContractor.Presenter {
    private RewardsContractor.View mView;
     RewardsModel model;

    public RewardsPresenter(RewardsContractor.View mView, AppRepository appRepository) {
        this.mView = mView;
        this.model=new RewardsModel(this,appRepository);
    }

    @Override
    public void requestTotalRewards() {
       mView.showLoadingView();
       model.requestTotalRewards();
    }

    @Override
    public void onTotalRewards(RewardsResponse rewardsResponse) {
     mView.hideLoadingView();
     mView.showTotalRewards(rewardsResponse);
    }


    @Override
    public void apiError(String error) {
         mView.hideLoadingView();
         mView.showError(error);
    }

    @Override
    public void apiError(int error) {
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void close() {
      model.close();
    }
}
