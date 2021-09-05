package com.foodtogo.user.ui.myreviews.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.myreviews.MyReviewResponse;


public class MyReviewsPresenter implements MyReviewsContractor.Presenter {

    private MyReviewsContractor.View mView;
    private MyReviewsModel model;


    public MyReviewsPresenter(MyReviewsContractor.View view, AppRepository appRepository) {
        mView = view;
        model = new MyReviewsModel(this, appRepository);
    }


    @Override
    public void requestMyReviews(int pageNo) {
        if (pageNo == 1)
            mView.showLoadingView();
        model.requestMyReviews(pageNo);
    }

    @Override
    public void onMyReviews(MyReviewResponse myReviewResponse) {
        mView.hideLoadingView();
        mView.showMyReviews(myReviewResponse);
    }

    @Override
    public void onLoadMore(MyReviewResponse myReviewResponse) {
        mView.hideLoadingView();
        mView.showLoadMore(myReviewResponse);
    }

    @Override
    public void onLoadMoreError(String response) {
        mView.hideLoadingView();
        mView.showLoadMoreError(response);
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
