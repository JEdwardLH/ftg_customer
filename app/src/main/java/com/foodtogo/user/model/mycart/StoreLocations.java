package com.foodtogo.user.model.mycart;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreLocations implements Parcelable {

    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("store_location")
    @Expose
    private String storeLocation;

    protected StoreLocations(Parcel in) {
        storeName = in.readString();
        storeLocation = in.readString();
    }

    public static final Creator<StoreLocations> CREATOR = new Creator<StoreLocations>() {
        @Override
        public StoreLocations createFromParcel(Parcel in) {
            return new StoreLocations(in);
        }

        @Override
        public StoreLocations[] newArray(int size) {
            return new StoreLocations[size];
        }
    };

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(storeName);
        dest.writeString(storeLocation);
    }
}
