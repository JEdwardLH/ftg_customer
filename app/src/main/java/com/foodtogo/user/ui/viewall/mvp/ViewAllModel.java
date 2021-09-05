package com.foodtogo.user.ui.viewall.mvp;


import com.foodtogo.user.BaseApplication;
import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.allrestaurant.AllRestaurantResponse;
import com.foodtogo.user.model.categorybased.CategoryBasedRequest;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ViewAllModel implements ViewAllContractor.Model {


    private ViewAllPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;
    private static final String NEW_RESTAURANTS = "New Restaurant";
    private static final String TOP_RESTAURANTS = "Top Restaurant";
    private static final String NEARBY = "Nearby";
    private static final String PROMOTIONS = "Promotions";
    private static final String CATEGORIES = "Categories";

    ViewAllModel(ViewAllPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void onViewCategoryBasedItemRequest(String categoryId, int pageNo,String type) {
        CategoryBasedRequest categoryBasedRequest = new CategoryBasedRequest();
        ArrayList<String> categoriesId=new ArrayList<>();
        if(type.equals(CATEGORIES)){
            categoriesId.add(categoryId);
            categoryBasedRequest.setCategory_id(categoriesId);
        }
        categoryBasedRequest.setLang(appRepository.getLanguageCode());
        categoryBasedRequest.setPage(String.valueOf(pageNo));
        categoryBasedRequest.setUser_latitude(appRepository.getLatitude());
        categoryBasedRequest.setUser_longitude(appRepository.getLongitude());
        Single<BaseResponse<AllRestaurantResponse>> url = null;
        if(type.equals(NEW_RESTAURANTS)){
           url=apiInterface.getNewRestaurantList(categoryBasedRequest);
        }else if(type.equals(TOP_RESTAURANTS)){
            url=apiInterface.getTopRestaurantList(categoryBasedRequest);
        }else if(type.equals(NEARBY)){
            url=apiInterface.getNearByRestaurantList(categoryBasedRequest);
        }else if(type.equals(PROMOTIONS)){
            url=apiInterface.getFeaturedRestaurant(categoryBasedRequest);
        }else if(type.equals(CATEGORIES)){
            url=apiInterface.getCategoryBasedRestaurant(categoryBasedRequest);
        }else{
            mPresenter.apiError(BaseApplication.getContext().getString(R.string.no_type_restaurant));
        }

        if(url!=null)
        disposable.add(url
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<AllRestaurantResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<AllRestaurantResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            if(pageNo==1) {
                                mPresenter.onViewCategoryBasedItems(baseResponse.getData());
                            }else{
                                mPresenter.onLoadMore(baseResponse.getData());
                            }
                        } else {
                            if(pageNo==1) {
                                mPresenter.apiError(baseResponse.getMessage());
                            }else{
                                mPresenter.onLoadMoreError(baseResponse.getMessage());
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            mPresenter.apiError(NetworkUtils.getErrorMessage(responseBody));
                        } else if (e instanceof SocketTimeoutException) {
                            mPresenter.apiError(R.string.time_out_error);
                        } else if (e instanceof IOException) {
                            mPresenter.apiError(R.string.network_error);
                        } else {
                            mPresenter.apiError(e.getMessage());
                        }

                    }
                }));
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
