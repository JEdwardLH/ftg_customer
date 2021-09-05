package com.foodtogo.user.model.itemdetails;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemReviews implements Parcelable {

    public String getReviewCustomerName() {
        return reviewCustomerName;
    }

    public void setReviewCustomerName(String reviewCustomerName) {
        this.reviewCustomerName = reviewCustomerName;
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

    public String getReviewCustomerProfile() {
        return reviewCustomerProfile;
    }

    public void setReviewCustomerProfile(String reviewCustomerProfile) {
        this.reviewCustomerProfile = reviewCustomerProfile;
    }

    @SerializedName("review_customer_name")
    @Expose
    private String reviewCustomerName;
    @SerializedName("review_comments")
    @Expose
    private String reviewComments;
    @SerializedName("review_rating")
    @Expose
    private Integer reviewRating;
    @SerializedName("review_customer_profile")
    @Expose
    private String reviewCustomerProfile;


    public ItemReviews() {

    }


    protected ItemReviews(Parcel in) {
        reviewCustomerName = in.readString();
        reviewComments = in.readString();
        if (in.readByte() == 0) {
            reviewRating = null;
        } else {
            reviewRating = in.readInt();
        }
        reviewCustomerProfile = in.readString();
    }

    public static final Creator<ItemReviews> CREATOR = new Creator<ItemReviews>() {
        @Override
        public ItemReviews createFromParcel(Parcel in) {
            return new ItemReviews(in);
        }

        @Override
        public ItemReviews[] newArray(int size) {
            return new ItemReviews[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reviewCustomerName);
        dest.writeString(reviewComments);
        if (reviewRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(reviewRating);
        }
        dest.writeString(reviewCustomerProfile);
    }
}
