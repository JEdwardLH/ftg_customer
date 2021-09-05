package com.foodtogo.user.ui.orders.fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.model.orderdetails.CancelledDetail;
import com.foodtogo.user.model.orderdetails.OrderDetailResponse;
import com.foodtogo.user.ui.orders.adapter.CancelledItemAdapter;
import com.foodtogo.user.ui.orders.interfaces.CancelledListener;
import com.foodtogo.user.ui.refunddetails.activity.RefundDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.foodtogo.user.base.AppConstants.CANCELLED_DETAILS;
import static com.foodtogo.user.base.AppConstants.ORDER_TRANSACTION_ID;


public class CancelledFragment extends BaseFragment implements CancelledListener {


    public CancelledFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_items)
    RecyclerView recyclerView;

    @BindView(R.id.tv_no_order)
    TextView tvNoOrder;

    List<CancelledDetail> cancelledDetailList;
    String orderTransactionId;

    /**invoice views*/

    @BindView(R.id.tv_sub_total)
    TextView tvSubTotal;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    @BindView(R.id.tv_total_tax)
    TextView tvTotalTax;

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

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pending, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        OrderDetailResponse orderDetailResponse=  getArguments().getParcelable(CANCELLED_DETAILS);
        assert orderDetailResponse != null;
        ArrayList<CancelledDetail> cancelledDetailArrayList = orderDetailResponse.getCancelledDetails();
        orderTransactionId = getArguments().getString(ORDER_TRANSACTION_ID);
        cancelledDetailList = getList(cancelledDetailArrayList);
        if (cancelledDetailList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoOrder.setVisibility(View.GONE);
            recyclerView.setHasFixedSize(true);
            CancelledItemAdapter adapter = new CancelledItemAdapter(getActivity(), cancelledDetailList);
            adapter.setCancelledListener(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setVisibility(View.GONE);
            tvNoOrder.setVisibility(View.VISIBLE);
        }
       // invoiceLayout.setVisibility(View.VISIBLE);
       // invoiceViewBinding(orderDetailResponse);
    }


    /** invoice views*/
  /*  void invoiceViewBinding(OrderDetailResponse orderDetailResponse){
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



    private List<CancelledDetail> getList(ArrayList<CancelledDetail> pendingDetailArrayList) {
        List<CancelledDetail> newCancelledList = new ArrayList<>();
        boolean isHeader = true;
        for (int index = 0; index < pendingDetailArrayList.size(); index++) {
            CancelledDetail cancelledDetail = pendingDetailArrayList.get(index);
            int restaurantId = cancelledDetail.getRestaurantId();
            if (ifExist(restaurantId, newCancelledList)) {
                isHeader = true;
                for (int jIndex = 0; jIndex < pendingDetailArrayList.size(); jIndex++) {
                    CancelledDetail cancelledDetailNew = pendingDetailArrayList.get(jIndex);
                    int restaurantIdNew = cancelledDetailNew.getRestaurantId();
                    if (restaurantId == restaurantIdNew) {
                        cancelledDetailNew.setShowHeader(isHeader);
                        newCancelledList.add(cancelledDetailNew);
                        isHeader = false;
                    }
                }
            }
        }
        return newCancelledList;
    }

    private boolean ifExist(int restaurantId, List<CancelledDetail> cancelledDetailList) {
        for (int jIndex = 0; jIndex < cancelledDetailList.size(); jIndex++) {
            CancelledDetail cancelledDetail = cancelledDetailList.get(jIndex);
            int restaurantIdNew = cancelledDetail.getRestaurantId();
            if (restaurantId == restaurantIdNew) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void viewRefundDetails(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_TRANSACTION_ID, orderTransactionId);
        changeActivityExtras(getActivity(), RefundDetailsActivity.class, bundle);
    }

    @Override
    public void cancelForReason(int position) {
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_cancelation_info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        final TextView tvCancellationPolicy = dialog.findViewById(R.id.tv_cancellation_policy);
        final TextView tvCancellation = dialog.findViewById(R.id.tv_cancellation);
        final TextView tvCancellationInfo = dialog.findViewById(R.id.tv_cancellation_info);
        tvCancellationInfo.setVisibility(View.GONE);
        tvTitle.setText(cancelledDetailList.get(position).getRestaurantName());
        tvCancellation.setText(getResources().getString(R.string.reason_to_cancel));
        tvCancellationPolicy.setText(cancelledDetailList.get(position).getCancelledReaosn().equals("") ? cancelledDetailList.get(position).getFailedReaosn() : cancelledDetailList.get(position).getCancelledReaosn());
        final ImageView ivClose = dialog.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }
}