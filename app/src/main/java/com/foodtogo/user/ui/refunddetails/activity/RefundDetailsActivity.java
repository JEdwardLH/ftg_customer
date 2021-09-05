package com.foodtogo.user.ui.refunddetails.activity;

import android.os.Bundle;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.model.refunddetails.RefundDetailResponse;
import com.foodtogo.user.ui.refunddetails.adapter.RefundMenuAdapter;
import com.foodtogo.user.ui.refunddetails.fragment.CompletedRefund;
import com.foodtogo.user.ui.refunddetails.fragment.PendingRefund;
import com.foodtogo.user.ui.refunddetails.mvp.RefundDetailsContractor;
import com.foodtogo.user.ui.refunddetails.mvp.RefundDetailsPresenter;

import java.util.ArrayList;

import butterknife.BindView;


public class RefundDetailsActivity extends BaseActivity implements RefundDetailsContractor.View {


    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    RefundDetailsPresenter orderPresenter;
    RefundMenuAdapter adapter;
    String orderId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderPresenter = new RefundDetailsPresenter(this, appRepository);
        orderId = getIntent().getStringExtra(ORDER_TRANSACTION_ID);
        orderPresenter.requestRefundDetail(orderId);
        setupToolBar();
        tvTitle.setText(getResources().getString(R.string.refund));
    }

    @Override
    public int getLayout() {
        return R.layout.activity_order;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        orderPresenter.close();
    }


    @Override
    public void showLoadingView() {
        showProgress();
    }

    @Override
    public void hideLoadingView() {
        hideProgress();
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @Override
    public void showError(int message) {
        showToast(message);
    }

    public void setCustomFont() {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    //Put your font in assests folder
                    //assign name of the font here (Must be case sensitive)
                    ((TextView) tabViewChild).setTypeface(ResourcesCompat.getFont(context, R.font.helvetica_semi_bold));
                    ((TextView) tabViewChild).setAllCaps(false);
                }
            }
        }

    }

    @Override
    public void showRefundDetailResponse(ArrayList<RefundDetailResponse> pendingRefund, ArrayList<RefundDetailResponse> completedRefund) {
        Bundle bundlePending = new Bundle();
        bundlePending.putParcelableArrayList(PENDING_REFUND, pendingRefund);
        bundlePending.putString(ORDER_ID, orderId);
        PendingRefund pendingFragment = new PendingRefund();
        pendingFragment.setArguments(bundlePending);

        Bundle bundleCompleted = new Bundle();
        bundleCompleted.putParcelableArrayList(COMPLETED_REFUND, completedRefund);
        bundleCompleted.putString(ORDER_ID, orderId);
        CompletedRefund completedFragment = new CompletedRefund();
        completedFragment.setArguments(bundleCompleted);

        adapter = new RefundMenuAdapter(getSupportFragmentManager());
        adapter.addFragment(getString(R.string.pending), pendingFragment);
        adapter.addFragment(getString(R.string.completed), completedFragment);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_orange));
        setCustomFont();
    }


}
