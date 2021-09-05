package com.foodtogo.user.ui.home.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.home.RecentOrders;
import com.foodtogo.user.ui.home.interfaces.RestaurantClickListener;
import com.foodtogo.user.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDataAdapter extends RecyclerView.Adapter<RestaurantDataAdapter.RestaurantItemRowHolder> {

    private List<RecentOrders> allRecentOrdersList;
    private RestaurantClickListener restaurantClickListener;
    private Context mContext;

    public RestaurantDataAdapter(Context context, List<RecentOrders> recentOrdersList) {
        this.allRecentOrdersList = recentOrdersList;
        this.mContext = context;
    }

    public void setRestaurantListener(RestaurantClickListener restaurantClickListener) {
        this.restaurantClickListener = restaurantClickListener;
    }

    @NonNull
    @Override
    public RestaurantItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_recent_order, null);
        return new RestaurantItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantItemRowHolder holder, int i) {
        RecentOrders orders = allRecentOrdersList.get(i);
        holder.tvResName.setText(orders.getStemStoreName());
        holder.tvItemName.setText(orders.getItemName());
        String amount=orders.getItemHasDiscount().equals("Yes")?orders.getItemDiscountPrice():orders.getItemOriginalPrice();
        holder.tvItemPrice.setText(mContext.getString(R.string.hour_min,orders.getItemCurrency(),amount));
        GlideUtils.showImageCenter(mContext, holder.itemImage, R.drawable.image_placeholder_medium,orders.getItemImage());
        //holder.itemView.setOnClickListener(v -> restaurantClickListener.onClickRestaurant(orders.getItemName(), orders.getItemId()));
        holder.tvOrderAgain.setOnClickListener(v->restaurantClickListener.onClickOrderAgain(orders.getOrderId()));
    }

    @Override
    public int getItemCount() {
        return (null != allRecentOrdersList ? allRecentOrdersList.size() : 0);
    }

    class RestaurantItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_res_name)
        TextView tvResName;

        @BindView(R.id.tv_item_name)
        TextView tvItemName;

        @BindView(R.id.item_price)
        TextView tvItemPrice;

        @BindView(R.id.order_again)
        TextView tvOrderAgain;

        @BindView(R.id.iv_food)
        ImageView itemImage;

        RestaurantItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}