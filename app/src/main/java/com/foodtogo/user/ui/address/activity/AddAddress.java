package com.foodtogo.user.ui.address.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.alium.nibo.models.NiboSelectedPlace;
import com.alium.nibo.placepicker.NiboPlacePickerActivity;
import com.alium.nibo.utils.AddressConstants;
import com.alium.nibo.utils.NiboConstants;
import com.alium.nibo.utils.NiboStyle;
import com.foodtogo.user.BuildConfig;
import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.model.accountdetails.User;
import com.foodtogo.user.model.geocodeaddress.AddressComponent;
import com.foodtogo.user.model.geocodeaddress.GeoCodeAddress;
import com.foodtogo.user.model.geocodeaddress.Result;
import com.foodtogo.user.model.shippingaddress.MultiLocation;
import com.foodtogo.user.ui.address.mvp.AddAddressContractor;
import com.foodtogo.user.ui.address.mvp.AddAddressPresenter;
import com.foodtogo.user.ui.home.activity.Home;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import butterknife.OnClick;

import static com.alium.nibo.placepicker.NiboPlacePickerActivity.NiboPlacePickerBuilder;
import static com.alium.nibo.placepicker.NiboPlacePickerActivity.setBuilder;


public class AddAddress extends BaseActivity implements AddAddressContractor.View {


    AddAddressPresenter addAddressPresenter;
    String fromClass = "";
    private static final int LOCATION_REQUEST_CODE = 300;
    private static final String TAG = AddAddress.class.getSimpleName();

    // location last updated time
    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    private static final int REQUEST_CHECK_SETTINGS = 100;


    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;

    //Current Location
    String currentLat;
    String currentLong;
    private List<MultiLocation> multiLocationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addAddressPresenter = new AddAddressPresenter(this, appRepository,AddAddress.this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fromClass = bundle.getString(FROM_CLASS);
        }

        // initialize the necessary libraries
        init();
        // restore the values from saved instance state
        restoreValuesFromBundle(savedInstanceState);
        permissionRequest();

       // addAddressPresenter.

    }

    @Override
    public int getLayout() {
        return R.layout.activity_add_address;
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
        showToast(message);
    }

    @Override
    public void showError(int message) {
        showToast(message);
    }


    @OnClick(R.id.tv_manually)
    public void setManuallLocation() {
        if (isNetworkConnected())
            addAddressPresenter.onManualLocation();
        else
            showToast(R.string.no_internet_connection);
    }

    @OnClick(R.id.iv_auto)
    public void setCurrentLocation() {
        addAddressPresenter.onCurrentLocation();
    }

    @Override
    public void showManualLocation() {
        hideKeyboard();
        Intent intent = new Intent(this, NiboPlacePickerActivity.class);
        intent.putExtra("address",appDataSource.getSearchLocation());
        intent.putExtra("cart_count",String.valueOf(appDataSource.getCartCount()));
        NiboPlacePickerBuilder config = new NiboPlacePickerBuilder()
                .setSearchBarTitle(getString(R.string.set_delivery_location))
                .setConfirmButtonTitle(getString(R.string.confirm_location))
                .setMarkerPinIconRes(R.drawable.ic_map_marker_def)
                .setStyleEnum(NiboStyle.DEFAULT);
        setBuilder(config);
        startActivityForResult(intent, LOCATION_REQUEST_CODE);
    }

    @Override
    public void showUseCurrentLocation() {
        if (currentLat != null) {
            appRepository.setLatitude(currentLat);
            appRepository.setLongitude(currentLong);
            appRepository.saveLocation(true);
            addAddressPresenter.requestAddress(currentLat, currentLong);
        } else {
            permissionRequest();
        }
    }

    @Override
    public void showUserList(User user) {
        appRepository.setUserAvatar(user.getUserAvatar());
        appRepository.setUserEmail(user.getUserEmail());
        appRepository.setUserName(user.getUserName());
        appRepository.setUserPhoneNo(user.getUserPhone());
        Bundle bundle = new Bundle();
        bundle.putInt(TAB_POSITION, TAB_HOME);
        changeActivityClearBackStack(Home.class, bundle);
    }

    @Override
    public void showGeoCodeAddress(GeoCodeAddress geoCodeAddress) {
        if (geoCodeAddress.getResults().size() > 0) {
            Result result = geoCodeAddress.getResults().get(0);
            List<AddressComponent> addressComponentList = result.getAddressComponents();
            for (int i = 0; i < addressComponentList.size(); i++) {
                if (addressComponentList.get(i).getTypes().size() > 0) {
                    if (addressComponentList.get(i).getTypes().get(0).equals(POSTAL_CODE)) {
                        appRepository.setSearchPinCode(addressComponentList.get(i).getLongName());
                    }
                }
            }
            appRepository.setSearchLocation(result.getFormattedAddress());
        }
        addAddressPresenter.requestAccountDetails(0);
    }

    @Override
    public void showGeoCodeAddressError(String error) {
        addAddressPresenter.requestAccountDetails(0);
    }

    @Override
    public void bindMultiLocationList(List<MultiLocation> multiLocationList) {
       this.multiLocationList=multiLocationList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == LOCATION_REQUEST_CODE) {
            NiboSelectedPlace selectedPlace = data.getParcelableExtra(NiboConstants.SELECTED_PLACE_RESULT);
            appRepository.saveLocation(true);
            appRepository.setLatitude("" + selectedPlace.getLatitude());
            appRepository.setLongitude("" + selectedPlace.getLongitude());
            appRepository.setSearchLocation(selectedPlace.getPlaceAddress());
            appRepository.setSearchPinCode(AddressConstants.PIN_CODE);
           // PreferenceUtils.writeString(getApplicationContext(),LAND_MARK,data.getStringExtra(LAND_MARK));
            addAddressPresenter.requestAccountDetails(1);
        }
    }


    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

    }

    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }
        }

        updateLocationUI();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
        outState.putString("last_updated_on", mLastUpdateTime);
    }

    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");
                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, e -> {
                    int statusCode = ((ApiException) e).getStatusCode();
                    switch (statusCode) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                    "location settings ");
                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the
                                // result in onActivityResult().
                                ResolvableApiException rae = (ResolvableApiException) e;
                                rae.startResolutionForResult(AddAddress.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException sie) {
                                Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            String errorMessage = "Location settings are inadequate, and cannot be " +
                                    "fixed here. Fix in Settings.";
                            Log.e(TAG, errorMessage);
                            Toast.makeText(AddAddress.this, errorMessage, Toast.LENGTH_LONG).show();
                    }

                    updateLocationUI();
                });
    }

    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, task -> {
                });
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Resuming location updates depending on button state and
        // allowed permissions
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }

        updateLocationUI();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mRequestingLocationUpdates) {
            // pausing location updates
            stopLocationUpdates();
        }
    }

    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            Log.i(TAG, "updateLocationUI: " + "Lat: " + mCurrentLocation.getLatitude() + ", " +
                    "Lng: " + mCurrentLocation.getLongitude());
            currentLat = String.valueOf(mCurrentLocation.getLatitude());
            currentLong = String.valueOf(mCurrentLocation.getLongitude());
        }
    }

    private void permissionRequest() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


}
