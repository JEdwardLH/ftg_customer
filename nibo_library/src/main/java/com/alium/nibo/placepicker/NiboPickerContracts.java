package com.alium.nibo.placepicker;

import com.alium.nibo.models.NiboSelectedPlace;
import com.alium.nibo.mvp.contract.NiboPresentable;
import com.alium.nibo.mvp.contract.NiboViewable;
import com.google.android.libraries.places.api.model.Place;

/**
 * Created by aliumujib on 05/05/2018.
 */

public interface NiboPickerContracts {

    interface Presenter extends NiboPresentable<View> {


        void sendResults();

        void getGeocode(double lati, double longi,String language);

        void onGetPlaceDetailsError(Throwable exception);

        void onGetPlaceDetailsSuccess(Place place);

        void onGeocodeError(Throwable exception);

        void onGeocodeSuccess(String address);

        void getPlaceDetailsById(String placeID);

        void handleBackPress();

        void saveLocation(Double latitude,Double longitude,String address);
    }


    interface View extends NiboViewable<Presenter> {


        void setResults(NiboSelectedPlace mCurrentSelection);

        void setGeocodeAddress(String address);

        void showResultView();

        void displayErrorGeoCodingMessage();

        void setPlaceData(Place place);

        void displayPlaceDetailsError();

        boolean isSearching();

        void closeSearch();
    }


}
