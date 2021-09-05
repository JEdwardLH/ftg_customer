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
import com.foodtogo.user.model.orderdetails.FulfilledDetail;
import com.foodtogo.user.ui.orders.interfaces.FullFilledListener;
import com.foodtogo.user.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.foodtogo.user.base.AppConstants.NO;
import static com.foodtogo.user.base.AppConstants.YES;

public class FullFilledItemAdapter extends RecyclerView.Adapter<FullFilledItemAdapter.FullFilledItemRowHolder> {

    private List<FulfilledDetail> itemListList;
    private Context mContext;
    private FullFilledListener fullFilledListener;

    public FullFilledItemAdapter(Context context, List<FulfilledDetail> itemLists) {
        this.itemListList = itemLists;
        this.mContext = context;
    }

    public void setFullFilledListener(FullFilledListener mFullFilledListener) {
        this.fullFilledListener = mFullFilledListener;
    }

    @NonNull
    @Override
    public FullFilledItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_fullfilled, null);
        return new FullFilledItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FullFilledItemRowHolder holder, int i) {
        FulfilledDetail fulfilledDetail = itemListList.get(i);
        //Store info
        holder.tvStoreName.setText(fulfilledDetail.getRestaurantName());
        holder.rlStoreView.setVisibility(fulfilledDetail.isShowHeader() ? View.VISIBLE : View.GONE);
        holder.tvItemName.setText(fulfilledDetail.getItemName());
        String priceInfo = fulfilledDetail.getItemCurrency() + fulfilledDetail.getItemAmount();
        holder.tvFoodPrice.setText(priceInfo);
        holder.tvOrderStatus.setText(fulfilledDetail.getOrderStatus());
        GlideUtils.showImage(mContext, holder.ivFood, R.drawable.image_placeholder_small, fulfilledDetail.getItemImage());
        if (fulfilledDetail.getPreOrderDate() == null) {
            holder.tvPreOrder.setVisibility(View.GONE);
            holder.tvPreOrderLabel.setVisibility(View.GONE);
        } else {
            holder.tvPreOrder.setVisibility(View.VISIBLE);
            holder.tvPreOrderLabel.setVisibility(View.VISIBLE);
            holder.tvPreOrder.setText(fulfilledDetail.getPreOrderDate());
        }
        if (fulfilledDetail.getAlreadyRestaurantReviewed().equals(YES)
                && fulfilledDetail.getCanOrderReview().equals(NO)) {
            holder.tvAddReview.setVisibility(View.GONE);
        } else if (fulfilledDetail.getAlreadyOrderReviewed().equals(YES)
                && fulfilledDetail.getAlreadyRestaurantReviewed().equals(YES)) {
            holder.tvAddReview.setVisibility(View.GONE);
        } else {
            holder.tvAddReview.setVisibility(View.VISIBLE);

        }
        boolean itemView = fulfilledDetail.getAlreadyItemReviewed().equals(YES);
        holder.ivItemReview.setVisibility(itemView ? View.GONE : View.VISIBLE);
        holder.tvAddReview.setOnClickListener(v -> fullFilledListener.addReview(i));
        holder.ivItemReview.setOnClickListener(v -> fullFilledListener.addItemReview(i));
    }


    @Override
    public int getItemCount() {
        return (null != itemListList ? itemListList.size() : 0);
    }

    class FullFilledItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_store_name)
        TextView tvStoreName;

        @BindView(R.id.tv_add_review)
        TextView tvAddReview;

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

        @BindView(R.id.iv_item_review)
        ImageView ivItemReview;

        @BindView(R.id.rl_store_view)
        RelativeLayout rlStoreView;

        FullFilledItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}