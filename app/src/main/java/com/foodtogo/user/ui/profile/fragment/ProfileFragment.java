package com.foodtogo.user.ui.profile.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alium.nibo.placepicker.NiboPlacePickerActivity;
import com.alium.nibo.utils.NiboStyle;
import com.chaos.view.PinView;
import com.foodtogo.user.R;
import com.foodtogo.user.base.AppConstants;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.model.accountdetails.User;
import com.foodtogo.user.model.country.CountryList;
import com.foodtogo.user.model.profile.ProfileUpdateResponse;
import com.foodtogo.user.model.shippingaddress.CheckVerificationRequest;
import com.foodtogo.user.model.shippingaddress.SendEmailVerificationCodeRequest;
import com.foodtogo.user.ui.profile.mvp.ProfileContractor;
import com.foodtogo.user.ui.profile.mvp.ProfilePresenter;
import com.foodtogo.user.ui.register.adapter.CountryAdapter;
import com.foodtogo.user.util.GlideUtils;
import com.foodtogo.user.util.ImageUtil;
import com.foodtogo.user.util.PermissionUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static com.alium.nibo.placepicker.NiboPlacePickerActivity.setBuilder;
import static com.foodtogo.user.util.PermissionUtil.CAMERA_PERMISSION;
import static com.foodtogo.user.util.PermissionUtil.READ_STORAGE_PERMISSION;
import static com.foodtogo.user.util.PermissionUtil.WRITE_STORAGE_PERMISSION;


public class ProfileFragment extends BaseFragment implements ProfileContractor.View, AppConstants {


    @BindView(R.id.ll_profile_pic)
    LinearLayout ll_profile_pic;

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    @BindView(R.id.et_user_name)
    EditText etUserName;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_mobile_number)
    EditText etMobileNumber;

    @BindView(R.id.et_country_code)
    EditText etCountryCode;

    @BindView(R.id.spinner_country)
    Spinner spinnerCountry;


    @BindView(R.id.et_customer_address)
    EditText etCustomerAddress;

    @BindView(R.id.ic_profile_edit)
    ImageView ic_profile_edit;

    @BindView(R.id.ic_mobile_number_edit)
    ImageView ic_mobile_number_edit;

    @BindView(R.id.ic_email_edit)
    ImageView ic_email_edit;

    @BindView(R.id.ic_address_edit)
    ImageView ic_address_edit;

    @BindView(R.id.btn_save)
    Button btnSave;

    @BindView(R.id.tv_error)
    TextView tvError;

    @BindView(R.id.nsv_data_view)
    NestedScrollView nestedScrollView;

    private final static int IMAGE_RESULT = 200;

    int hideUser = 0;
    int hideEmail = 0;
    int hidePhoneNumber = 0;
    double latitude = 0.0;
    double longitude = 0.0;

    File file = null;

    String otp = "";

    private static final String TAG = ProfileFragment.class.getName();

    public ProfileFragment() {
        // Required empty public constructor
    }

    private static final int LOCATION_REQUEST_CODE = 300;
    ProfilePresenter profilePresenter;


    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profilePresenter = new ProfilePresenter(this, appRepository);
        nestedScrollView.setVisibility(View.GONE);
        profilePresenter.requestAccountDetails();
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
    public void showOtpPopup(ProfileUpdateResponse profileUpdateResponse,String msg) {
        otp = String.valueOf(profileUpdateResponse.getOtp());
        otpVerifyPopup(msg,false);
    }

    @Override
    public void showOtpError(String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        otpVerifyPopup(error,false);
    }

    @Override
    public void emailVerifyOtp(String otp, String msg) {
        this.otp=otp;
        otpVerifyPopup(msg,true);
    }

    @Override
    public void successInToast(String msg) {
        btnSave.setText(getString(R.string.save));
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

   /* @OnClick(R.id.et_country_code)
    public void setCountryClick() {
        spinnerCountry.performClick();
    }*/

    @Override
    public void showCountryList(CountryList countryList) {
        CountryAdapter adapter = new CountryAdapter(getActivity(),
                R.layout.list_item_restaruant_subcategory, countryList.getAllCountryCodeList());
        spinnerCountry.setAdapter(adapter);
        spinnerCountry.setSelection(0,false);
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String countryDial = countryList.getAllCountryCodeList().get(pos).getCountryDial();
                etCountryCode.setText(countryDial);
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public void showError(String message) {
        showToast(message);
    }

    @Override
    public void showError(int message) {
        showToast(message);
    }


    @OnClick(R.id.ll_profile_pic)
    public void setProfileImage() {
        profilePresenter.onCheckImagePermission(PermissionUtil.checkImagePermission(getActivity()));
    }


    @OnClick(R.id.ic_profile_edit)
    public void setEditUserName() {
        if (hideUser == 0) {
            hideUser = 1;
            showKeyboard(etUserName);
            enableView(etUserName);
        } else {
            hideUser = 0;
            disableView(etUserName);
            hideKeyboard(etUserName);
        }
    }

    @OnClick(R.id.ic_mobile_number_edit)
    public void setEditMobileUserName() {
        if (hidePhoneNumber == 0) {
            hidePhoneNumber = 1;
            showKeyboard(etMobileNumber);
            enableView(etMobileNumber);
        } else {
            hidePhoneNumber = 0;
            disableView(etMobileNumber);
            hideKeyboard(etMobileNumber);
        }
    }


    @OnClick(R.id.ic_email_edit)
    public void setEditEmail() {
        if (hideEmail == 0) {
            hideEmail = 1;
            showKeyboard(etEmail);
            enableView(etEmail);
        } else {
            hideEmail = 0;
            disableView(etEmail);
            hideKeyboard(etEmail);
        }
    }


    @OnClick(R.id.btn_save)
    public void setSaveProfile() {
        if(btnSave.getText().toString().equals(getString(R.string.save))){
            String userName = etUserName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String mobileNumber =etCountryCode.getText().toString()+etMobileNumber.getText().toString().trim();
            String address = etCustomerAddress.getText().toString().trim();
            profilePresenter.requestUpdateProfile(userName, email, mobileNumber, "", address, String.valueOf(latitude), String.valueOf(longitude), file,etCountryCode.getText().toString());
        }else{
            String emailAddress = etEmail.getText().toString();
            if (emailAddress.length() == 0) {
                showErrorDialog("Info",getString(R.string.warning_empty_email));
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                showErrorDialog("Info",getString(R.string.warning_invalid_email));
            }else{
                SendEmailVerificationCodeRequest request=new SendEmailVerificationCodeRequest(appRepository.getLanguageCode(),emailAddress);
                profilePresenter.requestVerificationCode(request);
            }
        }

    }


    @OnClick(R.id.ic_address_edit)
    public void setEditAddress() {
        Intent intent = new Intent(getActivity(), NiboPlacePickerActivity.class);
        intent.putExtra("address",appDataSource.getSearchLocation());
        NiboPlacePickerActivity.NiboPlacePickerBuilder config = new NiboPlacePickerActivity.NiboPlacePickerBuilder()
                .setSearchBarTitle(getString(R.string.set_customer_address))
                .setConfirmButtonTitle(getString(R.string.confirm_location))
                .setMarkerPinIconRes(R.drawable.ic_map_marker_def)
                .setStyleEnum(NiboStyle.DEFAULT);
        setBuilder(config);
        startActivityForResult(intent, LOCATION_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        profilePresenter.onActivityResult(context, requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void showUserList(User user) {
        if (getActivity() != null) {
            tvError.setVisibility(View.GONE);
            nestedScrollView.setVisibility(View.VISIBLE);
            etUserName.setText(user.getUserName());
            etEmail.setText(user.getUserEmail());
            etMobileNumber.setText(user.getUserPhn1Only());
            etCustomerAddress.setText(user.getUserAddress());
            GlideUtils.showRoundImage(getActivity(), ivProfile, R.drawable.profile_default_pic, user.getUserAvatar());
            appRepository.setUserEmail(user.getUserEmail());
            appRepository.setUserName(user.getUserName());
            appRepository.setUserAvatar(user.getUserAvatar());
            btnSave.setText(user.getEmailVerificationStatus().equals("1")?VERIFY_EMAIL:getString(R.string.save));
            etCountryCode.setText(user.getUserPhn1CountryCode().isEmpty()?"+63":user.getUserPhn1CountryCode());

            disableView(etUserName);
            disableView(etEmail);
            disableView(etMobileNumber);
            disableView(etCustomerAddress);
            try {
                latitude = Double.valueOf(user.getUserLatitude());
                longitude = Double.valueOf(user.getUserLongitude());
            } catch (Exception e) {
                Log.e(EXCEPTION, e.toString());
            }

            profilePresenter.requestCountryCode();
        }
    }

    @Override
    public void showAddressDetails(String address, double latitude, double longitude) {
        etCustomerAddress.setText(address);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public void requestImagePermission() {
        requestPermissions(new String[]{CAMERA_PERMISSION, WRITE_STORAGE_PERMISSION, READ_STORAGE_PERMISSION}, PermissionUtil.CAMERA_GALLERY_REQUEST);
    }

    @Override
    public void launchImagePicker() {
        startActivityForResult(ImageUtil.getPickImageChooserIntent(getActivity()), IMAGE_RESULT);
    }


    private void disableView(EditText editText) {
        editText.setEnabled(false);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.setBackgroundColor(getResources().getColor(R.color.colorWhite));

    }

    private void enableView(EditText editText) {
        editText.setEnabled(true);
        editText.setFocusable(true);
        editText.setCursorVisible(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.setSelection(editText.getText().toString().length());
    }

    @Override
    public void loadImage(String path) {
        file = new File(path);
        try {
            GlideUtils.showRoundImage(getActivity(), ivProfile, R.drawable.profile_default_pic, path);
        } catch (NullPointerException e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void onSuccess(String message, ProfileUpdateResponse profileUpdateResponse) {
        showSuccessDialog(message, 1);
        appRepository.setUserEmail(profileUpdateResponse.getUserEmail());
        appRepository.setUserName(profileUpdateResponse.getUserName());
        appRepository.setUserAvatar(profileUpdateResponse.getUserAvatar());
    }

    @Override
    public void checkImagePermission() {
        profilePresenter.onCheckImagePermission(PermissionUtil.checkImagePermission(getActivity()));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        profilePresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public void otpVerifyPopup(String msg,boolean isEmailVerify) {
        final Dialog dialog = new Dialog(getActivity(),R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_otp_verify);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final Button btnVerify = dialog.findViewById(R.id.btn_verify);
        final PinView etOtp = dialog.findViewById(R.id.et_otp);
        final TextView responseMsg = dialog.findViewById(R.id.response_msg);
        etOtp.setAnimationEnable(true);
        responseMsg.setVisibility(View.VISIBLE);
        responseMsg.setText(msg);
        //etOtp.setText(otp);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        btnVerify.setOnClickListener(v -> {
            if (etOtp.getText().toString().length() < 6) {
                Toast.makeText(context, getResources().getString(R.string.please_enter_otp), Toast.LENGTH_SHORT).show();
            }else if(isEmailVerify){
                hideKeyboard(v);
                dialog.dismiss();
                String emailAddress = etEmail.getText().toString();
                CheckVerificationRequest verificationRequest=new CheckVerificationRequest(appRepository.getLanguageCode(),emailAddress,otp,etOtp.getText().toString(),"account");
                profilePresenter.verifyCode(verificationRequest);
            } else {
                hideKeyboard(v);
                dialog.dismiss();
                String userName = etUserName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String mobileNumber = etMobileNumber.getText().toString().trim();
                String address = etCustomerAddress.getText().toString().trim();
                profilePresenter.requestUpdateProfileWithOtp(userName, email, mobileNumber, "", address, String.valueOf(latitude), String.valueOf(longitude), file, otp,
                        etOtp.getText().toString(),etCountryCode.getText().toString());
            }
        });
        dialog.show();
    }


}