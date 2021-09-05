package com.alium.nibo.placepicker;

import android.os.Bundle;
import androidx.annotation.DrawableRes;
import androidx.annotation.RawRes;

import com.alium.nibo.R;
import com.alium.nibo.base.BaseNiboActivity;
import com.alium.nibo.utils.NiboStyle;


public class NiboPlacePickerActivity extends BaseNiboActivity {

    private static NiboPlacePickerBuilder mConfig = new NiboPlacePickerBuilder();

    public static void setBuilder(NiboPlacePickerBuilder config) {

        if (config == null) {
            throw new NullPointerException("Config cannot be passed null. Not setting config will use default values.");
        }

        mConfig = config;
    }

    public static String cartCount="";
    public static String address="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nibo_picker);
        if(getIntent()!=null){
            if(getIntent().hasExtra("cart_count")) {
                cartCount = getIntent().getStringExtra("cart_count");
            }
            address=getIntent().getStringExtra("address");
        }

//        new NiboPlacePickerBuilder()
//                .setSearchBarTitle("Search for an area")
//                .setConfirmButtonTitle("Picker here bish")
//                .setMarkerPinIconRes(R.drawable.ic_place)
//                .setStyleEnum(NiboStyle.CUSTOM)
//                .setStyleFileID(R.raw.retro)
//                .build()

        replaceFragment(mConfig.build(),
                this);

    }




    public static class NiboPlacePickerBuilder {
        private String searchBarTitle;
        private String confirmButtonTitle;
        private NiboStyle styleEnum;
        private
        @RawRes
        int styleFileID;
        private
        @DrawableRes
        int markerPinIconRes;




        public NiboPlacePickerBuilder setSearchBarTitle(String searchBarTitle) {
            this.searchBarTitle = searchBarTitle;
            return this;
        }

        public NiboPlacePickerBuilder setConfirmButtonTitle(String confirmButtonTitle) {
            this.confirmButtonTitle = confirmButtonTitle;
            return this;
        }

        public NiboPlacePickerBuilder setStyleEnum(NiboStyle styleEnum) {
            this.styleEnum = styleEnum;
            return this;
        }

        public NiboPlacePickerBuilder setStyleFileID(int styleFileID) {
            this.styleFileID = styleFileID;
            return this;
        }

        public NiboPlacePickerBuilder setMarkerPinIconRes(int markerPinIconRes) {
            this.markerPinIconRes = markerPinIconRes;
            return this;
        }


        public NiboPickerFragment build() {
            return NiboPickerFragment.newInstance(searchBarTitle, confirmButtonTitle, styleEnum,
                    styleFileID, markerPinIconRes,cartCount,address);
        }

    }


}
