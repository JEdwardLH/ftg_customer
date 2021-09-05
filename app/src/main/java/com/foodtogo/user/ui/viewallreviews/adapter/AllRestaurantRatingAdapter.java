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
import com.foodtogo.user.model.restaurant.RestaurantReview;
import com.foodtogo.user.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllRestaurantRatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<RestaurantReview> restaurantReviewList;
    private Context mContext;
    private boolean isLoadingAdded = false;

    public AllRestaurantRatingAdapter(Context context, List<RestaurantReview> orderArrayList) {
        this.restaurantReviewList = orderArrayList;
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
        RestaurantReview restaurantReview = restaurantReviewList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final ItemVH itemVH = (ItemVH) holder;
                itemVH.tvRating.setText(restaurantReview.getReviewRating() == 0 ? ""  : ""+ restaurantReview.getReviewRating());
                itemVH.tvRating.setBackgroundResource(restaurantReview.getReviewRating() == 0 ? R.drawable.ic_with_star : R.drawable.ic_without_star );
                itemVH.tvUserName.setText(restaurantReview.getReviewCustomerName());
                itemVH.tvComments.setText(restaurantReview.getReviewComments());
                GlideUtils.showRoundImage(mContext, itemVH.ivProfile, R.drawable.profile_default_pic,restaurantReview.getReviewCustomerProfile());

                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return restaurantReviewList == null ? 0 : restaurantReviewList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == restaurantReviewList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(RestaurantReview r) {
        restaurantReviewList.add(r);
        notifyItemInserted(restaurantReviewList.size() - 1);
    }

    public void addAll(List<RestaurantReview> restautrantDetails) {
        for (RestaurantReview result : restautrantDetails) {
            add(result);
        }
    }

    public void removedItem(int position) {
        restaurantReviewList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, restaurantReviewList.size());
    }


    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new RestaurantReview());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = restaurantReviewList.size() - 1;
        RestaurantReview result = getItem(position);

        if (result != null) {
            restaurantReviewList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private RestaurantReview getItem(int position) {
        return restaurantReviewList.get(position);
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