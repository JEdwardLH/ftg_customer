package com.foodtogo.user.ui.invoice.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.invoice.ItemList;
import com.foodtogo.user.ui.invoice.interfaces.ClickStoreListener;
import com.foodtogo.user.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.foodtogo.user.base.AppConstants.SPACE;

public class InvoiceItemAdapter extends RecyclerView.Adapter<InvoiceItemAdapter.ItemRowHolder> {

    private List<ItemList> itemListList;
    private Context mContext;
    private ClickStoreListener clickStoreListener;

    InvoiceItemAdapter(Context context, List<ItemList> itemLists) {
        this.itemListList = itemLists;
        this.mContext = context;
    }

    public void setInfoClickListener(ClickStoreListener clickStoreListener) {
        this.clickStoreListener = clickStoreListener;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_invoice, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, int i) {
        ItemList itemList = itemListList.get(i);
        //Store info
        holder.tvItemName.setText(itemList.getItemName());
       /* if(itemList.getCancelStatus()==1){
            holder.tvItemName.setPaintFlags(holder.tvItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }*/
        String priceInfo = itemList.getOrdCurrency() + itemList.getSubTotal();
        holder.tvFoodPrice.setText(priceInfo);
        GlideUtils.showImage(mContext, holder.ivFood, R.drawable.image_placeholder_small, itemList.getPdtImage());
        String extraItems = mContext.getResources().getString(R.string.include) + SPACE + itemList.getSpecialRequest();
        holder.tvInclude.setText(extraItems);
        String quantityInfo = mContext.getResources().getString(R.string.qty) + SPACE + itemList.getOrdQuantity();
        holder.ivQuantityItem.setText(quantityInfo);
        holder.tvInclude.setVisibility(itemList.getSpecialRequest().equals("") ? View.GONE : View.VISIBLE);
        holder.viewExtras.setVisibility(itemList.getSpecialRequest().equals("") ? View.GONE : View.VISIBLE);
        holder.itemView.setOnClickListener(v -> clickStoreListener.showItems(itemList));
    }


    @Override
    public int getItemCount() {
        return (null != itemListList ? itemListList.size() : 0);
    }


    class ItemRowHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.iv_food)
        ImageView ivFood;

        @BindView(R.id.tv_item_name)
        TextView tvItemName;

        @BindView(R.id.tv_food_price)
        TextView tvFoodPrice;

        @BindView(R.id.tv_include)
        TextView tvInclude;

        @BindView(R.id.iv_quantity_item)
        TextView ivQuantityItem;

        @BindView(R.id.view_extras)
        View viewExtras;

        ItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}