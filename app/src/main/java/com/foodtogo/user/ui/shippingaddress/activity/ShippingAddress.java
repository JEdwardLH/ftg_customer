package com.foodtogo.user.ui.shippingaddress.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alium.nibo.utils.NiboConstants;
import com.chaos.view.PinView;
import com.foodtogo.user.R;
import com.foodtogo.user.base.AppConstants;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.model.country.CountryList;
import com.foodtogo.user.model.mycart.StoreLocations;
import com.foodtogo.user.model.shippingaddress.CheckVerificationRequest;
import com.foodtogo.user.model.shippingaddress.SendEmailVerificationCodeRequest;
import com.foodtogo.user.model.shippingaddress.ShippingAddressRequest;
import com.foodtogo.user.model.shippingaddress.ShippingAddressResponse;
import com.foodtogo.user.ui.payment.activity.Payment;
import com.foodtogo.user.ui.register.adapter.CountryAdapter;
import com.foodtogo.user.ui.shippingaddress.adapter.RestaurantAddressAdapter;
import com.foodtogo.user.ui.shippingaddress.mvp.ShippingAddressContractor;
import com.foodtogo.user.ui.shippingaddress.mvp.ShippingAddressPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.alium.nibo.placepicker.NiboPickerFragment.MULTI_ADDRESS_REQUEST_CODE;


public class ShippingAddress extends BaseActivity implements ShippingAddressContractor.View,
        RadioGroup.OnCheckedChangeListener, AppConstants {

    ShippingAddressPresenter shippingAddressPresenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.et_first_name)
    EditText etFirstName;

    @BindView(R.id.et_last_name)
    EditText etLastName;

    @BindView(R.id.et_email_id)
    EditText etEmailId;

    @BindView(R.id.et_country_code)
    EditText etCountryCode;

    @BindView(R.id.et_mobile_number)
    EditText etMobileNumber;

    @BindView(R.id.spinner_country)
    Spinner spinnerCountry;

    @BindView(R.id.et_sub_country_code)
    EditText etSubCountryCode;

    @BindView(R.id.et_alternate_number)
    EditText etAlternateNumber;

    @BindView(R.id.spinner_sub_country)
    Spinner spinnerSubCountry;

    @BindView(R.id.et_postal_address)
    EditText etPostalAddress;

    @BindView(R.id.et_landmark)
    EditText etLandmark;

    @BindView(R.id.btn_continue)
    Button btnContinue;

    @BindView(R.id.tv_self_pickup_label)
    TextView tvSelfPickupLabel;

    @BindView(R.id.rg_self_pickup)
    RadioGroup radioGroup;

    @BindView(R.id.rb_delivery)
    RadioButton rbDelivery;

    @BindView(R.id.rb_collection)
    RadioButton rbCollection;


    @BindView(R.id.nsv_data_view)
    NestedScrollView nestedScrollView;

    @BindView(R.id.ll_shipping_address_view)
    LinearLayout llShippingAddressView;

    @BindView(R.id.tv_error)
    TextView tvError;


    double latitude = 0.0;
    double longitude = 0.0;
    String pinCode = "";

    String totalAmountUSD = "";
    String totalAmount = "";
    String deliveryFee = "";
    String extraFee = "";
    String peekHourInfo = "";
    boolean isPeakHour = false;
    String subTotal = "";
    String totalTax = "";
    String currencySymbol = "";
    String firstName = "";
    String lastName = "";
    String emailId = "";
    String mobileNumber = "";
    String alternateNumber = "";
    String postalAddress = "";
    String ordSelfPickup = "0";
    String landmark = "";

    ArrayList<StoreLocations> storeLocationsList;
    SharedPreferences prefs;
    private String MY_PREFS_NAME = "Pref Name";
    private String LANDMARK = "landMark";
    private String THAI_CODE="+63";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shippingAddressPresenter = new ShippingAddressPresenter(this, appRepository);
        nestedScrollView.setVisibility(View.GONE);
        shippingAddressPresenter.requestCountry();
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        setupToolBar();
        tvTitle.setText(getResources().getString(R.string.shipping_address));
        radioGroup.setOnCheckedChangeListener(this);
        getPriceDetails();

    }

    private void getPriceDetails() {
        Bundle bundle = getIntent().getExtras();
        try {
            totalAmountUSD = bundle.getString(TOTAL_AMOUNT_USD);
            totalAmount = bundle.getString(TOTAL_AMOUNT);
            deliveryFee = bundle.getString(DELIVERY_FEE);
            extraFee = bundle.getString(EXTRA_CHARGE);
            totalTax = bundle.getString(TOTAL_TAX);
            peekHourInfo = bundle.getString(PEEK_INFO);
            isPeakHour = bundle.getBoolean(IS_PEAK_HOUR);
            currencySymbol = bundle.getString(CURRENCY_SYMBOL);
            subTotal = bundle.getString(SUB_TOTAL);
            storeLocationsList = bundle.getParcelableArrayList(STORE_LOCATIONS);
            RestaurantAddressAdapter restaurantAddressAdapter = new RestaurantAddressAdapter(this, storeLocationsList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(restaurantAddressAdapter);
        } catch (NullPointerException e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    @OnClick(R.id.choose_location)
    void chooseSavedLocations(){
        Intent intent = new Intent("com.foodtogo.user.MyAction");
        intent.putExtra(TAB_POSITION,3);
        startActivityForResult(intent,MULTI_ADDRESS_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == MULTI_ADDRESS_REQUEST_CODE) {
            if(data!=null){
                String address=data.getStringExtra(NiboConstants.SELECTED_PLACE_RESULT);
                latitude=Double.valueOf(data.getStringExtra(NiboConstants.NIBO_LATITUDE));
                longitude=Double.valueOf(data.getStringExtra(NiboConstants.NIBO_LONGITUDE));
                etPostalAddress.setText(address);
            }
        }
    }


   /* @OnClick(R.id.et_country_code)
    public void setCountryClick() {
        spinnerCountry.performClick();
    }*/

   /* @OnClick(R.id.et_sub_country_code)
    public void seSubCountryClick() {
        spinnerSubCountry.performClick();
    }*/


    @OnClick(R.id.btn_continue)
    public void setContinue() {
        if(btnContinue.getText().toString().equalsIgnoreCase(VERIFY_EMAIL)) {
            String emailAddress = etEmailId.getText().toString();
            if (emailAddress.length() == 0) {
                showErrorDialog(R.string.warning_empty_email);
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                showErrorDialog(R.string.warning_invalid_email);
            }else{
                SendEmailVerificationCodeRequest request=new SendEmailVerificationCodeRequest(appRepository.getLanguageCode(),emailAddress);
                shippingAddressPresenter.requestVerificationCode(request);

            }
        }else{
            if (rbDelivery.isChecked()) {
                ordSelfPickup = "0";
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String emailAddress = etEmailId.getText().toString();
                String mobileNumber = etMobileNumber.getText().toString();
                String mobileNumberWithCountryCode = etCountryCode.getText().toString() + etMobileNumber.getText().toString();
                String alternateNumberWithCountryCode = etSubCountryCode.getText().toString() + etAlternateNumber.getText().toString();
                String alternateNumber = etAlternateNumber.getText().toString();
                String landMark = etLandmark.getText().toString();
                String postalAddress = etPostalAddress.getText().toString();
                shippingAddressPresenter.postShippingAddress(firstName, lastName, emailAddress, mobileNumber, alternateNumber, mobileNumberWithCountryCode,
                        alternateNumberWithCountryCode, landMark, postalAddress, String.valueOf(latitude),
                        String.valueOf(longitude), pinCode,etCountryCode.getText().toString(),etSubCountryCode.getText().toString());
            } else {
                ordSelfPickup = "1";
                //move payment screen
                moveToPaymentScreen();
            }


        }
    }

    private void moveToPaymentScreen() {
        firstName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();
        emailId = etEmailId.getText().toString();
        mobileNumber = etMobileNumber.getText().toString();
        alternateNumber = etAlternateNumber.getText().toString();
        postalAddress = etPostalAddress.getText().toString();
        landmark = etLandmark.getText().toString();
        //updateLandmark(landmark);
        if(!landmark.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putString(TOTAL_AMOUNT_USD, totalAmountUSD);
            bundle.putString(TOTAL_AMOUNT, totalAmount);
            bundle.putString(DELIVERY_FEE, deliveryFee);
            bundle.putString(EXTRA_CHARGE, extraFee);
            bundle.putString(PEEK_INFO, peekHourInfo);
            bundle.putBoolean(IS_PEAK_HOUR, isPeakHour);
            bundle.putString(CURRENCY_SYMBOL, currencySymbol);
            bundle.putString(SUB_TOTAL, subTotal);
            bundle.putString(TOTAL_TAX, totalTax);
            bundle.putString(FIRST_NAME, firstName);
            bundle.putString(LAST_NAME, lastName);
            bundle.putString(EMAIL_ID, emailId);
            bundle.putString(MOBILE_NUMBER, etCountryCode.getText().toString() + mobileNumber);
            bundle.putString(ALTERNATE_NUMBER, etSubCountryCode.getText().toString() + alternateNumber);
            bundle.putString(LAND_MARK, landmark);
            bundle.putString(POSTAL_ADDRESS, postalAddress);
            bundle.putString(LATITUDE, String.valueOf(latitude));
            bundle.putString(LONGITUDE, String.valueOf(longitude));
            bundle.putString(PINCODE, pinCode);
            bundle.putString(ORD_SELF_PICKUP, ordSelfPickup);
            changeActivityExtras(Payment.class, bundle);
        }else{
            showToast(getString(R.string.enter_flat_no_landmark));
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_shipping_address;
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
        if (message.equals(NO_RECORDS_FOUND)) {
            nestedScrollView.setVisibility(View.VISIBLE);
        } else {
            tvError.setVisibility(View.VISIBLE);
            nestedScrollView.setVisibility(View.GONE);
            tvError.setText(message);
        }
    }

    @Override
    public void showError(int message) {
        tvError.setVisibility(View.VISIBLE);
        nestedScrollView.setVisibility(View.GONE);
        tvError.setText(getResources().getString(message));
    }


    @Override
    public void showCountryList(CountryList countryList) {
        CountryAdapter adapter = new CountryAdapter(this,
                R.layout.list_item_restaruant_subcategory, countryList.getAllCountryCodeList());
        spinnerCountry.setAdapter(adapter);
        spinnerSubCountry.setAdapter(adapter);
        spinnerCountry.setSelection(0,false);
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String countryDial = countryList.getAllCountryCodeList().get(pos).getCountryDial();
                etCountryCode.setText(countryDial);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerSubCountry.setSelection(0,false);
        spinnerSubCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String countryDial = countryList.getAllCountryCodeList().get(pos).getCountryDial();
                etSubCountryCode.setText(countryDial);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        shippingAddressPresenter.requestShippingAddress();
    }

    @Override
    public void showShippingResponse(ShippingAddressResponse shippingAddressResponse) {
        tvSelfPickupLabel.setVisibility(shippingAddressResponse.getSelfPickupStatus().equals("0") ? View.GONE : View.VISIBLE);
        radioGroup.setVisibility(shippingAddressResponse.getSelfPickupStatus().equals("0") ? View.GONE : View.VISIBLE);
        nestedScrollView.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
        firstName = shippingAddressResponse.getShCusFname();
        lastName = shippingAddressResponse.getShCusLname();
        emailId = shippingAddressResponse.getShCusEmail();
        mobileNumber = shippingAddressResponse.getShPhone1Only();
        alternateNumber = shippingAddressResponse.getShPhone2Only();
        postalAddress = shippingAddressResponse.getShLocation();
        btnContinue.setText(shippingAddressResponse.getEmailVerificationStatus().equals("1")?VERIFY_EMAIL:SUBMIT);


        etFirstName.setText(firstName.equals("") ? appRepository.getUserName() : firstName);
        etLastName.setText(lastName);
        etEmailId.setText(emailId.equals("")  ? appRepository.getUserEmail() : emailId );
        etMobileNumber.setText(mobileNumber.equals("") ? appRepository.getUserPhoneNo() :  mobileNumber);
        etAlternateNumber.setText(alternateNumber);
        etPostalAddress.setText(postalAddress);
        etLandmark.setText(shippingAddressResponse.getShLocation1()!=null?shippingAddressResponse.getShLocation1():"");

        pinCode = shippingAddressResponse.getShZipcode();
        latitude = Double.valueOf(shippingAddressResponse.getShLatitude());
        longitude = Double.valueOf(shippingAddressResponse.getShLongitude());
        etSubCountryCode.setText(shippingAddressResponse.getSubCountryCode().isEmpty()?THAI_CODE:"" + shippingAddressResponse.getSubCountryCode());
        etCountryCode.setText(shippingAddressResponse.getCountryCode().isEmpty()?THAI_CODE:"" + shippingAddressResponse.getCountryCode());

        rbDelivery.performClick();
    }

    @Override
    public void showSuccess(String message) {
        moveToPaymentScreen();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int selfPickUpId) {
        RadioButton checkedRadioButton = group.findViewById(selfPickUpId);
        boolean isChecked = checkedRadioButton.isChecked();
        if (isChecked) {
            if (checkedRadioButton.getText().equals(getString(R.string.self_pick_up))) {
                rbDelivery.setTextColor(getResources().getColor(R.color.edit_txt_hint));
                rbCollection.setTextColor(getResources().getColor(R.color.colorAccent));
                ordSelfPickup = "1";
                btnContinue.setText(getResources().getString(R.string.continue_txt));
                recyclerView.setVisibility(View.VISIBLE);
                llShippingAddressView.setVisibility(View.GONE);
            } else {
                rbDelivery.setTextColor(getResources().getColor(R.color.colorAccent));
                rbCollection.setTextColor(getResources().getColor(R.color.edit_txt_hint));
                recyclerView.setVisibility(View.GONE);
                llShippingAddressView.setVisibility(View.VISIBLE);
                ordSelfPickup = "0";
                btnContinue.setText(getResources().getString(R.string.continue_txt));
            }
        }
    }

    @Override
    public void showErrorDialog(String message) {
        showToast(message);
    }

    @Override
    public void showErrorDialog(int message) {
        showToast(message);
    }

    @Override
    public void showOtpDialog(String otp,String msg,boolean isEmailVerifyOnly) {
        otpVerifyPopup(otp,msg,isEmailVerifyOnly);
    }

    @Override
    public void successInToast(String msg) {
        btnContinue.setText(SUBMIT);
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void otpVerifyPopup(String otp,String msg,boolean isEmailVerifyOnly) {
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_otp_verify);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final Button btnVerify = dialog.findViewById(R.id.btn_verify);
        final PinView etOtp = dialog.findViewById(R.id.et_otp);
        final TextView etOtpMsg = dialog.findViewById(R.id.response_msg);
        etOtp.setAnimationEnable(true);
        etOtpMsg.setVisibility(View.VISIBLE);
        etOtpMsg.setText(msg);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        btnVerify.setOnClickListener(v -> {
            if (etOtp.getText().toString().length() < 6) {
                Toast.makeText(context, getResources().getString(R.string.please_enter_otp), Toast.LENGTH_SHORT).show();
            } else if(!isEmailVerifyOnly){
                hideKeyboard(v);
                dialog.dismiss();
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String emailAddress = etEmailId.getText().toString();
                String mobileNumber = etCountryCode.getText().toString() + etMobileNumber.getText().toString();
                String alternateNumber = etSubCountryCode.getText().toString() + etAlternateNumber.getText().toString();
                String postalAddress = etPostalAddress.getText().toString();
                ShippingAddressRequest addressRequest=new ShippingAddressRequest();
                addressRequest.setOtp(otp);
                addressRequest.setSh_cus_email(emailAddress);
                addressRequest.setSh_cus_fname(firstName);
                addressRequest.setSh_cus_lname(lastName);
                addressRequest.setSh_latitude(String.valueOf(latitude));
                addressRequest.setSh_longitude(String.valueOf(longitude));
                addressRequest.setCurrent_otp(etOtp.getText().toString());
                addressRequest.setLang(appRepository.getLanguageCode());
                addressRequest.setSh_phone1(mobileNumber);
                addressRequest.setSh_phone2(alternateNumber);
                addressRequest.setSh_location(postalAddress);
                addressRequest.setSh_zipcode(pinCode);
                addressRequest.setSh_phone1_code(etCountryCode.getText().toString());
                addressRequest.setSh_phone2_code(etSubCountryCode.getText().toString());
                shippingAddressPresenter.requestShippingAddressWithOtp(addressRequest);
            }else{
                hideKeyboard(v);
                dialog.dismiss();
                String emailAddress = etEmailId.getText().toString();
                CheckVerificationRequest verificationRequest=new CheckVerificationRequest(appRepository.getLanguageCode(),emailAddress,otp,etOtp.getText().toString(),"shipping_address");
                shippingAddressPresenter.verifyCode(verificationRequest);
            }
        });
        dialog.show();
    }


    private void updateLandmark(String updateLandmark) {
        if (updateLandmark.length() != 0) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(LANDMARK, updateLandmark);
            editor.apply();
        }
    }

}
