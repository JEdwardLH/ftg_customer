package com.foodtogo.user;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

import com.foodtogo.user.base.LocaleHelper;

import java.util.Locale;

public class BaseApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        System.out.println("Language:"+Locale.getDefault().getDisplayLanguage());
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
        MultiDex.install(this);
    }




}