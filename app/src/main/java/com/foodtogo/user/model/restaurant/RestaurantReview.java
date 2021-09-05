
package com.foodtogo.user.model.restaurant;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantReview implements Parcelable {

    @SerializedName("review_customer_name")
    @Expose
    private String reviewCustomerName;
    @SerializedName("review_customer_profile")
    @Expose
    private String reviewCustomerProfile;
    @SerializedName("review_comments")
    @Expose
    private String reviewComments;
    @SerializedName("review_rating")
    @Expose
    private Integer reviewRating;

    public RestaurantReview (){

    }


    protected RestaurantReview(Parcel in) {
        reviewCustomerName = in.readString();
        reviewCustomerProfile = in.readString();
        reviewComments = in.readString();
        if (in.readByte() == 0) {
            reviewRating = null;
        } else {
            reviewRating = in.readInt();
        }
    }

    public static final Creator<RestaurantReview> CREATOR = new Creator<RestaurantReview>() {
        @Override
        public RestaurantReview createFromParcel(Parcel in) {
            return new RestaurantReview(in);
        }

        @Override
        public RestaurantReview[] newArray(int size) {
            return new RestaurantReview[size];
        }
    };

    public String getReviewCustomerName() {
        return reviewCustomerName;
    }

    public void setReviewCustomerName(String reviewCustomerName) {
        this.reviewCustomerName = reviewCustomerName;
    }

    public String getReviewCustomerProfile() {
        return reviewCustomerProfile;
    }

    public void setReviewCustomerProfile(String reviewCustomerProfile) {
        this.reviewCustomerProfile = reviewCustomerProfile;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reviewCustomerName);
        dest.writeString(reviewCustomerProfile);
        dest.writeString(reviewComments);
        if (reviewRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(reviewRating);
        }
    }
}
