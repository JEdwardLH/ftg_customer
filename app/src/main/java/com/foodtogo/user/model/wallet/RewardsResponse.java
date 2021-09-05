package com.foodtogo.user.model.wallet;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RewardsResponse {

    @SerializedName("total_points")
    @Expose
    private String totalPoints;
    @SerializedName("rewarded_points")
    @Expose
    private String rewardedPoints;
    @SerializedName("balance_points")
    @Expose
    private String balancePoints;
    @SerializedName("earned_points_history")
    @Expose
    private List<Reward> earnedPointsHistory = null;
    @SerializedName("rewarded_history")
    @Expose
    private List<Reward> rewardedHistory = null;

    public String getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getRewardedPoints() {
        return rewardedPoints;
    }

    public void setRewardedPoints(String rewardedPoints) {
        this.rewardedPoints = rewardedPoints;
    }

    public String getBalancePoints() {
        return balancePoints;
    }

    public void setBalancePoints(String balancePoints) {
        this.balancePoints = balancePoints;
    }

    public List<Reward> getEarnedPointsHistory() {
        return earnedPointsHistory;
    }

    public void setEarnedPointsHistory(List<Reward> earnedPointsHistory) {
        this.earnedPointsHistory = earnedPointsHistory;
    }

    public List<Reward> getRewardedHistory() {
        return rewardedHistory;
    }

    public void setRewardedHistory(List<Reward> rewardedHistory) {
        this.rewardedHistory = rewardedHistory;
    }


}
