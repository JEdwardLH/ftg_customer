package com.foodtogo.user.ui.home.mvp;

import com.foodtogo.user.BaseView;

public interface HomeActivityContractor {

    interface View extends BaseView {

        void onSuccess(String message);

    }

    interface Presenter {


        void logout();

        void onSuccess(String message);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void logout();

        void close();
    }
}
