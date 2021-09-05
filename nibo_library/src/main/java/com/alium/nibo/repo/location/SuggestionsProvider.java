package com.alium.nibo.repo.location;

import android.content.Context;


import android.util.Log;

import com.alium.nibo.R;
import com.alium.nibo.autocompletesearchbar.NiboSearchSuggestionItem;
import com.alium.nibo.repo.contracts.ISuggestionRepository;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdulmujibaliu on 9/8/17.
 */

public class SuggestionsProvider implements ISuggestionRepository {


    private String TAG = getClass().getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private Context mContext;
    PlacesClient placesClient;


    public SuggestionsProvider(GoogleApiClient mGoogleApiClient, Context mContext) {
        this.mGoogleApiClient = mGoogleApiClient;
        this.mContext = mContext;
        com.google.android.libraries.places.api.Places.initialize(mContext,mContext.getString(R.string.google_places_key));
        // Create a new Places client instance.
        placesClient = com.google.android.libraries.places.api.Places.createClient(mContext);
    }

    public Observable<Collection<NiboSearchSuggestionItem>> getSuggestions(final String query) {
        final List<NiboSearchSuggestionItem> placeSuggestionItems = new ArrayList<>();
        return new Observable<Collection<NiboSearchSuggestionItem>>() {
            @Override
            protected void subscribeActual(final Observer<? super Collection<NiboSearchSuggestionItem>> observer) {
                AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
                FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                        .setSessionToken(token)
                        .setQuery(query)
                        .build();

                placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
                    for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                        Log.i(TAG, prediction.getPlaceId());
                        Log.i(TAG, prediction.getPrimaryText(null).toString());
                        NiboSearchSuggestionItem placeSuggestion = new NiboSearchSuggestionItem(
                                prediction.getFullText(null).toString(),
                                prediction.getPlaceId(), NiboSearchSuggestionItem.TYPE_SEARCH_ITEM_SUGGESTION,
                                mContext.getResources().getDrawable(R.drawable.ic_map_marker_def)
                        );
                        placeSuggestionItems.add(placeSuggestion);
                    }

                    observer.onNext(placeSuggestionItems);
                }).addOnFailureListener((exception) -> {
                    Log.d(TAG, exception.toString());
                    observer.onError(exception);

                });
            }
        };
    }


    public Observable<Place> getPlaceByID(final String placeId) {
        return new Observable<Place>() {
            @Override
            protected void subscribeActual(final Observer<? super Place> observer) {

                List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);

                // Construct a request object, passing the place ID and fields array.
                FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();
                placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                    com.google.android.libraries.places.api.model.Place place = response.getPlace();
                    LatLng queriedLocation = place.getLatLng();
                    Log.v("Latitude is", "" + queriedLocation.latitude);
                    Log.v("Longitude is", "" + queriedLocation.longitude);
                    observer.onNext(place);
                }).addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        ApiException apiException = (ApiException) exception;
                        int statusCode = apiException.getStatusCode();
                    }
                });
            }
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void stop() {
        mContext = null;
        mGoogleApiClient = null;
    }


}