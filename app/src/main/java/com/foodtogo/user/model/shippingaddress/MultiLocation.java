package com.foodtogo.user.model.shippingaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MultiLocation implements Serializable {
    @SerializedName("loc_ic")
    @Expose
    private Integer locIc;
    @SerializedName("loc_type")
    @Expose
    private Integer locType;
    @SerializedName("loc_location")
    @Expose
    private String locLocation;
    @SerializedName("loc_address")
    @Expose
    private String locAddress;
    @SerializedName("loc_latitude")
    @Expose
    private String locLatitude;
    @SerializedName("loc_logitude")
    @Expose
    private String locLogitude;
    @SerializedName("loc_address_name")
    @Expose
    private String locAddressName;
    @SerializedName("loc_zipcode")
    @Expose
    private String locZipCode;

    public String getLocZipCode() {
        return locZipCode;
    }

    public Integer getLocIc() {
        return locIc;
    }

    public void setLocIc(Integer locIc) {
        this.locIc = locIc;
    }

    public Integer getLocType() {
        return locType;
    }

    public void setLocType(Integer locType) {
        this.locType = locType;
    }

    public String getLocLocation() {
        return locLocation;
    }

    public void setLocLocation(String locLocation) {
        this.locLocation = locLocation;
    }

    public String getLocAddress() {
        return locAddress;
    }

    public void setLocAddress(String locAddress) {
        this.locAddress = locAddress;
    }

    public String getLocLatitude() {
        return locLatitude;
    }

    public void setLocLatitude(String locLatitude) {
        this.locLatitude = locLatitude;
    }

    public String getLocLogitude() {
        return locLogitude;
    }

    public void setLocLogitude(String locLogitude) {
        this.locLogitude = locLogitude;
    }

    public String getLocAddressName() {
        return locAddressName;
    }

    public void setLocAddressName(String locAddressName) {
        this.locAddressName = locAddressName;
    }
}
