package com.foodtogo.user.model.track;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDetails {

    @SerializedName("cus_name")
    @Expose
    private String cusName;

    @SerializedName("cus_address")
    @Expose
    private String cusAddress;

    @SerializedName("cus_address1")
    @Expose
    private String cusAddress1;

    @SerializedName("cus_phone")
    @Expose
    private String cusPhone;

    @SerializedName("cus_latitude")
    @Expose
    private String cusLatitude;

    @SerializedName("cus_longitude")
    @Expose
    private String cusLongitude;

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getCusAddress1() {
        return cusAddress1;
    }

    public void setCusAddress1(String cusAddress1) {
        this.cusAddress1 = cusAddress1;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    public String getCusLatitude() {
        return cusLatitude;
    }

    public void setCusLatitude(String cusLatitude) {
        this.cusLatitude = cusLatitude;
    }

    public String getCusLongitude() {
        return cusLongitude;
    }

    public void setCusLongitude(String cusLongitude) {
        this.cusLongitude = cusLongitude;
    }




}
