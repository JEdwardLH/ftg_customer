package com.foodtogo.user.ui.allrestaurant.mvp;


import android.util.Log;

import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.allrestaurant.AllRestaurantResponse;
import com.foodtogo.user.model.allrestaurant.CategoryList;
import com.foodtogo.user.model.categorybased.CategoryBasedRequest;
import com.foodtogo.user.model.home.HomeRequest;
import com.foodtogo.user.util.DataUtils;
import com.foodtogo.user.util.NetworkUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import static com.foodtogo.user.base.AppConstants.EXCEPTION;

public class AllRestaurantModel implements AllRestaurantContractor.Model {


    private AllRestaurantPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    AllRestaurantModel(AllRestaurantPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void requestAllRestaurant(int pageNo) {
        HomeRequest homeRequest = new HomeRequest();
        homeRequest.setUser_latitude(appRepository.getLatitude());
        homeRequest.setUser_longitude(appRepository.getLongitude());
        homeRequest.setLang(appRepository.getLanguageCode());
        homeRequest.setPage(String.valueOf(pageNo));
        disposable.add(apiInterface
                .getAllRestaurant(homeRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<AllRestaurantResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<AllRestaurantResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            if (pageNo == 1) {
                                mPresenter.onAllRestaurant(baseResponse.getData());
                            } else {
                                mPresenter.onLoadMore(baseResponse.getData());
                            }
                        } else {
                            if (pageNo == 1) {
                                mPresenter.apiError(baseResponse.getMessage());
                            } else {
                                mPresenter.showLoadMoreError(baseResponse.getMessage());
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
    public void requestAllRestaurant(int pageNo, String categoryId) {

    }


    @Override
    public void onViewCategoryBasedItemRequest(String type, int pageNo, ArrayList<CategoryList> categoryArrayList,
                                               String filterJSON, String searchHalal, String orderByDelivery, String orderByRating, String orderByOffers,ArrayList<String> categoryIds) {
        if (type.equals("All")) {
            try {
                JSONObject jsonObject = new JSONObject(filterJSON);
                String itemType = jsonObject.getString(DataUtils.KEY_ITEM_TYPE);
                ArrayList<Integer> itemListType = getItemType(itemType);
                ArrayList<Integer> preferTime = new ArrayList<>();
                        //getPreferTime(jsonObject);
                HomeRequest homeRequest = new HomeRequest();
                homeRequest.setUser_latitude(appRepository.getLatitude());
                homeRequest.setUser_longitude(appRepository.getLongitude());
                homeRequest.setLang(appRepository.getLanguageCode());
                homeRequest.setPage(String.valueOf(pageNo));
                homeRequest.setOrderBy_delivery(orderByDelivery);
                homeRequest.setOrderBy_offers(orderByOffers);
                homeRequest.setOrderBy_rating(orderByRating);
                homeRequest.setSearch_halal("");
                homeRequest.setRestaurant_type(itemListType);
                homeRequest.setPrefer_time(preferTime);
                disposable.add(apiInterface
                        .getAllRestaurant(homeRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<BaseResponse<AllRestaurantResponse>>() {
                            @Override
                            public void onSuccess(BaseResponse<AllRestaurantResponse> baseResponse) {
                                if (baseResponse.getCode() == 200) {
                                    if (pageNo == 1) {
                                        mPresenter.categoryBasedResponse(baseResponse.getData());
                                    } else {
                                        mPresenter.onLoadMore(baseResponse.getData());
                                    }
                                } else {
                                    if (pageNo == 1) {
                                        mPresenter.apiError(baseResponse.getMessage());
                                    } else {
                                        mPresenter.showLoadMoreError(baseResponse.getMessage());
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
            } catch (Exception e) {
                Log.e(EXCEPTION, e.toString());
            }
        } else {
            try {
                JSONObject jsonObject = new JSONObject(filterJSON);
                String itemType = jsonObject.getString(DataUtils.KEY_ITEM_TYPE);
                ArrayList<Integer> itemListType = getItemType(itemType);
                ArrayList<Integer> preferTime = new ArrayList<>();
                        //getPreferTime(jsonObject);
                CategoryBasedRequest categoryBasedRequest = new CategoryBasedRequest();
                categoryBasedRequest.setLang(appRepository.getLanguageCode());
                categoryBasedRequest.setPage(String.valueOf(pageNo));
                categoryBasedRequest.setUser_latitude(appRepository.getLatitude());
                categoryBasedRequest.setUser_longitude(appRepository.getLongitude());
                categoryBasedRequest.setOrderBy_delivery(orderByDelivery);
                categoryBasedRequest.setOrderBy_offers(orderByOffers);
                categoryBasedRequest.setOrderBy_rating(orderByRating);
                categoryBasedRequest.setSearch_halal("");
                categoryBasedRequest.setCategory_id(categoryIds);
                categoryBasedRequest.setRestaurant_type(itemListType);
                categoryBasedRequest.setPrefer_time(preferTime);
                disposable.add(apiInterface
                        .getCategoryBasedRestaurant(categoryBasedRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<BaseResponse<AllRestaurantResponse>>() {
                            @Override
                            public void onSuccess(BaseResponse<AllRestaurantResponse> baseResponse) {
                                if (baseResponse.getCode() == 200) {
                                    if (pageNo == 1) {
                                        mPresenter.categoryBasedResponse(baseResponse.getData());
                                    } else {
                                        mPresenter.onLoadMore(baseResponse.getData());
                                    }
                                } else {
                                    if (pageNo == 1) {
                                        mPresenter.apiError(baseResponse.getMessage());
                                    } else {
                                        mPresenter.showLoadMoreError(baseResponse.getMessage());
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
            } catch (Exception e) {
                Log.e(EXCEPTION, e.toString());
            }
        }
    }

    private ArrayList<Integer> getPreferTime(JSONObject jsonObject) {
        ArrayList<Integer> itemListArray = new ArrayList<>();
        try {
            boolean breakFast = jsonObject.getBoolean(DataUtils.KEY_BREAK_FAST);
            boolean lunch = jsonObject.getBoolean(DataUtils.KEY_LUNCH);
            boolean dinner = jsonObject.getBoolean(DataUtils.KEY_DINNER);
            if (breakFast) {
                itemListArray.add(1);
            }
            if (lunch) {
                itemListArray.add(2);
            }
            if (dinner) {
                itemListArray.add(3);
            }
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
        return itemListArray;
    }

    private ArrayList<Integer> getItemType(String itemType) {
        ArrayList<Integer> itemListArray = new ArrayList<>();
        switch (itemType) {
            case "1":
                itemListArray.add(1);
                break;
            case "2":
                itemListArray.add(2);
                break;
            case "12":
                itemListArray.add(1);
                itemListArray.add(2);
                break;
        }
        return itemListArray;
    }


  /*  private ArrayList<Integer> getFilterCategory(ArrayList<CategoryList> categoryArrayList) {
        ArrayList<Integer> categoryIdList = new ArrayList<>();
        for (int index = 0; index < categoryArrayList.size(); index++) {
            if (categoryArrayList.get(index).isSelected() && categoryArrayList.get(index).getCategoryType() == CategoryType.CUISINES) {
                categoryIdList.add(categoryArrayList.get(index).getCategoryId());
            }
        }
        return categoryIdList;
    }
*/

    @Override
    public void close() {
        disposable.dispose();
    }
}
