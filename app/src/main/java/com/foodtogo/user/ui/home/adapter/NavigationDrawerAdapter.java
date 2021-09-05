package com.foodtogo.user.ui.home.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.home.NavDrawerItem;
import com.foodtogo.user.ui.home.activity.Home;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationDrawerAdapter extends BaseAdapter {

    private NavigationCallBack navigationCallBack;
    private List<NavDrawerItem> data;
    private LayoutInflater inflater;
    private Context context;
    private int ITEM_LAST;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data, NavigationCallBack navigationCallBack) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.ITEM_LAST = data.size() - 1;
        this.navigationCallBack = navigationCallBack;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        MyViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.nav_drawer_row, parent, false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }


        NavDrawerItem current = data.get(position);

        if (current.getTitle().equals(context.getString(R.string.nav_item_empty))) {
            holder.dataItem.setVisibility(View.GONE);
            holder.llRoundBall.setVisibility(View.GONE);
        } else {
            holder.dataItem.setVisibility(View.VISIBLE);
            holder.llRoundBall.setVisibility(View.VISIBLE);
            holder.tvNavItemTxt.setText(current.getTitle());
            holder.imgNavIcon.setImageResource(current.getDefalutImg());

            if (Home.selectedPosition == position) {
                holder.imgNavIcon.setImageResource(current.getImgSelected());
                // holder.itemView.setBackgroundResource(R.color.color_orange);
                holder.itemView.setBackgroundResource(android.R.color.transparent);
                holder.tvNavItemTxt.setTextColor(ContextCompat.getColor(context, R.color.edit_txt));
            } else {
                holder.imgNavIcon.setImageResource(current.getDefalutImg());
                holder.itemView.setBackgroundResource(android.R.color.transparent);
                holder.tvNavItemTxt.setTextColor(ContextCompat.getColor(context, R.color.edit_txt));
            }
        }

        int ITEM_FIRST = 0;
        if (position == ITEM_FIRST) {
            holder.dotTopView.setVisibility(View.INVISIBLE);
            holder.dotBottomView.setVisibility(View.VISIBLE);
        } else if (position == ITEM_LAST) {
            holder.dotTopView.setVisibility(View.VISIBLE);
            holder.dotBottomView.setVisibility(View.INVISIBLE);
        }else{
            holder.dotTopView.setVisibility(View.VISIBLE);
            holder.dotBottomView.setVisibility(View.VISIBLE);
        }


        holder.itemView.setOnClickListener(v -> {
            Home.selectedPosition = position;
            notifyDataSetChanged();
            navigationCallBack.clickNavigationDetails(position);

        });
        return convertView;
    }


    class MyViewHolder {

        @BindView(R.id.tv_nav_item_text)
        TextView tvNavItemTxt;

        @BindView(R.id.menu_item_image)
        ImageView imgNavIcon;

        @BindView(R.id.itemView)
        LinearLayout itemView;

        @BindView(R.id.data_item)
        RelativeLayout dataItem;

        @BindView(R.id.ll_round_ball)
        LinearLayout llRoundBall;

        @BindView(R.id.top_view)
        View dotTopView;

        @BindView(R.id.bottom_view)
        View dotBottomView;

        MyViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    public interface NavigationCallBack {

        void clickNavigationDetails(int position);

    }
}
