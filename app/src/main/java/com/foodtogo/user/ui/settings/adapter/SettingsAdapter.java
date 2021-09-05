package com.foodtogo.user.ui.settings.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.settings.MenuSettings;
import com.foodtogo.user.ui.settings.interfaces.ItemListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ItemRowHolder> {

    private List<MenuSettings> menuSettingsList;
    private Context mContext;
    private ItemListener itemListener;
    private static final String MENU_6_NAME = "Promotional iNotification";

    public SettingsAdapter(Context context, List<MenuSettings> menuSettingsList) {
        this.menuSettingsList = menuSettingsList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_settings, null);
        return new ItemRowHolder(v);
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, int i) {
        MenuSettings menuSettings = menuSettingsList.get(i);
        holder.tvSettingsName.setText(menuSettings.getMenuName());
        holder.ivSettingsIcon.setImageResource(menuSettings.getMenuIcon());
        holder.rlItemView.setOnClickListener(v -> itemListener.itemClick(i));
        if (menuSettings.getMenuName().equals(MENU_6_NAME)) {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.notification_clr));
            holder.ivRightItem.setVisibility(View.GONE);
            holder.ivSwitch.setVisibility(View.VISIBLE);
        } else {
            holder.ivRightItem.setVisibility(View.VISIBLE);
            holder.ivSwitch.setVisibility(View.GONE);
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }
    }

    @Override
    public int getItemCount() {
        return (null != menuSettingsList ? menuSettingsList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_settings_icon)
        ImageView ivSettingsIcon;

        @BindView(R.id.iv_settings_name)
        TextView tvSettingsName;

        @BindView(R.id.iv_switch)
        Switch ivSwitch;

        @BindView(R.id.iv_right_item)
        ImageView ivRightItem;


        @BindView(R.id.rl_item_view)
        RelativeLayout rlItemView;




        ItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}