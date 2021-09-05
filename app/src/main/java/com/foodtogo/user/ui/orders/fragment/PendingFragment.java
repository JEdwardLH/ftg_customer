package com.foodtogo.user.ui.orders.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.model.orderdetails.OrderDetailResponse;
import com.foodtogo.user.model.orderdetails.PendingDetail;
import com.foodtogo.user.ui.orders.activity.OrderActivity;
import com.foodtogo.user.ui.orders.adapter.PendingItemAdapter;
import com.foodtogo.user.ui.orders.interfaces.PendingListener;
import com.foodtogo.user.ui.orders.mvp.PendingOrderContractor;
import com.foodtogo.user.ui.orders.mvp.PendingOrderPresenter;
import com.foodtogo.user.ui.settings.activity.SettingMenuActivity;
import com.foodtogo.user.ui.track.activity.LiveTrackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.foodtogo.user.base.AppConstants.AVAILABLE;
import static com.foodtogo.user.base.AppConstants.NOT_AVAILABLE;
import static com.foodtogo.user.base.AppConstants.ORDER_ID;
import static com.foodtogo.user.base.AppConstants.ORDER_TRANSACTION_ID;
import static com.foodtogo.user.base.AppConstants.PAYMENT_STATUS;
import static com.foodtogo.user.base.AppConstants.PENDING_DETAILS;
import static com.foodtogo.user.base.AppConstants.RESTAURANT_ID;
import static com.foodtogo.user.base.AppConstants.TAB_POSITION;
import static com.foodtogo.user.base.AppConstants.TAB_TRACK;
import static com.foodtogo.user.base.AppConstants.TITLE;


public class PendingFragment extends BaseFragment implements PendingOrderContractor.View, PendingListener {


    public PendingFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_items)
    RecyclerView recyclerView;

    @BindView(R.id.tv_no_order)
    TextView tvNoOrder;

    /**
     * invoice views
     */

    @BindView(R.id.tv_sub_total)
    TextView tvSubTotal;

    @BindView(R.id.tv_total_tax)
    TextView tvTotalTax;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    @BindView(R.id.tv_delivery_fee)
    TextView tvDeliveryFee;

    @BindView(R.id.tv_use_wallet)
    TextView tvWallet;

    @BindView(R.id.tv_use_offer)
    TextView tvOffer;

    @BindView(R.id.tv_cancelled_item)
    TextView tvCancelledItem;

    @BindView(R.id.rl_use_wallet)
    RelativeLayout rlWallet;

    @BindView(R.id.rl_use_offer)
    RelativeLayout rlOffer;

    @BindView(R.id.rl_cancelled_item)
    RelativeLayout rlCancelledItem;

    @BindView(R.id.ll_invoice)
    LinearLayout invoiceLayout;

    /******/

    PendingOrderPresenter pendingOrderPresenter;
    PendingItemAdapter adapter;

    List<PendingDetail> pendingDetailList;
    String orderTransactionId;
    String paymentStaus;


    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pending, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pendingOrderPresenter = new PendingOrderPresenter(this, appRepository);
        assert getArguments() != null;
        OrderDetailResponse orderDetailResponse = getArguments().getParcelable(PENDING_DETAILS);
        assert orderDetailResponse != null;
        ArrayList<PendingDetail> pendingDetailArrayList = orderDetailResponse.getPendingDetails();
        orderTransactionId = getArguments().getString(ORDER_TRANSACTION_ID);
        paymentStaus = getArguments().getString(PAYMENT_STATUS);
        pendingDetailList = getList(pendingDetailArrayList);
        if (pendingDetailList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoOrder.setVisibility(View.GONE);
            recyclerView.setHasFixedSize(true);
            adapter = new PendingItemAdapter(getActivity(), pendingDetailList);
            adapter.setPendingListener(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setVisibility(View.GONE);
            tvNoOrder.setVisibility(View.VISIBLE);
        }
        // invoiceLayout.setVisibility(View.VISIBLE);
        // invoiceViewBinding(orderDetailResponse);

    }

    /**
     * invoice views
     */
   /* void invoiceViewBinding(OrderDetailResponse orderDetailResponse){
        tvTotal.setText(String.format("%s%s", orderDetailResponse.getCurrency(), orderDetailResponse.getGrandTotal()));
        tvSubTotal.setText(String.format("%s%s", orderDetailResponse.getCurrency(), orderDetailResponse.getGrandSubTotal()));
        tvTotalTax.setText(String.format("%s%s", orderDetailResponse.getCurrency(), orderDetailResponse.getGrandTaxTotal()));
        tvDeliveryFee.setText(String.format("%s%s", orderDetailResponse.getCurrency(), orderDetailResponse.getGrandDeliveryFee()));
        tvWallet.setText(String.format("- %s%s", orderDetailResponse.getCurrency(), orderDetailResponse.getWalletUsed()));
        tvOffer.setText(String.format("- %s%s", orderDetailResponse.getCurrency(), orderDetailResponse.getOfferUsed()));
        tvCancelledItem.setText(String.format("- %s%s", orderDetailResponse.getCurrency(), orderDetailResponse.getCancelledItemAmount()));

        rlWallet.setVisibility(orderDetailResponse.getWalletUsed().equals("0.00")?View.GONE:View.VISIBLE);
        rlOffer.setVisibility(orderDetailResponse.getOfferUsed().equals("0.00")?View.GONE:View.VISIBLE);
        rlCancelledItem.setVisibility(orderDetailResponse.getCancelledItemAmount().equals("0.00")?View.GONE:View.VISIBLE);
    }*/
    private List<PendingDetail> getList(ArrayList<PendingDetail> pendingDetailArrayList) {
        List<PendingDetail> newPendingList = new ArrayList<>();
        boolean isHeader = true;
        for (int index = 0; index < pendingDetailArrayList.size(); index++) {
            PendingDetail pendingDetail = pendingDetailArrayList.get(index);
            int restaurantId = pendingDetail.getRestaurantId();
            if (ifExist(restaurantId, newPendingList)) {
                isHeader = true;
                for (int jIndex = 0; jIndex < pendingDetailArrayList.size(); jIndex++) {
                    PendingDetail pendingDetailNew = pendingDetailArrayList.get(jIndex);
                    int restaurantIdNew = pendingDetailNew.getRestaurantId();
                    if (restaurantId == restaurantIdNew) {
                        pendingDetailNew.setShowHeader(isHeader);
                        newPendingList.add(pendingDetailNew);
                        isHeader = false;
                    }
                }
            }
        }
        return newPendingList;
    }

    private boolean ifExist(int restaurantId, List<PendingDetail> pendingDetailArrayList) {
        for (int jIndex = 0; jIndex < pendingDetailArrayList.size(); jIndex++) {
            PendingDetail pendingDetailNew = pendingDetailArrayList.get(jIndex);
            int restaurantIdNew = pendingDetailNew.getRestaurantId();
            if (restaurantId == restaurantIdNew) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void cancelOrder(int position, int orderId, String restaurantName, String itemName) {
        if (paymentStaus.equals(NOT_AVAILABLE)) {
            showPaymentPopup(position, orderId, restaurantName, itemName);
        } else {
            cancelOrderDialog(position, orderId, restaurantName, itemName);
        }
    }

    private void showPaymentPopup(int position, int orderId, String restaurantName, String itemName) {
        final Dialog dialog = new Dialog(context, R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_skip_continue);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvMessage = dialog.findViewById(R.id.tv_message);
        final Button btnSkip = dialog.findViewById(R.id.btn_skip);
        final Button btnContinue = dialog.findViewById(R.id.btn_continue);
        dialog.setCancelable(false);
        tvMessage.setText(pendingDetailList.get(position).getPaymentStatusErr());
        btnContinue.setOnClickListener(v -> {
            dialog.dismiss();
            Bundle bundle = new Bundle();
            bundle.putString(TITLE, "Payment Settings");
            bundle.putInt(TAB_POSITION, 6);
            paymentStaus = AVAILABLE;
            changeActivityExtras(getActivity(), SettingMenuActivity.class, bundle);
        });

        btnSkip.setOnClickListener(v -> {
            dialog.dismiss();
            paymentStaus = AVAILABLE;
            cancelOrderDialog(position, orderId, restaurantName, itemName);
        });
        dialog.show();
    }

    private void cancelOrderDialog(int position, int orderId, String restaurantName, String itemName) {
        final Dialog dialog = new Dialog(context, R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_cancel_order);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        final TextView tvMessage = dialog.findViewById(R.id.tv_message);
        final ImageView ivClose = dialog.findViewById(R.id.iv_close);
        final EditText etComment = dialog.findViewById(R.id.et_comment);
        final Button btnCancelOrder = dialog.findViewById(R.id.btn_cancel_order);
        dialog.setCancelable(false);
        tvTitle.setText(restaurantName);
        tvMessage.setText(itemName);
        btnCancelOrder.setOnClickListener(v -> {
            hideKeyboard(v);
            pendingOrderPresenter.requestCancelOrder(position, String.valueOf(orderId), etComment.getText().toString());
            dialog.dismiss();
        });
        ivClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void showCancellationPolicy(String restaurantName, String description, String info) {
        final Dialog dialog = new Dialog(context, R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_cancelation_info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        final TextView tvCancellationPolicy = dialog.findViewById(R.id.tv_cancellation_policy);
        final TextView tvCancellationInfo = dialog.findViewById(R.id.tv_cancellation_info);
        tvTitle.setText(restaurantName);
        if (info != null && !TextUtils.isEmpty(info)) {
            tvCancellationInfo.setText(info);
        } else {
            tvCancellationInfo.setVisibility(View.GONE);
        }
        tvCancellationPolicy.setText(description);
        final ImageView ivClose = dialog.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void trackOrder(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_ID, orderTransactionId);
        bundle.putInt(RESTAURANT_ID, pendingDetailList.get(position).getRestaurantId());
        changeActivityExtras(getActivity(), LiveTrackActivity.class, bundle);
    }

    @Override
    public void showOrderDetailResponse(OrderDetailResponse orderDetailResponse) {

    }

    @Override
    public void showCancelOrderResponse(int position, String response) {
        adapter.removedItem(position);
        ((OrderActivity) getActivity()).refreshOrder(0);
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
}