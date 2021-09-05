package com.foodtogo.user.ui.shippingaddress.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.country.CountryList;
import com.foodtogo.user.model.shippingaddress.CheckVerificationRequest;
import com.foodtogo.user.model.shippingaddress.SendEmailVerificationCodeRequest;
import com.foodtogo.user.model.shippingaddress.ShippingAddressRequest;
import com.foodtogo.user.model.shippingaddress.ShippingAddressResponse;

public interface ShippingAddressContractor {

    interface View extends BaseView {

        void showCountryList(CountryList countryList);

        void showShippingResponse(ShippingAddressResponse shippingAddressResponse);

        void showSuccess(String message);

        void showProgressBar();

        void hideProgressBar();

        void showErrorDialog(String message);

        void showErrorDialog(int message);

        void showOtpDialog(String otp,String msg,boolean isEmailVerifyOnly);

        void successInToast(String msg);


    }

    interface Presenter {

        void requestShippingAddress();

        void requestCountry();

        void postShippingAddress(String firstName, String lastName, String emailAddress, String mobileNumber,
                                 String alternateNumber, String mobileNumberWithCountryCode,
                                 String alternateNumberWithCountryCode, String landmark, String postalAddress, String latitude, String longitude,
                                 String pinCode,String countryCode1,String countryCode2);

        void onShippingResponse(ShippingAddressResponse shippingAddressResponse);

        void countryList(CountryList countryList);

        void onSuccess(String message);

        void apiError(String error);

        void apiError(int error);

        void onCodeVerificationSuccess(String msg);

        void showErrorDialog(String msg);

        void close();

        void otpRequest(String otp,String msg,boolean isEmailVerifyOnly);

        void requestShippingAddressWithOtp(ShippingAddressRequest shippingAddressRequest);

        void requestVerificationCode(SendEmailVerificationCodeRequest request);

        void verifyCode(CheckVerificationRequest request);

    }

    interface Model {

        void postShippingAddress(String firstName, String lastName, String emailAddress, String mobileNumber,
                                 String alternateNumber, String landmark, String postalAddress, String latitude,
                                 String longitude, String pinCode,String countryCode1,String countryCode2);

        void requestShippingAddress();

        void requestCountry();

        void close();
        void postShippingAddressWithOtp(ShippingAddressRequest shippingAddressRequest);
        void sendVerificationCode(SendEmailVerificationCodeRequest request);
        void requestCheckVerificationCode(CheckVerificationRequest request);

    }
}
