package com.foodtogo.user.ui.myoffers.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.offers.OffersData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOffersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<OffersData> offerDataList;
    private Context mContext;
    private boolean isLoadingAdded = false;

    public MyOffersAdapter(List<OffersData> offerDataList, Context mContext) {
        this.offerDataList = offerDataList;
        this.mContext = mContext;
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
                viewHolder = new MyOffersAdapter.LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.list_item_offers, parent, false);
        viewHolder = new ItemVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OffersData offersData = offerDataList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final ItemVH itemVH = (ItemVH) holder;
                itemVH.tvNameTxt.setText(offersData.getName());
                itemVH.tvDesText.setText(offersData.getDesc());
                itemVH.dateTxt.setText(offersData.getExpiredOn());
                itemVH.offerAmountTxt.setText(String.format("%s%s", offersData.getCurrency(), offersData.getPrice()));
                itemVH.tvStatus.setText(offersData.getStatus());
                itemVH.tvOrderIdLay.setVisibility(offersData.getOrderId().equals("")?View.GONE:View.VISIBLE);
                itemVH.tvOrderId.setText(offersData.getOrderId());
                break;

            case LOADING:
//                Do nothing
                break;
        }
    }

    @Override
    public int getItemCount() {
        return offerDataList == null ? 0 : offerDataList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return (position == offerDataList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(OffersData r) {
        offerDataList.add(r);
        notifyItemInserted(offerDataList.size() - 1);
    }
    public void addAll(List<OffersData> offersDetails) {
        for(int i=0;i<offersDetails.size();i++){
            add(offersDetails.get(i));
        }
        System.out.println("add all..");
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new OffersData());
    }

    public void removeLoadingFooter() {
            isLoadingAdded = false;
            int position = offerDataList.size() - 1;
            OffersData result = getItem(position);
            if (result != null) {
                offerDataList.remove(position);
                notifyItemRemoved(position);
            }

    }

    private OffersData getItem(int position) {
        return offerDataList.get(position);
    }


    class ItemVH extends RecyclerView.ViewHolder {

        @BindView(R.id.name_text)
        TextView tvNameTxt;

        @BindView(R.id.desc_text)
        TextView tvDesText;

        @BindView(R.id.tv_date)
        TextView dateTxt;

        @BindView(R.id.tv_amount)
        TextView offerAmountTxt;

        @BindView(R.id.tv_status)
        TextView tvStatus;

        @BindView(R.id.order_id_txt)
        TextView tvOrderId;

        @BindView(R.id.order_id_lay)
        LinearLayout tvOrderIdLay;

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
