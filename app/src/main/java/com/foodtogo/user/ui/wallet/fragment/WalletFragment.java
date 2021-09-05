package com.foodtogo.user.ui.wallet.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.ui.home.activity.Home;
import com.foodtogo.user.ui.orders.adapter.OrderHistoryMenuAdapter;
import com.foodtogo.user.ui.wallet.mvp.WalletContractor;
import com.foodtogo.user.ui.wallet.mvp.WalletPresenter;
import com.foodtogo.user.ui.wallet.totalwallet.fragment.TotalWallet;
import com.foodtogo.user.ui.wallet.usedwallet.fragment.UsedWallet;
import com.foodtogo.user.util.ControllableAppBarLayout;

import butterknife.BindView;
import butterknife.OnClick;

import static com.foodtogo.user.base.AppConstants.EXCEPTION;
import static com.foodtogo.user.base.AppConstants.SPACE;


public class WalletFragment extends BaseFragment implements WalletContractor.View {

    @BindView(R.id.tv_amount)
    TextView tvAmount;


    public WalletFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.appbar)
    ControllableAppBarLayout appbarLayout;


    String balanceText = "";

    WalletPresenter myCartPresenter;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myCartPresenter = new WalletPresenter(this, appRepository);
        myCartPresenter.requestWallet(currentPage);
    }


    private void setSelectedTab(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        view.setBackgroundResource(R.drawable.wallet_view_bg);
        switch (tab.getPosition()) {
            case 0:
                tabLayout.getTabAt(1).getCustomView().setBackgroundResource(R.drawable.wallet_white_bg);
                break;
            case 1:
                tabLayout.getTabAt(0).getCustomView().setBackgroundResource(R.drawable.wallet_white_bg);
                break;
        }

    }




    @OnClick(R.id.iv_refer_friend)
    public void setTvReferView() {
        try {
            ((Home) getActivity()).showReferFriend();
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
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
    public void showResult(String currencyCode, String walletBalance, Bundle bundleTotalWallet, Bundle bundleUsedWallet, Bundle bundleTotalRewards) {
        balanceText = "Available Balance : " + currencyCode + SPACE + walletBalance ;
        tvAmount.setText(currencyCode + SPACE + walletBalance);
        OrderHistoryMenuAdapter adapter = new OrderHistoryMenuAdapter(getChildFragmentManager());
        TotalWallet totalWallet = new TotalWallet();
        totalWallet.setArguments(bundleTotalWallet);
        UsedWallet usedWallet = new UsedWallet();
        usedWallet.setArguments(bundleUsedWallet);
        adapter.addFragment("", totalWallet);
        adapter.addFragment("", usedWallet);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.tab_wallet1, null));
        tabLayout.getTabAt(1).setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.tab_wallet2, null));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setSelectedTab(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                setSelectedTab(tab);
            }
        });
        setSelectedTab(tabLayout.getTabAt(0));
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
//        tvError.setText(message);
//        tvError.setVisibility(View.VISIBLE);
//        llWalletView.setVisibility(View.GONE);
    }

    @Override
    public void showError(int message) {
//        tvError.setText(getResources().getString(message));
//        tvError.setVisibility(View.VISIBLE);
//        llWalletView.setVisibility(View.GONE);
    }




}