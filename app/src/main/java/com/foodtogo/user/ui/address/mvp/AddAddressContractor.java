package com.foodtogo.user.ui.address.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.accountdetails.User;
import com.foodtogo.user.model.geocodeaddress.GeoCodeAddress;
import com.foodtogo.user.model.shippingaddress.MultiLocation;

import java.util.List;

public interface AddAddressContractor {

    interface View extends BaseView {

        void showManualLocation();

        void showUseCurrentLocation();

        void showUserList(User user);

        void showGeoCodeAddress(GeoCodeAddress geoCodeAddress);

        void showGeoCodeAddressError(String error);

        void bindMultiLocationList(List<MultiLocation> multiLocationList);

    }

    interface Presenter {

        void onManualLocation();

        void onCurrentLocation();

        void requestAccountDetails(int show);

        void requestAddress(String lat, String longitude);

        void onGeoCodeAddress(GeoCodeAddress geoCodeAddress);

        void onGeoCodeAddressError(String error);

        void onSuccess(User user);

        void apiError(String error);

        void apiError(int error);

        void requestMultiLocation(String lang);

        void onSuccessMultiLocation(List<MultiLocation> multiLocationList);

    }

    interface Model {

        void requestAddress(String lat, String longitude);

        void requestSaveShippingAddress(User user);

        void requestMultiLocation(String lat);

        void requestAccountDetails();

        void close();
    }

}
