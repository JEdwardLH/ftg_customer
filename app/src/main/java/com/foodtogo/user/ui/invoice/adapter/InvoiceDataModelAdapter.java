package com.foodtogo.user.ui.invoice.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.invoice.ItemList;
import com.foodtogo.user.model.invoice.OrderDetailArray;
import com.foodtogo.user.ui.invoice.interfaces.ClickStoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InvoiceDataModelAdapter extends RecyclerView.Adapter<InvoiceDataModelAdapter.ItemRowHolder> {

    private List<OrderDetailArray> dataList;
    private Context mContext;
    private ClickStoreListener clickStoreListener;

    public InvoiceDataModelAdapter(Context context, List<OrderDetailArray> orderDetailArrays) {
        this.dataList = orderDetailArrays;
        this.mContext = context;
    }

    public void setInfoClickListener(ClickStoreListener clickStoreListener) {
        this.clickStoreListener = clickStoreListener;
    }


    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_invoice_header, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder itemRowHolder, int i) {
        OrderDetailArray orderDetailArray = dataList.get(i);
        final String storeName = orderDetailArray.getStoreName();
        final String storeLocation = orderDetailArray.getStoreLocation();
        final String storePhone = orderDetailArray.getStorePhone();
        itemRowHolder.tvStoreName.setText(storeName);
        List<ItemList> itemLists = orderDetailArray.getItemLists();
        InvoiceItemAdapter itemListDataAdapter = new InvoiceItemAdapter(mContext, itemLists);
        itemListDataAdapter.setInfoClickListener(clickStoreListener);
        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
        itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);
        itemRowHolder.ivInfo.setOnClickListener(v -> clickStoreListener.showStoreLocation(storeName,storeLocation,storePhone));
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.recycler_view_list)
        RecyclerView recycler_view_list;

        @BindView(R.id.tv_store_name)
        TextView tvStoreName;

        @BindView(R.id.iv_info)
        ImageView ivInfo;


        ItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}