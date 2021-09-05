package com.foodtogo.user.ui.myoffers.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.offers.OffersResponse;

public interface OffersContractor {
    interface View extends BaseView {

        void showTotalOfferLoadMore(OffersResponse offerResponse,int pageNo);

        void showLoadMoreError(String error,int pageNo);

        void showProgressBar();

        void hideProgressBar();

    }

    interface Presenter {

        void requestTotalOffers(int pageNo);

        void onLoadMoreTotalOffers(OffersResponse offersResponse,int pageNo);

        void onLoadMoreError(String error,int pageNo);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestTotalOffers(int pageNo);

        void close();
    }
}
