package com.foodtogo.user.ui.orderhistory.adapter;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.orderhistory.OrderArray;
import com.foodtogo.user.ui.orderhistory.interfaces.OrderHistoryListener;
import com.foodtogo.user.util.ConversionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderHistoryItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private OrderHistoryListener orderHistoryListener;
    private List<OrderArray> orderArrayList;
    private Context mContext;
    private boolean isLoadingAdded = false;

    public OrderHistoryItemAdapter(Context context, List<OrderArray> orderArrayList) {
        this.orderArrayList = orderArrayList;
        this.mContext = context;
    }

    public void setOrderHistoryListener(OrderHistoryListener orderHistoryListener) {
        this.orderHistoryListener = orderHistoryListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.list_item_order, parent, false);
        viewHolder = new ItemVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderArray orderArray = orderArrayList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final ItemVH itemVH = (ItemVH) holder;
                itemVH.tvOrderOn.setText(ConversionUtils.getFormatDateHistory(orderArray.getOrderDate()));
                String orderAmount = orderArray.getOrdCurrency() + orderArray.getOrderAmount();
                itemVH.tvOrderPrice.setText(orderAmount);
                itemVH.tvOrderId.setText(orderArray.getOrderId());

                itemVH.ivInvoice.setOnClickListener(v -> orderHistoryListener.onClickInvoice(position));
                itemVH.ivRepeatOrder.setOnClickListener(v -> orderHistoryListener.onClickRepeatOrder(orderArray.getOrderId()));
                itemVH.itemView.setOnClickListener(v -> orderHistoryListener.onClickOrderDetails(orderArray.getOrderId()));
                itemVH.tvViewAll.setOnClickListener(v -> orderHistoryListener.onClickOrderDetails(orderArray.getOrderId()));
                itemVH.btnTrack.setOnClickListener(v -> orderHistoryListener.onClickTrack(position));
                itemVH.btnTrack.setVisibility(orderArray.getOrderTrack() ? View.VISIBLE : View.GONE);
                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return orderArrayList == null ? 0 : orderArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == orderArrayList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(OrderArray r) {
        orderArrayList.add(r);
        notifyItemInserted(orderArrayList.size() - 1);
    }

    public void addAll(List<OrderArray> restautrantDetails) {
        for (OrderArray result : restautrantDetails) {
            add(result);
        }
    }


    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new OrderArray());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = orderArrayList.size() - 1;
        OrderArray result = getItem(position);

        if (result != null) {
            orderArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private OrderArray getItem(int position) {
        return orderArrayList.get(position);
    }


    class ItemVH extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_order_id)
        TextView tvOrderId;

        @BindView(R.id.tv_order_on)
        TextView tvOrderOn;

        @BindView(R.id.tv_order_price)
        TextView tvOrderPrice;

        @BindView(R.id.iv_invoice)
        TextView ivInvoice;

        @BindView(R.id.view_all)
        TextView tvViewAll;

        @BindView(R.id.iv_repeat_order)
        LinearLayout ivRepeatOrder;

        @BindView(R.id.tv_track)
        Button btnTrack;


        ItemVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private class LoadingVH extends RecyclerView.ViewHolder {

        private LoadingVH(View itemView) {
            super(itemView);
        }
    }
}