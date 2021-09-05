package com.foodtogo.user.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.foodtogo.user.R;
import com.foodtogo.user.util.GlideUtils;


import java.util.ArrayList;


public class DemoInfiniteAdapter extends LoopingPagerAdapter<String> {

     ArrayList<String> mItemList;
     private BannerClickListener clickListener;
    public DemoInfiniteAdapter(Context context, ArrayList<String> itemList, boolean isInfinite) {
        super(context, itemList, isInfinite);
        this.mItemList = itemList;
    }

    public void setClickListener(BannerClickListener clickListener) {
        this.clickListener = clickListener;
    }

    //This method will be triggered if the item View has not been inflated before.
    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {
        return LayoutInflater.from(context).inflate(R.layout.dot_layout, container, false);
    }

    //Bind your data with your item View here.
    //Below is just an example in the demo app.
    //You can assume convertView will not be null here.
    //You may also consider using a ViewHolder pattern.
    @Override
    protected void bindView(View convertView, int listPosition, int viewType) {
        ImageView imageview=convertView.findViewById(R.id.image);
        GlideUtils.showImage(context,imageview,R.drawable.image_placeholder_medium,itemList.get(listPosition));
        imageview.setOnClickListener(v->clickListener.bannerClick(listPosition));
    }

    public interface BannerClickListener{
        void bannerClick(int position);
    }

    
}