package com.foodtogo.user.ui.home.mvp;


import com.foodtogo.user.BaseApplication;
import com.foodtogo.user.R;
import com.foodtogo.user.base.AppConstants;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.ErrorResponse;
import com.foodtogo.user.model.home.AllRestaurant;
import com.foodtogo.user.model.home.AllRestaurantDetail;
import com.foodtogo.user.model.home.HomeRequest;
import com.foodtogo.user.model.home.HomeResponse;
import com.foodtogo.user.model.home.HomeSearchRequest;
import com.foodtogo.user.model.home.HomeSearchResponse;
import com.foodtogo.user.model.home.RecentOrders;
import com.foodtogo.user.model.home.RestaurantDataModel;
import com.foodtogo.user.model.home.RestaurantDetail;
import com.foodtogo.user.model.orderhistory.RepeatOrderRequest;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class HomeModel implements HomeContractor.Model {

    private HomePresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
   public static final String RESTAURANTS = "Restaurant";
   public static final String NEW_RESTAURANTS = "New Restaurant";
   public static final String TOP_RESTAURANTS = "Top Restaurant";
   public static final String CATEGORIES = "Categories";
   public static final String PROMOTIONS = "Promotions";
   public static final String NEARBY = "Nearby";
   public static final String ALL_RESTAURANT = "All Restaurants";
   public static final String TYPE_RESTAURANTS = "TYPE_RESTAURANTS";
    private AppRepository appRepository;
    private static final int LIMIT_DATA = 3;
    private ApiInterface apiInterface;

    private List<RestaurantDataModel> allModelList = new ArrayList<>();

    HomeModel(HomePresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public Disposable search(HomeSearchRequest homeSearchRequest) {
        DisposableSingleObserver<BaseResponse<HomeSearchResponse>> d = new DisposableSingleObserver<BaseResponse<HomeSearchResponse>>() {
            @Override
            public void onSuccess(BaseResponse<HomeSearchResponse> baseResponse) {
                if (baseResponse.getCode() == 200) {
                    mPresenter.onReceiveHomeSearch(baseResponse.getData());
                } else {
                    mPresenter.errorHomeSearchResponse(baseResponse.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    mPresenter.errorHomeSearchResponse(NetworkUtils.getErrorMessage(responseBody));
                } else if (e instanceof SocketTimeoutException) {
                    mPresenter.errorHomeSearchResponse(R.string.time_out_error);
                } else if (e instanceof IOException) {
                    mPresenter.errorHomeSearchResponse(R.string.network_error);
                } else {
                    mPresenter.errorHomeSearchResponse(e.getMessage());
                }

            }
        };

        Single single = apiInterface
                .homeSearch(homeSearchRequest)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        SingleObserver singleObserver = single.subscribeWith(d);

        disposable.add(d);

        return d;
    }

    @Override
    public void requestRestaurant() {
        HomeRequest homeRequest = new HomeRequest();
        homeRequest.setUser_latitude(appRepository.getLatitude());
        homeRequest.setUser_longitude(appRepository.getLongitude());
        homeRequest.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .requestRestaurant(homeRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<HomeResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<HomeResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            HomeResponse homeResponse = baseResponse.getData();
                            if (homeResponse.getAllRestaurant().size() == 0
                                    && homeResponse.getFeaturedRestaurant().size() == 0) {
                                mPresenter.apiError(R.string.no_restaurant_found);
                            } else {
                                List<RestaurantDataModel> topAllModelList = new ArrayList<>();
                                List<RecentOrders> recentOrders = homeResponse.getRecentOrdersList();
                                    RestaurantDataModel allSampleData = new RestaurantDataModel();
                                    allSampleData.setRestaurantName(RESTAURANTS);
                                    allSampleData.setModel(TYPE_RESTAURANTS);
                                    allSampleData.setAllRecentOrders(recentOrders);
                                    topAllModelList.add(allSampleData);


                                List<RestaurantDetail> allRestaurantDetailList=homeResponse.getAllRestaurant();
                                    RestaurantDataModel allRestaurant=new RestaurantDataModel();
                                    allRestaurant.setRestaurantName(ALL_RESTAURANT);
                                    allRestaurant.setModel(ALL_RESTAURANT);
                                    allRestaurant.setAllRestaurantDetailList(allRestaurantDetailList);
                                    allRestaurant.setDeliveryStatus(homeResponse.getDeliveryFeeStatus());
                                    topAllModelList.add(allRestaurant);




                                List<RestaurantDetail> newRestaurantList=homeResponse.getNewRestaurantDetail();
                                if(newRestaurantList.size()>0){
                                    restaurantListing(NEW_RESTAURANTS, 0,NEW_RESTAURANTS, newRestaurantList,homeResponse.getDeliveryFeeStatus());
                                }
                                List<RestaurantDetail> topRestaurantDetail=homeResponse.getTopRestaurantDetail();
                                if(topRestaurantDetail.size()>0){
                                    restaurantListing(TOP_RESTAURANTS, 0, TOP_RESTAURANTS, topRestaurantDetail,homeResponse.getDeliveryFeeStatus());
                                }

                                List<AllRestaurant> allCategoriesList = homeResponse.getCategoryList();
                                if (allCategoriesList.size() > 0) {
                                    RestaurantDataModel allSampleData1 = new RestaurantDataModel();
                                    allSampleData1.setRestaurantName(CATEGORIES);
                                    allSampleData1.setModel(CATEGORIES);
                                    allSampleData1.setAllRestaurantList(allCategoriesList);
                                    allModelList.add(allSampleData1);
                                }
                                List<RestaurantDetail> promotionRestaurant=homeResponse.getFeaturedRestaurant();
                                if(promotionRestaurant.size()>0){
                                    restaurantListing(PROMOTIONS,0,PROMOTIONS,promotionRestaurant,homeResponse.getDeliveryFeeStatus());
                                }


                                List<RestaurantDetail> nearByRestaurant=homeResponse.getNearByRestaurant();
                                if(nearByRestaurant.size()>0){
                                    restaurantListing(NEARBY,0,NEARBY,nearByRestaurant,homeResponse.getDeliveryFeeStatus());
                                }

                                mPresenter.showHomeResponse(topAllModelList, allModelList,homeResponse.getOffers());
                            }
                        } else {
                            mPresenter.apiError(baseResponse.getMessage().equals(AppConstants.NO_RECORDS_FOUND)?
                                    BaseApplication.getContext().getString(R.string.no_restaurant_found_home)
                                    :baseResponse.getMessage());
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
    public void requestRepeatOrder(String orderId) {
        RepeatOrderRequest repeatOrderRequest = new RepeatOrderRequest();
        repeatOrderRequest.setLang(appRepository.getLanguageCode());
        repeatOrderRequest.setOrder_id(orderId);
        disposable.add(apiInterface
                .repeatOrder(repeatOrderRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<ErrorResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<ErrorResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            ErrorResponse response = baseResponse.getData();
                            if (response != null && response.getErrors() != null) {
                                if (response.getErrors().size() > 0) {
                                    mPresenter.apiError(response.getErrors().get(0));
                                } else {
                                    mPresenter.onRepeatOrder(baseResponse.getMessage());
                                }
                            } else {
                                mPresenter.onRepeatOrder(baseResponse.getMessage());
                            }
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


    private void restaurantListing(String model, int categoryId, String title, List<RestaurantDetail> restaurantLists,int deliveryStatus){
        RestaurantDataModel allSampleData = new RestaurantDataModel();
        allSampleData.setModel(model);
        allSampleData.setCategoryId(categoryId);
        allSampleData.setRestaurantName(title);
        allSampleData.setDeliveryStatus(deliveryStatus);
        allSampleData.setAllFoodDetails(restaurantLists);
        allModelList.add(allSampleData);
    }


    @Override
    public void close() {
        disposable.dispose();
    }
}
