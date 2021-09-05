package com.foodtogo.user.ui.youraddress.mvp;

import android.util.Patterns;

import com.foodtogo.user.R;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.country.CountryList;
import com.foodtogo.user.model.shippingaddress.ShippingAddressRequest;
import com.foodtogo.user.model.shippingaddress.ShippingAddressResponse;


public class YourAddressPresenter implements YourAddressContractor.Presenter {

    private YourAddressContractor.View mView;
    private YourAddressModel model;
    private AppRepository mAppRepository;


    public YourAddressPresenter(YourAddressContractor.View view, AppRepository appRepository) {
        mView = view;
        model = new YourAddressModel(this, appRepository);
        mAppRepository=appRepository;
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
    public void postShippingAddress(String firstName, String lastName, String emailAddress,String mobileNumberOnly, String mobileNumber,
                                    String alternateNumber, String landmark, String postalAddress, String latitude,
                                    String longitude, String pinCode,String countryCode1,String countryCode2) {
        if (firstName.length() == 0) {
            mView.showWarning(R.string.error_first_name);
        } else if (lastName.length() == 0) {
            mView.showWarning(R.string.error_last_name);
        } else if (emailAddress.length() == 0) {
            mView.showWarning(R.string.warning_empty_email);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            mView.showWarning(R.string.warning_invalid_email);
        } else if (mobileNumberOnly.length() ==0) {
            mView.showWarning(R.string.error_empty_customer_mobile_number);
        } else if (mobileNumberOnly.length() < 5) {
            mView.showError(R.string.error_valid_customer_mobile_number);
        }
//        else if (alternateNumber.length() != 10) {
//            mView.showError(R.string.error_valid_alternate_mobile_number);
//        }
        else if (postalAddress.length() == 0) {
            mView.showWarning(R.string.error_address);
        }   else if (landmark.length() == 0) {
            mView.showWarning(R.string.error_landmark);
        } else {
            mView.showLoadingView();
            model.postShippingAddress(firstName, lastName, emailAddress, mobileNumber,
                    "", landmark, postalAddress, String.valueOf(latitude), String.valueOf(longitude),
                    pinCode,countryCode1,"");
        }
    }

    @Override
    public void onShippingResponse(ShippingAddressResponse shippingAddressResponse) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showShippingResponse(shippingAddressResponse);
    }

    @Override
    public void countryList(CountryList countryList){
        mView.showCountryList(countryList);
    }

    @Override
    public void onSuccess(String message,String postalAddress) {
        mAppRepository.setSearchLocation(postalAddress);
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showSuccess(message);
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
    public void mobileEmailError(String msg) {
        mView.hideLoadingView();
        mView.mobileEmailError(msg);
    }


    @Override
    public void close() {
        model.close();
    }

    @Override
    public void otpRequest(String otp,String msg) {
        mView.hideLoadingView();
        mView.showOtpDialog(otp,msg);
    }

    @Override
    public void requestShippingAddressWithOtp(ShippingAddressRequest shippingAddressRequest) {
        mView.showLoadingView();
        model.postShippingAddressWithOtp(shippingAddressRequest);
    }
}
