package com.foodtogo.user.util;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.foodtogo.user.base.GlideApp;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.foodtogo.user.base.AppConstants.EXCEPTION;

public class GlideUtils {

    public static void showImage(Context context, ImageView imageView, int placeHolder, String imageUrl) {
        try {
            if (context != null)
                GlideApp.with(context)
                        .load(imageUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .placeholder(placeHolder)
                        .into(imageView);
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }
    public static void showCatImage(Context context, ImageView imageView, int placeHolder, String imageUrl) {
        try {
            if (context != null)
                GlideApp.with(context)
                        .load(imageUrl).dontAnimate()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .placeholder(0)
                        .into(imageView);
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }


    public static void showRoundImage(Context context, ImageView imageView, int placeHolder, String imageUrl) {
        try {
            if (context != null)
                GlideApp.with(context)
                        .load(imageUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .transform(new CircleCrop())
                        .placeholder(placeHolder)
                        .into(imageView);
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }


    public static void showBlurImage(Context context, ImageView imageView, int placeHolder, String imageUrl) {
        try {
            if (context != null)
                GlideApp.with(context)
                        .load(imageUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .transform(new BlurTransformation(70))
                        .placeholder(placeHolder)
                        .into(imageView);
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    public static void showCrossFadeImage(Context context, ImageView imageView, String imageUrl) {
        try {
            if (context != null)
                GlideApp.with(context)
                        .load(imageUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView);
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }


    public static void showDefaultBlurImage(Context context, ImageView imageView, int placeHolder) {
        try {
            if (context != null)
                GlideApp.with(context)
                        .load(placeHolder)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .transform(new BlurTransformation(70))
                        .placeholder(placeHolder)
                        .into(imageView);
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }


    public static void showDefaultRoundImage(Context context, ImageView imageView, int placeHolder) {
        try {
            if (context != null)
                GlideApp.with(context)
                        .load(placeHolder)
                        .transform(new CircleCrop())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .placeholder(placeHolder)
                        .into(imageView);
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    public static void showImageCenter(Context context, ImageView imageView, int placeHolder, String imageUrl) {
        try {
            if (context != null)
                GlideApp.with(context)
                        .load(imageUrl)
                        .apply(new RequestOptions().centerCrop())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .placeholder(placeHolder)
                        .into(imageView);
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }
}
