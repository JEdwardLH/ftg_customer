package com.foodtogo.user.ui.wishlist.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.wishlist.FavouriteResponse;


public class WishListPresenter implements WishListContractor.Presenter {

    private WishListContractor.View mView;
    private WishListModel model;


    public WishListPresenter(WishListContractor.View view, AppRepository appRepository) {
        mView = view;
        model = new WishListModel(this, appRepository);
    }


    @Override
    public void requestFavourites(int pageNo) {
        if (pageNo == 1)
            mView.showProgressBar();
        model.requestFavourites(pageNo);
    }

    @Override
    public void onWishListResult(FavouriteResponse favouriteResponse) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.showWishList(favouriteResponse);
    }

    @Override
    public void onLoadMore(FavouriteResponse favouriteResponse) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.showLoadMore(favouriteResponse);
    }

    @Override
    public void onLoadMoreError(String error) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.showLoadMoreError(error);
    }

    @Override
    public void removeFavourites(int position, String itemId) {
        mView.hideProgressBar();
        mView.showLoadingView();
        model.removeFavourites(position, itemId);
    }

    @Override
    public void onFavouriteResult(int position, String message) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.showFavouriteResult(position, message);
    }

    @Override
    public void apiError(String error) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void apiError(int error) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.showError(error);
    }


    @Override
    public void close() {
        model.close();
    }
}
