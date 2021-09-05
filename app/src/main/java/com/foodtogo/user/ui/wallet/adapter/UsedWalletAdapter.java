package com.foodtogo.user.ui.wallet.adapter;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.wallet.UsedDetail;
import com.foodtogo.user.util.ConversionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Wallet adapter.
 */
public class UsedWalletAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<UsedDetail> usedDetailList;
    private Context mContext;
    private boolean isLoadingAdded = false;
    private String REFERRAL_HISTORY = "REFERRAL_HISTORY";

    /**
     * Instantiates a new Wallet adapter.
     *
     * @param context        the context
     * @param orderArrayList the order array list
     */
    public UsedWalletAdapter(Context context, List<UsedDetail> orderArrayList) {
        this.usedDetailList = orderArrayList;
        this.mContext = context;
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
        View v1 = inflater.inflate(R.layout.list_item_wallet_referral, parent, false);
        viewHolder = new ItemVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UsedDetail usedDetail = usedDetailList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final ItemVH itemVH = (ItemVH) holder;
                String amountDetails = usedDetail.getOrdCurrency() +  usedDetail.getUsedAmount();
                itemVH.tvOrderIdTxt.setText("Order Id");
                itemVH.tvOrderId.setText(usedDetail.getOrderId());
                itemVH.tvDateTxt.setText("Order Date");
                itemVH.tvDate.setText(ConversionUtils.getFormatDateTime(usedDetail.getOrderDate()).toUpperCase());
                itemVH.tvAmountTxt.setText("Wallet Used");
                itemVH.tvAmount.setText(amountDetails);
                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return usedDetailList == null ? 0 : usedDetailList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == usedDetailList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /**
     * Add.
     *
     * @param r the r
     */
    public void add(UsedDetail r) {
        usedDetailList.add(r);
        notifyItemInserted(usedDetailList.size() - 1);
    }

    /**
     * Add all.
     *
     * @param restautrantDetails the restautrant details
     */
    public void addAll(List<UsedDetail> restautrantDetails) {
        for (UsedDetail result : restautrantDetails) {
            add(result);
        }
    }


    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    /**
     * Add loading footer.
     */
    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new UsedDetail());
    }

    /**
     * Remove loading footer.
     */
    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = usedDetailList.size() - 1;
        UsedDetail result = getItem(position);

        if (result != null) {
            usedDetailList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private UsedDetail getItem(int position) {
        return usedDetailList.get(position);
    }


    /**
     * The type Item vh.
     */
    class ItemVH extends RecyclerView.ViewHolder {


        /**
         * The Tv order id.
         */
        @BindView(R.id.tv_order_id_txt)
        TextView tvOrderIdTxt;


        /**
         * The Tv order id.
         */
        @BindView(R.id.tv_order_id)
        TextView tvOrderId;

        /**
         * The Tv order date.
         */
        @BindView(R.id.tv_date_txt)
        TextView tvDateTxt;

        /**
         * The Tv order date.
         */
        @BindView(R.id.tv_date)
        TextView tvDate;


        /**
         * The Tv order date.
         */
        @BindView(R.id.tv_amount_txt)
        TextView tvAmountTxt;


        /**
         * The Tv amount.
         */
        @BindView(R.id.tv_amount)
        TextView tvAmount;

        /**
         * Instantiates a new Item vh.
         *
         * @param itemView the item view
         */
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