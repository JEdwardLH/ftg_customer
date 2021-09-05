package com.foodtogo.user.ui.track.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.foodtogo.user.R;
import com.foodtogo.user.util.GlideUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DriverInfoFragment extends BottomSheetDialogFragment {

    @BindView(R.id.iv_driver_profile)
    ImageView ivDriverProfile;

    @BindView(R.id.tv_call)
    TextView tvCall;

    @BindView(R.id.tv_eta_time)
    TextView tvEtaTime;

    @BindView(R.id.tv_driver_name)
    TextView tvDriverName;

    @BindView(R.id.btn_call)
    Button btnCall;

    public static final String IMAGE = "image";
    public static final String CALL_NUMBER = "callNumber";
    public static final String DRIVER_NAME = "driverName";
    public static final String ESM_TIME = "esmTime";
    public static final String BTN_TEXT = "btnText";

    private BottomSheetBehavior.BottomSheetCallback
            mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.BottomSheetTheme);
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, R.style.BottomSheetTheme);
        View contentView = View.inflate(getContext(), R.layout.layout_driver_info, null);
        ButterKnife.bind(this, contentView);
        dialog.setContentView(contentView);
        GlideUtils.showRoundImage(getActivity(), ivDriverProfile, R.drawable.profile_default_pic, getArguments().getString(IMAGE));
        tvCall.setText(getArguments().getString(CALL_NUMBER));
        tvDriverName.setText(getArguments().getString(DRIVER_NAME));
        tvEtaTime.setText(getArguments().getString(ESM_TIME));
        btnCall.setText(getArguments().getString(BTN_TEXT));
        if (getArguments().getString(CALL_NUMBER).equals("")) {
            btnCall.setEnabled(false);
            btnCall.setAlpha(0.6f);
        }

    }

    @OnClick(R.id.iv_close)
    public void setCloseDialog() {
        dismiss();
    }


    @OnClick(R.id.btn_call)
    public void setCallDialog() {
        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + tvCall.getText().toString()));
                        startActivity(intent);
                        dismiss();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            btnCall.performClick();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }


}

