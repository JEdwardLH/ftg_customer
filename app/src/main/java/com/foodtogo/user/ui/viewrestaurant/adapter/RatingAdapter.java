package com.foodtogo.user.ui.viewrestaurant.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.restaurant.RestaurantReview;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingRowHolder> {

    private List<RestaurantReview> restaurantReviews;
    private Context mContext;
    private int limitSize;


    public RatingAdapter(Context context, List<RestaurantReview> restaurantReviews1) {
        this.restaurantReviews = restaurantReviews1;
        this.mContext = context;
    }

    public void setRatingLimit(int limitSize) {
        this.limitSize = limitSize;
    }

    @NonNull
    @Override
    public RatingRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_restaruant_rating, null);
        return new RatingRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingRowHolder holder, int i) {
        RestaurantReview restaurantReview = restaurantReviews.get(i);
        //holder.tvRating.setText("" + restaurantReview.getReviewRating());
        holder.tvRating.setText(restaurantReview.getReviewRating() == 0 ? ""  : ""+ restaurantReview.getReviewRating());
        holder.tvRating.setBackgroundResource(restaurantReview.getReviewRating() == 0 ? R.drawable.ic_with_star : R.drawable.ic_without_star );

        holder.tvUserName.setText(restaurantReview.getReviewCustomerName());
        holder.tvComments.setText(restaurantReview.getReviewComments());

    }

    @Override
    public int getItemCount() {
        return limitSize;
    }

    class RatingRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_rating)
        TextView tvRating;

        @BindView(R.id.tv_user_name)
        TextView tvUserName;

        @BindView(R.id.tv_comments)
        TextView tvComments;

        RatingRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}