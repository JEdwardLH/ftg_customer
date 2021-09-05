
package com.foodtogo.user.model.track;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryPersonDetails {

    @SerializedName("deliver_assigned")
    @Expose
    private String deliverAssigned;
    @SerializedName("deliver_id")
    @Expose
    private Integer deliverId;
    @SerializedName("deliver_name")
    @Expose
    private String deliverName;
    @SerializedName("deliver_mobile")
    @Expose
    private String deliverMobile;
    @SerializedName("deliver_image")
    @Expose
    private String deliverImage;
    @SerializedName("deliver_latitude")
    @Expose
    private String deliverLatitude;
    @SerializedName("deliver_longitude")
    @Expose
    private String deliverLongitude;

    public String getDeliverAssigned() {
        return deliverAssigned;
    }

    public void setDeliverAssigned(String deliverAssigned) {
        this.deliverAssigned = deliverAssigned;
    }

    public Integer getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(Integer deliverId) {
        this.deliverId = deliverId;
    }

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }

    public String getDeliverMobile() {
        return deliverMobile;
    }

    public void setDeliverMobile(String deliverMobile) {
        this.deliverMobile = deliverMobile;
    }

    public String getDeliverImage() {
        return deliverImage;
    }

    public void setDeliverImage(String deliverImage) {
        this.deliverImage = deliverImage;
    }

    public String getDeliverLatitude() {
        return deliverLatitude;
    }

    public void setDeliverLatitude(String deliverLatitude) {
        this.deliverLatitude = deliverLatitude;
    }

    public String getDeliverLongitude() {
        return deliverLongitude;
    }

    public void setDeliverLongitude(String deliverLongitude) {
        this.deliverLongitude = deliverLongitude;
    }

}
