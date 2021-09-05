package com.foodtogo.user.ui.viewallreviews.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.itemdetails.ItemReviews;
import com.foodtogo.user.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllItemRatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<ItemReviews> itemReviewsList;
    private Context mContext;
    private boolean isLoadingAdded = false;

    public AllItemRatingAdapter(Context context, List<ItemReviews> itemReviewsList) {
        this.itemReviewsList = itemReviewsList;
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
        View v1 = inflater.inflate(R.layout.list_item_restaruant_all_rating, parent, false);
        viewHolder = new ItemVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemReviews itemReviews = itemReviewsList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final ItemVH itemVH = (ItemVH) holder;
                //holder.tvRating.setText("" + restaurantReview.getReviewRating());
                itemVH.tvRating.setText(itemReviews.getReviewRating() == 0 ? ""  : ""+ itemReviews.getReviewRating());
                itemVH.tvRating.setBackgroundResource(itemReviews.getReviewRating() == 0 ? R.drawable.ic_with_star : R.drawable.ic_without_star );

                itemVH.tvUserName.setText(itemReviews.getReviewCustomerName());
                itemVH.tvComments.setText(itemReviews.getReviewComments());
                GlideUtils.showRoundImage(mContext, itemVH.ivProfile, R.drawable.profile_default_pic, itemReviews.getReviewCustomerProfile());
                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return itemReviewsList == null ? 0 : itemReviewsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == itemReviewsList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(ItemReviews r) {
        itemReviewsList.add(r);
        notifyItemInserted(itemReviewsList.size() - 1);
    }

    public void addAll(List<ItemReviews> restautrantDetails) {
        for (ItemReviews result : restautrantDetails) {
            add(result);
        }
    }

    public void removedItem(int position) {
        itemReviewsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemReviewsList.size());
    }


    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new ItemReviews());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = itemReviewsList.size() - 1;
        ItemReviews result = getItem(position);

        if (result != null) {
            itemReviewsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private ItemReviews getItem(int position) {
        return itemReviewsList.get(position);
    }


    class ItemVH extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_rating)
        TextView tvRating;

        @BindView(R.id.tv_user_name)
        TextView tvUserName;

        @BindView(R.id.tv_comments)
        TextView tvComments;

        @BindView(R.id.iv_profile)
        ImageView ivProfile;

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