package com.foodtogo.user.ui.orderhistory.adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.orderhistory.StoreDetails;
import com.foodtogo.user.ui.orderhistory.interfaces.TrackListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackRestaurantAdapter extends RecyclerView.Adapter<TrackRestaurantAdapter.ItemRowHolder> {

    private List<StoreDetails> itemListList;
    private TrackListener trackListener;
    private String transactionId;

    public TrackRestaurantAdapter(List<StoreDetails> itemLists) {
        this.itemListList = itemLists;
    }

    public void setTrackListener(TrackListener trackListener, String transactionId) {
        this.trackListener = trackListener;
        this.transactionId = transactionId;
    }


    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_order_track, null);
        return new ItemRowHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, int i) {
        StoreDetails restaurantDetails = itemListList.get(i);
        holder.tvRestaurantAddress.setText(restaurantDetails.getStore_location());
        holder.tvRestaurantName.setText(restaurantDetails.getStore_name());
        holder.tvTrack.setOnClickListener(v -> trackListener.trackOrder(restaurantDetails.getStore_id(), transactionId));
    }


    @Override
    public int getItemCount() {
        return (null != itemListList ? itemListList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_track)
        Button tvTrack;

        @BindView(R.id.tv_restaurant_address)
        TextView tvRestaurantAddress;

        @BindView(R.id.tv_restaurant_name)
        TextView tvRestaurantName;


        ItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}