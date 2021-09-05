package com.foodtogo.user.ui.myoffers.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.offers.OffersResponse;

public class OffersPresenter implements OffersContractor.Presenter {
    private OffersContractor.View view;
    private OffersModel model;

    public OffersPresenter(OffersContractor.View view, AppRepository appRepository) {
        this.view = view;
        this.model=new OffersModel(this,appRepository);
    }

    @Override
    public void requestTotalOffers(int pageNo) {
       model.requestTotalOffers(pageNo);
    }

    @Override
    public void onLoadMoreTotalOffers(OffersResponse offersResponse,int pageNo) {
        view.hideLoadingView();
        view.hideProgressBar();
        view.showTotalOfferLoadMore(offersResponse,pageNo);
    }

    @Override
    public void onLoadMoreError(String error,int pageNo) {
        view.hideProgressBar();
        view.hideLoadingView();
        view.showLoadMoreError(error,pageNo);
    }

    @Override
    public void apiError(String error) {
        view.hideProgressBar();
        view.hideLoadingView();
        view.showError(error);
    }

    @Override
    public void apiError(int error) {
        view.hideProgressBar();
        view.hideLoadingView();
        view.showError(error);
    }

    @Override
    public void close() {
       model.close();
    }
}
