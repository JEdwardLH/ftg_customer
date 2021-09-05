package com.foodtogo.user.ui.home.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.home.AllRestaurant;
import com.foodtogo.user.model.home.RestaurantDataModel;
import com.foodtogo.user.model.home.RestaurantDetail;
import com.foodtogo.user.ui.home.interfaces.MoreClickLister;
import com.foodtogo.user.ui.home.interfaces.RestaurantClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.foodtogo.user.ui.home.mvp.HomeModel.ALL_RESTAURANT;
import static com.foodtogo.user.ui.home.mvp.HomeModel.NEARBY;
import static com.foodtogo.user.ui.home.mvp.HomeModel.NEW_RESTAURANTS;
import static com.foodtogo.user.ui.home.mvp.HomeModel.PROMOTIONS;
import static com.foodtogo.user.ui.home.mvp.HomeModel.TOP_RESTAURANTS;
import static com.foodtogo.user.ui.home.mvp.HomeModel.TYPE_RESTAURANTS;

public class HomeDataModelAdapter extends RecyclerView.Adapter<HomeDataModelAdapter.ItemRowHolder> {

    private List<RestaurantDataModel> dataList;
    private Context mContext;
    private MoreClickLister moreListener;
    private RestaurantClickListener restaurantClickListener;
    private static final String CATEGORIES = "Categories";

    public HomeDataModelAdapter(Context context, List<RestaurantDataModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    public void setRestaurantListener(RestaurantClickListener restaurantClickListener) {
        this.restaurantClickListener = restaurantClickListener;
    }

    public void setMoreListener(MoreClickLister moreListener) {
        this.moreListener = moreListener;
    }


    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_home_model, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder itemRowHolder, int i) {
        RestaurantDataModel restaurantDataModel = dataList.get(i);
        final String sectionName = restaurantDataModel.getModel();
        setTitle(itemRowHolder,sectionName);

        itemRowHolder.itemTitle.setAllCaps(false);
        if (restaurantDataModel.getModel().equals(CATEGORIES)) {
            itemRowHolder.btnMore.setVisibility(View.GONE);
            List<AllRestaurant> allRestaurantList = dataList.get(i).getAllRestaurantList();
            CategoriesDataAdapter itemListDataAdapter = new CategoriesDataAdapter(mContext, allRestaurantList);
            itemListDataAdapter.setRestaurantListener(moreListener);
            itemRowHolder.recycler_view_list.setHasFixedSize(true);
            itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
            itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);
            //itemRowHolder.btnMore.setOnClickListener(v -> moreListener.onClickViewAll(itemRowHolder.itemTitle.getText().toString(),allRestaurantList.get(i).getRestaurantId(),restaurantDataModel.getModel()));
        } else {
            int OTHER=1;
            itemRowHolder.itemTitle.setVisibility(View.VISIBLE);
            List<RestaurantDetail> allRestaurantList = dataList.get(i).getAllFoodDetails();
            RestaurantPopularDataAdapter itemListDataAdapter = new RestaurantPopularDataAdapter(mContext, allRestaurantList,dataList.get(i).getDeliveryStatus(),OTHER);
            itemRowHolder.recycler_view_list.setHasFixedSize(true);
            itemListDataAdapter.setRestaurantListener(restaurantClickListener);
            itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
            itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);
            itemRowHolder.btnMore.setOnClickListener(v -> moreListener.onClickViewAll(itemRowHolder.itemTitle.getText().toString(), restaurantDataModel.getCategoryId(),restaurantDataModel.getModel()));
        }


    }

    private void setTitle(ItemRowHolder itemRowHolder,String sectionName){
        if(sectionName.equals(TYPE_RESTAURANTS))
        itemRowHolder.itemTitle.setText(mContext.getString(R.string.restaurant));
        else if(sectionName.equals(NEW_RESTAURANTS))
            itemRowHolder.itemTitle.setText(mContext.getString(R.string.new_restaurants));
        else if (sectionName.equals(TOP_RESTAURANTS))
            itemRowHolder.itemTitle.setText(mContext.getString(R.string.top_restaurants));
        else if (sectionName.equals(PROMOTIONS))
            itemRowHolder.itemTitle.setText(mContext.getString(R.string.promotions));
        else if (sectionName.equals(NEARBY))
            itemRowHolder.itemTitle.setText(mContext.getString(R.string.nearby));
        else if (sectionName.equals(CATEGORIES))
            itemRowHolder.itemTitle.setText(mContext.getString(R.string.categories));
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.recycler_view_list)
        RecyclerView recycler_view_list;

        @BindView(R.id.btnMore)
        TextView btnMore;

        @BindView(R.id.itemTitle)
        TextView itemTitle;


        ItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}