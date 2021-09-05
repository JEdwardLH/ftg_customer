package com.foodtogo.user.data.rest;

import androidx.annotation.NonNull;
import android.util.Log;

import com.foodtogo.user.BaseApplication;
import com.foodtogo.user.data.source.AppDataSource;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.data.source.sharedpreference.AppPreferenceDataSource;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;


public class ApiInterceptor implements Interceptor {


    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_ACCEPT = "Accept";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final String APPLICATION_TYPE = "application/json";


    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        AppDataSource appDataSource = new AppPreferenceDataSource(BaseApplication.getContext());
        AppRepository appRepository = new AppRepository(appDataSource);
        Request chainRequest = chain.request();
        Builder builder = chainRequest.newBuilder();
        builder.header(HEADER_CONTENT_TYPE, APPLICATION_TYPE);
        builder.header(HEADER_ACCEPT, APPLICATION_TYPE);
        builder.header(HEADER_AUTHORIZATION, BEARER + appRepository.getOAuthKey());
        Log.i(HEADER_AUTHORIZATION, appRepository.getOAuthKey());
        Request request = builder.build();
        return chain.proceed(request);
    }
}
