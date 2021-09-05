package com.foodtogo.user.ui.orderhistory.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.model.orderhistory.OrderArray;
import com.foodtogo.user.model.orderhistory.OrderHistoryResponse;
import com.foodtogo.user.model.orderhistory.StoreDetails;
import com.foodtogo.user.ui.home.activity.Home;
import com.foodtogo.user.ui.invoice.activity.InvoiceDetails;
import com.foodtogo.user.ui.orderhistory.adapter.OrderHistoryItemAdapter;
import com.foodtogo.user.ui.orderhistory.adapter.TrackRestaurantAdapter;
import com.foodtogo.user.ui.orderhistory.interfaces.OrderHistoryListener;
import com.foodtogo.user.ui.orderhistory.interfaces.TrackListener;
import com.foodtogo.user.ui.orderhistory.mvp.OrderHistoryContractor;
import com.foodtogo.user.ui.orderhistory.mvp.OrderHistoryPresenter;
import com.foodtogo.user.ui.orders.activity.OrderActivity;
import com.foodtogo.user.ui.track.activity.LiveTrackActivity;
import com.foodtogo.user.util.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.foodtogo.user.base.AppConstants.EXCEPTION;
import static com.foodtogo.user.base.AppConstants.OFF_SET_LIMIT;
import static com.foodtogo.user.base.AppConstants.ORDER_ID;
import static com.foodtogo.user.base.AppConstants.ORDER_TRANSACTION_ID;
import static com.foodtogo.user.base.AppConstants.RESTAURANT_ID;
import static com.foodtogo.user.base.AppConstants.TAB_CART;
import static com.foodtogo.user.base.AppConstants.TAB_POSITION;


public class OrderHistoryFragment extends BaseFragment implements OrderHistoryContractor.View, OrderHistoryListener, TrackListener {


    public OrderHistoryFragment() {
        // Required empty public constructor
    }

    OrderHistoryPresenter myCartPresenter;

    List<OrderArray> orderArrayList;

    private static int REQUEST_CODE = 123;

    @BindView(R.id.recycler_items)
    RecyclerView recyclerView;

    @BindView(R.id.tv_error)
    TextView tvError;

    @BindView(R.id.ll_order_view)
    LinearLayout llOrderView;

    OrderHistoryItemAdapter orderHistoryItemAdapter;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;

    String totalAmount = "";

    Dialog dialog;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_history, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myCartPresenter = new OrderHistoryPresenter(this, appRepository);
        setRecyclerView();
        myCartPresenter.requestOrderList(currentPage);
        llOrderView.setVisibility(View.GONE);
    }

    private void setRecyclerView() {
        orderArrayList = new ArrayList<>();
        orderHistoryItemAdapter = new OrderHistoryItemAdapter(getActivity(), orderArrayList);
        recyclerView.setHasFixedSize(true);
        orderHistoryItemAdapter.setOrderHistoryListener(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setRecyclerViewAnimation(recyclerView);
        recyclerView.setAdapter(orderHistoryItemAdapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                // mocking network delay for API call
                new Handler().postDelayed(() -> loadNextPage(), 1000);
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

    }

    private void loadNextPage() {
        myCartPresenter.requestOrderList(currentPage);
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
        llOrderView.setVisibility(View.GONE);
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
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
    public void showError(int message) {
        llOrderView.setVisibility(View.GONE);
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }


    @Override
    public void showOrderList(OrderHistoryResponse orderHistoryResponse) {
        tvError.setVisibility(View.GONE);
        llOrderView.setVisibility(View.VISIBLE);
        orderHistoryItemAdapter.addAll(orderHistoryResponse.getOrderArray());
        orderHistoryItemAdapter.addLoadingFooter();
        startRecyclerAnimation(recyclerView);
        if (orderHistoryResponse.getOrderArray().size() < OFF_SET_LIMIT) {
            orderHistoryItemAdapter.removeLoadingFooter();
            isLastPage = true;
        }
    }

    @Override
    public void showLoadMore(OrderHistoryResponse orderHistoryResponse) {
        orderHistoryItemAdapter.removeLoadingFooter();
        isLoading = false;
        orderHistoryItemAdapter.addAll(orderHistoryResponse.getOrderArray());
        orderHistoryItemAdapter.addLoadingFooter();
        if (orderHistoryResponse.getOrderArray().size() < OFF_SET_LIMIT) {
            orderHistoryItemAdapter.removeLoadingFooter();
            isLastPage = true;
        }
    }

    @Override
    public void showLoadMoreError(String error) {
        orderHistoryItemAdapter.removeLoadingFooter();
        isLastPage = true;
    }

    @Override
    public void showRepeatOrder(String message) {
        Bundle bundle = new Bundle();
        bundle.putInt(TAB_POSITION, TAB_CART);
        changeActivityClearBackStack(getActivity(), Home.class, bundle);
    }

    @Override
    public void onClickInvoice(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_TRANSACTION_ID, orderArrayList.get(position).getOrderId());
        changeActivityExtras(getActivity(), InvoiceDetails.class, bundle);

    }

    @Override
    public void onClickRepeatOrder(String orderId) {
        myCartPresenter.requestRepeatOrder(orderId);
    }

    @Override
    public void onClickOrderDetails(String orderId) {
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_ID, orderId);
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onClickTrack(int position) {
        List<StoreDetails> restaurantDetailsList = orderArrayList.get(position).getStoreDetails();
        String orderTransactionId = orderArrayList.get(position).getOrderId();
        if (restaurantDetailsList.size() == 1) {
            String restaurantId = restaurantDetailsList.get(0).getStore_id();
            showTrackView(Integer.valueOf(restaurantId), orderTransactionId);
        } else {
            restaurantDetailsList = new ArrayList<>();
            for (int index = 0; index < orderArrayList.get(position).getStoreDetails().size(); index++) {
                if (orderArrayList.get(position).getStoreDetails().get(index).isCanTrack()) {
                    restaurantDetailsList.add(orderArrayList.get(position).getStoreDetails().get(index));
                }
            }
            showMultipleRestaurant(restaurantDetailsList, orderTransactionId);
        }
    }

    private void showMultipleRestaurant(List<StoreDetails> restaurantDetailsList, String orderTransactionId) {
        dialog = new Dialog(context, R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_order_track);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        final ImageView ivClose = dialog.findViewById(R.id.iv_close);
        final RecyclerView trackRecyclerView = dialog.findViewById(R.id.track_recycler_view);
        TrackRestaurantAdapter trackRestaurantAdapter = new TrackRestaurantAdapter(restaurantDetailsList);
        trackRecyclerView.setHasFixedSize(true);
        trackRestaurantAdapter.setTrackListener(this, orderTransactionId);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        trackRecyclerView.setLayoutManager(mLayoutManager);
        trackRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        trackRecyclerView.setAdapter(trackRestaurantAdapter);
        ivClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }


    private void showTrackView(int restaurantId, String orderTransactionId) {
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_ID, orderTransactionId);
        bundle.putInt(RESTAURANT_ID, restaurantId);
        changeActivityExtras(getActivity(), LiveTrackActivity.class, bundle);
    }


    @Override
    public void trackOrder(String restaurantId, String transactionId) {
        try {
            if (dialog != null) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
        showTrackView(Integer.valueOf(restaurantId), transactionId);
    }
}