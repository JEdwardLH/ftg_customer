package com.alium.nibo.placepicker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alium.nibo.R;
import com.alium.nibo.autocompletesearchbar.NiboAutocompleteSVProvider;
import com.alium.nibo.autocompletesearchbar.NiboPlacesAutoCompleteSearchView;
import com.alium.nibo.autocompletesearchbar.NiboSearchSuggestionItem;
import com.alium.nibo.base.BaseNiboFragment;
import com.alium.nibo.models.MultiLocation;
import com.alium.nibo.models.NiboSelectedPlace;
import com.alium.nibo.utils.NiboConstants;
import com.alium.nibo.utils.NiboStyle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.alium.nibo.utils.NiboConstants.HOME_ADDRESS;
import static com.alium.nibo.utils.NiboConstants.NIBO_ADDRESS_TYPE;
import static com.alium.nibo.utils.NiboConstants.TAB_POSITION;

/**
 * A placeholder fragment containing a simple view.
 */
public class NiboPickerFragment extends BaseNiboFragment<NiboPickerContracts.Presenter> implements NiboAutocompleteSVProvider, NiboPickerContracts.View {

    private RelativeLayout mRootLayout;
    private NiboPlacesAutoCompleteSearchView mSearchView;
    private LinearLayout mLocationDetails;
    private TextView mGeocodeAddress;
    //private EditText mLandMark;
    protected String mSearchBarTitle;
    private Button mPickLocationLL;
    private FloatingActionButton centerMyLocationFab;
    private int addressType=HOME_ADDRESS;

    //
    private String MY_PREFS_NAME = "Pref Name";
    String LAND_MARK = "landMark";
    SharedPreferences prefs;
    String textAddress="";
    String currentAddress="";
    String cartCount="";
    public static int MULTI_ADDRESS_REQUEST_CODE=101;

    public NiboPickerFragment() {

    }

    public static NiboPickerFragment newInstance(String searchBarTitle, String confirmButtonTitle, NiboStyle styleEnum, @RawRes int styleFileID,
                                                 @DrawableRes int markerIconRes, String cartCount, String address) {
        Bundle args = new Bundle();
        NiboPickerFragment fragment = new NiboPickerFragment();
        args.putString(NiboConstants.SEARCHBAR_TITLE_ARG, searchBarTitle);
        args.putString(NiboConstants.CART_COUNT, cartCount);
        args.putString(NiboConstants.SELECTION_BUTTON_TITLE, confirmButtonTitle);
        args.putSerializable(NiboConstants.STYLE_ENUM_ARG, styleEnum);
        args.putInt(NiboConstants.STYLE_FILE_ID, styleFileID);
        args.putInt(NiboConstants.MARKER_PIN_ICON_RES, markerIconRes);
        args.putString(NiboConstants.ADDRESS, address);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void setPlaceData(Place place) {
        addSingleMarkerToMap(place.getLatLng());
    }

    @Override
    public void displayPlaceDetailsError() {
        displayError("Error details");
    }

    @Override
    public boolean isSearching() {
        return mSearchView.isSearching();
    }

    @Override
    public void closeSearch() {
        mSearchView.closeSearch();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_picker_nibo;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        initView(view);
        setUpBackPresses(view);
        initMap();

        if (mSearchBarTitle != null && !mSearchBarTitle.equals("")) {
            mSearchView.setLogoText(mSearchBarTitle);
        }

        mPickLocationLL.setOnClickListener(v -> {
            if(!cartCount.equals("") && Integer.parseInt(cartCount)>0){
                showAddressChangeInfo();
            }else{

                 //if(!mLandMark.getText().toString().isEmpty()){
                     presenter.sendResults();
               //  }else{
                    // Toast.makeText(getActivity(),getString(R.string.landmark_error),Toast.LENGTH_LONG).show();
              //   }


            }
        });

        configureCameraIdle();
    }


    private void configureCameraIdle() {
        onCameraIdleListener = () -> {
            extractGeocode(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
        };
    }




    private void setUpBackPresses(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                // handle back button's click listener
                presenter.handleBackPress();
                return true;
            }
            return false;
        });
    }

    protected void addSingleMarkerToMap(LatLng latLng) {
        if (mMap != null) {
            CameraPosition cameraPosition =
                    new CameraPosition.Builder().target(latLng)
                            .zoom(getDefaultZoom())
                            .build();
            hasWiderZoom = false;
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            extractGeocode(latLng.latitude, latLng.longitude);
        }
    }

    @Override
    protected void extractGeocode(final double lati, final double longi) {
        try {
            Log.d("dsadadsad","dine" + lati+" ---  "+ longi);
            presenter.getGeocode(lati, longi,getString(R.string.language));
        } catch (Exception e) {
            Log.d("dsadadsad",e.getMessage());
        }
    }


    @Override
    protected void handleLocationRetrieval(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        addOverlay(latLng);
        addSingleMarkerToMap(latLng);
    }


    void showAddressWithTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(mRootLayout);
        }
        mLocationDetails.setVisibility(View.VISIBLE);
      //  mMap.setPadding(0, 0, 0, mLocationDetails.getHeight());

    }


    void hideAddressWithTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(mRootLayout);
        }
        mLocationDetails.setVisibility(View.VISIBLE);
       // mMap.setPadding(0, 0, 0, 0);

    }


    private void initView(View convertView) {
        this.mRootLayout = convertView.findViewById(R.id.root_layout);
        this.mSearchView =  convertView.findViewById(R.id.searchview);
        this.mLocationDetails =  convertView.findViewById(R.id.location_details);
        this.mGeocodeAddress =  convertView.findViewById(R.id.geocode_address);
        this.mPickLocationLL =  convertView.findViewById(R.id.pick_location_btn);
        //this.mLandMark =  convertView.findViewById(R.id.et_landmark);
        TextView tv_title_details =  convertView.findViewById(R.id.tv_title_details);
        TextView labelLandmark =  convertView.findViewById(R.id.label_landmark);
        TextView tvChooseLocation =  convertView.findViewById(R.id.tv_choose_location);
        this.centerMyLocationFab =  convertView.findViewById(R.id.center_my_location_fab);
        this.mSearchView.setmProvider(this);
        String title = getArguments().getString(NiboConstants.SEARCHBAR_TITLE_ARG);
        String button = getArguments().getString(NiboConstants.SELECTION_BUTTON_TITLE);
        cartCount=getArguments().getString(NiboConstants.CART_COUNT);
        textAddress=getArguments().getString(NiboConstants.ADDRESS);
        mPickLocationLL.setText(button);
        tv_title_details.setText(title);
        if(!title.equals(getString(R.string.set_delivery_location))){
            labelLandmark.setVisibility(View.GONE);
            //mLandMark.setVisibility(View.GONE);
        }
       // mLandMark.setText(prefs.getString(LAND_MARK,""));

        System.out.println("Address:"+getArguments().getString(NiboConstants.ADDRESS).isEmpty());
        if(getArguments()!=null && getArguments().getString(NiboConstants.ADDRESS).isEmpty()){
            tvChooseLocation.setVisibility(View.GONE);
        }

        centerMyLocationFab.setOnClickListener(view -> {
            if(currentLatLng!=null){
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(currentLatLng.latitude, currentLatLng.longitude))
                        .zoom(19f)
                        .build();
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
        tvChooseLocation.setOnClickListener(v->{
            Intent intent = new Intent("com.foodtogo.user.MyAction");
            intent.putExtra(TAB_POSITION,3);
            startActivityForResult(intent,MULTI_ADDRESS_REQUEST_CODE);
        });

    }


    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    @Override
    public void onHomeButtonClicked() {
        getActivity().finish();
    }

    @Override
    public NiboPlacesAutoCompleteSearchView.SearchListener getSearchListener() {
        return new SearchListenerImpl();
    }

    @Override
    public boolean getShouldUseVoice() {
        return false;
    }

    @Override
    public void setResults(NiboSelectedPlace niboSelectedPlace) {
        Intent intent = new Intent();
        intent.putExtra(NiboConstants.SELECTED_PLACE_RESULT, niboSelectedPlace);
        intent.putExtra(LAND_MARK, "");
        intent.putExtra(NIBO_ADDRESS_TYPE, addressType);
        getActivity().setResult(RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    public void setGeocodeAddress(String address) {
              currentAddress=address;
             mPickLocationLL.setEnabled(true);
             mGeocodeAddress.setText(address);

    }

    private void showAddressChangeInfo() {
        final Dialog dialog = new Dialog(getActivity(),R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_skip_continue);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvMessage = dialog.findViewById(R.id.tv_message);
        final Button btnOk = dialog.findViewById(R.id.btn_skip);
        final Button btnCancel = dialog.findViewById(R.id.btn_continue);
        dialog.setCancelable(false);
        tvMessage.setText(getString(R.string.user_address_change_info));
        btnOk.setText(getString(R.string.ok));
        btnCancel.setText(getString(R.string.cancel));
        btnOk.setOnClickListener(v -> {
            dialog.dismiss();
            System.out.println("Preference1:"+prefs.getString(LAND_MARK,""));
            //if(!mLandMark.getText().toString().isEmpty()){
                presenter.sendResults();
          //  }else{ Toast.makeText(getActivity(),getString(R.string.landmark_error),Toast.LENGTH_LONG).show(); }

        });

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void injectDependencies() {
        super.injectDependencies();
        injectPresenter(injection.getNiboPickerPresenter());
    }

    @Override
    public void showResultView() {
        showAddressWithTransition();
    }

    @Override
    public void displayErrorGeoCodingMessage() {
        displayError("Error getting address for your location");
    }


    class SearchListenerImpl implements NiboPlacesAutoCompleteSearchView.SearchListener {

        @Override
        public void onSearchEditOpened() {
            hideAddressWithTransition();
        }

        @Override
        public void onSearchEditClosed() {
            showAddressWithTransition();
        }


        @Override
        public boolean onSearchEditBackPressed() {
            if (mSearchView.isEditing()) {
                mSearchView.cancelEditing();
                return true;
            }
            return false;
        }

        @Override
        public void onSearchExit() {

        }

        @Override
        public void onSearchTermChanged(String term) {


        }

        @Override
        public void onSearch(String string) {

        }

        @Override
        public boolean onSuggestion(NiboSearchSuggestionItem niboSearchSuggestionItem) {
            initSearchViews(niboSearchSuggestionItem);
            presenter.getPlaceDetailsById(niboSearchSuggestionItem.getPlaceID());
            return false;
        }

        @Override
        public void onSearchCleared() {

        }

    }

    public void initSearchViews(NiboSearchSuggestionItem niboSearchSuggestionItem) {
        mSearchView.setSearchString(niboSearchSuggestionItem.getFullTitle(), true);
        mSearchView.setLogoText(niboSearchSuggestionItem.getFullTitle());
        mSearchView.closeSearch();
        hideKeyboard();
        hideAddressWithTransition();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == MULTI_ADDRESS_REQUEST_CODE) {
            String address=data.getStringExtra(NiboConstants.SELECTED_PLACE_RESULT);
            String latitude=data.getStringExtra(NiboConstants.NIBO_LATITUDE);
            String longitude=data.getStringExtra(NiboConstants.NIBO_LONGITUDE);
            addressType=data.getIntExtra(NiboConstants.NIBO_ADDRESS_TYPE,1);

            mGeocodeAddress.setText(address);
            presenter.saveLocation(Double.valueOf(latitude),Double.valueOf(longitude),address);
        }
    }

}

