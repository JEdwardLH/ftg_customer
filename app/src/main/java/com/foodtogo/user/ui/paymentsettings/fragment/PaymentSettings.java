package com.foodtogo.user.ui.paymentsettings.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.model.paymentsettings.PaymentSettingResponse;
import com.foodtogo.user.ui.paymentsettings.mvp.PaymentSettingsPresenter;
import com.foodtogo.user.ui.paymentsettings.mvp.PaymentSettingsContractor;

import butterknife.BindView;
import butterknife.OnClick;

import static com.foodtogo.user.base.AppConstants.INFO;
import static com.foodtogo.user.base.AppConstants.UN_PUBLISH;
import static com.foodtogo.user.base.AppConstants.YES;


public class PaymentSettings extends BaseFragment implements PaymentSettingsContractor.View {


    @BindView(R.id.card_view_net_banking)
    CardView cardViewNetBanking;
    //Net Banking
    @BindView(R.id.et_account_number)
    EditText etAccountNumber;

    @BindView(R.id.et_bank_name)
    EditText etBankName;

    @BindView(R.id.et_branch_name)
    EditText etBranchName;

    @BindView(R.id.btn_update)
    Button btnUpdate;

    String info = "";

    public PaymentSettings() {
        // Required empty public constructor
    }

    private PaymentSettingsPresenter profilePresenter;


    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment_setting, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profilePresenter = new PaymentSettingsPresenter(getActivity(), this,appRepository);
        profilePresenter.requestPaymentSettings();
        Bundle args;
        if ((args = getArguments()) != null) {
            info = args.getString(INFO);
        }
    }

    public static PaymentSettings newInstance(String info) {
        Bundle args = new Bundle();
        PaymentSettings fragment = new PaymentSettings();
        args.putString(INFO, info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showLoadingView() {
        showProgressDialog();
    }

    @Override
    public void hideLoadingView() {
        hideProgressDialog();
    }

    @Override
    public void showProgressBar() {
        showProgress();
    }

    @Override
    public void hideProgressBar() {
        hideProgress();
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @Override
    public void showError(int message) {
        showToast(message);
    }




    @OnClick(R.id.btn_update)
    public void setUpdate() {

        String accountNumber = etAccountNumber.getText().toString();
        String bankName = etBankName.getText().toString();
        String branchName = etBranchName.getText().toString();

        profilePresenter.updatePaymentSetting(false, true, "", "",
                accountNumber, bankName, branchName, "");

    }


    @Override
    public void showPaymentSettingResponse(PaymentSettingResponse paymentSettingResponse) {
        if (!paymentSettingResponse.getNetBankingStatus().equals(UN_PUBLISH)) {
            cardViewNetBanking.setVisibility(View.VISIBLE);
            etAccountNumber.setText(paymentSettingResponse.getNetBankingAccNo());
            etBankName.setText(paymentSettingResponse.getNetBankingBankName());
            etBranchName.setText(paymentSettingResponse.getNetBankingBranch());
        }

    }

    @Override
    public void showUpdateSuccess(String message) {
        if (info.equals(YES)) {
            showSuccessDialog(message, 2);
        } else {
            showToast(message);
        }

    }
}