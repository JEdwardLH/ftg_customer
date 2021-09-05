package com.foodtogo.user.ui.home.pager;

import android.os.Handler;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.foodtogo.user.R;
import com.foodtogo.user.ui.home.adapter.DemoInfiniteAdapter;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfinitePager {

    @BindView(R.id.vpSlide)
    ViewPager vpSlide;
    @BindView(R.id.worm_dots_indicator)
    WormDotsIndicator wormIndicator;

    ArrayList<String> data;
    private int currentPage = 0;
    public int NUM_PAGES;

   // Indicator indicator;

    DemoInfiniteAdapter adapter;
    DemoInfiniteAdapter.BannerClickListener clickListener;

    public InfinitePager(View view,DemoInfiniteAdapter.BannerClickListener bannerClickListener) {
        ButterKnife.bind(this,view);

        data = new ArrayList<>();
        adapter = new DemoInfiniteAdapter(vpSlide.getContext(), data, true);
        vpSlide.setAdapter(adapter);
        wormIndicator.setViewPager(vpSlide);
        this.clickListener=bannerClickListener;
        adapter.setClickListener(clickListener);

        Handler handler=new Handler();
        final Runnable Update = () -> {
            if (currentPage == NUM_PAGES+2) {
                currentPage = 0;
            }
            vpSlide.setCurrentItem(currentPage++, true);
        };


        Timer timer = new Timer(); // This will create a new Thread
        //delay in milliseconds before task is to be executed
        long DELAY_MS = 4500;
        // time in milliseconds between successive task executions.
        long PERIOD_MS = 4500;
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }



    public void setData(ArrayList<String> data) {
        this.data.clear();
        this.data.addAll(data);
        NUM_PAGES = data.size();
        adapter.setItemList(this.data);


        //indicator.setData(data);
    }





}