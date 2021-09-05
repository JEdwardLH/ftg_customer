package com.foodtogo.user.ui.myrewards.adapter;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.wallet.Reward;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.foodtogo.user.util.ConversionUtils.getFormatDateTime;

public class RewardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final String EARNED = "Earned";
    private static final String REWARDED = "Rewarded";
    private List<Reward> usedDetailList;
    private Context mContext;
    private boolean isLoadingAdded = false;
    private String fromValue;

    public RewardAdapter(Context context, List<Reward> orderArrayList, String from) {
        this.usedDetailList = orderArrayList;
        this.mContext = context;
        this.fromValue = from;
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
        View v1 = inflater.inflate(R.layout.list_item_rewards, parent, false);
        viewHolder = new ItemVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Reward reward = usedDetailList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final ItemVH itemVH = (ItemVH) holder;
                if (fromValue.equals(EARNED)) {
                    itemVH.description.setVisibility(View.VISIBLE);
                    itemVH.description.setText(reward.getDesc());
                    itemVH.tvOrderIdTxt.setText(mContext.getString(R.string.order_ids));
                    itemVH.tvOrderIdValue.setText(reward.getOrderId());
                    itemVH.pointsTxt.setText(mContext.getString(R.string.point_earned));
                    itemVH.tvPointsValue.setText(reward.getPoints());
                    itemVH.tvDateValue.setText(getFormatDateTime(reward.getAddedDate()));
                } else {
                    itemVH.description.setVisibility(View.GONE);
                    itemVH.tvOrderIdTxt.setText(mContext.getString(R.string.reward_amount));
                    itemVH.tvOrderIdValue.setText(reward.getWalletAmt());
                    itemVH.pointsTxt.setText(mContext.getString(R.string.points));
                    itemVH.tvPointsValue.setText(reward.getPoints());
                    itemVH.tvDateValue.setText(getFormatDateTime(reward.getActionedOn()));

                }
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


    public void add(Reward r) {
        usedDetailList.add(r);
        notifyItemInserted(usedDetailList.size() - 1);
    }

    public void addAll(List<Reward> restautrantDetails) {
        for(int i=0;i<restautrantDetails.size();i++){
            add(restautrantDetails.get(i));
        }
    }


    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Reward());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = usedDetailList.size() - 1;
        Reward result = getItem(position);

        if (result != null) {
            usedDetailList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private Reward getItem(int position) {
        return usedDetailList.get(position);
    }


    class ItemVH extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_order_id_txt)
        TextView tvOrderIdTxt;

        @BindView(R.id.tv_order_id)
        TextView tvOrderIdValue;

        @BindView(R.id.desc_text)
        TextView description;

        @BindView(R.id.points)
        TextView pointsTxt;

        @BindView(R.id.tv_points)
        TextView tvPointsValue;

        @BindView(R.id.tv_date_txt)
        TextView tvDateTxt;

        @BindView(R.id.tv_date)
        TextView tvDateValue;


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