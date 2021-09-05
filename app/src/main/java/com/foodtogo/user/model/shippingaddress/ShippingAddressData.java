package com.foodtogo.user.model.shippingaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShippingAddressData {

    @SerializedName("shipping_address")
    @Expose
    private ShippingAddressResponse shippingAddress;
    @SerializedName("multi_location")
    @Expose
    private List<MultiLocation> multiLocation = null;

    public ShippingAddressResponse getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddressResponse shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<MultiLocation> getMultiLocation() {
        return multiLocation;
    }

    public void setMultiLocation(List<MultiLocation> multiLocation) {
        this.multiLocation = multiLocation;
    }
}
