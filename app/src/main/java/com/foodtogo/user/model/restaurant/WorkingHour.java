package com.foodtogo.user.model.restaurant;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkingHour implements Parcelable {

    @SerializedName("available_status")
    @Expose
    private String availableStatus;
    @SerializedName("working_date")
    @Expose
    private String workingDate;
    @SerializedName("working_from_time")
    @Expose
    private String workingFromTime;
    @SerializedName("working_end_time")
    @Expose
    private String workingEndTime;

    protected WorkingHour(Parcel in) {
        availableStatus = in.readString();
        workingDate = in.readString();
        workingFromTime = in.readString();
        workingEndTime = in.readString();
    }

    public static final Creator<WorkingHour> CREATOR = new Creator<WorkingHour>() {
        @Override
        public WorkingHour createFromParcel(Parcel in) {
            return new WorkingHour(in);
        }

        @Override
        public WorkingHour[] newArray(int size) {
            return new WorkingHour[size];
        }
    };

    public String getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(String availableStatus) {
        this.availableStatus = availableStatus;
    }

    public String getWorkingDate() {
        return workingDate;
    }

    public void setWorkingDate(String workingDate) {
        this.workingDate = workingDate;
    }

    public String getWorkingFromTime() {
        return workingFromTime;
    }

    public void setWorkingFromTime(String workingFromTime) {
        this.workingFromTime = workingFromTime;
    }

    public String getWorkingEndTime() {
        return workingEndTime;
    }

    public void setWorkingEndTime(String workingEndTime) {
        this.workingEndTime = workingEndTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(availableStatus);
        dest.writeString(workingDate);
        dest.writeString(workingFromTime);
        dest.writeString(workingEndTime);
    }
}
