package com.foodtogo.user.ui.payment.activity;

import android.content.Intent;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.AppConstants;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.model.payment.CheckQuantityResponse;
import com.foodtogo.user.model.payment.CouponList;
import com.foodtogo.user.model.payment.PaymentMethodResponse;

import com.foodtogo.user.model.payment.UseWalletResponse;
import com.foodtogo.user.model.wallet.WalletBalanceResponse;
import com.foodtogo.user.ui.common.Common;
import com.foodtogo.user.ui.payment.enums.OfferType;
import com.foodtogo.user.ui.payment.fragment.CouponFragment;
import com.foodtogo.user.ui.payment.interfaces.OnApplyListener;
import com.foodtogo.user.ui.payment.mvp.PaymentContractor;
import com.foodtogo.user.ui.payment.mvp.PaymentPresenter;
import com.foodtogo.user.ui.payment.paypal.PayPalUtil;



import java.util.ArrayList;

import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;



public class Payment extends BaseActivity implements PaymentContractor.View,
        AppConstants,OnApplyListener, CompoundButton.OnCheckedChangeListener {

    PaymentPresenter paymentPresenter;

    @BindView(R.id.nsv_data_view)
    NestedScrollView nsvDataView;

    @BindView(R.id.tv_error)
    TextView tvError;


    @BindView(R.id.cb_terms_and_condition)
    CheckBox cbTermsAndCondition;

    @BindView(R.id.ll_wallet_view)
    LinearLayout llWalletView;

    @BindView(R.id.ll_choose_to_pay)
    LinearLayout llChooseToPay;


    @BindView(R.id.rl_use_wallet)
    RelativeLayout rlUseWallet;

    @BindView(R.id.rl_use_offer)
    RelativeLayout rlUseOffer;

    @BindView(R.id.cb_wallet_balance)
    CheckBox cbWalletBalance;

    @BindView(R.id.tv_sub_total)
    TextView tvSubTotal;

    @BindView(R.id.tv_tax_total)
    TextView tvTaxTotal;

    @BindView(R.id.tv_use_wallet)
    TextView tvUseWallet;

    @BindView(R.id.tv_use_offer)
    TextView tvUseOffer;

    @BindView(R.id.tv_delivery_fee)
    TextView tvDeliveryFee;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    @BindView(R.id.btn_pay)
    Button btnPay;


    @BindView(R.id.ll_coupon_view)
    LinearLayout llCouponView;

    @BindView(R.id.ivPeakHours)
    ImageView ivPeakHours;

    ArrayList<CouponList> couponListArrayList;

    String subfullAmount = "";
    String fullAmount = "";
    String totalAmount = "";
    String totalAmountUSD = "";
    boolean isPeakHour = false;
    String paypalAmount = "";
    String deliveryFee = "";
    String peekHourInfo = "";
    String extraFee = "";
    String currencySymbol = "";
    String subTotal = "";
    String taxTotal = "";
    String firstName = "";
    String lastName = "";
    String emailId = "";
    String mobileNumber = "";
    String alternateNumber = "";
    String landMark = "";

    String postalAddress = "";
    String latitude = "";
    String longitude = "";
    String pinCode = "";
    String ordSelfPickup = "";
    String useWallet = "0";
    String walletAmount = "0";
    String useCoupon = "0";
    String couponId = "";
    String couponAmount = "";
    String fullyUsedWallet;
    @BindView(R.id.rb_cash_on_delivery)
    RadioButton rbCashOnDelivery;

    UseWalletResponse useWalletResponse;

    private List<String> quantityError = new ArrayList<>();
    private String paymentError = null;
    private String paymentStatus = "";
    public final static int REQUEST_PAYMENT = 1003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paymentPresenter = new PaymentPresenter(this, appRepository);
        paymentPresenter.onRequestPaymentMethod();
        nsvDataView.setVisibility(View.GONE);
        llWalletView.setVisibility(View.GONE);
        cbWalletBalance.setOnCheckedChangeListener(this);
        setupToolBar();
        tvTitle.setText(getResources().getString(R.string.payment));
        getPriceDetails();
        PayPalUtil.startPayPalService(this);


    }

    private void getPriceDetails() {
        Bundle bundle = getIntent().getExtras();
        try {
            if (bundle != null) {
                totalAmountUSD = bundle.getString(TOTAL_AMOUNT_USD);
                paypalAmount = bundle.getString(TOTAL_AMOUNT_USD);
                totalAmount = bundle.getString(TOTAL_AMOUNT);
                fullAmount = bundle.getString(TOTAL_AMOUNT);
                subfullAmount = bundle.getString(SUB_TOTAL);
                deliveryFee = bundle.getString(DELIVERY_FEE);
                extraFee = bundle.getString(EXTRA_CHARGE);
                peekHourInfo = bundle.getString(PEEK_INFO);
                isPeakHour = bundle.getBoolean(IS_PEAK_HOUR);
                currencySymbol = bundle.getString(CURRENCY_SYMBOL);
                subTotal = bundle.getString(SUB_TOTAL);
                taxTotal = bundle.getString(TOTAL_TAX);
                firstName = bundle.getString(FIRST_NAME);
                lastName = bundle.getString(LAST_NAME);
                emailId = bundle.getString(EMAIL_ID);
                mobileNumber = bundle.getString(MOBILE_NUMBER);
                alternateNumber = bundle.getString(ALTERNATE_NUMBER);
                landMark = bundle.getString(LAND_MARK);
                postalAddress = bundle.getString(POSTAL_ADDRESS);
                latitude = bundle.getString(LATITUDE);
                longitude = bundle.getString(LONGITUDE);
                pinCode = bundle.getString(PINCODE);
                ordSelfPickup = bundle.getString(ORD_SELF_PICKUP);
                String subTotalDetails = currencySymbol + SPACE + subTotal;
                String deliveryDetails = currencySymbol + SPACE + deliveryFee;
                String totalDetails = currencySymbol + SPACE + totalAmount;

                ivPeakHours.setVisibility(isPeakHour ? View.VISIBLE : View.GONE);
                tvTaxTotal.setText(String.format("%s%s%s", currencySymbol, SPACE, taxTotal));
                if (ordSelfPickup.equals("1")) {
                    tvSubTotal.setText(subTotalDetails);
                    tvDeliveryFee.setText("0");
                    subfullAmount=String.valueOf(Float.valueOf(subTotal)+Float.valueOf(taxTotal));
                    tvTotal.setText(subfullAmount);
                    String pay = getResources().getString(R.string.pay) + SPACE + currencySymbol + SPACE + subfullAmount;
                    btnPay.setText(pay);
                } else {
                    tvSubTotal.setText(subTotalDetails);
                    tvDeliveryFee.setText(deliveryDetails);
                    tvTotal.setText(totalDetails);
                    String pay = getResources().getString(R.string.pay) + SPACE + currencySymbol + SPACE + totalAmount;
                    btnPay.setText(pay);
                }
            }
        } catch (NullPointerException e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    @OnClick(R.id.ivPeakHours)
    public void onClickPeakHours() {
        Common.buildToolTip(this, ivPeakHours,peekHourInfo, extraFee,currencySymbol).show();
    }

    @OnClick(R.id.tv_coupon_offers)
    public void setCouponOffer() {
        if (cbTermsAndCondition.isChecked()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            CouponFragment couponFragment = CouponFragment.newInstance(couponListArrayList,couponId);
            couponFragment.show(fragmentManager, "fragment_coupon");
        } else {
            showToast(R.string.error_terms_conditions);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_payment;
    }


    @OnClick(R.id.btn_pay)
    public void setContinue() {
        if (quantityError.size() > 0) {
            showToast(quantityError.get(0));
        } else {
            if (cbTermsAndCondition.isChecked()) {
                if (btnPay.getText().toString().equals(getResources().getString(R.string.pay))) {
                    System.out.println("USEcOUPON*:"+useCoupon);
                    paymentPresenter.paymentWallet(firstName, lastName, emailId, mobileNumber, alternateNumber, postalAddress,
                            landMark, latitude, longitude, ordSelfPickup, useWallet, fullyUsedWallet, useCoupon, couponId, couponAmount);
                } else{
                    paymentCashOnDelivery();
                }
            } else {
                showToast(R.string.error_terms_conditions);
            }
        }
    }


    private void paymentCashOnDelivery() {
        paymentPresenter.paymentCashOnDelivery(firstName, lastName, emailId, mobileNumber, alternateNumber, postalAddress,
                landMark, latitude, longitude, ordSelfPickup, useWallet, walletAmount, useCoupon, couponId, couponAmount);
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
    public void onOfferError(String msg) {
        useCoupon="0";
        couponId="";
        couponAmount="";
        showToast(msg);
        //System.out.println("offer error applied1");
    }

    @Override
    public void usedWalletError(String msg) {
        showToast(msg);
        useWallet = "0";
        cbWalletBalance.setChecked(false);
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @Override
    public void showError(int message) {
        showToast(message);
    }


    @Override
    public void showSuccess(String message) {
        appRepository.setCartCount(0);
        showSuccessDialog(2, message);
    }

    @Override
    public void showWalletError(String error) {
        nsvDataView.setVisibility(View.VISIBLE);
        llWalletView.setVisibility(View.GONE);
    }

    @Override
    public void onWalletResult(WalletBalanceResponse walletBalanceResponse) {
        nsvDataView.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
        walletAmount = walletBalanceResponse.getAvailableBalance();
        try {
            double wallAmount = Double.valueOf(walletAmount);
            nsvDataView.setVisibility(View.VISIBLE);
            llWalletView.setVisibility(wallAmount > 0 ? View.VISIBLE : View.GONE);
            String walletBalance = getResources().getString(R.string.wallet_use) + SPACE + currencySymbol + walletBalanceResponse.getAvailableBalance();
            cbWalletBalance.setText(walletBalance);
            String userWallet = currencySymbol + walletBalanceResponse.getAvailableBalance();
            tvUseWallet.setText(userWallet);
        } catch (Exception e) {
            nsvDataView.setVisibility(View.VISIBLE);
            llWalletView.setVisibility(View.VISIBLE);
            String walletBalance = getResources().getString(R.string.wallet_use) + SPACE + currencySymbol + walletBalanceResponse.getAvailableBalance();
            cbWalletBalance.setText(walletBalance);
            String userWallet = currencySymbol + walletBalanceResponse.getAvailableBalance();
            tvUseWallet.setText(userWallet);
        }
    }

    @Override
    public void paymentMethodResult(int status, CheckQuantityResponse checkQuantityResponse, PaymentMethodResponse paymentMethodResponse) {
        if (status == 200) {
            quantityError = new ArrayList<>();
            paymentError = null;
            paymentStatus = "";
        } else {
            quantityError = checkQuantityResponse.getQuantityError();
            paymentError = checkQuantityResponse.getPaymentError();
            paymentStatus = checkQuantityResponse.getPaymentStatus();
        }

        couponListArrayList = paymentMethodResponse.getCouponlist();
        llCouponView.setVisibility(couponListArrayList.size() == 0 ? View.GONE : View.VISIBLE);
        paymentPresenter.requestWalletBalance("");

    }

    @Override
    public void showPaymentMethodResultError(int status, CheckQuantityResponse checkQuantityResponse, String error) {
        if (status == 200) {
            quantityError = new ArrayList<>();
            paymentError = null;
        } else {
            quantityError = checkQuantityResponse.getQuantityError();
            paymentError = checkQuantityResponse.getPaymentError();
        }
        showErrorDialog(1, error);
    }


    @Override
    public void onSuccess(OfferType offerType, UseWalletResponse _useWalletResponse, String message) {
        System.out.println("USEcOUPON**:"+useCoupon);

        this.useWalletResponse = _useWalletResponse;
        fullyUsedWallet = useWalletResponse.getUsedWallet();
        String userWallet = "- "+_useWalletResponse.getCurrencyCode() + useWalletResponse.getUsedWallet();
        String totalDetails = _useWalletResponse.getCurrencyCode() + useWalletResponse.getPayableAmount();
        tvTotal.setText(totalDetails);
        paypalAmount = _useWalletResponse.getPayableAmtUsd();
        String pay = getResources().getString(R.string.pay) + SPACE + _useWalletResponse.getCurrencyCode() + useWalletResponse.getPayableAmount();
        btnPay.setText(pay);
        if (message.equals(MESSAGE_NEED_TO_PAY)) {
            btnPay.setText(getResources().getString(R.string.pay));
            llChooseToPay.setVisibility(View.GONE);
        }
        if (OfferType.COUPON == offerType) {
            useCoupon = "1";
            rlUseOffer.setVisibility(View.VISIBLE);
            String userOffer = "- "+_useWalletResponse.getCurrencyCode() + useWalletResponse.getUsedOffer();
            tvUseOffer.setText(userOffer);
            if(_useWalletResponse.getUsedOffer()==null || _useWalletResponse.getUsedOffer().equals("0")){
                rlUseOffer.setVisibility(View.GONE);
            }
            System.out.println("USEcOUPON***:"+useCoupon);

        } else if (OfferType.WALLET == offerType) {
            rlUseWallet.setVisibility(View.VISIBLE);
            tvUseWallet.setText(userWallet);
            useWallet = "1";
            if(_useWalletResponse.getUsedWallet()==null || _useWalletResponse.getUsedWallet().equals("0")){
                rlUseWallet.setVisibility(View.GONE);
            }
            //useCoupon = "0";

        }
    }


    @Override
    public void onDestroy() {
        PayPalUtil.stopPayPalService(this);
        super.onDestroy();
    }



       @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           super.onActivityResult(requestCode, resultCode, data);
       if (requestCode == REQUEST_PAYMENT) {
            paymentStatus = AVAILABLE;
            paymentPresenter.onRequestPaymentMethod();
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (cbTermsAndCondition.isChecked()) {
            if (isChecked) {
                useWallet = "1";
               // couponId = "";
                System.out.println("offer error applied:"+useCoupon+":"+couponAmount);
                paymentPresenter.useWallet(OfferType.WALLET, ordSelfPickup, useWallet, walletAmount, deliveryFee, useCoupon, couponId, couponAmount);
            } else {
                try {
                    paypalAmount = totalAmountUSD;
                    useWallet = "0";
                    llCouponView.setVisibility(couponListArrayList.size() == 0 ? View.GONE : View.VISIBLE);
                    rlUseWallet.setVisibility(View.GONE);
                    llChooseToPay.setVisibility(View.VISIBLE);
                    if (ordSelfPickup.equals("1")) {
                        String fullAmountDetails = currencySymbol + fullAmount;
                        tvTotal.setText(fullAmountDetails);
                        String payDetails = getResources().getString(R.string.pay) + SPACE + currencySymbol + fullAmount;
                        btnPay.setText(payDetails);
                    } else {
                        if(useWalletResponse!=null) {
                            String fullAmountDetails = currencySymbol + (Double.valueOf(useWalletResponse.getPayableAmount()) + Double.valueOf(useWalletResponse.getUsedWallet()));
                            tvTotal.setText(fullAmountDetails);
                            String payDetails = getResources().getString(R.string.pay) + SPACE + fullAmountDetails;
                            btnPay.setText(payDetails);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        } else {
            cbWalletBalance.setChecked(!isChecked);
            showToast(R.string.error_terms_conditions);
        }
    }

    @Override
    public void onClickApply(int position) {
       // useWallet = "0";
        useCoupon = "1";
        couponId = couponListArrayList.get(position).getCouponId();
        couponAmount = couponListArrayList.get(position).getCouponPrice();
        paymentPresenter.useOffer(OfferType.COUPON, ordSelfPickup, useWallet, walletAmount, deliveryFee, useCoupon, couponId, couponAmount);

    }

    @Override
    public void clearAll() {
        useCoupon = "0";
        couponId="";
        couponAmount="";
        paymentPresenter.useOffer(OfferType.COUPON, ordSelfPickup, useWallet, walletAmount, deliveryFee, useCoupon, couponId, couponAmount);
    }

}
