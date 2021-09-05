package com.foodtogo.user.model.wallet;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reward implements Parcelable {

    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("added_date")
    @Expose
    private String addedDate;

    @SerializedName("order_id")
    @Expose
    private String orderId;

    @SerializedName("actioned_on")
    @Expose
    private String actionedOn;
    @SerializedName("rewarded_amount")
    @Expose
    private String rewardAmount;
    public Reward() {

    }

    public Reward(Parcel in) {
        desc = in.readString();
        points = in.readString();
        rewardAmount = in.readString();
        addedDate = in.readString();
        actionedOn = in.readString();
        orderId = in.readString();
    }

    public static final Creator<Reward> CREATOR = new Creator<Reward>() {
        @Override
        public Reward createFromParcel(Parcel in) {
            return new Reward(in);
        }

        @Override
        public Reward[] newArray(int size) {
            return new Reward[size];
        }
    };

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getWalletAmt() {
        return rewardAmount;
    }

    public void setWalletAmt(String walletAmt) {
        this.rewardAmount = walletAmt;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }
    public String getActionedOn() {
        return actionedOn;
    }

    public void setActionedOn(String actionedOn) {
        this.actionedOn = actionedOn;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(desc);
        dest.writeString(points);
        dest.writeString(rewardAmount);
        dest.writeString(addedDate);
        dest.writeString(actionedOn);
        dest.writeString(orderId);
    }
}
