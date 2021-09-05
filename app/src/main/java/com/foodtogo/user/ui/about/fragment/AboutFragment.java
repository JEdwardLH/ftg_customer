package com.foodtogo.user.ui.about.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.AppConstants;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.ui.about.mvp.AboutContractor;
import com.foodtogo.user.ui.about.mvp.AboutPresenter;

import butterknife.BindView;


public class AboutFragment extends BaseFragment implements AboutContractor.View, AppConstants {


    @BindView(R.id.tv_version_name)
    TextView tvVersionName;


    public AboutFragment() {
        // Required empty public constructor
    }

    AboutPresenter myCartPresenter;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myCartPresenter = new AboutPresenter(this, appRepository);
        myCartPresenter.requestAppVersion(getActivity());
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
    }

    @Override
    public void showError(int message) {
        showToast(message);
    }

    @Override
    public void showVersion(String version) {
        tvVersionName.setText(getString(R.string.version) + SPACE + version);
    }

}