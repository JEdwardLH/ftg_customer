package com.foodtogo.user.ui.track.mvp;

import android.content.Context;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.directions.DirectionResponse;
import com.foodtogo.user.model.directions.DistanceResponse;
import com.foodtogo.user.model.track.OrderStatusDetail;
import com.foodtogo.user.model.track.TrackDetailResponse;

import java.util.List;

import io.reactivex.disposables.Disposable;

import static com.foodtogo.user.base.AppConstants.YES;


public class TrackPresenter implements TrackContractor.Presenter {

    private TrackContractor.View mView;
    private TrackModel model;
    private Disposable disposable;
    private final static String NEW_ORDER = "New Order";
    private final static String ACCEPTED = "Accepted";
    private final static String PREPARE = "Preparing for deliver";
    private final static String DISPATCHED = "Dispatched";
    private final static String STARTED = "Started";
    private final static String ARRIVED = "Arrived";
    private final static String DELIVERED = "Delivered";
    private static final String TYPE_ACCEPT = "ACCEPT";
    private static final String TYPE_ASSIGNED = "ASSIGNED";
    private static final String TYPE_STARTED = "STARTED";
    private static final String TYPE_ARRIVED = "ARRIVED";
    private static final String TYPE_DELIVERED = "DELIVERED";
    private static final String TYPE_SEND_OTP = "SEND_OTP";
    private static final String TYPE_FAILED = "FAILED";


    public TrackPresenter(TrackContractor.View view, AppRepository appRepository, Context context) {
        mView = view;
        model = new TrackModel(this, appRepository,context);
    }


    @Override
    public void repeatCallTrackDetails(String storeId, String orderId, int returnType) {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
        disposable = model.repeatCallTrackDetails(storeId, orderId, returnType);

    }

    @Override
    public void requestTrackDetails(String storeId, String orderId) {
        mView.showLoadingView();
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
        disposable = model.requestTrackDetails(storeId, orderId);
    }

    @Override
    public void onTrackDetailResponse(TrackDetailResponse trackDetailResponse) {
        mView.hideLoadingView();
        mView.showTrackDetailResponse(trackDetailResponse);
    }

    @Override
    public void onRepeatTrackDetailResponse(TrackDetailResponse trackDetailResponse) {
        mView.hideLoadingView();
        mView.showRepeatTrackDetailResponse(trackDetailResponse);
    }

    @Override
    public void onSendOtpResponse(TrackDetailResponse trackDetailResponse) {
        mView.hideLoadingView();
        mView.showSendOtpResponse(trackDetailResponse);
    }

    @Override
    public void onReDirectionResponse(DirectionResponse directionResponse) {
        mView.showReDirectionResponse(directionResponse);
    }

    @Override
    public void onDirectionResponse(DirectionResponse directionResponse) {
        mView.showDirection(directionResponse);
    }

    @Override
    public void onDistanceResponse(DistanceResponse distanceResponse) {
        mView.showDistance(distanceResponse);
    }

    @Override
    public void requestPolylineData(String originLat, String originLng, String restaurantLat, String restaurantLng, String customerLat, String customerLng) {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
        disposable = model.requestPolylineData(originLat, originLng, restaurantLat, restaurantLng, customerLat, customerLng);
    }

    @Override
    public void requestRePolylineData(String originLat, String originLng, String restaurantLat, String restaurantLng, String customerLat, String customerLng) {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
        disposable = model.requestRePolylineData(originLat, originLng, restaurantLat, restaurantLng, customerLat, customerLng);
    }


    @Override
    public void apiError(String error) {
        mView.hideLoadingView();
        mView.showError(error);
    }

    @Override
    public void directionAPIError(String error) {
        mView.hideLoadingView();
        mView.directionAPIError(error);
    }

    @Override
    public void apiError(int error) {
        mView.hideLoadingView();
        mView.showError(error);
    }


    @Override
    public void close() {
        model.close();
    }

    @Override
    public void getCurrentType(List<OrderStatusDetail> orderStatusDetails) {
        String type = "";
        for (int index = 0; index < orderStatusDetails.size(); index++) {
            if (orderStatusDetails.get(index).getStageCompleted().equals(YES)) {
                switch (orderStatusDetails.get(index).getOrdTitle()) {
                    case NEW_ORDER:
                        type = "";
                        break;
                    case ACCEPTED:
                        type = TYPE_ACCEPT;
                        break;
                    case PREPARE:
                        type = TYPE_ASSIGNED;
                        break;
                    case DISPATCHED:
                        type = TYPE_STARTED;
                        break;
                    case STARTED:
                        type = TYPE_STARTED;
                        break;
                    case ARRIVED:
                        type = TYPE_ARRIVED;
                        break;
                    case DELIVERED:
                        type = TYPE_DELIVERED;
                        break;

                }
            }
        }
        mView.showType(type);
    }


}
