package com.foodtogo.user.ui.profile.mvp;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.accountdetails.User;
import com.foodtogo.user.model.country.CountryList;
import com.foodtogo.user.model.profile.ProfileUpdateResponse;
import com.foodtogo.user.model.shippingaddress.CheckVerificationRequest;
import com.foodtogo.user.model.shippingaddress.SendEmailVerificationCodeRequest;

import java.io.File;

public interface ProfileContractor {

    interface View extends BaseView {

        void showUserList(User user);

        void showAddressDetails(String address, double latitude, double longitude);

        void requestImagePermission();

        void launchImagePicker();

        void loadImage(String path);

        void onSuccess(String message, ProfileUpdateResponse profileUpdateResponse);

        void checkImagePermission();

        void showProgressBar();

        void hideProgressBar();

        void showOtpPopup(ProfileUpdateResponse profileUpdateResponse,String msg);

        void showOtpError(String error);

        void emailVerifyOtp(String otp,String msg);

        void successInToast(String msg);

        void showCountryList(CountryList countryList);

    }

    interface Presenter {

        void requestAccountDetails();

        void onSuccess(User user);

        void onOtpPopup(ProfileUpdateResponse profileUpdateResponse,String msg);

        void onSuccess(String message, ProfileUpdateResponse profileUpdateResponse);

        void requestUpdateProfile(String userName, String email, String phone1, String phone2, String address,
                                  String latitude, String longitude, File image,String phoneCode1);

        void requestUpdateProfileWithOtp(String userName, String email, String phone1, String phone2,
                                         String address, String latitude, String longitude, File image,
                                         String otp, String currentOtp,String phoneCode1);

        void onCheckImagePermission(boolean hasPermission);

        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                        @NonNull int[] grantResults);

        void onActivityResult(Context context, int requestCode, int resultCode, Intent data);
        void requestVerificationCode(SendEmailVerificationCodeRequest request);

        void apiError(String error);

        void apiError(int error);

        void otpError(String error);

        void close();

        void emailVerifyOtpSuccess(String otp,String msg);
        void verifyCode(CheckVerificationRequest verificationRequest);

        void onCodeVerificationSuccess(String msg);

        void requestCountryCode();
        void countryList(CountryList countryList);

    }

    interface Model {

        void requestAccountDetails();

        void requestUpdateProfile(String userName, String email, String phone1, String phone2, String address,
                                  String latitude, String longitude, File image,String phoneCode1);

        void requestUpdateProfileWithOtp(String userName, String email, String phone1, String phone2, String address,
                                         String latitude, String longitude, File image, String otp, String currentOtp,String phoneCode1);

        void close();

        void requestCountry();

         void sendVerificationCode(SendEmailVerificationCodeRequest request);

         void  requestCheckVerificationCode(CheckVerificationRequest request);

    }
}
