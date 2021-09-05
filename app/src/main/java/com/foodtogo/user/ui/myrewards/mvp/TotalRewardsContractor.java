package com.foodtogo.user.ui.myrewards.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.wallet.RewardsResponse;

public interface TotalRewardsContractor {

    interface View extends BaseView {

        void showTotalRewardsLoadMore(RewardsResponse rewardsResponse,String from);

        void showLoadMoreError(String error);

        void showProgressBar();

        void hideProgressBar();

    }

    interface Presenter {

        void requestTotalRewards(int pageNo,String from);

        void onLoadMoreTotalRewards(RewardsResponse rewardsResponse,String from);

        void onLoadMoreError(String error);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestTotalRewards(int pageNo,String from);

        void close();
    }
}
