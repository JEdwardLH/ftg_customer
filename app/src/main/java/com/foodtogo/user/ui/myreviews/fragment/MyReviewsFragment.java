package com.foodtogo.user.ui.myreviews.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.model.myreviews.MyReviewResponse;
import com.foodtogo.user.ui.myreviews.interfaces.OnChildItemListener;
import com.foodtogo.user.ui.myreviews.interfaces.OnChildOrderListener;
import com.foodtogo.user.ui.myreviews.interfaces.OnChildRestaurantListener;
import com.foodtogo.user.ui.myreviews.mvp.MyReviewsContractor;
import com.foodtogo.user.ui.myreviews.mvp.MyReviewsPresenter;
import com.foodtogo.user.ui.orders.adapter.OrderHistoryMenuAdapter;

import butterknife.BindView;

import static com.foodtogo.user.base.AppConstants.REVIEWS_ITEMS;
import static com.foodtogo.user.base.AppConstants.REVIEWS_ORDER;
import static com.foodtogo.user.base.AppConstants.REVIEWS_RESTAURANT;


public class MyReviewsFragment extends BaseFragment implements MyReviewsContractor.View,
        OnChildItemListener, OnChildRestaurantListener, OnChildOrderListener {


    public MyReviewsFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    OrderHistoryMenuAdapter adapter;


    @BindView(R.id.tv_error)
    TextView tvError;

    @BindView(R.id.ll_tab_view)
    LinearLayout llTabView;

    MyReviewsItem myReviewsItem = null;
    MyReviewsRestaurant myReviewsRestaurant = null;
    MyReviewsOrder myReviewsOrder = null;

    MyReviewsPresenter myCartPresenter;
    private static final int PAGE_START = 1;
    public static int currentPage = PAGE_START;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myreviews, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentPage = 1;
        myCartPresenter = new MyReviewsPresenter(this, appRepository);
        llTabView.setVisibility(View.GONE);
        myCartPresenter.requestMyReviews(PAGE_START);

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
        llTabView.setVisibility(View.GONE);
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(int message) {
        llTabView.setVisibility(View.GONE);
        tvError.setText(getResources().getString(message));
        tvError.setVisibility(View.VISIBLE);
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
    public void showMyReviews(MyReviewResponse myReviewResponse) {
        tvError.setVisibility(View.GONE);
        llTabView.setVisibility(View.VISIBLE);
        Bundle bundleItems = new Bundle();
        bundleItems.putParcelableArrayList(REVIEWS_ITEMS, myReviewResponse.getItemReviewList());
        myReviewsItem = new MyReviewsItem();
        myReviewsItem.setArguments(bundleItems);

        Bundle bundleRestaurants = new Bundle();
        bundleRestaurants.putParcelableArrayList(REVIEWS_RESTAURANT, myReviewResponse.getRestReviewList());
        myReviewsRestaurant = new MyReviewsRestaurant();
        myReviewsRestaurant.setArguments(bundleRestaurants);

        Bundle bundleOrder = new Bundle();
        bundleOrder.putParcelableArrayList(REVIEWS_ORDER, myReviewResponse.getOrderReviewList());
        myReviewsOrder = new MyReviewsOrder();
        myReviewsOrder.setArguments(bundleOrder);

        adapter = new OrderHistoryMenuAdapter(getChildFragmentManager());
        adapter.addFragment(getString(R.string.items), myReviewsItem);
        adapter.addFragment(getString(R.string.restaurants), myReviewsRestaurant);
        adapter.addFragment(getString(R.string.orders), myReviewsOrder);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_orange));
        setCustomFont();
    }

    @Override
    public void showLoadMore(MyReviewResponse myReviewResponse) {
        //adapter.getItem(0)
        myReviewsItem.loadMore(myReviewResponse.getItemReviewList());
        myReviewsRestaurant.loadMore(myReviewResponse.getRestReviewList());
        myReviewsOrder.loadMore(myReviewResponse.getOrderReviewList());
    }

    @Override
    public void showLoadMoreError(String response) {
        myReviewsItem.loadMoreError(response);
        myReviewsRestaurant.loadMoreError(response);
        myReviewsOrder.loadMoreError(response);
    }


    @Override
    public void refreshItem() {
        myCartPresenter.requestMyReviews(currentPage);
        //Toast.makeText(context, "message From Item Child", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void refreshRestaurant() {
        myCartPresenter.requestMyReviews(currentPage);
        //Toast.makeText(context, "message From Restaurant Child", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshOrder() {
        myCartPresenter.requestMyReviews(currentPage);
        // Toast.makeText(context, "message From Order Child", Toast.LENGTH_SHORT).show();
    }
}