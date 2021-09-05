package com.foodtogo.user.ui.referfriend.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.AppConstants;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.ui.referfriend.mvp.ReferFriendContractor;
import com.foodtogo.user.ui.referfriend.mvp.ReferFriendPresenter;

import butterknife.BindView;
import butterknife.OnClick;


public class ReferFriendFragment extends BaseFragment implements ReferFriendContractor.View, AppConstants {

    @BindView(R.id.tv_offer)
    TextView tvOffer;

    @BindView(R.id.et_email_id)
    EditText etEmailAddress;

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.tv_error)
    TextView tvError;

    @BindView(R.id.ll_refer_view)
    LinearLayout llReferView;


    public ReferFriendFragment() {
        // Required empty public constructor
    }

    ReferFriendPresenter referFriendPresenter;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_refer_friend, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        referFriendPresenter = new ReferFriendPresenter(this, appRepository);
        referFriendPresenter.requestAppOffer();
        tvError.setVisibility(View.GONE);
        llReferView.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_submit)
    public void setReferView() {
        referFriendPresenter.requestReferFriend(etEmailAddress.getText().toString().trim());
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
    public void onSuccess(String message) {
        tvOffer.setText(message);
        llReferView.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
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
    public void PostReferError(String error) {
        showToast(error);
    }

    @Override
    public void PostRefer(String message) {
        showToast(message);
        etEmailAddress.setText("");
    }

    @Override
    public void showError(String message) {
        llReferView.setVisibility(View.GONE);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(message);
    }

    @Override
    public void showError(int message) {
        llReferView.setVisibility(View.GONE);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(getResources().getString(message));
    }

}