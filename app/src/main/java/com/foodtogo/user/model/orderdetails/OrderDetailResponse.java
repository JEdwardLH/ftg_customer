
package com.foodtogo.user.model.orderdetails;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailResponse implements Parcelable {

    @SerializedName("order_transaction_id")
    @Expose
    private String orderTransactionId;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("grand_sub_total")
    @Expose
    private String grandSubTotal;
    @SerializedName("grand_tax_total")
    @Expose
    private String grandTaxTotal;
    @SerializedName("delivery_fee")
    @Expose
    private String grandDeliveryFee;
    @SerializedName("wallet_used")
    @Expose
    private String walletUsed;
    @SerializedName("offer_used")
    @Expose
    private String offerUsed;
    @SerializedName("cancelled_item_amount")
    @Expose
    private String cancelledItemAmount;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;

    @SerializedName("pending_details")
    @Expose
    private ArrayList<PendingDetail> pendingDetails = null;
    @SerializedName("fulfilled_details")
    @Expose
    private ArrayList<FulfilledDetail> fulfilledDetails = null;
    @SerializedName("cancelled_details")
    @Expose
    private ArrayList<CancelledDetail> cancelledDetails = null;

    protected OrderDetailResponse(Parcel in) {
        orderTransactionId = in.readString();
        currency = in.readString();
        grandSubTotal = in.readString();
        grandTaxTotal = in.readString();
        grandDeliveryFee = in.readString();
        walletUsed = in.readString();
        offerUsed = in.readString();
        cancelledItemAmount = in.readString();
        grandTotal = in.readString();
        pendingDetails = in.createTypedArrayList(PendingDetail.CREATOR);
        fulfilledDetails = in.createTypedArrayList(FulfilledDetail.CREATOR);
        cancelledDetails = in.createTypedArrayList(CancelledDetail.CREATOR);
    }

    public static final Creator<OrderDetailResponse> CREATOR = new Creator<OrderDetailResponse>() {
        @Override
        public OrderDetailResponse createFromParcel(Parcel in) {
            return new OrderDetailResponse(in);
        }

        @Override
        public OrderDetailResponse[] newArray(int size) {
            return new OrderDetailResponse[size];
        }
    };

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public String getOrderTransactionId() {
        return orderTransactionId;
    }

    public String getGrandSubTotal() {
        return grandSubTotal;
    }

    public void setGrandSubTotal(String grandSubTotal) {
        this.grandSubTotal = grandSubTotal;
    }

    public String getGrandTaxTotal() {
        return grandTaxTotal;
    }

    public void setGrandTaxTotal(String grandTaxTotal) {
        this.grandTaxTotal = grandTaxTotal;
    }

    public String getGrandDeliveryFee() {
        return grandDeliveryFee;
    }

    public void setGrandDeliveryFee(String grandDeliveryFee) {
        this.grandDeliveryFee = grandDeliveryFee;
    }

    public String getWalletUsed() {
        return walletUsed;
    }

    public void setWalletUsed(String walletUsed) {
        this.walletUsed = walletUsed;
    }

    public String getOfferUsed() {
        return offerUsed;
    }

    public void setOfferUsed(String offerUsed) {
        this.offerUsed = offerUsed;
    }

    public String getCancelledItemAmount() {
        return cancelledItemAmount;
    }

    public void setCancelledItemAmount(String cancelledItemAmount) {
        this.cancelledItemAmount = cancelledItemAmount;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }


    public void setOrderTransactionId(String orderTransactionId) {
        this.orderTransactionId = orderTransactionId;
    }

    public ArrayList<PendingDetail> getPendingDetails() {
        return pendingDetails;
    }

    public void setPendingDetails(ArrayList<PendingDetail> pendingDetails) {
        this.pendingDetails = pendingDetails;
    }

    public ArrayList<FulfilledDetail> getFulfilledDetails() {
        return fulfilledDetails;
    }

    public void setFulfilledDetails(ArrayList<FulfilledDetail> fulfilledDetails) {
        this.fulfilledDetails = fulfilledDetails;
    }

    public ArrayList<CancelledDetail> getCancelledDetails() {
        return cancelledDetails;
    }

    public void setCancelledDetails(ArrayList<CancelledDetail> cancelledDetails) {
        this.cancelledDetails = cancelledDetails;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(orderTransactionId);
        parcel.writeString(currency);
        parcel.writeString(grandSubTotal);
        parcel.writeString(grandTaxTotal);
        parcel.writeString(grandDeliveryFee);
        parcel.writeString(walletUsed);
        parcel.writeString(offerUsed);
        parcel.writeString(cancelledItemAmount);
        parcel.writeString(grandTotal);
        parcel.writeTypedList(pendingDetails);
        parcel.writeTypedList(fulfilledDetails);
        parcel.writeTypedList(cancelledDetails);
    }
}
