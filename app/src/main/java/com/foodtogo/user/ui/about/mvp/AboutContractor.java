package com.foodtogo.user.ui.about.mvp;

import android.content.Context;

import com.foodtogo.user.BaseView;

public interface AboutContractor {

    interface View extends BaseView {

        void showVersion(String version);

    }

    interface Presenter {

        void requestAppVersion(Context context);

        void responseVersion(String version);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestAppVersion(Context context);

        void close();
    }
}
