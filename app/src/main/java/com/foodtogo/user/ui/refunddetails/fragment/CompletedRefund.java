package com.foodtogo.user.ui.refunddetails.fragment;

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
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.model.refunddetails.RefundDetailResponse;
import com.foodtogo.user.ui.refunddetails.adapter.CompletedRefundAdapter;
import com.foodtogo.user.ui.refunddetails.interfaces.CompletedRefundListener;

import java.util.ArrayList;

import butterknife.BindView;

import static com.foodtogo.user.base.AppConstants.COMPLETED_REFUND;
import static com.foodtogo.user.base.AppConstants.ORDER_ID;


public class CompletedRefund extends BaseFragment implements CompletedRefundListener {


    public CompletedRefund() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_items)
    RecyclerView recyclerView;

    @BindView(R.id.tv_no_order)
    TextView tvNoOrder;
    ArrayList<RefundDetailResponse> completedRefundDetails;
    String orderId;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pending, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         completedRefundDetails = getArguments().getParcelableArrayList(COMPLETED_REFUND);
          orderId=getArguments().getString(ORDER_ID);
        if (completedRefundDetails.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoOrder.setVisibility(View.GONE);
            recyclerView.setHasFixedSize(true);
            CompletedRefundAdapter adapter = new CompletedRefundAdapter(completedRefundDetails,orderId);
            adapter.setRefundDetailsListener(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            setRecyclerViewAnimation(recyclerView);
            recyclerView.setAdapter(adapter);
            startRecyclerAnimation(recyclerView);
        } else {
            tvNoOrder.setText(getString(R.string.no_completed_refund));
            recyclerView.setVisibility(View.GONE);
            tvNoOrder.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void showRefundDetails(int position) {
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_refund_info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        final TextView tvRefundDate = dialog.findViewById(R.id.tv_refund_date);
        final TextView tvTransactionId = dialog.findViewById(R.id.tv_transaction_id);
        final TextView tvRefundAmount = dialog.findViewById(R.id.tv_refund_amount);
        final TextView tvCommission = dialog.findViewById(R.id.tv_commission);
        final TextView tvOffer = dialog.findViewById(R.id.tv_offer);
        final TextView tvDeliveryFee = dialog.findViewById(R.id.tv_delivery_fee);
        final LinearLayout offerLay = dialog.findViewById(R.id.offer_lay);
        final ImageView ivClose = dialog.findViewById(R.id.iv_close);
        tvRefundDate.setText(completedRefundDetails.get(position).getRefundDate());
        tvTransactionId.setText(completedRefundDetails.get(position).getTransactionId());
        tvRefundAmount.setText(completedRefundDetails.get(position).getRefundAmount());
        tvDeliveryFee.setText(String.format("%s%s", completedRefundDetails.get(position).getOrderCurrency(), completedRefundDetails.get(position).getDeliveryFee()));
        if(!completedRefundDetails.get(position).getOfferAmount().equals("0")){
            offerLay.setVisibility(View.VISIBLE);
            tvOffer.setText(String.format("- %s%s",completedRefundDetails.get(position).getOrderCurrency(), completedRefundDetails.get(position).getOfferAmount()));
        }else{
            offerLay.setVisibility(View.GONE);
        }
        tvCommission.setText(String.format(" - %s", completedRefundDetails.get(position).getCommission()));
        ivClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }





}