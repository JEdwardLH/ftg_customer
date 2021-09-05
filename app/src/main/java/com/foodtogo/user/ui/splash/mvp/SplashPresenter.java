package com.foodtogo.user.ui.splash.mvp;

import android.content.Context;
import android.os.Handler;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.util.AppUtils;

public class SplashPresenter implements SplashContractor.Presenter {

    private SplashContractor.View mSplashView;
    private AppRepository appRepository;

    public SplashPresenter(SplashContractor.View view, AppRepository appRepository1) {
        this.mSplashView = view;
        this.appRepository = appRepository1;
    }


    @Override
    public void startAnimation(Context context) {
        AppUtils.getHashKey(context);
        int TIME_OUT = 3500;
        new Handler().postDelayed(() -> {
            if (appRepository.isLoggedIn()) {
                mSplashView.showDashBoardActivity();
            } else {
                mSplashView.showLoginActivity();
            }
        }, TIME_OUT);
    }




}
