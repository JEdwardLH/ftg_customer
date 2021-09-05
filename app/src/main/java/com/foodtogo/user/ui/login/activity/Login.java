/*******************************************************************************
 * Created by Pofi Technologies on 02-02-17.
 * <p>
 * Copyright (c) 2017 Pofi Technologies. All rights reserved.
 *******************************************************************************/

package com.foodtogo.user.ui.login.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.foodtogo.user.R;
import com.foodtogo.user.base.AppConstants;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.model.login.LoginResponse;
import com.foodtogo.user.ui.address.activity.AddAddress;
import com.foodtogo.user.ui.forgotpassword.activity.ForgotPassword;
import com.foodtogo.user.ui.login.mvp.LoginContractor;
import com.foodtogo.user.ui.login.mvp.LoginPresenter;
import com.foodtogo.user.ui.register.activity.Register;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
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

public class Login extends BaseActivity implements LoginContractor.View {


    //initialize the widgets
    @BindView(R.id.et_username)
    AppCompatEditText etUserName;

    @BindView(R.id.et_password)
    AppCompatEditText etPassword;

    @BindView(R.id.btn_fb)
    LoginButton loginButton;


    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "Login";

    //facebook call back manager
    CallbackManager callbackManager;

    //login presenter
    LoginPresenter loginPresenter;


    /* onCreate Method */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter = new LoginPresenter(this, appRepository);
        initGoogleLogin();
        initFacebookLogin();
    }


    @Override
    public int getLayout() {
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        return R.layout.activity_login;
    }


    @OnClick(R.id.btn_login)
    public void setBtnLogin() {
        hideKeyboard();
        if (isNetworkConnected())
            loginPresenter.loginButtonClicked(etUserName.getText().toString().trim(), etPassword.getText().toString().trim());
        else
            showToast(R.string.no_internet_connection);
    }


    @OnClick(R.id.tv_sign_up)
    public void setBtnRegister() {
        changeActivity(Register.class);
    }


    @OnClick(R.id.tv_forgot_password)
    public void setForgotPassword() {
        changeActivity(ForgotPassword.class);
    }


    @OnClick(R.id.iv_google_plus)
    public void setGooglePlus() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @OnClick(R.id.iv_facebook)
    public void setFacebookLogin() {
        loginButton.performClick();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.close();
    }


    @Override
    public void showEmailEmptyError() {
        showToast(R.string.warning_empty_email_number);
    }

    @Override
    public void showMobileNumberError() {
        showToast(R.string.warning_valid_email_number);
    }


    @Override
    public void showNotValidEmailError() {
        showToast(R.string.warning_invalid_email);
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
    public void showLoginResponse(LoginResponse loginResponse) {
        appRepository.setCartCount(0);
        hideKeyboard();
        Bundle bundle = new Bundle();
        bundle.putString(FROM_CLASS, LANDING);
        changeActivityExtras(AddAddress.class, bundle);
        finish();
    }


    /* method call show progress view  */
    @Override
    public void showLoadingView() {
        showProgressDialog();
    }

    /* method call show hide view  */
    @Override
    public void hideLoadingView() {
        hideProgressDialog();
    }

    /* method call API error   */
    @Override
    public void showError(String message) {
        showToast(message);
    }

    /* method call API Connection error   */
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
            Log.w(TAG, "signInResult" + e.getMessage());
            updateUI(null);
        }
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            loginPresenter.loginViaGoogle(account.getDisplayName(), account.getEmail(), account.getId());
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
                            loginPresenter.loginViaFacebook(object);
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
                Toast.makeText(Login.this, getResources().getString(R.string.warning_empty_email), Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                Toast.makeText(Login.this, getResources().getString(R.string.warning_invalid_email), Toast.LENGTH_SHORT).show();
            } else {
                dialog.dismiss();
                String email = etEmail.getText().toString().trim();
                loginPresenter.loginViaFacebook(name, email, facebookId);
                LoginManager.getInstance().logOut();
            }
        });
        dialog.show();

    }


}
