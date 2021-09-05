package com.foodtogo.user.ui.myreviews.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.myreviews.RestReviewList;
import com.foodtogo.user.ui.myreviews.interfaces.ReviewRestaurantClickListener;
import com.foodtogo.user.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewRestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<RestReviewList> itemReviewLists;
    private Context mContext;
    private boolean isLoadingAdded = false;
    private ReviewRestaurantClickListener restaurantClickListener;

    public ReviewRestaurantAdapter(Context context, List<RestReviewList> itemReviewLists) {
        this.itemReviewLists = itemReviewLists;
        this.mContext = context;
    }

    public void setReviewRestaurantListener(ReviewRestaurantClickListener restaurantClickListener) {
        this.restaurantClickListener = restaurantClickListener;
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
        View v1 = inflater.inflate(R.layout.list_item_review_item, parent, false);
        viewHolder = new ItemVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RestReviewList reviewList = itemReviewLists.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final ItemVH itemVH = (ItemVH) holder;
                itemVH.tvItemName.setText(reviewList.getRestaurantName());
                itemVH.tvDate.setText(reviewList.getCreatedDate());
                itemVH.tvDesc.setText(reviewList.getReviewComments());
                itemVH.tvStatus.setText(reviewList.getReviewStatus()==0?mContext.getString(R.string.disapproved):mContext.getString(R.string.approved));
                itemVH.tvStatus.setTextColor(reviewList.getReviewStatus()==0? Color.RED:Color.GREEN);
                setRatingView(itemVH.ivRating1, itemVH.ivRating2, itemVH.ivRating3, itemVH.ivRating4, reviewList.getReviewRating());
                GlideUtils.showImage(mContext, itemVH.ivFood, R.drawable.image_placeholder_small,reviewList.getRestaurantImage());
                itemVH.itemView.setOnClickListener(v -> restaurantClickListener.clickItem(position));
                break;
            case LOADING:
//                Do nothing
                break;
        }

    }

    private void setRatingView(ImageView ivRating1, ImageView ivRating2, ImageView ivRating3, ImageView ivRating4, int rating) {
        switch (rating) {
            case 0:
                ivRating1.setVisibility(View.GONE);
                ivRating2.setVisibility(View.GONE);
                ivRating3.setVisibility(View.GONE);
                ivRating4.setVisibility(View.GONE);
                break;
            case 1:
                ivRating1.setVisibility(View.VISIBLE);
                ivRating2.setVisibility(View.GONE);
                ivRating3.setVisibility(View.GONE);
                ivRating4.setVisibility(View.GONE);
                break;
            case 2:
                ivRating1.setVisibility(View.VISIBLE);
                ivRating2.setVisibility(View.VISIBLE);
                ivRating3.setVisibility(View.GONE);
                ivRating4.setVisibility(View.GONE);
                break;
            case 3:
                ivRating1.setVisibility(View.VISIBLE);
                ivRating2.setVisibility(View.VISIBLE);
                ivRating3.setVisibility(View.VISIBLE);
                ivRating4.setVisibility(View.GONE);
                break;
            case 4:
                ivRating1.setVisibility(View.VISIBLE);
                ivRating2.setVisibility(View.VISIBLE);
                ivRating3.setVisibility(View.VISIBLE);
                ivRating4.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return itemReviewLists == null ? 0 : itemReviewLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == itemReviewLists.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(RestReviewList r) {
        itemReviewLists.add(r);
        notifyItemInserted(itemReviewLists.size() - 1);
    }

    public void addAll(List<RestReviewList> restautrantDetails) {
        for (RestReviewList result : restautrantDetails) {
            add(result);
        }
    }


    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new RestReviewList());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = itemReviewLists.size() - 1;
        RestReviewList result = getItem(position);

        if (result != null) {
            itemReviewLists.remove(position);
            notifyItemRemoved(position);
        }
    }

    private RestReviewList getItem(int position) {
        return itemReviewLists.get(position);
    }


    class ItemVH extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_food)
        ImageView ivFood;

        @BindView(R.id.tv_item_name)
        TextView tvItemName;

        @BindView(R.id.tv_date)
        TextView tvDate;

        @BindView(R.id.tv_status)
        TextView tvStatus;

        @BindView(R.id.iv_rating_1)
        ImageView ivRating1;

        @BindView(R.id.iv_rating_2)
        ImageView ivRating2;

        @BindView(R.id.iv_rating_3)
        ImageView ivRating3;

        @BindView(R.id.iv_rating_4)
        ImageView ivRating4;

        @BindView(R.id.tv_desc)
        TextView tvDesc;

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