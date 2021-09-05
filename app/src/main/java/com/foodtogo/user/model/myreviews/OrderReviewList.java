
package com.foodtogo.user.model.myreviews;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderReviewList implements Parcelable {

    public OrderReviewList(){

    }

    @SerializedName("delivery_person_name")
    @Expose
    private String deliveryPersonName;
    @SerializedName("review_comments")
    @Expose
    private String reviewComments;
    @SerializedName("review_rating")
    @Expose
    private Integer reviewRating;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("review_status")
    @Expose
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    protected OrderReviewList(Parcel in) {
        deliveryPersonName = in.readString();
        reviewComments = in.readString();
        status=in.readInt();
        if (in.readByte() == 0) {
            reviewRating = null;
        } else {
            reviewRating = in.readInt();
        }
        createdDate = in.readString();
    }

    public static final Creator<OrderReviewList> CREATOR = new Creator<OrderReviewList>() {
        @Override
        public OrderReviewList createFromParcel(Parcel in) {
            return new OrderReviewList(in);
        }

        @Override
        public OrderReviewList[] newArray(int size) {
            return new OrderReviewList[size];
        }
    };

    public String getDeliveryPersonName() {
        return deliveryPersonName;
    }

    public void setDeliveryPersonName(String deliveryPersonName) {
        this.deliveryPersonName = deliveryPersonName;
    }

    public String getReviewComments() {
        return reviewComments;
    }

    public void setReviewComments(String reviewComments) {
        this.reviewComments = reviewComments;
    }

    public Integer getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(Integer reviewRating) {
        this.reviewRating = reviewRating;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deliveryPersonName);
        dest.writeString(reviewComments);
        dest.writeInt(status);
        if (reviewRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(reviewRating);
        }
        dest.writeString(createdDate);
    }
}
