package com.foodtogo.user.ui.youraddress.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.country.CountryList;
import com.foodtogo.user.model.shippingaddress.ShippingAddressRequest;
import com.foodtogo.user.model.shippingaddress.ShippingAddressResponse;

public interface YourAddressContractor {

    interface View extends BaseView {

        void showCountryList(CountryList countryList);

        void showShippingResponse(ShippingAddressResponse shippingAddressResponse);

        void showSuccess(String message);

        void showErrorDialog(String message);

        void showErrorDialog(int message);

        void showProgressBar();

        void hideProgressBar();
        void showWarning(int message);

        void showOtpDialog(String otp,String msg);

        void mobileEmailError(String msg);

    }

    interface Presenter {

        void requestShippingAddress();

        void requestCountry();

        void postShippingAddress(String firstName, String lastName, String emailAddress,String mobileNumberOnly, String mobileNumber,
                                 String alternateNumber, String landmark, String postalAddress, String latitude,
                                 String longitude, String pinCode,String countryCode1,String countryCode2);

        void onShippingResponse(ShippingAddressResponse shippingAddressResponse);

        void countryList(CountryList countryList);

        void onSuccess(String message,String postalAddress);

        void apiError(String error);

        void apiError(int error);

        void mobileEmailError(String msg);

        void close();

        void otpRequest(String otp,String msg);

        void requestShippingAddressWithOtp(ShippingAddressRequest shippingAddressRequest);

    }

    interface Model {

        void postShippingAddress(String firstName, String lastName, String emailAddress, String mobileNumber,
                                 String alternateNumber, String landmark, String postalAddress, String latitude,
                                 String longitude, String pinCode,String countryCode1,String countryCode2);

        void requestShippingAddress();

        void postShippingAddressWithOtp(ShippingAddressRequest shippingAddressRequest);

        void requestCountry();

        void close();
    }
}
