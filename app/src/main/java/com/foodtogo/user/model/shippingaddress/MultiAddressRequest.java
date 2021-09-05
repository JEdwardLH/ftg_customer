package com.foodtogo.user.model.shippingaddress;

public class MultiAddressRequest {

   private String lang;
   private String address_type;
   private String location;
   private String address_info;
   private String latitude;
   private String longitude;
   private String address_name;
   private String edit_id;
   private String zipcode;

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getAddress_type() {
        return address_type;
    }

    public void setAddress_type(String address_type) {
        this.address_type = address_type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress_info() {
        return address_info;
    }

    public void setAddress_info(String address_info) {
        this.address_info = address_info;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getEdit_id() {
        return edit_id;
    }

    public void setEdit_id(String edit_id) {
        this.edit_id = edit_id;
    }


}
