
package com.foodtogo.user.model.myreviews;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestReviewList implements Parcelable {

    public RestReviewList() {

    }

    @SerializedName("res_store_id")
    @Expose
    private Integer resStoreId;
    @SerializedName("restaurant_image")
    @Expose
    private String restaurantImage;
    @SerializedName("review_comments")
    @Expose
    private String reviewComments;
    @SerializedName("review_rating")
    @Expose
    private Integer reviewRating;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("review_status")
    @Expose
    private int reviewStatus;

    protected RestReviewList(Parcel in) {
        if (in.readByte() == 0) {
            resStoreId = null;
        } else {
            resStoreId = in.readInt();
        }
        reviewStatus=in.readInt();
        restaurantImage = in.readString();
        reviewComments = in.readString();
        if (in.readByte() == 0) {
            reviewRating = null;
        } else {
            reviewRating = in.readInt();
        }
        restaurantName = in.readString();
        createdDate = in.readString();
    }

    public static final Creator<RestReviewList> CREATOR = new Creator<RestReviewList>() {
        @Override
        public RestReviewList createFromParcel(Parcel in) {
            return new RestReviewList(in);
        }

        @Override
        public RestReviewList[] newArray(int size) {
            return new RestReviewList[size];
        }
    };

    public int getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(int reviewStatus) {
        this.reviewStatus = reviewStatus;
    }


    public Integer getResStoreId() {
        return resStoreId;
    }

    public void setResStoreId(Integer resStoreId) {
        this.resStoreId = resStoreId;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
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

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
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
        if (resStoreId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(resStoreId);
        }
        dest.writeString(restaurantImage);
        dest.writeString(reviewComments);
        dest.writeInt(reviewStatus);
        if (reviewRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(reviewRating);
        }
        dest.writeString(restaurantName);
        dest.writeString(createdDate);
    }
}
