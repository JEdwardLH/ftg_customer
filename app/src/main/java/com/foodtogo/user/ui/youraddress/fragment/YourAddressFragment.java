package com.foodtogo.user.ui.youraddress.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alium.nibo.models.NiboSelectedPlace;
import com.alium.nibo.placepicker.NiboPlacePickerActivity;
import com.alium.nibo.utils.AddressConstants;
import com.alium.nibo.utils.NiboConstants;
import com.alium.nibo.utils.NiboStyle;
import com.chaos.view.PinView;
import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.model.country.CountryList;
import com.foodtogo.user.model.shippingaddress.ShippingAddressRequest;
import com.foodtogo.user.model.shippingaddress.ShippingAddressResponse;
import com.foodtogo.user.ui.manageaddress.activity.ManageAddressActivity;
import com.foodtogo.user.ui.register.adapter.CountryAdapter;
import com.foodtogo.user.ui.youraddress.mvp.YourAddressContractor;
import com.foodtogo.user.ui.youraddress.mvp.YourAddressPresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;
import static com.alium.nibo.placepicker.NiboPickerFragment.MULTI_ADDRESS_REQUEST_CODE;
import static com.alium.nibo.placepicker.NiboPlacePickerActivity.setBuilder;
import static com.alium.nibo.utils.NiboConstants.TAB_POSITION;
import static com.foodtogo.user.base.AppConstants.HOME_ADDRESS;
import static com.foodtogo.user.base.AppConstants.NO_RECORDS_FOUND;


public class YourAddressFragment extends BaseFragment implements YourAddressContractor.View {


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

    @BindView(R.id.et_landmark)
    EditText etLandMark;

    @BindView(R.id.et_postal_address)
    EditText etPostalAddress;

    @BindView(R.id.tv_error)
    TextView tvError;

    @BindView(R.id.tv_address_type)
    TextView tvAddressType;

    @BindView(R.id.ll_shipping_address_view)
    LinearLayout llShippingAddressView;

    double latitude = 0.0;
    double longitude = 0.0;
    String pinCode = "";
    String THAI_PHONE_CODE="+63";


    private static final int LOCATION_REQUEST_CODE = 300;
    private int addressType=HOME_ADDRESS;


    public YourAddressFragment() {
        // Required empty public constructor
    }

    YourAddressPresenter yourAddressPresenter;
    SharedPreferences prefs;
    private String MY_PREFS_NAME = "Pref Name";
    private String LANDMARK = "landMark";

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_your_address, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        yourAddressPresenter = new YourAddressPresenter(this, appRepository);
        llShippingAddressView.setVisibility(View.GONE);
        prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        yourAddressPresenter.requestCountry();
        System.out.println("addressType:"+appRepository.getAddressType());
        tvAddressType.setText(appRepository.getAddressType().equals("1")?
                getString(R.string.nav_home):
                appRepository.getAddressType().equals("2")?getString(R.string.key_work):getString(R.string.others));
    }



    @OnClick(R.id.et_postal_address)
    public void setAddressClick() {
        Intent intent = new Intent(getActivity(), NiboPlacePickerActivity.class);
        intent.putExtra("address",appDataSource.getSearchLocation());
        intent.putExtra("cart_count",String.valueOf(appDataSource.getCartCount()));
        NiboPlacePickerActivity.NiboPlacePickerBuilder config = new NiboPlacePickerActivity.NiboPlacePickerBuilder()
                .setSearchBarTitle(getString(R.string.set_delivery_location))
                .setConfirmButtonTitle(getString(R.string.confirm_location))
                .setMarkerPinIconRes(R.drawable.ic_map_marker_def)
                .setStyleEnum(NiboStyle.HOPPER);
        setBuilder(config);
        startActivityForResult(intent, LOCATION_REQUEST_CODE);
    }

    @OnClick(R.id.choose_location)
    void chooseSavedLocations(){
        Intent intent = new Intent(getActivity(),ManageAddressActivity.class);
        startActivityForResult(intent,MULTI_ADDRESS_REQUEST_CODE);
    }


    @OnClick(R.id.btn_continue)
    public void setContinue() {
        if(isLocationUpdated && appDataSource.getCartCount()>0){
            showAddressChangeInfo(view -> saveShippingAddress());
            return;
        }
        saveShippingAddress();
    }

    void saveShippingAddress(){
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String emailAddress = etEmailId.getText().toString();
        String mobileNumber = etCountryCode.getText().toString() + etMobileNumber.getText().toString();
        String alternateNumber = etSubCountryCode.getText().toString() + etAlternateNumber.getText().toString();
        String postalAddress = etPostalAddress.getText().toString();
        String landmark = etLandMark.getText().toString();
        updateLandmark(landmark);

        yourAddressPresenter.postShippingAddress(firstName, lastName, emailAddress, etMobileNumber.getText().toString(), mobileNumber,
                alternateNumber, landmark, postalAddress, String.valueOf(latitude), String.valueOf(longitude),
                pinCode,etCountryCode.getText().toString(),etSubCountryCode.getText().toString());
    }

    private void showAddressChangeInfo(View.OnClickListener onClickListener) {
        final Dialog dialog = new Dialog(getActivity(),R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_skip_continue);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvMessage = dialog.findViewById(R.id.tv_message);
        final Button btnOk = dialog.findViewById(R.id.btn_skip);
        final Button btnCancel = dialog.findViewById(R.id.btn_continue);
        dialog.setCancelable(false);
        tvMessage.setText(getString(R.string.user_address_change_info));
        btnOk.setText(getString(R.string.ok));
        btnCancel.setText(getString(R.string.cancel));
        btnOk.setOnClickListener(v -> {
            dialog.dismiss();
            onClickListener.onClick(null);
        });

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
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
    public void showWarning(int message) {
        showToast(getString(message));
    }

    @Override
    public void showOtpDialog(String otp,String msg) {
        /*ToDo show otp dialog*/
        otpVerifyPopup(otp,msg);
    }

    @Override
    public void mobileEmailError(String msg) {
        showToast(msg);
    }


    @Override
    public void showError(String message) {
        if (message.equals(NO_RECORDS_FOUND)) {
            llShippingAddressView.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
        } else {
            llShippingAddressView.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(message);
        }
    }

    @Override
    public void showError(int message) {
        llShippingAddressView.setVisibility(View.GONE);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(getResources().getString(message));
    }


    boolean isLocationUpdated = false;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == LOCATION_REQUEST_CODE) {
            NiboSelectedPlace selectedPlace = data.getParcelableExtra(NiboConstants.SELECTED_PLACE_RESULT);
            if(selectedPlace.getPlaceAddress().contains("#")){
                String[] address = selectedPlace.getPlaceAddress().split("#");
                etPostalAddress.setText(address[0]);
                etLandMark.setText(address[1]);
            }else{
                etPostalAddress.setText(selectedPlace.getPlaceAddress());
                etLandMark.setText("");
            }

            latitude = selectedPlace.getLatitude();
            longitude = selectedPlace.getLongitude();
            pinCode = AddressConstants.PIN_CODE;
        }else if (resultCode == Activity.RESULT_OK && requestCode == MULTI_ADDRESS_REQUEST_CODE) {
            if(data!=null){
                isLocationUpdated = true;
                 latitude=Double.valueOf(data.getStringExtra(NiboConstants.NIBO_LATITUDE));
                 longitude=Double.valueOf(data.getStringExtra(NiboConstants.NIBO_LONGITUDE));
                String[] address = data.getStringExtra(NiboConstants.SELECTED_PLACE_RESULT).split("#");
                etPostalAddress.setText(address[0]);
                etLandMark.setText(address[1]);
            }
        }
        if(data!=null){
            addressType=data.getIntExtra(NiboConstants.NIBO_ADDRESS_TYPE,1);
            tvAddressType.setText(addressType==1?getString(R.string.nav_home):addressType==2?getString(R.string.key_work):getString(R.string.others));
        }
    }

    @Override
    public void showCountryList(CountryList countryList) {
        CountryAdapter adapter = new CountryAdapter(getActivity(),
                R.layout.list_item_restaruant_subcategory, countryList.getAllCountryCodeList());
        spinnerCountry.setAdapter(adapter);
        spinnerSubCountry.setAdapter(adapter);
        //set Spinner
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

        yourAddressPresenter.requestShippingAddress();
    }
   /* @OnClick(R.id.et_country_code)
    public void setClickCountry() {
        spinnerCountry.performClick();
    }

    @OnClick(R.id.et_sub_country_code)
    public void setClickSubCountry(){
        spinnerSubCountry.performClick();
    }*/


    @Override
    public void showShippingResponse(ShippingAddressResponse shippingAddressResponse) {
        tvError.setVisibility(View.GONE);
        llShippingAddressView.setVisibility(View.VISIBLE);
        etFirstName.setText( shippingAddressResponse.getShCusFname());
        etLastName.setText(shippingAddressResponse.getShCusLname());
        etEmailId.setText(shippingAddressResponse.getShCusEmail() );
        etMobileNumber.setText(shippingAddressResponse.getShPhone1Only());
        etAlternateNumber.setText(shippingAddressResponse.getShPhone2Only());
        etPostalAddress.setText(shippingAddressResponse.getShLocation());
        pinCode = shippingAddressResponse.getShZipcode();
        latitude = Double.valueOf(shippingAddressResponse.getShLatitude());
        longitude = Double.valueOf(shippingAddressResponse.getShLongitude());
        etLandMark.setText( shippingAddressResponse.getShLocation1()!=null?shippingAddressResponse.getShLocation1():"");

        etSubCountryCode.setText(shippingAddressResponse.getSubCountryCode().isEmpty()?THAI_PHONE_CODE:String.format("%s", shippingAddressResponse.getSubCountryCode()));
        etCountryCode.setText(shippingAddressResponse.getSubCountryCode().isEmpty()?THAI_PHONE_CODE:String.format("%s", shippingAddressResponse.getCountryCode()));
    }

    @Override
    public void showSuccess(String message) {
        appRepository.setAddressType(String.valueOf(addressType));
        showSuccessDialog(message);
    }

    @Override
    public void showErrorDialog(String message) {
        showSuccessDialog(message);
    }

    @Override
    public void showErrorDialog(int message) {
        showSuccessDialog(getString(message));
    }

    public void otpVerifyPopup(String otp,String msg) {
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
            } else {
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
                yourAddressPresenter.requestShippingAddressWithOtp(addressRequest);
            }
        });
        dialog.show();
    }

}
