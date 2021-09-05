package com.foodtogo.user.ui.orders.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.orderdetails.CancelledDetail;
import com.foodtogo.user.ui.orders.interfaces.CancelledListener;
import com.foodtogo.user.util.ConversionUtils;
import com.foodtogo.user.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.foodtogo.user.base.AppConstants.COD;

public class CancelledItemAdapter extends RecyclerView.Adapter<CancelledItemAdapter.RestaurantItemRowHolder> {

    private List<CancelledDetail> itemListList;
    private Context mContext;
    private CancelledListener cancelledListener;

    public CancelledItemAdapter(Context context, List<CancelledDetail> itemLists) {
        this.itemListList = itemLists;
        this.mContext = context;
    }

    public void setCancelledListener(CancelledListener mCancelledListener) {
        this.cancelledListener = mCancelledListener;
    }

    @NonNull
    @Override
    public RestaurantItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_cancelled, null);

        return new RestaurantItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantItemRowHolder holder, int i) {
        CancelledDetail pendingDetail = itemListList.get(i);
        //Store info
        holder.tvStoreName.setText(pendingDetail.getRestaurantName());
        holder.rlStoreView.setVisibility(pendingDetail.isShowHeader() ? View.VISIBLE : View.GONE);
        holder.tvItemName.setText(pendingDetail.getItemName());
        String priceInfo = pendingDetail.getItemCurrency() + pendingDetail.getItemAmount();
        holder.tvFoodPrice.setText(priceInfo);
        holder.tvCancelDate.setText(ConversionUtils.getFormatDateTime(pendingDetail.getCancelledDate()));
        GlideUtils.showImage(mContext, holder.ivFood, R.drawable.image_placeholder_small, pendingDetail.getItemImage());
        holder.ivCancelForReason.setOnClickListener(v ->
                cancelledListener.cancelForReason(i));
        holder.tvRefundStatus.setOnClickListener(v -> cancelledListener.viewRefundDetails(i));
        holder.tvRefundStatus.setVisibility(pendingDetail.getOrderType().equals(COD) ? View.GONE : View.VISIBLE);
        holder.tvRefundStatusTitle.setVisibility(pendingDetail.getOrderType().equals(COD) ? View.GONE : View.VISIBLE);
    }


    @Override
    public int getItemCount() {
        return (null != itemListList ? itemListList.size() : 0);
    }


    class RestaurantItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_store_name)
        TextView tvStoreName;

        @BindView(R.id.iv_food)
        ImageView ivFood;

        @BindView(R.id.tv_item_name)
        TextView tvItemName;

        @BindView(R.id.tv_food_price)
        TextView tvFoodPrice;

        @BindView(R.id.tv_cancel_date)
        TextView tvCancelDate;

        @BindView(R.id.iv_cancal_for_reason)
        ImageView ivCancelForReason;

        @BindView(R.id.tv_refund_status_title)
        TextView tvRefundStatusTitle;

        @BindView(R.id.tv_refund_status)
        TextView tvRefundStatus;

        @BindView(R.id.rl_store_view)
        RelativeLayout rlStoreView;

        RestaurantItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}