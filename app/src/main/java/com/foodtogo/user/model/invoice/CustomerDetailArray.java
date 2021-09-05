
package com.foodtogo.user.model.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDetailArray {

    @SerializedName("customeName")
    @Expose
    private String customeName;
    @SerializedName("customerAddress1")
    @Expose
    private String customerAddress1;
    @SerializedName("customerAddress2")
    @Expose
    private String customerAddress2;
    @SerializedName("customerMobile")
    @Expose
    private String customerMobile;
    @SerializedName("customerEmail")
    @Expose
    private String customerEmail;

    public String getCustomeName() {
        return customeName;
    }

    public void setCustomeName(String customeName) {
        this.customeName = customeName;
    }

    public String getCustomerAddress1() {
        return customerAddress1;
    }

    public void setCustomerAddress1(String customerAddress1) {
        this.customerAddress1 = customerAddress1;
    }

    public String getCustomerAddress2() {
        return customerAddress2;
    }

    public void setCustomerAddress2(String customerAddress2) {
        this.customerAddress2 = customerAddress2;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

}
