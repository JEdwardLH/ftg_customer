package com.foodtogo.user.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.foodtogo.user.R;
import com.foodtogo.user.data.source.AppDataSource;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.data.source.sharedpreference.AppPreferenceDataSource;
import com.foodtogo.user.ui.home.activity.Home;
import com.foodtogo.user.ui.login.activity.Login;
import com.foodtogo.user.ui.payment.activity.Payment;
import com.foodtogo.user.ui.settings.activity.SettingMenuActivity;
import com.foodtogo.user.util.NetworkUtils;

import java.util.Locale;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements AppConstants {

    protected Context context;
    protected ProgressDialog progressDialog;
    protected AppRepository appRepository;
    protected AppDataSource appDataSource;
    protected TextView tvTitle;
    protected Dialog progressView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        appDataSource = new AppPreferenceDataSource(this);
        appRepository = new AppRepository(appDataSource);
        context = this;
        setLanguage();
    }
    public void setLanguage() {
        // Create a new Locale object
        LocaleHelper.setLocale(this, appRepository.getLanguageCode());

    }



    public abstract @LayoutRes
    int getLayout();


    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void setRecyclerViewAnimation(RecyclerView recyclerView) {
        LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom);
        recyclerView.setLayoutAnimation(controller);
    }

    public void startRecyclerAnimation(RecyclerView recyclerView) {
        recyclerView.scheduleLayoutAnimation();
    }


    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showProgressDialog() {
        try {
            if (progressDialog != null) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading_wait));
            progressDialog.setCancelable(false);
            progressDialog.show();
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    public void hideProgressDialog() {
        try {
            if (progressDialog != null)
                progressDialog.dismiss();
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }


    public void showProgress() {
        try {
            progressView = new Dialog(this, R.style.AppCompatProgressStyle);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_progress, null);
            progressView.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressView.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressView.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            progressView.setCancelable(false);
            progressView.setContentView(view);
            progressView.show();
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    public void hideProgress() {
        try {
            if (progressView != null)
                progressView.dismiss();
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }


    public void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        tvTitle = toolbar.findViewById(R.id.tv_tool_title);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_foodtogo);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                hideKeyboard();
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showToast(String message) {
        final Dialog dialog = new Dialog(context, R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        final TextView tvMessage = dialog.findViewById(R.id.tv_message);
        final Button btnGo = dialog.findViewById(R.id.btn_go);
        dialog.setCancelable(false);
        tvTitle.setText(getString(R.string.warning));
        tvMessage.setText(message);
        if (message.equals(MESSAGE_ORDER_PLACED)) {
            tvTitle.setText(getResources().getString(R.string.success));
        } else if (message.equals(MESSAGE_CART_ADDED)) {
            tvTitle.setText(getResources().getString(R.string.success));
        } else if (message.equals(MESSAGE_PROFILE_ADDED)) {
            tvTitle.setText(getResources().getString(R.string.success));
        } else if (message.equals(MESSAGE_PROFILE_UPDATED)) {
            tvTitle.setText(getResources().getString(R.string.success));
        } else if (message.equals(MESSAGE_TOKEN_IS_EXPIRED)) {
            tvTitle.setText(getResources().getString(R.string.session_expired_title));
            tvMessage.setText(getResources().getString(R.string.session_expired_message));
        }
        btnGo.setOnClickListener(v -> {
            dialog.dismiss();
            if (message.equals(MESSAGE_TOKEN_IS_EXPIRED)) {
                appRepository.saveIsLoggedIn(false);
                changeActivityClearBackStack(Login.class);
            } else if (message.equals(MESSAGE_ORDER_PLACED)) {
                Bundle bundle = new Bundle();
                bundle.putInt(TAB_POSITION, TAB_HOME);
                changeActivityClearBackStack(Home.class, bundle);
            }
        });
        dialog.show();
    }

    public void showSuccessDialog(String message) {
        try {
            final Dialog dialog = new Dialog(context, R.style.DefaultDialog);
            dialog.setContentView(R.layout.dialog_info);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCancelable(false);
            final TextView tvTitle = dialog.findViewById(R.id.tv_title);
            final TextView tvMessage = dialog.findViewById(R.id.tv_message);
            final Button btnGo = dialog.findViewById(R.id.btn_go);
            tvTitle.setText(getString(R.string.success));
            tvMessage.setText(message);
            btnGo.setOnClickListener(v -> {
                dialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putInt(TAB_POSITION, TAB_HOME);
                changeActivityClearBackStack(Home.class, bundle);

            });
            dialog.show();
        }catch (Exception e){

        }
    }


    public void showSuccessDialog(int status, String message) {
        final Dialog dialog = new Dialog(context, R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        final TextView tvMessage = dialog.findViewById(R.id.tv_message);
        final Button btnGo = dialog.findViewById(R.id.btn_go);
        dialog.setCancelable(false);
        tvMessage.setText(message);
        tvTitle.setText(getResources().getString(R.string.success));
        btnGo.setOnClickListener(v -> {
            dialog.dismiss();
            if (status == 1) {
                appRepository.saveIsLoggedIn(false);
                appRepository.setCartCount(0);
                changeActivityClearBackStack(Login.class);
            } else if (status == 2) {
                Bundle bundle = new Bundle();
                bundle.putInt(TAB_POSITION, 1);
                changeActivityClearBackStack(Home.class, bundle);
            } else if (status == 3) {
                finish();
            }
        });
        dialog.show();
    }

    public void showErrorDialog(String title, String message) {
        final Dialog dialog = new Dialog(context, R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        final TextView tvMessage = dialog.findViewById(R.id.tv_message);
        final Button btnGo = dialog.findViewById(R.id.btn_go);
        tvTitle.setText(title);
        tvMessage.setText(message);
        btnGo.setOnClickListener(v -> {
            dialog.dismiss();
            Bundle bundle = new Bundle();
            bundle.putInt(TAB_POSITION, TAB_HOME);


        });
        dialog.show();
    }

    public void showErrorDialog(int status, String message) {
        final Dialog dialog = new Dialog(context, R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        final TextView tvMessage = dialog.findViewById(R.id.tv_message);
        final Button btnGo = dialog.findViewById(R.id.btn_go);
        dialog.setCancelable(false);
        tvMessage.setText(message);
        tvTitle.setText(getResources().getString(R.string.warning));
        btnGo.setOnClickListener(v -> {
            dialog.dismiss();
            if (status == 1) {
                finish();
            } else if (status == 2) {
                Bundle bundle = new Bundle();
                bundle.putInt(TAB_POSITION, 2);
                changeActivityClearBackStack(Home.class, bundle);
            } else if (status == 3) {
                Bundle bundle = new Bundle();
                bundle.putString(TITLE, "Payment Settings");
                bundle.putInt(TAB_POSITION, 6);
                changeActivityForResultExtras(SettingMenuActivity.class, Payment.REQUEST_PAYMENT, bundle);
            }
        });
        dialog.show();
    }


    public void showToast(@StringRes int strRes) {
        final Dialog dialog = new Dialog(context, R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        final TextView tvMessage = dialog.findViewById(R.id.tv_message);
        final Button btnGo = dialog.findViewById(R.id.btn_go);
        dialog.setCancelable(false);
        tvMessage.setText(strRes);
        tvTitle.setText(getResources().getString(R.string.warning));
        btnGo.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }


    public void changeActivity(Class clz) {
        Intent i = new Intent(this, clz);
        startActivity(i);
    }

    public void changeActivityWithFinish(Class clz) {
        Intent i = new Intent(this, clz);
        startActivity(i);
        finish();
    }

    public void changeActivityExtras(Class clz, Bundle bundle) {
        Intent i = new Intent(this, clz);
        i.putExtras(bundle);
        startActivity(i);
    }

    public void changeActivityForResult(Class clz, int requestCode) {
        Intent i = new Intent(this, clz);
        startActivityForResult(i, requestCode);
    }

    public void changeActivityForResultExtras(Class clz, int requestCode, Bundle bundle) {
        Intent i = new Intent(this, clz);
        i.putExtras(bundle);
        startActivityForResult(i, requestCode);
    }

    public void changeActivityClearBackStack(Class clz) {
        Intent i = new Intent(this, clz);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    public void changeActivityClearBackStack(Class clz, Bundle bundle) {
        Intent i = new Intent(this, clz);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtras(bundle);
        startActivity(i);
    }


}