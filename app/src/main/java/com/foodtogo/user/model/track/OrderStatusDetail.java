
package com.foodtogo.user.model.track;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderStatusDetail {

    @SerializedName("ord_stage")
    @Expose
    private Integer ordStage;
    @SerializedName("ord_title")
    @Expose
    private String ordTitle;
    @SerializedName("ord_timing")
    @Expose
    private String ordTiming;
    @SerializedName("stage_completed")
    @Expose
    private String stageCompleted;

    public Integer getOrdStage() {
        return ordStage;
    }

    public void setOrdStage(Integer ordStage) {
        this.ordStage = ordStage;
    }

    public String getOrdTitle() {
        return ordTitle;
    }

    public void setOrdTitle(String ordTitle) {
        this.ordTitle = ordTitle;
    }

    public String getOrdTiming() {
        return ordTiming;
    }

    public void setOrdTiming(String ordTiming) {
        this.ordTiming = ordTiming;
    }

    public String getStageCompleted() {
        return stageCompleted;
    }

    public void setStageCompleted(String stageCompleted) {
        this.stageCompleted = stageCompleted;
    }

}
