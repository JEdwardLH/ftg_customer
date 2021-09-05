package com.foodtogo.user.ui.myrewards.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.wallet.RewardsResponse;

public interface RewardsContractor {
    interface View extends BaseView {

        void showTotalRewards(RewardsResponse rewardsResponse);

    }

    interface Presenter {

        void requestTotalRewards();

        void onTotalRewards(RewardsResponse rewardsResponse);


        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestTotalRewards();

        void close();
    }
}
