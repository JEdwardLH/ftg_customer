package com.foodtogo.user.ui.splash.mvp;

import android.content.Context;

public interface SplashContractor {


    interface View {

        void showLoginActivity();

        void showDashBoardActivity();
    }

    interface Presenter {

        void startAnimation(Context context);

    }

}
