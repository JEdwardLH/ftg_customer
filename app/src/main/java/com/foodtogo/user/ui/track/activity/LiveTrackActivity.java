package com.foodtogo.user.ui.track.activity;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Rational;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.model.directions.DirectionResponse;
import com.foodtogo.user.model.directions.DistanceResponse;
import com.foodtogo.user.model.directions.Duration;
import com.foodtogo.user.model.directions.Leg;
import com.foodtogo.user.model.directions.Route;
import com.foodtogo.user.model.directions.Step;
import com.foodtogo.user.model.track.DeliveryPersonDetails;
import com.foodtogo.user.model.track.OrderStatusDetail;
import com.foodtogo.user.model.track.RestaurantDetails;
import com.foodtogo.user.model.track.TrackDetailResponse;
import com.foodtogo.user.mqtt.MQTTCallBack;
import com.foodtogo.user.mqtt.MQTTServerClient;
import com.foodtogo.user.ui.home.activity.Home;
import com.foodtogo.user.ui.track.adapter.DriverInfoWindowAdapter;
import com.foodtogo.user.ui.track.adapter.TrackAdapter;
import com.foodtogo.user.ui.track.adapter.TrackingDividerItem;
import com.foodtogo.user.ui.track.fragment.DriverInfoFragment;
import com.foodtogo.user.ui.track.interfaces.DriverInfo;
import com.foodtogo.user.ui.track.mvp.TrackContractor;
import com.foodtogo.user.ui.track.mvp.TrackPresenter;
import com.foodtogo.user.util.DataUtils;
import com.foodtogo.user.util.PolyUtil;
import com.foodtogo.user.util.ViewUtils;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

import static com.google.android.gms.maps.model.JointType.ROUND;
import static com.foodtogo.user.ui.track.fragment.DriverInfoFragment.BTN_TEXT;
import static com.foodtogo.user.ui.track.fragment.DriverInfoFragment.CALL_NUMBER;
import static com.foodtogo.user.ui.track.fragment.DriverInfoFragment.DRIVER_NAME;
import static com.foodtogo.user.ui.track.fragment.DriverInfoFragment.ESM_TIME;
import static com.foodtogo.user.ui.track.fragment.DriverInfoFragment.IMAGE;


public class LiveTrackActivity extends BaseActivity implements TrackContractor.View, DriverInfo, OnMapReadyCallback, MQTTCallBack {


    public LiveTrackActivity() {
        // Required empty public constructor
    }

    private static final String TAG = "TrackFragment";
    private static final String TYPE_ACCEPT = "ACCEPT";
    private static final String TYPE_ASSIGNED = "ASSIGNED";
    private static final String TYPE_STARTED = "STARTED";
    private static final String TYPE_ARRIVED = "ARRIVED";
    private static final String TYPE_DELIVERED = "DELIVERED";
    private static final String TYPE_SEND_OTP = "SEND_OTP";
    private static final String TYPE_FAILED = "FAILED";

    TrackPresenter trackPresenter;


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;


    @BindView(R.id.card_view_success)
    CardView cardViewSuccess;


    @BindView(R.id.card_view)
    RelativeLayout cardView;

    @BindView(R.id.tv_otp)
    TextView tvOtp;

    @BindView(R.id.order_msg)
    TextView orderMsg;

    @BindView(R.id.iv_enjoy)
    ImageView ivEnjoy;

    @BindView(R.id.btn_home)
    Button btnHome;

    @BindView(R.id.tv_order_detail_message)
    TextView tvOrderDetailMessage;

    @BindView(R.id.slidingLayout)
    SlidingUpPanelLayout mLayout;

    @BindView(R.id.map_layout)
    FrameLayout mapLayout;

    @BindView(R.id.tool_bar)
    Toolbar toolbar;

    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;

    @BindView(R.id.iv_open_drawer)
    ImageView ivOpenDrawer;

    @BindView(R.id.tv_tool_desc)
    public TextView tvTitleDesc;

    @BindView(R.id.slidingContainer)
    RelativeLayout slidingContainer;

    Marker driverMarker;
    Marker customerMarker;

    DeliveryPersonDetails deliveryPersonDetails = null;
    TrackDetailResponse mTrackDetailResponse = null;
    RestaurantDetails restaurantDetails;
    // MQTT objects
    MqttAndroidClient mqttAndroidClient = null;
    MQTTServerClient mqttServerClient = null;
    ArrayList<LatLng> pointsLatLngArrayList;
    String totalEstimateTime = "";

    String orderId;
    int restaurantId;

    private GoogleMap mMap;
    String topic = "";
    private Polyline blackPolyLine;
    private float v;
    String lastStatus = "";
    int index = 0;
    private LatLng startPosition;
    private LatLng endPosition;
    String driverId = "";

    String customerLat;
    String customerLng;

    LatLng zoomingLatlng;
    float zoomLevel = 14f;
    public Timer timer;
    protected int API_CALL_INTERVAL = 10000;
    private String driverLat = "0.0", driverLng = "0.0";
    private String currentType = "";
    private List<OrderStatusDetail> orderStatusDetails = new ArrayList<>();
    private TrackAdapter trackAdapter;
    boolean isFirst = true;
    long prevEventTime;

    boolean isDelivered = false;
    boolean isFailed = false;
    boolean isArrived = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar();
        trackPresenter = new TrackPresenter(this, appRepository, LiveTrackActivity.this);
        prevEventTime = System.currentTimeMillis();
        tvTitle.setText(getResources().getString(R.string.nav_track));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderId = bundle.getString(ORDER_ID);
            System.out.println("orderId:" + orderId +appRepository.getUserId());
            try {

                tvTitleDesc.setText(getString(R.string.order_num) + SPACE + orderId);
                tvTitleDesc.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                Log.e(EXCEPTION, e.toString());
            }
            restaurantId = bundle.getInt(RESTAURANT_ID);
            trackPresenter.requestTrackDetails(String.valueOf(restaurantId), orderId);
        }
        mqttServerClient = new MQTTServerClient();
        mqttServerClient.mqttCallBack(this);

        initMap();
        initSlideUp();
        initRecyclerView();


    }


    private void initRecyclerView() {
        trackAdapter = new TrackAdapter(this, orderStatusDetails);
        trackAdapter.setDeliveryPersonDetails(deliveryPersonDetails);
        recyclerView.setHasFixedSize(true);
        trackAdapter.setDriverInfo(this);
        recyclerView.addItemDecoration(new TrackingDividerItem(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(trackAdapter);
    }

    private void initSlideUp() {
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
                if (SlidingUpPanelLayout.PanelState.COLLAPSED == newState) {
                    expandMap();
                } else if (SlidingUpPanelLayout.PanelState.EXPANDED == newState) {
                    collapseMap();
                }
            }
        });
        mLayout.setFadeOnClickListener(view -> mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED));
        mLayout.setCoveredFadeColor(Color.TRANSPARENT);
    }

    private void collapseMap() {
        if (mMap != null && zoomingLatlng != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(zoomingLatlng, 12f), 1000, null);
        }
    }

    private void expandMap() {
        if (mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel), 1000, null);
        }
    }

    @Override
    public void showLoadingView() {
        showProgressDialog();
    }

    @Override
    public void hideLoadingView() {
        hideProgressDialog();
    }

    @Override
    public void showError(String message) {
        stopTimer();
        showToast(message);
    }

    @Override
    public void showError(int message) {
        stopTimer();
        showToast(message);
    }


    @Override
    public void onPause() {
        super.onPause();
        try {
            if (mqttAndroidClient != null) {
                if (topic.equals("")) {
                    mqttServerClient.unsubscribe(mqttAndroidClient, topic);
                }
                mqttAndroidClient.unregisterResources();
                mqttAndroidClient.close();
            }
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mqttAndroidClient != null) {
            mqttAndroidClient.unregisterResources();
            mqttAndroidClient.close();
        }
        if (mqttServerClient != null) {
            mqttServerClient.closeConnection();

        }
        stopTimer();
    }

    @OnClick(R.id.btn_home)
    public void btn_home() {
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("Home", 10);
            changeActivityClearBackStack(Home.class, bundle);
            cardViewSuccess.setVisibility(View.GONE);
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }


    @Override
    public void showTrackDetailResponse(TrackDetailResponse trackDetailResponse) {
        customerLat = trackDetailResponse.getCustomerDetails().getCusLatitude();
        customerLng = trackDetailResponse.getCustomerDetails().getCusLongitude();

        if (trackDetailResponse.getOrderOtp() != null) {
            String otpCode = "OTP : " + trackDetailResponse.getOrderOtp();
            tvOtp.setText(otpCode);
            tvOtp.setVisibility(View.VISIBLE);
        } else {
            tvOtp.setVisibility(View.GONE);
        }
        mTrackDetailResponse = trackDetailResponse;
        totalEstimateTime = trackDetailResponse.getEstimatedArrivalTime();
        deliveryPersonDetails = trackDetailResponse.getDeliveryPersonDetails();
        trackAdapter.setDeliveryPersonDetails(deliveryPersonDetails);
        trackPresenter.getCurrentType(trackDetailResponse.getOrderStatusDetails());
        orderStatusDetails.clear();
        orderStatusDetails.addAll(trackDetailResponse.getOrderStatusDetails());
        trackAdapter.notifyDataSetChanged();
        restaurantDetails = trackDetailResponse.getRestaurantDetails();
        LatLng restaurantLocation = new LatLng(Double.valueOf(restaurantDetails.getRestaurantLatitude()), Double.valueOf(restaurantDetails.getRestaurantLongitude()));
        MarkerOptions markerOptions = new MarkerOptions()
                .position(restaurantLocation)
                .title(restaurantDetails.getRestaurantName())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pick));
        Marker marker = mMap.addMarker(markerOptions);
        markerHide(marker);
        MarkerOptions customer = new MarkerOptions()
                .title("Your Here")
                .position(new LatLng(Double.valueOf(customerLat), Double.valueOf(customerLng)))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_drop));
        customerMarker = mMap.addMarker(customer);
        markerHide(customerMarker);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, zoomLevel));


        if (deliveryPersonDetails.getDeliverAssigned().equals(YES)) {
            Log.d("getDeliverAssigned", deliveryPersonDetails.getDeliverAssigned());
            driverId = String.valueOf(deliveryPersonDetails.getDeliverId());
            driverLat = deliveryPersonDetails.getDeliverLatitude();
            driverLng = deliveryPersonDetails.getDeliverLongitude();
            Log.d("driverLat", driverLat);
            Log.d("driverLng", driverLng);

            if (!driverLat.equals("") || !driverLng.equals("")) {
                LatLng driverLocation = new LatLng(Double.valueOf(driverLat), Double.valueOf(driverLng));
                MarkerOptions deliveryBoy = new MarkerOptions()
                        .position(driverLocation)
                        .title("......")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_vehicle));
                this.zoomingLatlng = driverLocation;
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(driverLocation, zoomLevel));
                mMap.setInfoWindowAdapter(new DriverInfoWindowAdapter(this));
                mMap.setOnMarkerClickListener(marker1 -> {
                    driverMarker.showInfoWindow();
                    return true;
                });
                driverMarker = mMap.addMarker(deliveryBoy);
                driverMarker.setVisible(true);
                driverMarker.showInfoWindow();


                if (mqttAndroidClient != null) {
                    topic = "delivery/" + driverId;
                    mqttServerClient.subscribe(mqttAndroidClient, topic);
                }
                trackPresenter.requestPolylineData(driverLat, driverLng, restaurantDetails.getRestaurantLatitude(), restaurantDetails.getRestaurantLongitude(), customerLat, customerLng);
            }
        } else {
            if (mqttAndroidClient != null) {
                topic = "order/" + orderId + "/restaurant/" + restaurantId;
                mqttServerClient.subscribe(mqttAndroidClient, topic);
            }
        }

        for (int i = 0; i < orderStatusDetails.size(); i++) {
            if (orderStatusDetails.get(i).getStageCompleted().equals(YES)) {
                lastStatus = orderStatusDetails.get(i).getOrdTitle();
            }
        }
    }


    private void markerHide(Marker marker) {
        try {
            marker.hideInfoWindow();
        } catch (Exception e) {

        }
    }

    @Override
    public void showRepeatTrackDetailResponse(TrackDetailResponse trackDetailResponse) {

        customerLat = trackDetailResponse.getCustomerDetails().getCusLatitude();
        customerLng = trackDetailResponse.getCustomerDetails().getCusLongitude();
        if (trackDetailResponse.getOrderOtp() != null) {
            String otpCode = "OTP : " + trackDetailResponse.getOrderOtp();
            tvOtp.setText(otpCode);
            tvOtp.setVisibility(View.VISIBLE);
        } else {
            tvOtp.setVisibility(View.GONE);
        }

        totalEstimateTime = trackDetailResponse.getEstimatedArrivalTime();
        deliveryPersonDetails = trackDetailResponse.getDeliveryPersonDetails();
        trackAdapter.setDeliveryPersonDetails(deliveryPersonDetails);
        trackPresenter.getCurrentType(trackDetailResponse.getOrderStatusDetails());
        orderStatusDetails.clear();
        orderStatusDetails.addAll(trackDetailResponse.getOrderStatusDetails());
        trackAdapter.notifyDataSetChanged();
        restaurantDetails = trackDetailResponse.getRestaurantDetails();
        if (deliveryPersonDetails.getDeliverAssigned().equals(YES)) {
            driverId = String.valueOf(deliveryPersonDetails.getDeliverId());
            driverLat = deliveryPersonDetails.getDeliverLatitude();
            driverLng = deliveryPersonDetails.getDeliverLongitude();
            if (mqttAndroidClient != null) {
                topic = "delivery/" + driverId;
                mqttServerClient.subscribe(mqttAndroidClient, topic);
            }
            trackPresenter.requestPolylineData(driverLat, driverLng, restaurantDetails.getRestaurantLatitude(),
                    restaurantDetails.getRestaurantLongitude(), customerLat, customerLng);
        } else {
            if (mqttAndroidClient != null) {
                topic = "order/" + orderId + "/restaurant/" + restaurantId;
                mqttServerClient.subscribe(mqttAndroidClient, topic);
            }
        }
    }


    @Override
    public void showSendOtpResponse(TrackDetailResponse trackDetailResponse) {
        orderStatusDetails.clear();
        deliveryPersonDetails = trackDetailResponse.getDeliveryPersonDetails();
        trackAdapter.setDeliveryPersonDetails(trackDetailResponse.getDeliveryPersonDetails());
        orderStatusDetails.addAll(trackDetailResponse.getOrderStatusDetails());
        trackAdapter.notifyDataSetChanged();
        if (trackDetailResponse.getOrderOtp() != null) {
            String otpCode = "OTP : " + trackDetailResponse.getOrderOtp();
            tvOtp.setText(otpCode);
            tvOtp.setVisibility(View.VISIBLE);
        } else {
            tvOtp.setVisibility(View.GONE);
        }

        if (deliveryPersonDetails.getDeliverAssigned().equals(YES)) {
            driverId = String.valueOf(deliveryPersonDetails.getDeliverId());
            driverLat = deliveryPersonDetails.getDeliverLatitude();
            driverLng = deliveryPersonDetails.getDeliverLongitude();
            if (mqttAndroidClient != null) {
                topic = "delivery/" + driverId;
                mqttServerClient.subscribe(mqttAndroidClient, topic);
            }
            trackPresenter.requestPolylineData(driverLat, driverLng, restaurantDetails.getRestaurantLatitude(), restaurantDetails.getRestaurantLongitude(), customerLat, customerLng);
        } else {
            if (mqttAndroidClient != null) {
                topic = "order/" + orderId + "/restaurant/" + restaurantId;
                mqttServerClient.subscribe(mqttAndroidClient, topic);
            }
        }
        checkOrderDelivered(trackDetailResponse.getOrderStatusDetails(), trackDetailResponse.getOrderFailedReason());
    }


    private void checkOrderDelivered(List<OrderStatusDetail> orderStatusDetails, String reason) {
        for (int i = 0; i < orderStatusDetails.size(); i++) {
            if (orderStatusDetails.get(i).getStageCompleted().equals(YES)) {
                lastStatus = orderStatusDetails.get(i).getOrdTitle();
            }

            if (orderStatusDetails.get(i).getOrdStage() == 7 && orderStatusDetails.get(i).getStageCompleted().equals(YES)) {
                if (!isArrived) {
                    isArrived = true;
                }
            }

            if (orderStatusDetails.get(i).getOrdStage() == 8 && orderStatusDetails.get(i).getStageCompleted().equals(YES)) {
                isDelivered = true;
                isArrived = false;
            }

            if (orderStatusDetails.get(i).getOrdStage() == 9 && orderStatusDetails.get(i).getStageCompleted().equals(YES)) {
                isFailed = true;
                isArrived = false;
            }

        }
      /*  if(isArrived){
            if(ArrivedCount==0)
            showToast(String.format("You have to pay:%s%s", mTrackDetailResponse.getCurrency(), mTrackDetailResponse.getPayableAmount()));
            ArrivedCount++;
        }*/
        if (isDelivered) {
            stopTimer();
            cardView.setVisibility(View.GONE);
            cardViewSuccess.setVisibility(View.VISIBLE);
            isDelivered = false;
        }

        if (isFailed) {
            stopTimer();
            showErrorDialog("Failed", reason);

        }
        tvOrderStatus.setText(lastStatus);
        if (!lastStatus.equals("")) {
            TSnackbar snackbar = TSnackbar
                    .make(cardView, lastStatus, TSnackbar.LENGTH_LONG);
            snackbar.setIconLeft(DataUtils.getStatus(lastStatus), 24);
            snackbar.setIconPadding(10);
            View snackbarView = snackbar.getView();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(ViewUtils.dpToPx(8), ViewUtils.dpToPx(6), ViewUtils.dpToPx(8), 0);
            snackbarView.setLayoutParams(params);
            snackbarView.setBackgroundColor(Color.TRANSPARENT);
            TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            textView.setBackgroundResource(R.drawable.snack_bar_track_);
            textView.setTypeface(ResourcesCompat.getFont(context, R.font.helvetica_ultra_light));

            textView.setTextColor(Color.BLACK);
            snackbar.show();
        }
    }

    @Override
    public void showDirection(DirectionResponse directionResponse) {

        LatLng restaurantLocation = new LatLng(Double.valueOf(restaurantDetails.getRestaurantLatitude()), Double.valueOf(restaurantDetails.getRestaurantLongitude()));
        //LatLng restaurantLocation = new LatLng(11.0168, 76.9558);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(restaurantLocation)
                .title(restaurantDetails.getRestaurantName())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pick));
        Marker marker = mMap.addMarker(markerOptions);
        marker.hideInfoWindow();
        //directionResponse.getRoutes();

        driverMarker.hideInfoWindow();
        List<Route> routeList = directionResponse.getRoutes();
        if (routeList.size() > 0) {
            String durationTime = calculateDuration(routeList.get(0).getLegs());
            if (checkDriverMarker()) {
                driverMarker.setTitle(durationTime);
                driverMarker.showInfoWindow();
            } else {
                driverLat = deliveryPersonDetails.getDeliverLatitude();
                driverLng = deliveryPersonDetails.getDeliverLongitude();
                if (!driverLat.equals("") || !driverLng.equals("")) {
                    LatLng driverLocation = new LatLng(Double.valueOf(driverLat), Double.valueOf(driverLng));
                    MarkerOptions deliveryBoy = new MarkerOptions()
                            .position(driverLocation)
                            .title(durationTime)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_vehicle));
                    this.zoomingLatlng = driverLocation;
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(driverLocation, zoomLevel));
                    mMap.setInfoWindowAdapter(new DriverInfoWindowAdapter(this));
                    mMap.setOnMarkerClickListener(marker1 -> {
                        driverMarker.setTitle(durationTime);
                        driverMarker.showInfoWindow();
                        return true;
                    });
                    driverMarker = mMap.addMarker(deliveryBoy);
                    driverMarker.setVisible(true);
                    driverMarker.setTitle(durationTime);
                    driverMarker.showInfoWindow();
                }
            }


            PolylineOptions polylineOptions = getPolyDraw(directionResponse.getRoutes());
            polylineOptions.width(7);
            polylineOptions.color(Color.DKGRAY);
            polylineOptions.startCap(new SquareCap());
            polylineOptions.endCap(new SquareCap());
            polylineOptions.jointType(ROUND);
            blackPolyLine = mMap.addPolyline(polylineOptions);
        }
    }


    private boolean checkDriverMarker() {
        try {
            if (driverMarker != null) {
                driverMarker.getPosition();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public void showReDirectionResponse(DirectionResponse directionResponse) {
        driverMarker.hideInfoWindow();
        List<Route> routeList = directionResponse.getRoutes();
        if (routeList.size() > 0) {
            String durationTime = calculateDuration(routeList.get(0).getLegs());
            markerHide(driverMarker);
            driverMarker.setTitle(durationTime);
            driverMarker.showInfoWindow();

            removePoliLine();
            PolylineOptions polylineOptions = getPolyDraw(directionResponse.getRoutes());
            polylineOptions.width(8);
            polylineOptions.color(Color.DKGRAY);
            polylineOptions.startCap(new SquareCap());
            polylineOptions.endCap(new SquareCap());
            polylineOptions.jointType(ROUND);
            blackPolyLine = mMap.addPolyline(polylineOptions);

        }

        trackPresenter.repeatCallTrackDetails(String.valueOf(restaurantId), orderId, 1);
    }


    private void removePoliLine() {
        try {
            blackPolyLine.remove();
        } catch (Exception e) {
            Log.e(TAG, "removePoliLine: " + e.toString());
        }
    }

    @Override
    public void showDistance(DistanceResponse distanceResponse) {
        driverMarker.hideInfoWindow();
        if (distanceResponse.getRows().size() > 0) {
            if (distanceResponse.getRows().get(0).getElements().size() > 0) {
                String newDistance = distanceResponse.getRows().get(0).getElements().get(0).getDuration().getText();
                driverMarker.setTitle(newDistance);
                driverMarker.showInfoWindow();
                Log.i(TAG, "showDistance : " + newDistance);
            }
        }
    }


    @Override
    public void directionAPIError(String error) {

        if (isFirst) {
            isFirst = false;
            Toast.makeText(context, "Route not found", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void showType(String type) {
        currentType = type;
    }

    private String calculateDuration(List<Leg> legs) {
        int oldValue = 0;
        String durationTime = "";
        for (int index = 0; index < legs.size(); index++) {
            Leg leg = legs.get(index);
            Duration duration = leg.getDuration();
            if (oldValue == 0) {
                oldValue = duration.getValue();
                durationTime = duration.getText();
            } else if (oldValue< duration.getValue()) {
                oldValue=duration.getValue();
                durationTime = duration.getText();
            }

        }
        return durationTime;
    }

    private PolylineOptions getPolyDraw(List<Route> routeList) {
        PolylineOptions polylineOptions = null;
        for (int index = 0; index < routeList.size(); index++) {
            List<Leg> legList = routeList.get(index).getLegs();
            pointsLatLngArrayList = new ArrayList<>();
            polylineOptions = new PolylineOptions();
            for (int jIndex = 0; jIndex < legList.size(); jIndex++) {
                List<Step> stepList = legList.get(jIndex).getSteps();
                for (int kIndex = 0; kIndex < stepList.size(); kIndex++) {
                    String points = stepList.get(kIndex).getPolyline().getPoints();
                    pointsLatLngArrayList.addAll(PolyUtil.decodePoly(points));
                }
            }
            polylineOptions.addAll(pointsLatLngArrayList);
        }
        return polylineOptions;
    }

    @Override
    public void driverInfoClick() {
        String deliverImage = deliveryPersonDetails.getDeliverImage();
        String callNumber = deliveryPersonDetails.getDeliverMobile();
        String driverName = deliveryPersonDetails.getDeliverName();
        String btnText = getResources().getString(R.string.call_txt) + SPACE + deliveryPersonDetails.getDeliverName() + " ?";
        Bundle args = new Bundle();
        args.putString(IMAGE, deliverImage);
        args.putString(CALL_NUMBER, callNumber);
        args.putString(DRIVER_NAME, driverName);
        args.putString(ESM_TIME, totalEstimateTime);
        args.putString(BTN_TEXT, btnText);

        DriverInfoFragment driverInfoFragment = new DriverInfoFragment();
        driverInfoFragment.setArguments(args);
        driverInfoFragment.show(getSupportFragmentManager(), driverInfoFragment.getTag());
    }


    protected void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(com.alium.nibo.R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        LatLng customer = new LatLng(Double.valueOf(appRepository.getLatitude()), Double.valueOf(appRepository.getLongitude()));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(customer)
                .zoom(10)
                .build();
        this.zoomingLatlng = customer;
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onSuccess(MqttAndroidClient _mqttAndroidClient) {
        try {
            this.mqttAndroidClient = _mqttAndroidClient;
            if (mqttAndroidClient != null) {
                if (driverId.equals("")) {
                    topic = "order/" + orderId + "/restaurant/" + restaurantId;
                    mqttServerClient.subscribe(mqttAndroidClient, topic);
                } else {
                    topic = "delivery/" + driverId;
                    mqttServerClient.subscribe(mqttAndroidClient, topic);
                }
            }
        } catch (NullPointerException e) {
            Log.e(MQTT_CONNECTION_FAILED, e.toString());
        } catch (Exception e) {
            Log.e(MQTT_CONNECTION_FAILED, e.toString());
        }
    }

    @Override
    public void onFailure(String message) {
        Log.i(TAG, "connectionFailure: " + message);
    }


    @Override
    public void onMessageReceived(String topic, MqttMessage mqttMessage) {
        try {
            byte[] payload = mqttMessage.getPayload();
            String response = new String(payload);
            Log.i("MQTT Response", response);
            JSONObject jsonObject = new JSONObject(response);
            currentType = jsonObject.getString("type");
            zoomLevel = mMap.getCameraPosition().zoom;
            prevEventTime = System.currentTimeMillis();
            switch (currentType) {
                case TYPE_ASSIGNED:
                    JSONObject data = jsonObject.getJSONObject("data");
                    driverId = data.getString("deliver_boy_id");
                    topic = "delivery/" + driverId;
                    mqttServerClient.subscribe(mqttAndroidClient, topic);
                    trackPresenter.repeatCallTrackDetails(String.valueOf(restaurantId), orderId, 0);
                    break;
                case TYPE_ACCEPT:
                    driverLat = jsonObject.getString("lat");
                    driverLng = jsonObject.getString("lng");
                    startBikeAnimation(Double.valueOf(driverLat), Double.valueOf(driverLng));
                    trackPresenter.requestRePolylineData(String.valueOf(driverLat), String.valueOf(driverLng), restaurantDetails.getRestaurantLatitude(), restaurantDetails.getRestaurantLongitude(), customerLat, customerLng);
                    break;
                case TYPE_STARTED:
                    driverLat = jsonObject.getString("lat");
                    driverLng = jsonObject.getString("lng");
                    startBikeAnimation(Double.valueOf(driverLat), Double.valueOf(driverLng));
                    trackPresenter.requestRePolylineData(String.valueOf(driverLat), String.valueOf(driverLng), "", "", customerLat, customerLng);
                case TYPE_ARRIVED:
                    driverLat = jsonObject.getString("lat");
                    driverLng = jsonObject.getString("lng");
                    startBikeAnimation(Double.valueOf(driverLat), Double.valueOf(driverLng));
                    trackPresenter.requestRePolylineData(String.valueOf(driverLat), String.valueOf(driverLng), "", "", customerLat, customerLng);
                    break;
                case TYPE_SEND_OTP:
                    driverLat = jsonObject.getString("lat");
                    driverLng = jsonObject.getString("lng");
                    startBikeAnimation(Double.valueOf(driverLat), Double.valueOf(driverLng));
                    trackPresenter.repeatCallTrackDetails(String.valueOf(restaurantId), orderId, 1);
                    break;
                case TYPE_DELIVERED:
                    cardView.setVisibility(View.GONE);
                    cardViewSuccess.setVisibility(View.VISIBLE);
                    stopTimer();
                    break;
                case TYPE_FAILED:
                    stopTimer();
                    showErrorDialog("Failed", jsonObject.getString("reason"));
                    break;
            }

        } catch (Exception e) {
            Log.e(MQTT_EXCEPTION, e.toString());
        }
    }


    private void startBikeAnimation(Double latitude, Double longitude) {
        try {
            endPosition = new LatLng(latitude, longitude);
            startPosition = driverMarker.getPosition();
            if (startPosition != endPosition) {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.setDuration(3000);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(valueAnimator1 -> {
                    v = valueAnimator1.getAnimatedFraction();
                    double lng = v * endPosition.longitude + (1 - v)
                            * startPosition.longitude;
                    double lat = v * endPosition.latitude + (1 - v)
                            * startPosition.latitude;
                    LatLng newPos = new LatLng(lat, lng);
                    driverMarker.setPosition(newPos);
                    driverMarker.setAnchor(0.5f, 0.5f);
                    mMap.moveCamera(CameraUpdateFactory
                            .newCameraPosition
                                    (new CameraPosition.Builder()
                                            .target(newPos)
                                            .zoom(zoomLevel)
                                            .build()));
                });
                valueAnimator.start();
                index = index + 1;
            }
        } catch (Exception e) {
            Log.e(TAG, "startBikeAnimation: " + e.toString());
        }
    }

    @Override
    public void connectionLost(String error) {
        Log.i(TAG, "connectionLost: " + error);
    }

    @Override
    public void reConnected(MqttAndroidClient mqttAndroidClient) {
        if (mqttAndroidClient != null) {
            mqttServerClient.subscribe(mqttAndroidClient, topic);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mqttServerClient != null &&  !isDestroyed()) {
            mqttServerClient.clientConnect(this);
        }
        startTimer();
    }


    private void startTimer() {
        stopTimer();
        timer = new Timer();
        timer.schedule(new TrackTimerTask(), API_CALL_INTERVAL, API_CALL_INTERVAL);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_live_track;
    }

    private class TrackTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(() -> {
                Log.i(TAG, "run: auto refresh");
                if (Math.abs(prevEventTime - System.currentTimeMillis()) > 10000) {
                    Log.i(TAG, "run: auto refresh API");
                    trackPresenter.repeatCallTrackDetails(String.valueOf(restaurantId), orderId, 1);
                }

            });

        }
    }


}