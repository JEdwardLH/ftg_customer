package com.foodtogo.user.ui.viewitemdetails.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.itemdetails.ResponseItemDetails;
import com.foodtogo.user.model.restaurant.ResponseAddCart;

import java.util.ArrayList;
import java.util.List;


public class ViewItemDetailsPresenter implements ViewItemDetailsContractor.Presenter {

    private ViewItemDetailsContractor.View mView;
    private ViewItemDetailsModel model;

    public ViewItemDetailsPresenter(ViewItemDetailsContractor.View view, AppRepository appRepository) {
        mView = view;
        model = new ViewItemDetailsModel(this, appRepository);
    }


    @Override
    public void requestItemDetail(int itemId, int reviewPageNo) {
        mView.showProgressBar();
        model.requestItemDetail(itemId, reviewPageNo);
    }

    @Override
    public void onSuccess(ResponseItemDetails responseItemDetails) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showItemDetails(responseItemDetails);
    }

    @Override
    public void onSuccess(ResponseAddCart responseAddCart, String message) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showAddCartResponse(responseAddCart, message);
    }

    @Override
    public void addFavourites(int position, String itemId) {
        mView.showLoadingView();
        mView.hideProgressBar();
        model.addFavourites(position, itemId);
    }

    @Override
    public void onFavouriteResult(int position, String message) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.onFavouriteResult(position, message);
    }

    @Override
    public void requestAddCart(int storeId, int itemId, String quantity, List<Integer> choiceItem, String specialNotes) {
        mView.showLoadingView();
        model.requestAddCart(storeId, itemId, quantity, choiceItem, specialNotes);
    }

    @Override
    public void updateCartQuantity(int cartId, int quantity) {
        mView.showLoadingView();
        model.updateCartQuantity(cartId, quantity);
    }

    @Override
    public void requestToppingsApi(String storeId, String itemId, ArrayList<Integer> choiceId) {
        mView.showLoadingView();
        model.requestToppings(storeId,itemId,choiceId);
    }

    @Override
    public void updateToppings(String quantity) {
      mView.hideLoadingView();
      mView.updateToppings(quantity);
    }

    @Override
    public void apiError(String error) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showError(error);
    }

    @Override
    public void addCartError(String error) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.addCartError(error);
    }

    @Override
    public void apiError(int error) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showError(error);
    }


}

