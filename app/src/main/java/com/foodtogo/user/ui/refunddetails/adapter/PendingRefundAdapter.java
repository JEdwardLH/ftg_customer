package com.foodtogo.user.ui.refunddetails.adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.refunddetails.RefundDetailResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.foodtogo.user.base.AppConstants.SPACE;

public class PendingRefundAdapter extends RecyclerView.Adapter<PendingRefundAdapter.PendingItemRowHolder> {

    private List<RefundDetailResponse> itemListList;
    String orderId;

    public PendingRefundAdapter(List<RefundDetailResponse> itemLists,String mOrderId) {
        this.itemListList = itemLists;
        this.orderId=mOrderId;
    }

    @NonNull
    @Override
    public PendingItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_pending_refund, null);
        return new PendingItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingItemRowHolder holder, int i) {
        RefundDetailResponse refundDetailResponse = itemListList.get(i);
        holder.tvItemName.setText(refundDetailResponse.getItemName());
        String priceInfo = refundDetailResponse.getOrderCurrency() + SPACE +refundDetailResponse.getOrderTotal();
        holder.tvAmount.setText(priceInfo);
        holder.tvCancelType.setText(refundDetailResponse.getCancelType());
        holder.tvOrderId.setText(orderId);
    }


    @Override
    public int getItemCount() {
        return (null != itemListList ? itemListList.size() : 0);
    }

    class PendingItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_name)
        TextView tvItemName;

        @BindView(R.id.tv_amount)
        TextView tvAmount;

        @BindView(R.id.tv_cancel_type)
        TextView tvCancelType;

        @BindView(R.id.tv_order_id)
        TextView tvOrderId;


        PendingItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}