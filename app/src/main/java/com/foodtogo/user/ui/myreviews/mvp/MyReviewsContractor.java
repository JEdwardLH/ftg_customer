package com.foodtogo.user.ui.myreviews.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.myreviews.MyReviewResponse;

public interface MyReviewsContractor {

    interface View extends BaseView {

        void showMyReviews(MyReviewResponse myReviewResponse);

        void showLoadMore(MyReviewResponse myReviewResponse);

        void showLoadMoreError(String response);

    }

    interface Presenter {

        void requestMyReviews(int pageNo);

        void onMyReviews(MyReviewResponse myReviewResponse);

        void onLoadMore(MyReviewResponse myReviewResponse);

        void onLoadMoreError(String response);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void close();

        void requestMyReviews(int pageNo);
    }
}
