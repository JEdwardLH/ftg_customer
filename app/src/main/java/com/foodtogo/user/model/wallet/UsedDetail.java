
package com.foodtogo.user.model.wallet;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsedDetail implements Parcelable {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("used_amount")
    @Expose
    private String usedAmount;
    @SerializedName("ord_currency")
    @Expose
    private String ordCurrency;

    public UsedDetail (){

    }
    public UsedDetail(Parcel in) {
        orderId = in.readString();
        orderDate = in.readString();
        usedAmount = in.readString();
        ordCurrency = in.readString();
        type = in.readString();
    }

    public static final Creator<UsedDetail> CREATOR = new Creator<UsedDetail>() {
        @Override
        public UsedDetail createFromParcel(Parcel in) {
            return new UsedDetail(in);
        }

        @Override
        public UsedDetail[] newArray(int size) {
            return new UsedDetail[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @SerializedName("type")
    @Expose
    private String type;



    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(String usedAmount) {
        this.usedAmount = usedAmount;
    }

    public String getOrdCurrency() {
        return ordCurrency;
    }

    public void setOrdCurrency(String ordCurrency) {
        this.ordCurrency = ordCurrency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(orderDate);
        dest.writeString(usedAmount);
        dest.writeString(ordCurrency);
        dest.writeString(type);
    }
}
