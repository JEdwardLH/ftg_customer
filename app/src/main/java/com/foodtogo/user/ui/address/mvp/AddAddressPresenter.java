package com.foodtogo.user.ui.address.mvp;

import android.content.Context;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.accountdetails.User;
import com.foodtogo.user.model.geocodeaddress.GeoCodeAddress;
import com.foodtogo.user.model.shippingaddress.MultiLocation;

import java.util.List;


public class AddAddressPresenter implements AddAddressContractor.Presenter {

    private AddAddressContractor.View mView;
    private AddAddressModel model;


    public AddAddressPresenter(AddAddressContractor.View view, AppRepository appRepository1, Context context) {
        mView = view;
        model = new AddAddressModel(this, appRepository1,context);

    }


    @Override
    public void onManualLocation() {
        mView.showManualLocation();
    }

    @Override
    public void onCurrentLocation() {
        mView.showUseCurrentLocation();
    }

    @Override
    public void requestAccountDetails(int show) {
        if (show == 1)
            mView.showLoadingView();
            model.requestAccountDetails();
    }

    @Override
    public void requestAddress(String lat, String longitude) {
       // mView.showLoadingView();
       // model.requestAddress(lat, longitude);
    }


    @Override
    public void onGeoCodeAddress(GeoCodeAddress geoCodeAddress) {
        mView.showGeoCodeAddress(geoCodeAddress);
    }

    @Override
    public void onGeoCodeAddressError(String error) {
        mView.showGeoCodeAddressError(error);
    }

    @Override
    public void onSuccess(User user) {
        mView.hideLoadingView();
        mView.showUserList(user);
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
    public void requestMultiLocation(String lang) {
        mView.showLoadingView();
        model.requestMultiLocation(lang);
    }

    @Override
    public void onSuccessMultiLocation(List<MultiLocation> multiLocationList) {
        mView.hideLoadingView();
        mView.bindMultiLocationList(multiLocationList);
    }


}

