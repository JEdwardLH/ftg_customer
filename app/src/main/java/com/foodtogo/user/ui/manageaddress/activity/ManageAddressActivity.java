package com.foodtogo.user.ui.manageaddress.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alium.nibo.models.NiboSelectedPlace;
import com.alium.nibo.placepicker.NiboPlacePickerActivity;
import com.alium.nibo.utils.NiboConstants;
import com.alium.nibo.utils.NiboStyle;
import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.model.shippingaddress.MultiAddressRequest;
import com.foodtogo.user.model.shippingaddress.MultiLocation;
import com.foodtogo.user.ui.manageaddress.adapter.ManageAddressAdapter;
import com.foodtogo.user.ui.manageaddress.mvp.ManageAddressContractor;
import com.foodtogo.user.ui.manageaddress.mvp.ManageAddressPresenter;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static com.alium.nibo.placepicker.NiboPlacePickerActivity.setBuilder;

public class ManageAddressActivity extends BaseActivity implements ManageAddressContractor.View,ManageAddressAdapter.AddressClickListener {

    private List<MultiLocation> multiLocationList;
    private ManageAddressAdapter adapter;

    @BindView(R.id.recycler_items)
    RecyclerView rv;

    @BindView(R.id.tv_error)
    TextView tvError;

    @BindView(R.id.ll_order_view)
    LinearLayout llOrderView;

    private EditText edtLocation;
    private String latitude;
    private String longitude;
    private String zipCode;
    private String addressType="1";

    private static final int LOCATION_REQUEST_CODE = 300;

    private LinearLayoutManager mLayoutManager;
    private ManageAddressPresenter presenter;

    private MultiLocation multiLocation;

   /* public static ManageAddressActivity newInstance (boolean isManage) {
        Bundle args = new Bundle();
        ManageAddressActivity fragment = new ManageAddressActivity();
        args.putBoolean(IS_MANAGE, isManage);
        fragment.setArguments(args);
        return fragment;

    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRecyclerView();
        setupToolBar();
        tvTitle.setText(getString(R.string.nav_item_manage_address));
        presenter=new ManageAddressPresenter(this,appRepository);
        requestToGetAddress();
    }

   /* @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         if(getArguments()!=null){
             isManage=getArguments().getBoolean(IS_MANAGE);
         }
        setRecyclerView(isManage);
        presenter=new ManageAddressPresenter(this,appRepository);
        requestToGetAddress();
    }*/

    private void requestToGetAddress(){
        if(NetworkUtils.isNetworkConnected(getApplicationContext())){
            presenter.requestToGetAllAddress(appRepository.getLanguageCode());
        }else{
            showError(R.string.no_internet_connection);
        }
    }

    @OnClick(R.id.fabAdd)
    void onFabClick(){
        addressType="1";
        showAddOrEditDialog(getString(R.string.add_new_address),"");
    }

    @Override
    public void successMsg(String msg) {
        showSuccessDialog(msg);
    }

    @Override
    public void showLoadingView() {
        showProgress();
    }

    @Override
    public void hideLoadingView() {
        hideProgress();
    }

    @Override
    public void showError(String message) {
        llOrderView.setVisibility(View.GONE);
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(int message) {
        llOrderView.setVisibility(View.GONE);
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBindMultiLocationList(List<MultiLocation> multiLocations) {
        this.multiLocationList=multiLocations;
        if(multiLocationList.size()>0){
            adapter.setList(multiLocationList);
            tvError.setVisibility(View.GONE);
            llOrderView.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }else{
            llOrderView.setVisibility(View.GONE);
            tvError.setText(getResources().getString(R.string.no_address_added));
            tvError.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onAddOrEditSuccess(String msg) {
         Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
        requestToGetAddress();
    }

    @Override
    public void addEditError(String msg) {
      showToast(msg);
    }

    private void setRecyclerView() {
        try {
            multiLocationList=new ArrayList<>();
            adapter = new ManageAddressAdapter(this,multiLocationList);
            rv.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            rv.setLayoutManager(mLayoutManager);
            rv.setItemAnimator(new DefaultItemAnimator());
            rv.setAdapter(adapter);
            setRecyclerViewAnimation(rv);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void editAddressField(MultiLocation mMultiLocation) {
        if(NetworkUtils.isNetworkConnected(getApplicationContext())){
            this.multiLocation=mMultiLocation;
             showAddOrEditDialog("Edit Address",String.valueOf(mMultiLocation.getLocIc()));
        }else{
            showError(R.string.no_internet_connection);
        }
    }

    @Override
    public void deleteAddressField(String id) {
        if(NetworkUtils.isNetworkConnected(getApplicationContext())){
            presenter.requestDeleteAddress(appRepository.getLanguageCode(),id);
        }else{
            showError(R.string.no_internet_connection);
        }
    }

    @Override
    public void onItemViewClicked(MultiLocation multiLocation) {
        setResults(multiLocation);
    }


    private void showAddOrEditDialog(String title,String id){
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_add_address);
        dialog.setCanceledOnTouchOutside(false);
        /*Bind views*/
        RadioGroup radioGroup=dialog.findViewById(R.id.type_rg);
        RadioButton homeRb=dialog.findViewById(R.id.home_rb);
        RadioButton workRb=dialog.findViewById(R.id.work_rb);
        RadioButton otherRb=dialog.findViewById(R.id.other_rb);
        Button findLocation=dialog.findViewById(R.id.find_my_location);
        TextView titleTv=dialog.findViewById(R.id.title);
        edtLocation=dialog.findViewById(R.id.edt_location);
        EditText edtAddressInfo=dialog.findViewById(R.id.edt_address_info);
       // EditText edtAddressName=dialog.findViewById(R.id.edt_address_name);
       // TextView tvAddressName=dialog.findViewById(R.id.tv_address_name);
        Button saveBtn=dialog.findViewById(R.id.save_btn);
        Button cancelBtn=dialog.findViewById(R.id.cancel_btn);
        titleTv.setText(title);

        findLocation.setOnClickListener(v-> passIntent());
        edtLocation.setOnClickListener(v-> passIntent());
        cancelBtn.setOnClickListener(v->dialog.dismiss());

        if(!id.isEmpty()){
            if(multiLocation.getLocType()==1){
                homeRb.setChecked(true);
            }else if(multiLocation.getLocType()==2){
                workRb.setChecked(true);
            }else{
                otherRb.setChecked(true);
            }
            edtLocation.setText(multiLocation.getLocLocation());
            edtAddressInfo.setText(multiLocation.getLocAddress());
            latitude=multiLocation.getLocLatitude();
            longitude=multiLocation.getLocLogitude();
            zipCode=multiLocation.getLocZipCode();
            addressType=String.valueOf(multiLocation.getLocType());
        }


        saveBtn.setOnClickListener(v->{
            if(edtLocation.getText().toString().isEmpty()){
               showToast(getString(R.string.enter_location));
            }else if(edtAddressInfo.getText().toString().isEmpty()){
                showToast(getString(R.string.enter_address_info));
            }else{
                MultiAddressRequest addressRequest=new MultiAddressRequest();
                addressRequest.setAddress_info(edtAddressInfo.getText().toString());
                addressRequest.setAddress_name("");
                addressRequest.setAddress_type(addressType);
                addressRequest.setEdit_id(id);
                addressRequest.setLang(appRepository.getLanguageCode());
                addressRequest.setLatitude(latitude);
                addressRequest.setLongitude(longitude);
                addressRequest.setZipcode(zipCode);
                addressRequest.setLocation(edtLocation.getText().toString());
                dialog.dismiss();
                presenter.requestToAddOrEditAddress(addressRequest);
            }
        });


        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            if(radioGroup1.getCheckedRadioButtonId()==R.id.other_rb){
                addressType=String.valueOf(OTHER_ADDRESS);
            }else{
                addressType=radioGroup1.getCheckedRadioButtonId()==R.id.home_rb?String.valueOf(HOME_ADDRESS):String.valueOf(WORK_ADDRESS);
            }
        });

        dialog.show();

    }


    private void passIntent(){
        Intent intent = new Intent(getApplicationContext(), NiboPlacePickerActivity.class);
        intent.putExtra("address","");
        intent.putExtra("cart_count","0");
        NiboPlacePickerActivity.NiboPlacePickerBuilder config = new NiboPlacePickerActivity.NiboPlacePickerBuilder()
                .setSearchBarTitle(getString(R.string.set_delivery_location))
                .setConfirmButtonTitle(getString(R.string.confirm_location))
                .setMarkerPinIconRes(R.drawable.ic_map_marker_def)
                .setStyleEnum(NiboStyle.HOPPER);
        setBuilder(config);
        startActivityForResult(intent, LOCATION_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == LOCATION_REQUEST_CODE) {
            NiboSelectedPlace selectedPlace = data.getParcelableExtra(NiboConstants.SELECTED_PLACE_RESULT);
            edtLocation.setText(selectedPlace.getPlaceAddress());
            latitude = String.valueOf(selectedPlace.getLatitude());
            longitude = String.valueOf(selectedPlace.getLongitude());
            zipCode = getZipCode(selectedPlace.getLatitude(),selectedPlace.getLongitude());
        }
    }

    String getZipCode(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            Address address=null;
            String addr="";
            String zipcode="";
            String city="";
            String state="";
            if (addresses != null && addresses.size() > 0){

                addr=addresses.get(0).getAddressLine(0)+"," +addresses.get(0).getSubAdminArea();
                city=addresses.get(0).getLocality();
                state=addresses.get(0).getAdminArea();

                for(int i=0 ;i<addresses.size();i++){
                    address = addresses.get(i);
                    if(address.getPostalCode()!=null){
                        zipcode=address.getPostalCode();
                        break;
                    }

                }
            }
            return zipcode;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    void setResults(MultiLocation multiLocation){
        Intent intent = new Intent();
        intent.putExtra(NiboConstants.SELECTED_PLACE_RESULT, multiLocation.getLocLocation()+"#"+multiLocation.getLocAddress());
        intent.putExtra(NiboConstants.NIBO_LATITUDE, multiLocation.getLocLatitude());
        intent.putExtra(NiboConstants.NIBO_LONGITUDE ,multiLocation.getLocLogitude());
        intent.putExtra(NiboConstants.NIBO_ADDRESS_TYPE ,multiLocation.getLocType());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_manage_address;
    }
}
