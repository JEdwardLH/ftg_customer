package com.foodtogo.user.ui.mycart.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.mycart.CartResponse;

import java.util.List;


public class MyCartPresenter implements MyCartContractor.Presenter {

    private MyCartContractor.View mView;
    private MyCartModel model;

    public MyCartPresenter(MyCartContractor.View view, AppRepository appRepository) {
        mView = view;
        model = new MyCartModel(this, appRepository);
    }


    @Override
    public void requestCart() {
        mView.showProgressBar();
        model.requestCart();
    }

    @Override
    public void removeCartItem(int cartId) {
        mView.showLoadingView();
        model.removeCartItem(cartId);
    }

    @Override
    public void removeItemChoice(int productId, int cartId, List<Integer> choice) {
        mView.showLoadingView();
        model.removeItemChoice(productId, cartId, choice);
    }

    @Override
    public void updateCartChoice(int productId, int cartId, List<Integer> choices, String specialRequest) {
        mView.showLoadingView();
        model.updateCartChoice(productId, cartId, choices, specialRequest);
    }

    @Override
    public void updatePreOrder(int storeId, String preOrderDate) {
        mView.showLoadingView();
        model.updatePreOrder(storeId, preOrderDate);
    }

    @Override
    public void updateCartQuantity(int cartId, int quantity) {
        mView.showLoadingView();
        model.updateCartQuantity(cartId, quantity);
    }

    @Override
    public void onCartResponse(CartResponse cartResponse) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.showCartResponse(cartResponse);
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

    @Override
    public void removePreOrder(String storeId) {
        mView.showLoadingView();
        model.requestRemovePreOrder(storeId);
    }

}
