
package com.foodtogo.user.model.orderdetails;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FulfilledDetail implements Parcelable {

    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("item_amount")
    @Expose
    private String itemAmount;
    @SerializedName("item_currency")
    @Expose
    private String itemCurrency;
    @SerializedName("item_image")
    @Expose
    private String itemImage;
    @SerializedName("restaurant_id")
    @Expose
    private Integer restaurantId;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("can_order_review")
    @Expose
    private String canOrderReview;
    @SerializedName("already_res_reviewed")
    @Expose
    private String alreadyRestaurantReviewed;
    @SerializedName("has_choice")
    @Expose
    private String hasChoice;
    @SerializedName("choice_list")
    @Expose
    private List<ChoiceList> choiceList = null;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("pre_order_date")
    @Expose
    private String preOrderDate;
    @SerializedName("delivery_id")
    @Expose
    private String deliveryId;
    @SerializedName("already_order_reviewed")
    @Expose
    private String alreadyOrderReviewed;
    @SerializedName("already_item_reviewed")
    @Expose
    private String alreadyItemReviewed;
    @SerializedName("self_pickup")
    @Expose
    private String selfPickup;


    public boolean isShowHeader() {
        return isShowHeader;
    }

    public void setShowHeader(boolean showHeader) {
        isShowHeader = showHeader;
    }

    private boolean isShowHeader;


    protected FulfilledDetail(Parcel in) {
        itemName = in.readString();
        if (in.readByte() == 0) {
            itemId = null;
        } else {
            itemId = in.readInt();
        }
        itemAmount = in.readString();
        itemCurrency = in.readString();
        itemImage = in.readString();
        if (in.readByte() == 0) {
            restaurantId = null;
        } else {
            restaurantId = in.readInt();
        }
        restaurantName = in.readString();
        orderStatus = in.readString();
        canOrderReview = in.readString();
        alreadyRestaurantReviewed = in.readString();
        alreadyOrderReviewed= in.readString();
        alreadyItemReviewed = in.readString();
        hasChoice = in.readString();
        if (in.readByte() == 0) {
            orderId = null;
        } else {
            orderId = in.readInt();
        }
        orderDate = in.readString();
        preOrderDate = in.readString();
        deliveryId = in.readString();
        selfPickup = in.readString();
    }

    public static final Creator<FulfilledDetail> CREATOR = new Creator<FulfilledDetail>() {
        @Override
        public FulfilledDetail createFromParcel(Parcel in) {
            return new FulfilledDetail(in);
        }

        @Override
        public FulfilledDetail[] newArray(int size) {
            return new FulfilledDetail[size];
        }
    };

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getItemCurrency() {
        return itemCurrency;
    }

    public String getCanOrderReview() {
        return canOrderReview;
    }

    public void setCanOrderReview(String canOrderReview) {
        this.canOrderReview = canOrderReview;
    }

    public void setItemCurrency(String itemCurrency) {
        this.itemCurrency = itemCurrency;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getHasChoice() {
        return hasChoice;
    }

    public void setHasChoice(String hasChoice) {
        this.hasChoice = hasChoice;
    }

    public List<ChoiceList> getChoiceList() {
        return choiceList;
    }

    public void setChoiceList(List<ChoiceList> choiceList) {
        this.choiceList = choiceList;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPreOrderDate() {
        return preOrderDate;
    }

    public void setPreOrderDate(String preOrderDate) {
        this.preOrderDate = preOrderDate;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getAlreadyRestaurantReviewed() {
        return alreadyRestaurantReviewed;
    }

    public void setAlreadyRestaurantReviewed(String alreadyRestaurantReviewed) {
        this.alreadyRestaurantReviewed = alreadyRestaurantReviewed;
    }

    public String getAlreadyOrderReviewed() {
        return alreadyOrderReviewed;
    }

    public void setAlreadyOrderReviewed(String alreadyOrderReviewed) {
        this.alreadyOrderReviewed = alreadyOrderReviewed;
    }

    public String getAlreadyItemReviewed() {
        return alreadyItemReviewed;
    }

    public void setAlreadyItemReviewed(String alreadyItemReviewed) {
        this.alreadyItemReviewed = alreadyItemReviewed;
    }

    public String getSelfPickup() {
        return selfPickup;
    }

    public void setSelfPickup(String selfPickup) {
        this.selfPickup = selfPickup;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        if (itemId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(itemId);
        }
        dest.writeString(itemAmount);
        dest.writeString(itemCurrency);
        dest.writeString(itemImage);
        if (restaurantId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(restaurantId);
        }
        dest.writeString(restaurantName);
        dest.writeString(orderStatus);
        dest.writeString(alreadyRestaurantReviewed);
        dest.writeString(alreadyOrderReviewed);
        dest.writeString(alreadyItemReviewed);
        dest.writeString(hasChoice);
        if (orderId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(orderId);
        }
        dest.writeString(orderDate);
        dest.writeString(preOrderDate);
        dest.writeString(deliveryId);
        dest.writeString(selfPickup);
        dest.writeString(canOrderReview);
    }
}
