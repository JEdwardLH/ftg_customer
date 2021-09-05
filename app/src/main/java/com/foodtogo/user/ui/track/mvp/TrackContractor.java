package com.foodtogo.user.ui.track.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.directions.DirectionResponse;
import com.foodtogo.user.model.directions.DistanceResponse;
import com.foodtogo.user.model.track.OrderStatusDetail;
import com.foodtogo.user.model.track.TrackDetailResponse;

import java.util.List;

import io.reactivex.disposables.Disposable;

public interface TrackContractor {

    interface View extends BaseView {

        void showTrackDetailResponse(TrackDetailResponse trackDetailResponse);

        void showRepeatTrackDetailResponse(TrackDetailResponse trackDetailResponse);

        void showSendOtpResponse(TrackDetailResponse trackDetailResponse);

        void showDirection(DirectionResponse directionResponse);

        void showReDirectionResponse(DirectionResponse directionResponse);

        void showDistance(DistanceResponse distanceResponse);

        void directionAPIError(String error);

        void showType(String type);
    }

    interface Presenter {

        void repeatCallTrackDetails(String storeId, String orderId, int returnType);

        void requestTrackDetails(String storeId, String orderId);

        void onTrackDetailResponse(TrackDetailResponse trackDetailResponse);

        void onRepeatTrackDetailResponse(TrackDetailResponse trackDetailResponse);

        void onSendOtpResponse(TrackDetailResponse trackDetailResponse);

        void onReDirectionResponse(DirectionResponse directionResponse);

        void onDirectionResponse(DirectionResponse directionResponse);

        void onDistanceResponse(DistanceResponse distanceResponse);

        void requestPolylineData(String originLat, String originLng, String restaurantLat, String restaurantLng, String customerLat, String customerLng);

        void requestRePolylineData(String originLat, String originLng, String restaurantLat, String restaurantLng, String customerLat, String customerLng);

        void apiError(String error);

        void directionAPIError(String error);

        void apiError(int error);

        void close();

        void getCurrentType(List<OrderStatusDetail> orderStatusDetails);


    }

    interface Model {

        Disposable repeatCallTrackDetails(String storeId, String orderId, int returnType);

        Disposable requestTrackDetails(String storeId, String orderId);

        Disposable requestPolylineData(String originLat, String originLng, String restaurantLat, String restaurantLng, String customerLat, String customerLng);

        Disposable requestRePolylineData(String originLat, String originLng, String restaurantLat, String restaurantLng, String customerLat, String customerLng);

        void close();
    }
}
