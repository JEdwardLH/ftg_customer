
package com.foodtogo.user.model.orderdetails;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PendingDetail implements Parcelable {

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
    @SerializedName("already_reviewed")
    @Expose
    private String alreadyReviewed;
    @SerializedName("has_choice")
    @Expose
    private String hasChoice;
    @SerializedName("choice_list")
    @Expose
    private List<Object> choiceList = null;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("pre_order_date")
    @Expose
    private String preOrderDate;
    @SerializedName("cancel_status")
    @Expose
    private String cancelStatus;
    @SerializedName("cancel_policy")
    @Expose
    private String cancelPolicy;
    @SerializedName("refund_text")
    @Expose
    private String refundText;

    @SerializedName("cancel_info")
    @Expose
    private String cancelInfo;

    protected PendingDetail(Parcel in) {
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
        alreadyReviewed = in.readString();
        hasChoice = in.readString();
        cancelInfo=in.readString();
        if (in.readByte() == 0) {
            orderId = null;
        } else {
            orderId = in.readInt();
        }
        orderDate = in.readString();
        preOrderDate = in.readString();
        cancelStatus = in.readString();
        cancelPolicy = in.readString();
        refundText = in.readString();
        paymentStatus = in.readString();
        paymentStatusErr = in.readString();
        isShowHeader = in.readByte() != 0;
    }

    public static final Creator<PendingDetail> CREATOR = new Creator<PendingDetail>() {
        @Override
        public PendingDetail createFromParcel(Parcel in) {
            return new PendingDetail(in);
        }

        @Override
        public PendingDetail[] newArray(int size) {
            return new PendingDetail[size];
        }
    };

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentStatusErr() {
        return paymentStatusErr;
    }

    public void setPaymentStatusErr(String paymentStatusErr) {
        this.paymentStatusErr = paymentStatusErr;
    }

    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("payment_status_err")
    @Expose
    private String paymentStatusErr;
    private boolean isShowHeader;
    public String getCancelInfo() {
        return cancelInfo;
    }

    public void setCancelInfo(String cancelInfo) {
        this.cancelInfo = cancelInfo;
    }


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

    public String getAlreadyReviewed() {
        return alreadyReviewed;
    }

    public void setAlreadyReviewed(String alreadyReviewed) {
        this.alreadyReviewed = alreadyReviewed;
    }

    public String getHasChoice() {
        return hasChoice;
    }

    public void setHasChoice(String hasChoice) {
        this.hasChoice = hasChoice;
    }

    public List<Object> getChoiceList() {
        return choiceList;
    }

    public void setChoiceList(List<Object> choiceList) {
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

    public String getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(String cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public String getCancelPolicy() {
        return cancelPolicy;
    }

    public void setCancelPolicy(String cancelPolicy) {
        this.cancelPolicy = cancelPolicy;
    }

    public String getRefundText() {
        return refundText;
    }

    public void setRefundText(String refundText) {
        this.refundText = refundText;
    }


    public boolean isShowHeader() {
        return isShowHeader;
    }

    public void setShowHeader(boolean showHeader) {
        isShowHeader = showHeader;
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
        dest.writeString(alreadyReviewed);
        dest.writeString(hasChoice);
        if (orderId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(orderId);
        }
        dest.writeString(orderDate);
        dest.writeString(preOrderDate);
        dest.writeString(cancelStatus);
        dest.writeString(cancelPolicy);
        dest.writeString(refundText);
        dest.writeString(paymentStatus);
        dest.writeString(paymentStatusErr);
        dest.writeString(cancelInfo);
        dest.writeByte((byte) (isShowHeader ? 1 : 0));
    }
}
