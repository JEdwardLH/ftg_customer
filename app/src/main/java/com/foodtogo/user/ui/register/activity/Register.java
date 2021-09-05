package com.foodtogo.user.ui.register.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.model.country.CountryList;
import com.foodtogo.user.model.login.LoginResponse;
import com.foodtogo.user.model.register.RegisterResponse;
import com.foodtogo.user.ui.address.activity.AddAddress;
import com.foodtogo.user.ui.register.adapter.CountryAdapter;
import com.foodtogo.user.ui.register.mvp.RegisterContractor;
import com.foodtogo.user.ui.register.mvp.RegisterPresenter;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;


public class Register extends BaseActivity implements RegisterContractor.View {

    @BindView(R.id.et_name)
    AppCompatEditText etName;

    @BindView(R.id.et_email)
    AppCompatEditText etEmail;

    @BindView(R.id.et_country_code)
    AppCompatEditText etCountryCode;

    @BindView(R.id.et_mobile_number)
    AppCompatEditText etMobileNumber;

    @BindView(R.id.et_password)
    AppCompatEditText etPassword;

    @BindView(R.id.spinner_country)
    Spinner spinCountry;

    @BindView(R.id.tv_referral_code)
    TextView tvReferralCode;

    @BindView(R.id.tv_apply)
    TextView tvApply;

    @BindView(R.id.btn_fb)
    LoginButton loginButton;


    RegisterPresenter registerPresenter;

    String referralCode = "";
    private static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient mGoogleSignInClient;

    private static final String TAG = "Login";

    //Facebook
    CallbackManager callbackManager;
    private static final String EMAIL = "email";

    String otp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerPresenter = new RegisterPresenter(this, appRepository);
        registerPresenter.requestCountry();
        initGoogleLogin();
        initFacebookLogin();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_register;
    }


    @OnClick(R.id.iv_google_plus)
    public void setGooglePlus() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @OnClick(R.id.back_action)
    void backAction(){
        finish();
    }

    @OnClick(R.id.iv_facebook)
    public void setFacebookLogin() {
        loginButton.performClick();
    }

    /*@OnClick(R.id.et_country_code)
    public void setClickCountry() {
        spinCountry.performClick();
    }*/


    @OnClick(R.id.btn_register)
    public void setRegister() {
        hideKeyboard();
        if (isNetworkConnected())
            registerPresenter.registerButtonClicked(etName.getText().toString().trim(),
                    etEmail.getText().toString().trim(),
                    etCountryCode.getText().toString().trim(),
                    etMobileNumber.getText().toString().trim(),
                    etPassword.getText().toString().trim(),
                    referralCode);
        else
            showToast(R.string.no_internet_connection);

    }


    @OnClick(R.id.tv_apply)
    public void setApply() {
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_input);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        final EditText etReferralCode = dialog.findViewById(R.id.et_input);
        final Button btnGo = dialog.findViewById(R.id.btn_go);
        tvTitle.setText(getString(R.string.referral_code));
        etReferralCode.setHint(getString(R.string.enter_apply_code));
        btnGo.setText(getString(R.string.apply));
        btnGo.setOnClickListener(v -> {
            if (etReferralCode.getText().toString().trim().equals("")) {
                Toast.makeText(Register.this, getResources().getString(R.string.enter_apply_code), Toast.LENGTH_SHORT).show();
            } else {
                dialog.dismiss();
                tvReferralCode.setText(getResources().getString(R.string.referral_code_txt));
                tvApply.setText(etReferralCode.getText().toString());
                referralCode = etReferralCode.getText().toString();
            }
        });
        dialog.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerPresenter.close();
    }


    @Override
    public void showNameEmptyError() {
        showToast(R.string.warning_empty_name);
    }

    @Override
    public void showEmailEmptyError() {
        showToast(R.string.warning_empty_email);
    }

    @Override
    public void showNotValidEmailError() {
        showToast(R.string.warning_invalid_email);
    }

    @Override
    public void showMobileNumberEmptyError() {
        showToast(R.string.warning_empty_mobile_number);
    }

    @Override
    public void showNotValidMobileNumberError() {
        showToast(R.string.warning_valid_mobile_number);
    }

    @Override
    public void showPasswordEmptyError() {
        showToast(R.string.warning_empty_password);
    }

    @Override
    public void showNotValidPasswordError() {
        showToast(R.string.warning_invalid_password);
    }

    @Override
    public void showRegisterResponse(RegisterResponse registerResponse) {
        hideKeyboard();
        Bundle bundle = new Bundle();
        bundle.putString(FROM_CLASS, LANDING);
        changeActivityClearBackStack(AddAddress.class, bundle);
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
    public void showError(String message) {
        showToast(message);
        if (message.equals(INVALID_REFERAL_CODE)) {
            tvApply.setText(getString(R.string.apply));
            referralCode = "";
        }
    }

    @Override
    public void showError(int message) {
        showToast(message);
    }

    private void initGoogleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            registerPresenter.registerViaGoogle(account.getDisplayName(), account.getEmail(), account.getId());
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, task -> {
                        Log.i(TAG, "Log out");
                    });
        }
    }


    private void initFacebookLogin() {
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(TAG, "onSuccess: " + loginResult.getAccessToken().getUserId());
                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                        (object, response) -> {
                            String jsonString = object.toString();
                            Log.i(TAG, "onSuccess: " + jsonString);
                            LoginManager.getInstance().logOut();
                            registerPresenter.registerViaFacebook(object);
                        });
                request.setParameters(getLoginBundle());
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

    }

    @NonNull
    private Bundle getLoginBundle() {
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, email, picture");
        return parameters;
    }


    @Override
    public void showFacebookDialog(String facebookId, String name) {
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_input);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        final EditText etEmail = dialog.findViewById(R.id.et_input);
        final Button btnGo = dialog.findViewById(R.id.btn_go);
        tvTitle.setText(getString(R.string.for_your_info));
        etEmail.setHint(getString(R.string.warning_empty_email));
        btnGo.setOnClickListener(v -> {
            if (etEmail.getText().toString().trim().equals("")) {
                Toast.makeText(Register.this, getResources().getString(R.string.warning_empty_email), Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                Toast.makeText(Register.this, getResources().getString(R.string.warning_invalid_email), Toast.LENGTH_SHORT).show();
            } else {
                dialog.dismiss();
                String email = etEmail.getText().toString().trim();
                registerPresenter.registerViaFacebook(name, email, facebookId);
                LoginManager.getInstance().logOut();
            }
        });
        dialog.show();

    }

    @Override
    public void showLoginResponse(LoginResponse loginResponse) {
        appRepository.setCartCount(0);
        hideKeyboard();
        Bundle bundle = new Bundle();
        bundle.putString(FROM_CLASS, LANDING);
        changeActivityExtras(AddAddress.class, bundle);
    }

    @Override
    public void showCountryList(CountryList countryList) {
        CountryAdapter adapter = new CountryAdapter(this,
                R.layout.list_item_restaruant_subcategory, countryList.getAllCountryCodeList());
        spinCountry.setAdapter(adapter);
        spinCountry.setSelection(0,false);
        spinCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String countryDial = countryList.getAllCountryCodeList().get(pos).getCountryDial();
                etCountryCode.setText(countryDial);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etCountryCode.setText(countryList.getCountryDetails().get(0).getCountryDial());
    }

    @Override
    public void showOtp(RegisterResponse registerOtpResponse) {
        otp = String.valueOf(registerOtpResponse.getOtp());
        otpVerifyPopup();
    }

    @Override
    public void otpError(String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        otpVerifyPopup();
    }


    public void otpVerifyPopup() {
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_otp_verify);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final Button btnVerify = dialog.findViewById(R.id.btn_verify);
        final PinView etOtp = dialog.findViewById(R.id.et_otp);
        final TextView etOtpMsg = dialog.findViewById(R.id.response_msg);
        //etOtp.setText(otp);
        etOtp.setAnimationEnable(true);
        etOtpMsg.setVisibility(View.VISIBLE);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        btnVerify.setOnClickListener(v -> {
            if (etOtp.getText().toString().length() < 6) {
                Toast.makeText(context, getResources().getString(R.string.please_enter_otp), Toast.LENGTH_SHORT).show();
            } else {
                hideKeyboard(v);
                dialog.dismiss();
                registerPresenter.registerWithOtp(etName.getText().toString().trim(),
                        etEmail.getText().toString().trim(),
                       "",
                        etMobileNumber.getText().toString().trim(),
                        etPassword.getText().toString().trim(),
                        referralCode, otp, etOtp.getText().toString());
            }
        });
        dialog.show();
    }

}
