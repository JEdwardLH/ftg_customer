package com.foodtogo.user.model.payment;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponList implements Parcelable {

    @SerializedName("coupon_name")
    @Expose
    private String  couponName;

    @SerializedName("coupon_code")
    @Expose
    private String  couponCode;

    @SerializedName("coupon_id")
    @Expose
    private String  couponId;

    @SerializedName("coupon_price")
    @Expose
    private String  couponPrice;

    public String getCouponCurrency() {
        return couponCurrency;
    }

    public void setCouponCurrency(String couponCurrency) {
        this.couponCurrency = couponCurrency;
    }

    @SerializedName("currency")
    @Expose
    private String  couponCurrency;

    protected CouponList(Parcel in) {
        couponName = in.readString();
        couponCode = in.readString();
        couponId = in.readString();
        couponPrice = in.readString();
        couponCurrency = in.readString();
    }

    public static final Creator<CouponList> CREATOR = new Creator<CouponList>() {
        @Override
        public CouponList createFromParcel(Parcel in) {
            return new CouponList(in);
        }

        @Override
        public CouponList[] newArray(int size) {
            return new CouponList[size];
        }
    };

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }


    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(String couponPrice) {
        this.couponPrice = couponPrice;
    }
    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(couponName);
        dest.writeString(couponCode);
        dest.writeString(couponId);
        dest.writeString(couponPrice);
        dest.writeString(couponCurrency);
    }
}
