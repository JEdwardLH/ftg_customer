package com.foodtogo.user.ui.viewitemdetails.service;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.foodtogo.user.base.GlideApp;

import static com.foodtogo.user.base.AppConstants.EXCEPTION;

public class ImageLoadingService implements ss.com.bannerslider.ImageLoadingService {
    public Context context;

    public ImageLoadingService(Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        try {
            if (context != null)
                GlideApp.with(context)
                        .load(url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView);
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    @Override
    public void loadImage(int resource, ImageView imageView) {
        try {
            if (context != null)
                GlideApp.with(context)
                        .load(resource)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView);
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
        try {
            if (context != null)
                GlideApp.with(context)
                        .load(url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .placeholder(placeHolder)
                        .into(imageView);
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }
}
