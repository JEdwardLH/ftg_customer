
package com.foodtogo.user.model.myreviews;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemReviewList implements Parcelable {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("item_title")
    @Expose
    private String itemTitle;
    @SerializedName("item_image")
    @Expose
    private String itemImage;
    @SerializedName("review_comments")
    @Expose
    private String reviewComments;
    @SerializedName("review_rating")
    @Expose
    private Integer reviewRating;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    @SerializedName("review_status")
    @Expose
    private int reviewStatus;

    public ItemReviewList() {

    }

    protected ItemReviewList(Parcel in) {
        if (in.readByte() == 0) {
            productId = null;
        } else {
            productId = in.readInt();
        }
        itemTitle = in.readString();
        itemImage = in.readString();
        reviewComments = in.readString();
        if (in.readByte() == 0) {
            reviewRating = null;
        } else {
            reviewRating = in.readInt();
        }
        shopName = in.readString();
        createdDate = in.readString();
        reviewStatus=in.readInt();
    }

    public int getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(int reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public static final Creator<ItemReviewList> CREATOR = new Creator<ItemReviewList>() {
        @Override
        public ItemReviewList createFromParcel(Parcel in) {
            return new ItemReviewList(in);
        }

        @Override
        public ItemReviewList[] newArray(int size) {
            return new ItemReviewList[size];
        }
    };

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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
        if (productId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(productId);
        }
        dest.writeString(itemTitle);
        dest.writeString(itemImage);
        dest.writeString(reviewComments);
        dest.writeInt(reviewStatus);
        if (reviewRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(reviewRating);
        }
        dest.writeString(shopName);
        dest.writeString(createdDate);
    }
}
