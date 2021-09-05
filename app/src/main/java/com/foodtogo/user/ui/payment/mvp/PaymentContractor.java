package com.foodtogo.user.ui.payment.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.payment.CheckQuantityResponse;
import com.foodtogo.user.model.payment.PaymentMethodResponse;
import com.foodtogo.user.model.payment.UseWalletResponse;
import com.foodtogo.user.model.wallet.WalletBalanceResponse;
import com.foodtogo.user.ui.payment.enums.OfferType;

public interface PaymentContractor {

    interface View extends BaseView {

        void showSuccess(String message);

        void showWalletError(String error);

        void onWalletResult(WalletBalanceResponse walletBalanceResponse);

        void paymentMethodResult(int status, CheckQuantityResponse checkQuantityResponse, PaymentMethodResponse paymentMethodResponse);

        void showPaymentMethodResultError(int status, CheckQuantityResponse checkQuantityResponse, String error);

        void onSuccess(OfferType offerType, UseWalletResponse useWalletResponse, String message);

        void showProgressBar();

        void hideProgressBar();

        void onOfferError(String msg);

        void usedWalletError(String msg);

    }

    interface Presenter {

        void requestWalletBalance(String pageNo);

        void onSuccess(String message);

        void onWalletResult(WalletBalanceResponse walletBalanceResponse);

        void paymentMethodResult(int status, CheckQuantityResponse checkQuantityResponse, PaymentMethodResponse paymentMethodResponse);

        void paymentCashOnDelivery(String firstName, String lastName, String emailId, String mobileNumber, String alternateNumber, String postalAddress,
                                   String landMark, String latitude, String longitude, String ordSelfPickup, String useWallet, String walletAmount, String useCoupon, String couponId, String couponAmount);

        void paymentPayPal(String payPalResponse, String firstName, String lastName, String emailId, String mobileNumber, String alternateNumber, String postalAddress,
                           String landMark, String latitude, String longitude, String ordSelfPickup, String useWallet, String walletAmount, String useCoupon, String couponId, String couponAmount);

        void paymentStripe(String cardNumber, String cardMonth, String cardYear, String cvv, String firstName, String lastName, String emailId, String mobileNumber, String alternateNumber, String postalAddress,
                           String landMark, String latitude, String longitude, String ordSelfPickup, String useWallet, String walletAmount, String useCoupon, String couponId, String couponAmount);

        void paymentWallet(String firstName, String lastName, String emailId, String mobileNumber, String alternateNumber, String postalAddress,
                           String landMark,String latitude, String longitude, String ordSelfPickup, String useWallet, String walletAmount, String useCoupon, String couponId, String couponAmount);

        void useWallet(OfferType offerType, String ordSelfPickup, String useWallet, String walletAmt, String deliveryFee, String useCoupon, String couponId, String couponAmount);
        void useOffer(OfferType offerType, String ordSelfPickup, String useWallet, String walletAmt, String deliveryFee, String useCoupon, String couponId, String couponAmount);

        void paymentMethodResultError(int status, CheckQuantityResponse checkQuantityResponse, String error);

        void onRequestPaymentMethod();

        void onSuccess(OfferType offerType, UseWalletResponse useWalletResponse, String message);

        void apiError(String error);

        void onWalletError(String error);

        void apiError(int error);
        void onOfferError(String msg);

        void usedWalletError(String msg);

        void close();
    }

    interface Model {

        void requestWalletBalance(String pageNo);

        void requestPaymentMethod();

        void paymentCashOnDelivery(String firstName, String lastName, String emailId, String mobileNumber, String alternateNumber, String postalAddress,
                                   String landMark,  String latitude, String longitude, String ordSelfPickup, String useWallet, String walletAmount, String useCoupon, String couponId, String couponAmount);

        void paymentPayPal(String payPalResponse, String firstName, String lastName, String emailId, String mobileNumber, String alternateNumber, String postalAddress,
                           String landMark, String latitude, String longitude, String ordSelfPickup, String useWallet, String walletAmount, String useCoupon, String couponId, String couponAmount);

        void paymentStripe(String cardNumber, String cardMonth, String cardYear, String cvv, String firstName, String lastName, String emailId, String mobileNumber, String alternateNumber, String postalAddress,
                           String landMark,String latitude, String longitude, String ordSelfPickup, String useWallet, String walletAmount, String useCoupon, String couponId, String couponAmount);

        void paymentWallet(String firstName, String lastName, String emailId, String mobileNumber, String alternateNumber, String postalAddress,
                           String landMark,  String latitude, String longitude, String ordSelfPickup, String useWallet, String walletAmount, String useCoupon, String couponId, String couponAmount);

        void useWallet(OfferType offerType, String ordSelfPickup, String useWallet, String walletAmt, String deliveryFee, String useCoupon, String couponId, String couponAmount);
        void useOffer(OfferType offerType, String ordSelfPickup, String useWallet, String walletAmt, String deliveryFee, String useCoupon, String couponId, String couponAmount);
        void close();
    }
}
