package com.foodtogo.user.ui.payment.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.payment.CheckQuantityResponse;
import com.foodtogo.user.model.payment.PaymentMethodResponse;
import com.foodtogo.user.model.payment.UseWalletResponse;
import com.foodtogo.user.model.wallet.WalletBalanceResponse;
import com.foodtogo.user.ui.payment.enums.OfferType;


public class PaymentPresenter implements PaymentContractor.Presenter {

    private PaymentContractor.View mView;
    private PaymentModel model;


    public PaymentPresenter(PaymentContractor.View view, AppRepository appRepository) {
        mView = view;
        model = new PaymentModel(this, appRepository);
    }


    @Override
    public void requestWalletBalance(String pageNo) {
        model.requestWalletBalance(pageNo);
    }

    @Override
    public void onSuccess(String message) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.showSuccess(message);
    }

    @Override
    public void onWalletResult(WalletBalanceResponse walletBalanceResponse) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.onWalletResult(walletBalanceResponse);
    }

    @Override
    public void paymentMethodResult(int status, CheckQuantityResponse checkQuantityResponse, PaymentMethodResponse paymentMethodResponse) {
        mView.paymentMethodResult(status, checkQuantityResponse, paymentMethodResponse);
    }

    @Override
    public void paymentCashOnDelivery(String firstName, String lastName, String emailId, String mobileNumber, String alternateNumber, String postalAddress,
                                      String landMark, String latitude, String longitude, String ordSelfPickup, String useWallet, String walletAmount, String useCoupon,
                                      String couponId, String couponAmount) {
        mView.showLoadingView();
        model.paymentCashOnDelivery(firstName, lastName, emailId, mobileNumber, alternateNumber, postalAddress,
                landMark, latitude, longitude, ordSelfPickup, useWallet, walletAmount, useCoupon, couponId, couponAmount);
    }

    @Override
    public void paymentPayPal(String payPalResponse, String firstName, String lastName, String emailId, String mobileNumber,
                              String alternateNumber, String postalAddress, String landMark, String latitude, String longitude,
                              String ordSelfPickup, String useWallet, String walletAmount, String useCoupon,
                              String couponId, String couponAmount) {
        mView.showLoadingView();
        model.paymentPayPal(payPalResponse, firstName, lastName, emailId, mobileNumber, alternateNumber, postalAddress,
                landMark, latitude, longitude, ordSelfPickup, useWallet, walletAmount, useCoupon, couponId, couponAmount);
    }

    @Override
    public void paymentStripe(String cardNumber, String cardMonth, String cardYear, String cvv, String firstName,
                              String lastName, String emailId, String mobileNumber, String alternateNumber,
                              String postalAddress, String landMark, String latitude, String longitude, String ordSelfPickup,
                              String useWallet, String walletAmount, String useCoupon, String couponId, String couponAmount) {
        mView.showLoadingView();
        model.paymentStripe(cardNumber, cardMonth, cardYear, cvv, firstName, lastName, emailId, mobileNumber, alternateNumber, postalAddress,
                landMark, latitude, longitude, ordSelfPickup, useWallet, walletAmount, useCoupon, couponId, couponAmount);
    }

    @Override
    public void paymentWallet(String firstName, String lastName, String emailId, String mobileNumber, String alternateNumber,
                              String postalAddress, String landMark, String latitude, String longitude, String ordSelfPickup,
                              String useWallet, String walletAmount, String useCoupon, String couponId, String couponAmount) {
        mView.showLoadingView();
        model.paymentWallet(firstName, lastName, emailId, mobileNumber, alternateNumber, postalAddress, landMark,
                latitude, longitude, ordSelfPickup, useWallet, walletAmount, useCoupon, couponId, couponAmount);
    }

    @Override
    public void useWallet(OfferType offerType, String ordSelfPickup, String useWallet, String walletAmt, String deliveryFee,
                          String useCoupon, String couponId, String couponAmount) {
        mView.showLoadingView();
        model.useWallet(offerType, ordSelfPickup, useWallet, walletAmt, deliveryFee, useCoupon, couponId, couponAmount);
    }

    @Override
    public void useOffer(OfferType offerType, String ordSelfPickup, String useWallet, String walletAmt, String deliveryFee, String useCoupon, String couponId, String couponAmount) {
        mView.showLoadingView();
        model.useOffer(offerType, ordSelfPickup, useWallet, walletAmt, deliveryFee, useCoupon, couponId, couponAmount);
    }

    @Override
    public void paymentMethodResultError(int status, CheckQuantityResponse checkQuantityResponse, String error) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showPaymentMethodResultError(status, checkQuantityResponse, error);
    }


    @Override
    public void onRequestPaymentMethod() {
        mView.showProgressBar();
        model.requestPaymentMethod();
    }

    @Override
    public void onSuccess(OfferType offerType, UseWalletResponse useWalletResponse, String message) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.onSuccess(offerType, useWalletResponse, message);
    }

    @Override
    public void apiError(String error) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showError(error);
    }

    @Override
    public void onWalletError(String error) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showWalletError(error);
    }

    @Override
    public void apiError(int error) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showError(error);
    }

    @Override
    public void onOfferError(String msg) {
        mView.hideLoadingView();
       mView.onOfferError(msg);
    }

    @Override
    public void usedWalletError(String msg) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.usedWalletError(msg);
    }


    @Override
    public void close() {
        model.close();
    }
}
