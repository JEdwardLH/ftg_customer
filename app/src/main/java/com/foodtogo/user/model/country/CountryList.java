
package com.foodtogo.user.model.country;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryList {

    @SerializedName("country_details")
    @Expose
    private List<CountryDetail> countryDetails = null;

    @SerializedName("country_code_list")
    @Expose List<CountryDetail> allCountryCodeList=null;

    public List<CountryDetail> getAllCountryCodeList() {
        return allCountryCodeList;

    }
    public void setAllCountryCodeList(List<CountryDetail> allCountryCodeList) {
        this.allCountryCodeList = allCountryCodeList;
    }

    public List<CountryDetail> getCountryDetails() {
        return countryDetails;
    }

    public void setCountryDetails(List<CountryDetail> countryDetails) {
        this.countryDetails = countryDetails;
    }

}
