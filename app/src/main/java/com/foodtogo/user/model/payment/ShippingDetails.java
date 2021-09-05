
package com.foodtogo.user.model.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingDetails {

    @SerializedName("ship_cus_name")
    @Expose
    private String shipCusName;
    @SerializedName("ship_cus_address")
    @Expose
    private String shipCusAddress;
    @SerializedName("ship_cus_phone1")
    @Expose
    private String shipCusPhone1;
    @SerializedName("ship_cus_phone2")
    @Expose
    private String shipCusPhone2;
    @SerializedName("ship_cus_mail")
    @Expose
    private String shipCusMail;

    public String getShipCusName() {
        return shipCusName;
    }

    public void setShipCusName(String shipCusName) {
        this.shipCusName = shipCusName;
    }

    public String getShipCusAddress() {
        return shipCusAddress;
    }

    public void setShipCusAddress(String shipCusAddress) {
        this.shipCusAddress = shipCusAddress;
    }

    public String getShipCusPhone1() {
        return shipCusPhone1;
    }

    public void setShipCusPhone1(String shipCusPhone1) {
        this.shipCusPhone1 = shipCusPhone1;
    }

    public String getShipCusPhone2() {
        return shipCusPhone2;
    }

    public void setShipCusPhone2(String shipCusPhone2) {
        this.shipCusPhone2 = shipCusPhone2;
    }

    public String getShipCusMail() {
        return shipCusMail;
    }

    public void setShipCusMail(String shipCusMail) {
        this.shipCusMail = shipCusMail;
    }

}
