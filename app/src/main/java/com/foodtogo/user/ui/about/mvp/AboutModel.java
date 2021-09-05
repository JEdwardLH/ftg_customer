package com.foodtogo.user.ui.about.mvp;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.foodtogo.user.data.source.AppRepository;

import io.reactivex.disposables.CompositeDisposable;

public class AboutModel implements AboutContractor.Model {


    private AboutPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;

    AboutModel(AboutPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
    }


//    @Override
//    public void requestRestaurant(ApiInterface apiInterface) {
//        HomeRequest homeRequest = new HomeRequest();
//        homeRequest.setUser_latitude("11.016010");
//        homeRequest.setUser_longitude("76.970310");
//        homeRequest.setLang(appRepository.getLanguageCode());
//        disposable.add(apiInterface
//                .requestRestaurant(homeRequest)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<BaseResponse<HomeResponse>>() {
//                    @Override
//                    public void onSuccess(BaseResponse<HomeResponse> baseResponse) {
//                        if (baseResponse.getCode() == 200) {
//                            HomeResponse homeResponse = baseResponse.getData();
//                            List<RestaurantDataModel> topAllModelList = new ArrayList<>();
//                            List<AllRestaurantActivity> allRestaurant = homeResponse.getAllRestaurant();
//                            if (allRestaurant.size() > 0) {
//                                RestaurantDataModel allSampleData = new RestaurantDataModel();
//                                allSampleData.setRestaurantName(RESTAURANTS);
//                                allSampleData.setModel(TYPE_RESTAURANTS);
//                                allSampleData.setAllRestaurantList(allRestaurant);
//                                topAllModelList.add(allSampleData);
//                            }
//
//
//                            List<AllRestaurantDetail> allRestaurantDetails = homeResponse.getAllRestaurantDetails();
//                            if (allRestaurantDetails.size() > 0) {
//                                if (allRestaurantDetails.get(0).getRestaurantDetails().size() > 0) {
//                                    RestaurantDataModel allSampleData = new RestaurantDataModel();
//                                    allSampleData.setModel(TYPE_POPULAR);
//                                    allSampleData.setRestaurantName(allRestaurantDetails.get(0).getCategoryName());
//                                    allSampleData.setAllPopularFoodDetails(allRestaurantDetails.get(0).getRestaurantDetails());
//                                    topAllModelList.add(allSampleData);
//                                }
//                            }
//                            List<RestaurantDataModel> allModelList = new ArrayList<>();
//                            List<AllRestaurantActivity> allFeatureRestaurant = homeResponse.getFeaturedRestaurant();
//                            if (allRestaurant.size() > 0) {
//                                RestaurantDataModel allSampleData = new RestaurantDataModel();
//                                allSampleData.setRestaurantName(FEATURED_RESTAURANTS);
//                                allSampleData.setModel(TYPE_FEATURED);
//                                allSampleData.setAllFeatureRestaurantList(allFeatureRestaurant);
//                                allModelList.add(allSampleData);
//                            }
//
//
//                            List<AllRestaurantDetail> allFoodDetails = homeResponse.getAllRestaurantDetails();
//                            for (int index = 0; index < allFoodDetails.size(); index++) {
//                                RestaurantDataModel allSampleData = new RestaurantDataModel();
//                                allSampleData.setModel(TYPE_OTHER);
//                                allSampleData.setRestaurantName(allFoodDetails.get(index).getCategoryName());
//                                allSampleData.setAllFoodDetails(allFoodDetails.get(index).getRestaurantDetails());
//                                allModelList.add(allSampleData);
//                            }
//                            mPresenter.showHomeResponse(topAllModelList, allModelList);
//                        } else {
//                            mPresenter.apiError(baseResponse.getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (e instanceof HttpException) {
//                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
//                            mPresenter.apiError(NetworkUtils.getErrorMessage(responseBody));
//                        } else if (e instanceof SocketTimeoutException) {
//                            mPresenter.apiError(R.string.time_out_error);
//                        } else if (e instanceof IOException) {
//                            mPresenter.apiError(R.string.network_error);
//                        } else {
//                            mPresenter.apiError(e.getMessage());
//                        }
//
//                    }
//                }));
//    }


    @Override
    public void requestAppVersion(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            mPresenter.responseVersion(pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
