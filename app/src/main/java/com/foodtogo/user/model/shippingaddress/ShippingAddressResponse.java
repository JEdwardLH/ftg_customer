package com.foodtogo.user.model.shippingaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingAddressResponse {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("sh_cus_fname")
    @Expose
    private String shCusFname;
    @SerializedName("sh_cus_lname")
    @Expose
    private String shCusLname;
    @SerializedName("sh_cus_email")
    @Expose
    private String shCusEmail;
    @SerializedName("sh_phone1")
    @Expose
    private String shPhone1;
    @SerializedName("sh_phone2")
    @Expose
    private String shPhone2;
    @SerializedName("ship_ph1_cnty_code")
    @Expose
    private String countryCode;

    @SerializedName("ship_ph2_cnty_code")
    @Expose
    private String subCountryCode;
    @SerializedName("ship_ph1_no_only")
    @Expose
    private String shPhone1Only;
    @SerializedName("ship_ph2_no_only")
    @Expose
    private String shPhone2Only;
    @SerializedName("sh_location")
    @Expose
    private String shLocation;
    @SerializedName("sh_latitude")
    @Expose
    private String shLatitude;
    @SerializedName("sh_longitude")
    @Expose
    private String shLongitude;
    @SerializedName("sh_zipcode")
    @Expose
    private String shZipcode;
    @SerializedName("self_pickup_status")
    @Expose
    private String selfPickupStatus;

    public String getShLocation1() {
        return shLocation1;
    }

    public void setShLocation1(String shLocation1) {
        this.shLocation1 = shLocation1;
    }

    @SerializedName("sh_location1")
    @Expose
    private String shLocation1;

    @SerializedName("email_verification_status")
    @Expose
    private String emailVerificationStatus;

    public String getCountryCode() {
        return countryCode;
    }

    public String getShPhone1Only() {
        return shPhone1Only;
    }

    public String getShPhone2Only() {
        return shPhone2Only;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getShCusFname() {
        return shCusFname;
    }

    public void setShCusFname(String shCusFname) {
        this.shCusFname = shCusFname;
    }

    public String getShCusLname() {
        return shCusLname;
    }

    public void setShCusLname(String shCusLname) {
        this.shCusLname = shCusLname;
    }

    public String getShCusEmail() {
        return shCusEmail;
    }

    public void setShCusEmail(String shCusEmail) {
        this.shCusEmail = shCusEmail;
    }

    public String getShPhone1() {
        return shPhone1;
    }

    public void setShPhone1(String shPhone1) {
        this.shPhone1 = shPhone1;
    }

    public String getShPhone2() {
        return shPhone2;
    }

    public void setShPhone2(String shPhone2) {
        this.shPhone2 = shPhone2;
    }

    public String getShLocation() {
        return shLocation;
    }

    public void setShLocation(String shLocation) {
        this.shLocation = shLocation;
    }

    public String getShLatitude() {
        return shLatitude;
    }

    public void setShLatitude(String shLatitude) {
        this.shLatitude = shLatitude;
    }

    public String getShLongitude() {
        return shLongitude;
    }

    public void setShLongitude(String shLongitude) {
        this.shLongitude = shLongitude;
    }

    public String getShZipcode() {
        return shZipcode;
    }

    public void setShZipcode(String shZipcode) {
        this.shZipcode = shZipcode;
    }

    public String getSelfPickupStatus() {
        return selfPickupStatus;
    }

    public void setSelfPickupStatus(String selfPickupStatus) {
        this.selfPickupStatus = selfPickupStatus;
    }
    public String getEmailVerificationStatus() {
        return emailVerificationStatus;
    }

    public void setEmailVerificationStatus(String emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
    }
    public String getSubCountryCode() {
        return subCountryCode;
    }

    public void setSubCountryCode(String subCountryCode) {
        this.subCountryCode = subCountryCode;
    }



}

