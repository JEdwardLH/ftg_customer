package com.foodtogo.user.ui.paymentsettings.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.paymentsettings.PaymentSettingResponse;

public interface PaymentSettingsContractor {

    interface View extends BaseView {

        void showPaymentSettingResponse(PaymentSettingResponse paymentSettingResponse);

        void showUpdateSuccess(String message);

        void showProgressBar();

        void hideProgressBar();

    }

    interface Presenter {

        void requestPaymentSettings();

        void onPaymentSettingResponse(PaymentSettingResponse paymentSettingResponse);

        void updatePaymentSetting(boolean isPayPal, boolean isCreditCard,
                                  String payPalClientId, String payPalSecretKey,
                                  String accountNumber, String bankName, String branchName, String IFSCCode);

        void onUpdateSuccess(String message);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestPaymentSettings();

        void updatePaymentSetting(boolean isPayPal, boolean isCreditCard,
                                  String payPalClientId, String payPalSecretKey,
                                  String accountNumber, String bankName, String branchName, String IFSCCode);

        void close();
    }
}
