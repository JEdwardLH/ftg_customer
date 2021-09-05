package com.foodtogo.user.ui.viewrestaurant.mvp;


import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.restaurant.CategoryBasedItems;
import com.foodtogo.user.model.restaurant.RequestRestaurantBasedItem;
import com.foodtogo.user.model.restaurant.RequestRestaurantDetail;
import com.foodtogo.user.model.restaurant.RestaurantDetailResponse;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ViewRestaurantModel implements ViewRestaurantContractor.Model {


    private ViewRestaurantPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    ViewRestaurantModel(ViewRestaurantPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void requestRestaurantDetail(int restaurantId, int reviewPageNo) {
        RequestRestaurantDetail restaurantDetail = new RequestRestaurantDetail();
        restaurantDetail.setLang(appRepository.getLanguageCode());
        restaurantDetail.setRestaurant_id(String.valueOf(restaurantId));
        restaurantDetail.setReview_page_no(String.valueOf(reviewPageNo));
        disposable.add(apiInterface
                .requestRestaurantDetails(restaurantDetail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<RestaurantDetailResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<RestaurantDetailResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onSuccess(baseResponse.getData(),false);
                        } else {
                            mPresenter.apiError(baseResponse.getMessage());
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
    public void requestRestaurantDetailFilter(RequestRestaurantDetail restaurantDetail) {
        disposable.add(apiInterface
                .requestRestaurantDetails(restaurantDetail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<RestaurantDetailResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<RestaurantDetailResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onSuccess(baseResponse.getData(),true);
                        } else {
                            mPresenter.apiError(baseResponse.getMessage());
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
    public Disposable requestRestaurantCategoryBased(String keywords, int restaurantId, int mainCategoryId, int subCategoryId,
                                                     int pageNumber, int sortBy, String orderBySplOffer, String orderByTopOffers,
                                                     String searchCombo, String searchHalal, String itemType,
                                                     ArrayList<String> availableTime) {
        RequestRestaurantBasedItem restaurantDetail = new RequestRestaurantBasedItem();
        restaurantDetail.setLang(appRepository.getLanguageCode());
        restaurantDetail.setRestaurant_id(String.valueOf(restaurantId));
        restaurantDetail.setMain_category_id(mainCategoryId == 0 ? "" : String.valueOf(mainCategoryId));
        restaurantDetail.setSub_category_id(mainCategoryId == 0 ? "" : String.valueOf(subCategoryId));
        restaurantDetail.setSearch_text(keywords);
        restaurantDetail.setSort_by(String.valueOf(sortBy));
        restaurantDetail.setPage_no(String.valueOf(pageNumber));
        restaurantDetail.setItem_type(itemType);
        restaurantDetail.setAll(mainCategoryId == 0 ? "1" : "0");
     /*   restaurantDetail.setOrderBy_spl_offer(orderBySplOffer);
        restaurantDetail.setOrderBy_top_offers(orderByTopOffers);
        restaurantDetail.setSearch_combo(searchCombo);
        restaurantDetail.setSearch_halal(searchHalal);
        restaurantDetail.setAvailable_time(availableTime);*/

        DisposableSingleObserver disposableSingleObserver = apiInterface
                .requestRestaurantCategoryBased(restaurantDetail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<CategoryBasedItems>>() {
                    @Override
                    public void onSuccess(BaseResponse<CategoryBasedItems> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            if (pageNumber == 1) {
                                mPresenter.onSuccess(baseResponse.getData());
                            } else {
                                mPresenter.onLoadMore(baseResponse.getData());
                            }
                        } else {
                            if (pageNumber == 1) {
                                mPresenter.restaurantCategoryError(baseResponse.getMessage());
                            } else {
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
                });
        disposable.add(disposableSingleObserver);
        return disposableSingleObserver;
    }




    @Override
    public void close() {
        disposable.dispose();
    }
}
