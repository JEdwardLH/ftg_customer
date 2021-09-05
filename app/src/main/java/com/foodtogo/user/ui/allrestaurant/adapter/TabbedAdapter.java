package com.foodtogo.user.ui.allrestaurant.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.allrestaurant.TabbedMenu;
import com.foodtogo.user.ui.allrestaurant.interfaces.TabClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabbedAdapter extends RecyclerView.Adapter<TabbedAdapter.RestaurantItemRowHolder> {

    private List<TabbedMenu> tabbedMenuList;
    private Context mContext;
    private TabClickListener tabClickListener;

    public TabbedAdapter(Context context, List<TabbedMenu> tabbedMenus) {
        this.tabbedMenuList = tabbedMenus;
        this.mContext = context;
    }

    public void setTabClickListener(TabClickListener tabClickListener) {
        this.tabClickListener = tabClickListener;
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
        TabbedMenu tabMenu = tabbedMenuList.get(i);
        holder.tvCategory.setText(tabMenu.getTabMenu());
        if (tabMenu.isActive()) {
            holder.tvCategory.setBackgroundResource(R.drawable.item_rounded_orange);
            holder.tvCategory.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
        } else {
            holder.tvCategory.setBackgroundResource(R.drawable.item_rounded_white);
            holder.tvCategory.setTextColor(mContext.getResources().getColor(R.color.edit_txt));
        }
        holder.tvCategory.setOnClickListener(v -> {
            boolean isChecked = !tabMenu.isActive();
            tabClickListener.onClickTab(isChecked, i);
        });

    }

    @Override
    public int getItemCount() {
        return (null != tabbedMenuList ? tabbedMenuList.size() : 0);
    }

    class RestaurantItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_category)
        CheckedTextView tvCategory;

        RestaurantItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}