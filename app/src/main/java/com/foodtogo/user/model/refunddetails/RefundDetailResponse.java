package com.foodtogo.user.model.refunddetails;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefundDetailResponse implements Parcelable {

    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_has_choice")
    @Expose
    private String itemHasChoice;
    @SerializedName("choice_array")
    @Expose
    private List<Object> choiceArray = null;
    @SerializedName("order_currency")
    @Expose
    private String orderCurrency;
    @SerializedName("order_total")
    @Expose
    private String orderTotal;
    @SerializedName("commission")
    @Expose
    private String commission;
    @SerializedName("cancel_type")
    @Expose
    private String cancelType;
    @SerializedName("refund_amount")
    @Expose
    private String refundAmount;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("refund_date")
    @Expose
    private String refundDate;
    @SerializedName("refund_status")
    @Expose
    private String refundStatus;
    @SerializedName("offer_amt")
    @Expose
    private String offerAmount;
    @SerializedName("del_fee")
    @Expose
    private String deliveryFee;

    protected RefundDetailResponse(Parcel in) {
        storeName = in.readString();
        itemName = in.readString();
        itemHasChoice = in.readString();
        orderCurrency = in.readString();
        orderTotal = in.readString();
        commission = in.readString();
        cancelType = in.readString();
        refundAmount = in.readString();
        transactionId = in.readString();
        refundDate = in.readString();
        refundStatus = in.readString();
        offerAmount = in.readString();
        deliveryFee = in.readString();
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
    public static final Creator<RefundDetailResponse> CREATOR = new Creator<RefundDetailResponse>() {
        @Override
        public RefundDetailResponse createFromParcel(Parcel in) {
            return new RefundDetailResponse(in);
        }

        @Override
        public RefundDetailResponse[] newArray(int size) {
            return new RefundDetailResponse[size];
        }
    };

    public String getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(String offerAmount) {
        this.offerAmount = offerAmount;
    }


    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemHasChoice() {
        return itemHasChoice;
    }

    public void setItemHasChoice(String itemHasChoice) {
        this.itemHasChoice = itemHasChoice;
    }

    public List<Object> getChoiceArray() {
        return choiceArray;
    }

    public void setChoiceArray(List<Object> choiceArray) {
        this.choiceArray = choiceArray;
    }

    public String getOrderCurrency() {
        return orderCurrency;
    }

    public void setOrderCurrency(String orderCurrency) {
        this.orderCurrency = orderCurrency;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getCancelType() {
        return cancelType;
    }

    public void setCancelType(String cancelType) {
        this.cancelType = cancelType;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(storeName);
        dest.writeString(itemName);
        dest.writeString(itemHasChoice);
        dest.writeString(orderCurrency);
        dest.writeString(orderTotal);
        dest.writeString(commission);
        dest.writeString(cancelType);
        dest.writeString(refundAmount);
        dest.writeString(transactionId);
        dest.writeString(refundDate);
        dest.writeString(refundStatus);
        dest.writeString(deliveryFee);
        dest.writeString(offerAmount);
    }
}
