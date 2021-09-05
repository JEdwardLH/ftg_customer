package com.foodtogo.user.ui.wishlist.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.wishlist.FavouriteResponse;

public interface WishListContractor {

    interface View extends BaseView {

        void showWishList(FavouriteResponse favouriteResponse);

        void showLoadMore(FavouriteResponse favouriteResponse);

        void showLoadMoreError(String error);

        void showFavouriteResult(int position, String message);

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter {

        void requestFavourites(int pageNo);

        void onWishListResult(FavouriteResponse favouriteResponse);

        void onLoadMore(FavouriteResponse favouriteResponse);

        void onLoadMoreError(String error);

        void removeFavourites(int position, String itemId);

        void onFavouriteResult(int position, String message);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void removeFavourites(int position, String itemId);

        void requestFavourites(int pageNo);

        void close();
    }
}
