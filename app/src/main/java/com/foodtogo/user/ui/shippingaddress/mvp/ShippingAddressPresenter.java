package com.foodtogo.user.ui.shippingaddress.mvp;

import android.util.Patterns;

import com.foodtogo.user.R;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.country.CountryList;
import com.foodtogo.user.model.shippingaddress.CheckVerificationRequest;
import com.foodtogo.user.model.shippingaddress.SendEmailVerificationCodeRequest;
import com.foodtogo.user.model.shippingaddress.ShippingAddressRequest;
import com.foodtogo.user.model.shippingaddress.ShippingAddressResponse;


public class ShippingAddressPresenter implements ShippingAddressContractor.Presenter {

    private ShippingAddressContractor.View mView;
    private ShippingAddressModel model;


    public ShippingAddressPresenter(ShippingAddressContractor.View view, AppRepository appRepository) {
        mView = view;
        model = new ShippingAddressModel(this, appRepository);

    }

    @Override
    public void requestShippingAddress() {
        model.requestShippingAddress();
    }

    @Override
    public void requestCountry() {
        mView.showProgressBar();
        model.requestCountry();
    }

    @Override
    public void postShippingAddress(String firstName, String lastName, String emailAddress, String mobileNumber, String alternateNumber, String mobileNumberWithCountryCode,
                                    String alternateNumberWithCountryCode, String landmark, String postalAddress, String latitude, String longitude,
                                    String pinCode,String countryCode1,String countryCode2) {
        if (firstName.length() == 0) {
            mView.showErrorDialog(R.string.error_first_name);
        } else if (lastName.length() == 0) {
            mView.showErrorDialog(R.string.error_last_name);
        } else if (emailAddress.length() == 0) {
            mView.showErrorDialog(R.string.warning_empty_email);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            mView.showErrorDialog(R.string.warning_invalid_email);
        } else if (mobileNumber.length() == 0) {
            mView.showErrorDialog(R.string.error_empty_customer_mobile_number);
        }
         else if (postalAddress.length() == 0) {
            mView.showErrorDialog(R.string.error_address);
        }else if(landmark.length()==0){
             mView.showErrorDialog(R.string.enter_flat_no_landmark);
        } else {
            mView.showLoadingView();
            model.postShippingAddress(firstName, lastName, emailAddress, mobileNumberWithCountryCode,
                    "", landmark, postalAddress, String.valueOf(latitude),
                    String.valueOf(longitude), pinCode,countryCode1,"");
        }
    }


    @Override
    public void onShippingResponse(ShippingAddressResponse shippingAddressResponse) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.showShippingResponse(shippingAddressResponse);
    }

    @Override
    public void countryList(CountryList countryList) {
        mView.showCountryList(countryList);
    }

    @Override
    public void onSuccess(String message) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.showSuccess(message);
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
    public void onCodeVerificationSuccess(String msg) {
        mView.hideLoadingView();
        mView.successInToast(msg);
    }

    @Override
    public void showErrorDialog(String msg) {
        mView.hideLoadingView();
        mView.showErrorDialog(msg);
    }


    @Override
    public void close() {
        model.close();
    }

    @Override
    public void otpRequest(String otp,String msg,boolean isEmailVerifyOnly) {
        mView.hideLoadingView();
        mView.showOtpDialog(otp,msg,isEmailVerifyOnly);
    }

    @Override
    public void requestShippingAddressWithOtp(ShippingAddressRequest shippingAddressRequest) {
        mView.showLoadingView();
        model.postShippingAddressWithOtp(shippingAddressRequest);
    }

    @Override
    public void requestVerificationCode(SendEmailVerificationCodeRequest request) {
        mView.showLoadingView();
         model.sendVerificationCode(request);
    }

    @Override
    public void verifyCode(CheckVerificationRequest request) {
        mView.showLoadingView();
        model.requestCheckVerificationCode(request);
    }
}
