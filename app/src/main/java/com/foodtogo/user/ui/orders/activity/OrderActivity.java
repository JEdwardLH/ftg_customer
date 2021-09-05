package com.foodtogo.user.ui.orders.activity;

import android.os.Bundle;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.model.orderdetails.OrderDetailResponse;
import com.foodtogo.user.ui.orders.adapter.OrderHistoryMenuAdapter;
import com.foodtogo.user.ui.orders.fragment.CancelledFragment;
import com.foodtogo.user.ui.orders.fragment.FullFilledFragment;
import com.foodtogo.user.ui.orders.fragment.PendingFragment;
import com.foodtogo.user.ui.orders.mvp.OrderContractor;
import com.foodtogo.user.ui.orders.mvp.OrderPresenter;

import butterknife.BindView;


public class OrderActivity extends BaseActivity implements OrderContractor.View {


    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    OrderPresenter orderPresenter;
    OrderHistoryMenuAdapter adapter;
    String orderId = "";
    String fromFireBase="";

    private int activeTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderPresenter = new OrderPresenter(this, appRepository);
        orderId = getIntent().getStringExtra(ORDER_ID);
         if(getIntent().getStringExtra(ORDER_CANCEL_MERCHANT)!=null){
             fromFireBase=getIntent().getStringExtra(ORDER_CANCEL_MERCHANT);
         }
        orderPresenter.requestOrderDetail(orderId);
        setupToolBar();
        tvTitle.setText(getResources().getString(R.string.orders));
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
    public void showOrderDetailResponse(OrderDetailResponse orderDetailResponse) {
        String paymentStatus = orderDetailResponse.getPendingDetails().size() > 0 ? orderDetailResponse.getPendingDetails().get(0).getPaymentStatus() : AVAILABLE;
        Bundle bundlePending = new Bundle();
        bundlePending.putParcelable(PENDING_DETAILS, orderDetailResponse);
        bundlePending.putString(ORDER_TRANSACTION_ID, orderDetailResponse.getOrderTransactionId());
        bundlePending.putString(PAYMENT_STATUS, paymentStatus);
        PendingFragment pendingFragment = new PendingFragment();
        pendingFragment.setArguments(bundlePending);

        Bundle bundleFullFilled = new Bundle();
        bundleFullFilled.putParcelable(FULFILLED_DETAILS, orderDetailResponse);
        bundleFullFilled.putString(ORDER_TRANSACTION_ID, orderDetailResponse.getOrderTransactionId());
        FullFilledFragment fullFilledFragment = new FullFilledFragment();
        fullFilledFragment.setArguments(bundleFullFilled);

        Bundle bundleCancelled = new Bundle();
        bundleCancelled.putParcelable(CANCELLED_DETAILS, orderDetailResponse);
        bundleCancelled.putString(ORDER_TRANSACTION_ID, orderDetailResponse.getOrderTransactionId());
        CancelledFragment cancelledFragment = new CancelledFragment();
        cancelledFragment.setArguments(bundleCancelled);

        String pendingListCount=orderDetailResponse.getPendingDetails().size()>0?getString(R.string.pending_tab,String.valueOf(orderDetailResponse.getPendingDetails().size())):getString(R.string.pending);
        String fulFilledListCount=orderDetailResponse.getFulfilledDetails().size()>0?getString(R.string.full_field_tab,String.valueOf(orderDetailResponse.getFulfilledDetails().size())):getString(R.string.full_field);
        String cancelledListCount=orderDetailResponse.getCancelledDetails().size()>0?getString(R.string.cancelled_tab, String.valueOf(orderDetailResponse.getCancelledDetails().size())):getString(R.string.cancelled);

        adapter = new OrderHistoryMenuAdapter(getSupportFragmentManager());
        adapter.addFragment(pendingListCount, pendingFragment);
        adapter.addFragment(fulFilledListCount, fullFilledFragment);
        adapter.addFragment(cancelledListCount, cancelledFragment);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_orange));
        setCustomFont();
        returnTabPositionBasedOnListCount(orderDetailResponse);
        viewPager.setCurrentItem(activeTab);
    }

    public void refreshOrder(int pageIndex) {
        this.activeTab = pageIndex;
        orderPresenter.requestOrderDetail(orderId);
    }

    void returnTabPositionBasedOnListCount(OrderDetailResponse orderDetailResponse){
        if(orderDetailResponse.getPendingDetails().size()>0){
            activeTab=0;
        }else if(orderDetailResponse.getFulfilledDetails().size()>0){
            activeTab=1;
        }else if(orderDetailResponse.getCancelledDetails().size()>0){
            activeTab=2;
        }else{
           activeTab=0;
        }

        if(!fromFireBase.equals("") && fromFireBase.equals(CANCELLED)){
            activeTab=2;
        }
    }
}
