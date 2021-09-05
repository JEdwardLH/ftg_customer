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
import com.foodtogo.user.model.restaurant.CategoryList;
import com.foodtogo.user.ui.viewrestaurant.interfaces.CategoryClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantCategoryAdapter extends RecyclerView.Adapter<RestaurantCategoryAdapter.RestaurantItemRowHolder> {

    private List<CategoryList> categoryLists;
    private Context mContext;
    private int itemPosition;
    private CategoryClickListener categoryClickListener;

    public RestaurantCategoryAdapter(Context context, List<CategoryList> allRestaurants) {
        this.categoryLists = allRestaurants;
        this.mContext = context;
    }

    public void setCategoryClick(CategoryClickListener mCategoryClickListener) {
        this.categoryClickListener = mCategoryClickListener;
    }

    public void setSelectedItem(int _itemPosition) {
        this.itemPosition = _itemPosition;
    }

    @NonNull
    @Override
    public RestaurantItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_restaruant_category, null);
        return new RestaurantItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantItemRowHolder holder, int i) {
        CategoryList categoryList = categoryLists.get(i);
        holder.tvCategory.setText(categoryList.getMainCategoryName());
        if (itemPosition == i) {
            holder.tvCategory.setBackgroundResource(R.drawable.item_rounded_orange);
            holder.tvCategory.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
        } else {
            holder.tvCategory.setBackgroundResource(R.drawable.item_rounded_white);
            holder.tvCategory.setTextColor(mContext.getResources().getColor(R.color.edit_txt));
        }

    }

    @Override
    public int getItemCount() {
        return (null != categoryLists ? categoryLists.size() : 0);
    }

    class RestaurantItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_category)
        TextView tvCategory;

        RestaurantItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> categoryClickListener.categoryClick(getAdapterPosition()));
        }
    }

}