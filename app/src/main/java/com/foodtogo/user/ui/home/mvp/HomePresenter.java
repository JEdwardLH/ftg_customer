package com.foodtogo.user.ui.home.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.home.HomeSearchRequest;
import com.foodtogo.user.model.home.HomeSearchResponse;
import com.foodtogo.user.model.home.Offers;
import com.foodtogo.user.model.home.RestaurantDataModel;
import com.foodtogo.user.util.NetworkUtils;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class HomePresenter implements HomeContractor.Presenter {

    private HomeContractor.View mView;
    private HomeModel model;
    AppRepository appRepository;

    public HomePresenter(HomeContractor.View view,AppRepository appRepository) {
        mView = view;
        model = new HomeModel(this,appRepository);
        this.appRepository = appRepository;
    }

    @Override
    public void requestRestaurant() {
        mView.showLoadingView();
        model.requestRestaurant();
    }

    @Override
    public void apiError(String error) {
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void apiError(int error) {
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void showHomeResponse(List<RestaurantDataModel> topRestaurantDataModelList, List<RestaurantDataModel> restaurantDataModelList, Offers offers) {
        mView.hideLoadingView();
        mView.showHomeResponse(restaurantDataModelList,offers);
        mView.showHomeTopResponse(topRestaurantDataModelList);
    }

    HomeSearchRequest homeSearchRequest;
    Disposable disposable;

    @Override
    public void homeSearch(String query) {
        homeSearchRequest = new HomeSearchRequest(appRepository.getLanguageCode(),query);
        disposeHomeSearch();
        model.search(homeSearchRequest);
    }

    @Override
    public void disposeHomeSearch() {
        if(disposable!=null && !disposable.isDisposed())
            disposable.dispose();
    }

    @Override
    public void onReceiveHomeSearch(HomeSearchResponse homeSearchResponse) {
        mView.onSuccessHomeSearchResponse(homeSearchResponse);
    }

    @Override
    public void errorHomeSearchResponse(int error) {
        mView.showHomeSearchError(error);
    }

    @Override
    public void errorHomeSearchResponse(String error) {
        mView.showHomeSearchError(error);
    }

    @Override
    public void onRepeatOrder(String message) {
        mView.hideLoadingView();
        mView.showRepeatOrder(message);
    }

    @Override
    public void requestRepeatOrder(String orderId) {
        mView.showLoadingView();
        model.requestRepeatOrder(orderId);
    }

    @Override
    public void close() {
        model.close();
    }
}
