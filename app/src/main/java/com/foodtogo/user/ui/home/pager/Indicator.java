package com.foodtogo.user.ui.home.pager;

import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.foodtogo.user.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Indicator {
   /* @BindView(R.id.tl)
    TabLayout tl;*/

   /* public Indicator(View view) {
        ButterKnife.bind(this,view);
    }

    public void setSelectedPosition(int position) {
        tl.setScrollPosition(position, 0, true);
    }

    public void setData(ArrayList<String> data) {
        tl.removeAllTabs();
        for (String in : data) {
            View customView = LayoutInflater.from(tl.getContext()).inflate(R.layout.tab_item_indicator, null);
            tl.addTab(tl.newTab().setCustomView(customView));
        }
    }*/
}