package com.foodtogo.user.ui.viewitemdetails.adapter;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;


public class ItemSliderAdapter extends SliderAdapter {

    private List<String> stringList;

    public ItemSliderAdapter(List<String> stringList) {
        this.stringList = stringList;
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        viewHolder.bindImageSlide(stringList.get(position));
    }

}
