package com.foodtogo.user.ui.referfriend.mvp;

import com.foodtogo.user.BaseView;

public interface ReferFriendContractor {

    interface View extends BaseView {

        void onSuccess(String message);

        void showProgressBar();

        void hideProgressBar();

        void showEmailEmptyError();

        void showNotValidEmailError();

        void PostReferError(String error);

        void PostRefer(String message);

    }

    interface Presenter {

        void requestAppOffer();

        void onSuccess(String message);

        void requestReferFriend(String email);

        void apiError(String error);

        void apiError(int error);

        void PostReferError(String error);

        void PostRefer(String message);

        void close();

    }

    interface Model {

        void requestAppOffer();

        void requestReferFriend(String email);

        void close();
    }
}
