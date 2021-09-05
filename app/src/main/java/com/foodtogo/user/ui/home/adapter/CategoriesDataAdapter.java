package com.foodtogo.user.ui.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.home.AllRestaurant;
import com.foodtogo.user.ui.home.interfaces.MoreClickLister;
import com.foodtogo.user.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesDataAdapter extends RecyclerView.Adapter<CategoriesDataAdapter.FeaturedItemRowHolder> {

    private List<AllRestaurant> allRestaurantList;
    private MoreClickLister restaurantClickListener;
    private Context mContext;
    private static final String CATEGORIES = "Categories";

    CategoriesDataAdapter(Context context, List<AllRestaurant> allRestaurants) {
        this.allRestaurantList = allRestaurants;
        this.mContext = context;
    }

    void setRestaurantListener(MoreClickLister restaurantClickListener) {
        this.restaurantClickListener = restaurantClickListener;
    }

    @NonNull
    @Override
    public FeaturedItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_categories, null);
        return new FeaturedItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedItemRowHolder holder, int i) {
        AllRestaurant allRestaurantDetail = allRestaurantList.get(i);
        holder.catName.setText(allRestaurantDetail.getRestaurantName());
        GlideUtils.showRoundImage(mContext, holder.itemImage,0, allRestaurantDetail.getRestaurantLogo());
        holder.itemView.setOnClickListener(v ->
                restaurantClickListener.onClickViewAll(allRestaurantDetail.getRestaurantName(), allRestaurantDetail.getRestaurantId(),CATEGORIES));
    }

    @Override
    public int getItemCount() {
        return (null != allRestaurantList ? allRestaurantList.size() : 0);
    }

    class FeaturedItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemImage)
        ImageView itemImage;

        @BindView(R.id.tv_cat_name)
        TextView catName;

        FeaturedItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}