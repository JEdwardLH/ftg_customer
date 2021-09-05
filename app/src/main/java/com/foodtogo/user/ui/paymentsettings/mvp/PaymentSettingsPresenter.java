package com.foodtogo.user.ui.paymentsettings.mvp;

import android.content.Context;
import android.util.Patterns;

import com.foodtogo.user.R;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.paymentsettings.PaymentSettingResponse;


public class PaymentSettingsPresenter implements PaymentSettingsContractor.Presenter {

    private PaymentSettingsContractor.View mView;
    private PaymentSettingsModel model;
    private Context context;


    public PaymentSettingsPresenter(Context mContext,PaymentSettingsContractor.View view, AppRepository appRepository) {
        mView = view;
        context=mContext;
        model = new PaymentSettingsModel(this, appRepository);
    }


    @Override
    public void requestPaymentSettings() {
        mView.showProgressBar();
        model.requestPaymentSettings();
    }

    @Override
    public void onPaymentSettingResponse(PaymentSettingResponse paymentSettingResponse) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showPaymentSettingResponse(paymentSettingResponse);
    }

    @Override
    public void updatePaymentSetting(boolean isPayPal, boolean isCreditCard, String payPalEmail,
                                     String payPalSecretKey, String accountNumber,
                                     String bankName, String branchName, String IFSCCode) {
        if (isCreditCard && bankName.length() == 0) {
            mView.showError(context.getResources().getString(R.string.enter_bank_name));
        }else if (isCreditCard && accountNumber.length() == 0) {
            mView.showError(context.getResources().getString(R.string.enter_account_number));
        }else if (isCreditCard && branchName.length() == 0) {
            mView.showError(context.getResources().getString(R.string.enter_account_name));
        }  else {
            mView.showLoadingView();
            model.updatePaymentSetting(isPayPal, isCreditCard, payPalEmail, payPalSecretKey,
                    accountNumber, bankName, branchName, IFSCCode);
        }
    }

    @Override
    public void onUpdateSuccess(String message) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showUpdateSuccess(message);
    }


    @Override
    public void apiError(String error) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showError(error);
    }

    @Override
    public void apiError(int error) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showError(error);
    }


    @Override
    public void close() {
        model.close();
    }
}
