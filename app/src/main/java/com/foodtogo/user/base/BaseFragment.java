package com.foodtogo.user.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.foodtogo.user.BaseApplication;
import com.foodtogo.user.R;
import com.foodtogo.user.data.source.AppDataSource;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.data.source.sharedpreference.AppPreferenceDataSource;
import com.foodtogo.user.ui.home.activity.Home;
import com.foodtogo.user.ui.login.activity.Login;

import butterknife.ButterKnife;

import static com.foodtogo.user.base.AppConstants.EXCEPTION;
import static com.foodtogo.user.base.AppConstants.MESSAGE_TOKEN_IS_EXPIRED;
import static com.foodtogo.user.base.AppConstants.TAB_HOME;
import static com.foodtogo.user.base.AppConstants.TAB_POSITION;


public abstract class BaseFragment extends Fragment {

    protected ProgressDialog progressDialog;
    protected Context context;
    protected AppRepository appRepository;
    protected AppDataSource appDataSource;
    protected Dialog progressView;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanseState) {
        View view = provideYourFragmentView(inflater, parent, savedInstanseState);
        context = this.getActivity();
        appDataSource = new AppPreferenceDataSource(getActivity());
        appRepository = new AppRepository(appDataSource);
        ButterKnife.bind(this, view);
        return view;
    }

    public abstract View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);


    public void showProgressDialog() {
        try {
            progressDialog = new ProgressDialog(context);
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

    public void setRecyclerViewAnimation(RecyclerView recyclerView) {
        LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom);
        recyclerView.setLayoutAnimation(controller);
    }

    public void startRecyclerAnimation(RecyclerView recyclerView) {
        recyclerView.scheduleLayoutAnimation();
    }

    public void showProgress() {
        try {
            progressView = new Dialog(getActivity(), R.style.AppCompatProgressStyle);
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_progress, null);
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


    public void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void showToast(String message) {
        try {
            final Dialog dialog = new Dialog(context, R.style.DefaultDialog);
            dialog.setContentView(R.layout.dialog_info);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCancelable(false);
            final TextView tvTitle = dialog.findViewById(R.id.tv_title);
            final TextView tvMessage = dialog.findViewById(R.id.tv_message);
            final Button btnGo = dialog.findViewById(R.id.btn_go);
            tvTitle.setText(getString(R.string.warning));
            tvMessage.setText(message);
            if (message.equals(MESSAGE_TOKEN_IS_EXPIRED)) {
                tvTitle.setText(getResources().getString(R.string.session_expired_title));
                tvMessage.setText(getResources().getString(R.string.session_expired_message));
            }
            btnGo.setOnClickListener(v -> {
                dialog.dismiss();
                if (message.equals(MESSAGE_TOKEN_IS_EXPIRED)) {
                    appRepository.saveIsLoggedIn(false);
                    changeActivityClearBackStack(context, Login.class);
                }
            });
            dialog.show();
        }catch (Exception e){

        }
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
                changeActivityClearBackStack(getActivity(), Home.class, bundle);

            });
            dialog.show();
        }catch (Exception e){

        }
    }

    public void showErrorDialog(String title, String message) {
        try{
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
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
            changeActivityClearBackStack(getActivity(), Home.class, bundle);

        });
        dialog.show();
        }catch (Exception e){

        }
    }

    public void showSuccessDialog(String message, int type) {
        try{
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
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
            if (type == 1) {
                getActivity().finish();
            } else if (type == 2) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        });
        dialog.show();
        }catch (Exception e){

        }
    }

    public void showToast(@StringRes int strRes) {
        try {
            final Dialog dialog = new Dialog(context, R.style.DefaultDialog);
            dialog.setContentView(R.layout.dialog_info);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCancelable(false);
            final TextView tvTitle = dialog.findViewById(R.id.tv_title);
            final TextView tvMessage = dialog.findViewById(R.id.tv_message);
            final Button btnGo = dialog.findViewById(R.id.btn_go);
            tvTitle.setText(BaseApplication.getContext().getResources().getString(R.string.warning));
            tvMessage.setText(BaseApplication.getContext().getResources().getString(strRes));
            btnGo.setOnClickListener(v -> {
                dialog.dismiss();
            });
            dialog.show();
        }catch (Exception e){

        }
    }

    public void changeActivity(Context context, Class clz) {
        Intent i = new Intent(context, clz);
        startActivity(i);
    }

    public void changeActivityExtras(Context context, Class clz, Bundle bundle) {
        Intent i = new Intent(context, clz);
        i.putExtras(bundle);
        startActivity(i);
    }

    public void changeActivityClearBackStack(Context context, Class clz) {
        Intent i = new Intent(context, clz);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    public void changeActivityClearBackStack(Context context, Class clz, Bundle bundle) {
        Intent i = new Intent(context, clz);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtras(bundle);
        startActivity(i);
    }



}