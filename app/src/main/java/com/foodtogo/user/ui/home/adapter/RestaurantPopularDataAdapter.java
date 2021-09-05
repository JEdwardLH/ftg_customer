package com.foodtogo.user.ui.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodtogo.user.BaseApplication;
import com.foodtogo.user.R;
import com.foodtogo.user.model.home.RestaurantDetail;
import com.foodtogo.user.ui.home.interfaces.RestaurantClickListener;
import com.foodtogo.user.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantPopularDataAdapter extends RecyclerView.Adapter<RestaurantPopularDataAdapter.RestaurantItemRowHolder> {

    private List<RestaurantDetail> allRestaurantDetails;
    private RestaurantClickListener restaurantClickListener;
    private Context mContext;
    private int deliveryFeeStatus;
    private int All_RESTAURANT=0;
    private int OTHER=1;
    private int type;

    public RestaurantPopularDataAdapter(Context context, List<RestaurantDetail> allRestaurants,int deliveryStatus, int mType) {
        this.allRestaurantDetails = allRestaurants;
        this.mContext = context;
        this.deliveryFeeStatus=deliveryStatus;
        this.type=mType;
    }

    public void setRestaurantListener(RestaurantClickListener restaurantClickListener) {
        this.restaurantClickListener = restaurantClickListener;
    }


    @NonNull
    @Override
    public RestaurantItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(type==1?R.layout.list_item_food:R.layout.all_restaurant_llist_item, null);
        return new RestaurantItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantItemRowHolder holder, int i) {
        RestaurantDetail allRestaurantDetail = allRestaurantDetails.get(i);
        holder.tvCatName.setVisibility(View.VISIBLE);
        if(allRestaurantDetail.getRestaurantCat()!=null) {
                String catName=allRestaurantDetail.getRestaurantCat();
                holder.tvCatName.setText(catName.contains(",")?catName.replace(",", " | "):catName);
        }
        holder.tvResName.setText(allRestaurantDetail.getRestaurantName());
        holder.tvFoodTiming.setText(allRestaurantDetail.getTodayWkingTime());
       // setSizeOfText(allRestaurantDetail.getDeliveryDuration(),mContext.getString(R.string.min)
        holder.tvDeliveryTime.setText(allRestaurantDetail.getDeliveryDuration());
        holder.tvRating.setText(allRestaurantDetail.getRestaurantRating() == 0 ? "" : "" + allRestaurantDetail.getRestaurantRating());
        holder.tvRating.setBackgroundResource(allRestaurantDetail.getRestaurantRating() == 0 ? R.drawable.ic_rating_with_star : R.drawable.ic_rating_bubble_color);
        GlideUtils.showImageCenter(mContext, holder.ivFood, R.drawable.image_placeholder_medium, type==1?allRestaurantDetail.getRestaurantImage():allRestaurantDetail.getRestaurantLogo());
        holder.itemView.setOnClickListener(v ->
                restaurantClickListener.onClickRestaurant(allRestaurantDetail.getRestaurantName(), allRestaurantDetail.getRestaurantId()));

        holder.tvFreeDeliveryStatus.setVisibility(deliveryFeeStatus==0?View.VISIBLE:View.GONE);
        if(deliveryFeeStatus==0){
            String free = getColoredSpanned(mContext.getString(R.string.free), "#8413D1");
            String delivery = getColoredSpanned(mContext.getString(R.string.delivery), "#0000");
            holder.tvFreeDeliveryStatus.setText(Html.fromHtml(free+" "+delivery));
        }
    }


    @Override
    public int getItemCount() {
        return (null != allRestaurantDetails ? allRestaurantDetails.size() : 0);
    }

    class RestaurantItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_rating)
        TextView tvRating;

        @BindView(R.id.tv_item_name)
        TextView tvResName;

        @BindView(R.id.tv_food_timing)
        TextView tvFoodTiming;

        @BindView(R.id.tv_category_name)
        TextView tvCatName;

        @BindView(R.id.delivery_time)
        TextView tvDeliveryTime;

        @BindView(R.id.iv_food)
        ImageView ivFood;

        @BindView(R.id.tv_free_delivery)
        TextView tvFreeDeliveryStatus;




        RestaurantItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

}