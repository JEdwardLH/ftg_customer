package com.foodtogo.user.model.shippingaddress;

public class AddressRequest {

    private String lang;
    private String search_latitude;
    private String search_longitude;
    private String location;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    private String zipcode;



    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getSearch_latitude() {
        return search_latitude;
    }

    public void setSearch_latitude(String search_latitude) {
        this.search_latitude = search_latitude;
    }

    public String getSearch_longitude() {
        return search_longitude;
    }

    public void setSearch_longitude(String search_longitude) {
        this.search_longitude = search_longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
