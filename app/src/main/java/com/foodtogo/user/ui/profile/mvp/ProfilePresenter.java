package com.foodtogo.user.ui.profile.mvp;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.util.Patterns;

import com.alium.nibo.models.NiboSelectedPlace;
import com.alium.nibo.utils.NiboConstants;
import com.foodtogo.user.R;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.accountdetails.User;
import com.foodtogo.user.model.country.CountryList;
import com.foodtogo.user.model.profile.ProfileUpdateResponse;
import com.foodtogo.user.model.shippingaddress.CheckVerificationRequest;
import com.foodtogo.user.model.shippingaddress.SendEmailVerificationCodeRequest;
import com.foodtogo.user.util.ImageUtil;
import com.foodtogo.user.util.PermissionUtil;

import java.io.File;

import static android.app.Activity.RESULT_OK;


public class ProfilePresenter implements ProfileContractor.Presenter {

    private static final int LOCATION_REQUEST_CODE = 300;
    private final static int IMAGE_RESULT = 200;
    private ProfileContractor.View mView;
    private ProfileModel model;


    public ProfilePresenter(ProfileContractor.View view, AppRepository appRepository) {
        mView = view;
        model = new ProfileModel(this, appRepository);
    }

    @Override
    public void requestAccountDetails() {
        mView.showProgressBar();
        model.requestAccountDetails();
    }

    @Override
    public void onSuccess(User user) {
        mView.hideProgressBar();
        mView.hideLoadingView();
        mView.showUserList(user);
    }

    @Override
    public void onOtpPopup(ProfileUpdateResponse profileUpdateResponse,String msg) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showOtpPopup(profileUpdateResponse,msg);
    }

    @Override
    public void onSuccess(String message, ProfileUpdateResponse profileUpdateResponse) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.onSuccess(message, profileUpdateResponse);
    }




    @Override
    public void requestUpdateProfile(String userName, String email, String phone1, String phone2,
                                     String address, String latitude, String longitude, File file,String phoneCode1) {
        if (userName.length() == 0) {
            mView.showError(R.string.error_user_name);
        } else if (email.length() == 0) {
            mView.showError(R.string.warning_empty_email);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mView.showError(R.string.warning_invalid_email);
        } else if (phone1.length() == 0) {
            mView.showError(R.string.error_empty_customer_mobile_number);
        } else if (address.length() == 0) {
            mView.showError(R.string.error_address);
        } else {
            mView.showLoadingView();
            model.requestUpdateProfile(userName, email, phone1, phone2, address, String.valueOf(latitude), String.valueOf(longitude), file,phoneCode1);
        }
    }

    @Override
    public void requestUpdateProfileWithOtp(String userName, String email, String phone1, String phone2,
                                            String address, String latitude, String longitude, File image, String otp,
                                            String currentOtp,String phoneCode1) {
        mView.showLoadingView();
        model.requestUpdateProfileWithOtp(userName, email, phone1, phone2, address, String.valueOf(latitude), String.valueOf(longitude), image, otp, currentOtp,phoneCode1);
    }

    @Override
    public void onCheckImagePermission(boolean hasPermission) {
        if (hasPermission) {
            mView.launchImagePicker();
        } else {
            mView.requestImagePermission();
        }
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
    public void otpError(String error) {
        mView.hideLoadingView();
        mView.hideProgressBar();
        mView.showOtpError(error);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionUtil.CAMERA_GALLERY_REQUEST:
                if (PermissionUtil.isPermissionGranted(grantResults)) {
                    mView.checkImagePermission();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case LOCATION_REQUEST_CODE:
                    NiboSelectedPlace selectedPlace = data.getParcelableExtra(NiboConstants.SELECTED_PLACE_RESULT);
                    mView.showAddressDetails(selectedPlace.getPlaceAddress(), selectedPlace.getLatitude(), selectedPlace.getLongitude());
                    break;
                case IMAGE_RESULT:
                    String filePath = ImageUtil.getImageFilePath(context, data);
                    filePath = ImageUtil.ResizeImage(context, filePath);
                    mView.loadImage(filePath);
                    break;
            }
        }
    }

    @Override
    public void requestVerificationCode(SendEmailVerificationCodeRequest request) {
        mView.showLoadingView();
        model.sendVerificationCode(request);
    }


    @Override
    public void close() {
        model.close();
    }

    @Override
    public void emailVerifyOtpSuccess(String otp, String msg) {
        mView.hideLoadingView();
        mView.emailVerifyOtp(otp,msg);
    }

    @Override
    public void verifyCode(CheckVerificationRequest verificationRequest) {
        mView.showLoadingView();
        model.requestCheckVerificationCode(verificationRequest);
    }

    @Override
    public void onCodeVerificationSuccess(String msg) {
        mView.hideLoadingView();
        mView.successInToast(msg);
    }

    @Override
    public void requestCountryCode() {
      mView.showLoadingView();
      model.requestCountry();
    }

    @Override
    public void countryList(CountryList countryList) {
     mView.hideLoadingView();
     mView.showCountryList(countryList);
    }
}
