package com.foodtogo.user.ui.refunddetails.adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.refunddetails.RefundDetailResponse;
import com.foodtogo.user.ui.refunddetails.interfaces.CompletedRefundListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.foodtogo.user.base.AppConstants.SPACE;

public class CompletedRefundAdapter extends RecyclerView.Adapter<CompletedRefundAdapter.ItemRowHolder> {

    private List<RefundDetailResponse> itemListList;
    private CompletedRefundListener completedRefundListener;
    private String orderId;

    public CompletedRefundAdapter(List<RefundDetailResponse> itemLists,String mOrderId) {
        this.itemListList = itemLists;
        this.orderId=mOrderId;
    }

    public void setRefundDetailsListener(CompletedRefundListener mCompletedRefundListener) {
        this.completedRefundListener = mCompletedRefundListener;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_completed_refund, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, int i) {
        RefundDetailResponse refundDetailResponse = itemListList.get(i);
        holder.tvItemName.setText(refundDetailResponse.getItemName());
        holder.tvRefundAmount.setText(refundDetailResponse.getRefundAmount());
        String itemDetails = refundDetailResponse.getOrderCurrency() +SPACE+ refundDetailResponse.getOrderTotal();
        holder.tvItemAmount.setText(itemDetails);
        holder.tvCancelType.setText(refundDetailResponse.getCancelType());
        holder.llViewRefundDetails.setOnClickListener(v -> completedRefundListener.showRefundDetails(i));
        holder.tvOrderId.setText(orderId);
    }


    @Override
    public int getItemCount() {
        return (null != itemListList ? itemListList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_name)
        TextView tvItemName;

        @BindView(R.id.tv_item_amount)
        TextView tvItemAmount;

        @BindView(R.id.tv_refund_amount)
        TextView tvRefundAmount;

        @BindView(R.id.tv_cancel_type)
        TextView tvCancelType;

        @BindView(R.id.tv_order_id)
        TextView tvOrderId;

        @BindView(R.id.ll_view_refund_details)
        LinearLayout llViewRefundDetails;


        ItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}