package com.foodtogo.user.ui.mycart.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.mycart.AddedItemDetail;
import com.foodtogo.user.model.mycart.CartDetail;
import com.foodtogo.user.ui.mycart.interfaces.MyCartListener;
import com.foodtogo.user.util.ConversionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartHeaderItemAdapter extends RecyclerView.Adapter<CartHeaderItemAdapter.RestaurantItemRowHolder> {

    private List<CartDetail> itemListList;
    private Context mContext;
    private MyCartListener myCartListener;

    public CartHeaderItemAdapter(Context context, List<CartDetail> itemLists) {
        this.itemListList = itemLists;
        this.mContext = context;
    }

    public void setAddCartListener(MyCartListener myCartListener) {
        this.myCartListener = myCartListener;
    }

    @NonNull
    @Override
    public RestaurantItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_cart_item_header, null);
        return new RestaurantItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantItemRowHolder holder, int i) {
        CartDetail cartDetail = itemListList.get(i);
        holder.tvStoreName.setText(cartDetail.getStoreName());
        if (cartDetail.getPreOrderStatus().equals("Available")) {
            holder.tvPreOrder.setVisibility(View.VISIBLE);
            holder.ivPreOrder.setVisibility(View.VISIBLE);
        } else {
            holder.tvPreOrder.setVisibility(View.GONE);
            holder.ivPreOrder.setVisibility(View.GONE);
        }
        List<AddedItemDetail> addedItemDetails = cartDetail.getAddedItemDetails();
        if (addedItemDetails.size() > 0) {
            String date = addedItemDetails.get(0).getCartPreOrder();
            date=addedItemDetails.get(0).getCartPreOrder().equals("0000-00-00 00:00:00")?"":date;
            holder.tvPreOrder.setText(date.equals("") ? mContext.getResources().getString(R.string.pre_order_camel) : ConversionUtils.getFormatDateTime(date));
            holder.ivPreOrderClose.setVisibility(date.equals("")?View.GONE:View.VISIBLE);
        }
        CartItemAdapter itemListDataAdapter = new CartItemAdapter(mContext, addedItemDetails);
        holder.recyclerSubItems.setHasFixedSize(true);
        itemListDataAdapter.setAddCartListener(myCartListener);
        holder.recyclerSubItems.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        holder.recyclerSubItems.setAdapter(itemListDataAdapter);
        holder.llPreOrder.setOnClickListener(v -> myCartListener.clickPreOrder(cartDetail.getStoreId(), cartDetail.getStoreName() + "'s Items(" + cartDetail.getAddedItemDetails().size() + ")"));
        holder.ivPreOrderClose.setOnClickListener(v->myCartListener.clickRemovePreOrder(cartDetail.getStoreId()));
    }


    @Override
    public int getItemCount() {
        return (null != itemListList ? itemListList.size() : 0);
    }

    class RestaurantItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_store_name)
        TextView tvStoreName;

        @BindView(R.id.tv_pre_order)
        TextView tvPreOrder;

        @BindView(R.id.iv_pre_order)
        ImageView ivPreOrder;

        @BindView(R.id.pre_order_close)
        ImageView ivPreOrderClose;

        @BindView(R.id.ll_pre_order)
        LinearLayout llPreOrder;

        @BindView(R.id.recycler_sub_items)
        RecyclerView recyclerSubItems;

        RestaurantItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}