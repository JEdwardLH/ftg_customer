package com.foodtogo.user.ui.orders.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.orderdetails.PendingDetail;
import com.foodtogo.user.ui.orders.interfaces.PendingListener;
import com.foodtogo.user.util.ConversionUtils;
import com.foodtogo.user.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PendingItemAdapter extends RecyclerView.Adapter<PendingItemAdapter.PendingItemRowHolder> {

    private List<PendingDetail> itemListList;
    private Context mContext;
    private PendingListener pendingListener;
    private static final String CANCEL_ORDER = "Can cancel";

    public PendingItemAdapter(Context context, List<PendingDetail> itemLists) {
        this.itemListList = itemLists;
        this.mContext = context;
    }

    public void setPendingListener(PendingListener mPendingListener) {
        this.pendingListener = mPendingListener;
    }

    @NonNull
    @Override
    public PendingItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_pending, null);

        return new PendingItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingItemRowHolder holder, int i) {
        PendingDetail pendingDetail = itemListList.get(i);
        //Store info
        holder.tvStoreName.setText(pendingDetail.getRestaurantName());
        holder.rlStoreView.setVisibility(pendingDetail.isShowHeader() ? View.VISIBLE : View.GONE);
        holder.tvItemName.setText(pendingDetail.getItemName());
        String priceInfo = pendingDetail.getItemCurrency() + pendingDetail.getItemAmount();
        holder.tvFoodPrice.setText(priceInfo);
        holder.tvOrderStatus.setText(pendingDetail.getOrderStatus());
        GlideUtils.showImage(mContext, holder.ivFood, R.drawable.image_placeholder_small, pendingDetail.getItemImage());
        if (pendingDetail.getPreOrderDate() == null || pendingDetail.getPreOrderDate().equals("")) {
            holder.tvPreOrder.setVisibility(View.GONE);
            holder.tvPreOrderLabel.setVisibility(View.GONE);
        } else {
            holder.tvPreOrder.setVisibility(View.VISIBLE);
            holder.tvPreOrderLabel.setVisibility(View.VISIBLE);
            holder.tvPreOrder.setText(ConversionUtils.getFormatDateTime(pendingDetail.getPreOrderDate()));
        }

        holder.ivRemoveItem.setVisibility(pendingDetail.getCancelStatus().equals(CANCEL_ORDER) ? View.VISIBLE : View.GONE);
        holder.ivInfo.setOnClickListener(v -> pendingListener.showCancellationPolicy(pendingDetail.getRestaurantName(), pendingDetail.getCancelPolicy(),pendingDetail.getCancelInfo()));
        holder.ivRemoveItem.setOnClickListener(v -> pendingListener.cancelOrder(i, pendingDetail.getOrderId(), pendingDetail.getRestaurantName(), pendingDetail.getItemName()));
        holder.tvTrack.setOnClickListener(v -> pendingListener.trackOrder(i));
    }


    @Override
    public int getItemCount() {
        return (null != itemListList ? itemListList.size() : 0);
    }

    public void removedItem(int position) {
        itemListList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
        // notifyItemRangeChanged(position, itemListList.size());
    }

    class PendingItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_store_name)
        TextView tvStoreName;

        @BindView(R.id.tv_track)
        Button tvTrack;

        @BindView(R.id.iv_info)
        ImageView ivInfo;

        @BindView(R.id.iv_food)
        ImageView ivFood;

        @BindView(R.id.tv_item_name)
        TextView tvItemName;

        @BindView(R.id.tv_food_price)
        TextView tvFoodPrice;

        @BindView(R.id.tv_pre_order_label)
        TextView tvPreOrderLabel;

        @BindView(R.id.tv_pre_order)
        TextView tvPreOrder;

        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;

        @BindView(R.id.iv_remove_item)
        TextView ivRemoveItem;

        @BindView(R.id.rl_store_view)
        RelativeLayout rlStoreView;

        PendingItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}