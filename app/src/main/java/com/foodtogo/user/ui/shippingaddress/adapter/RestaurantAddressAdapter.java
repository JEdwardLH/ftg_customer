package com.foodtogo.user.ui.shippingaddress.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.mycart.StoreLocations;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantAddressAdapter extends RecyclerView.Adapter<RestaurantAddressAdapter.ItemRowHolder> {

    private ArrayList<StoreLocations> storeLocations;
    private Context mContext;

    public RestaurantAddressAdapter(Context context, ArrayList<StoreLocations> itemLists) {
        this.storeLocations = itemLists;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_restaurant_address, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, int i) {
        StoreLocations locations = storeLocations.get(i);
        //Store info
        holder.tvStoreName.setText(locations.getStoreName());
        holder.tvStoreAddress.setText(locations.getStoreLocation());

    }


    @Override
    public int getItemCount() {
        return (null != storeLocations ? storeLocations.size() : 0);
    }


    class ItemRowHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_store_name)
        TextView tvStoreName;

        @BindView(R.id.tv_store_address)
        TextView tvStoreAddress;


        ItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}