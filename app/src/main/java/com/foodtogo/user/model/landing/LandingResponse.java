
package com.foodtogo.user.model.landing;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LandingResponse {

    @SerializedName("grocery_icon_android")
    @Expose
    private String groceryIconAndroid;
    @SerializedName("restaurant_icon_android")
    @Expose
    private String restaurantIconAndroid;
    @SerializedName("grocery_icon_ios")
    @Expose
    private String groceryIconIos;
    @SerializedName("restaurant_icon_ios")
    @Expose
    private String restaurantIconIos;
    @SerializedName("banner_details")
    @Expose
    private List<BannerDetail> bannerDetails = null;

    public String getGroceryIconAndroid() {
        return groceryIconAndroid;
    }

    public void setGroceryIconAndroid(String groceryIconAndroid) {
        this.groceryIconAndroid = groceryIconAndroid;
    }

    public String getRestaurantIconAndroid() {
        return restaurantIconAndroid;
    }

    public void setRestaurantIconAndroid(String restaurantIconAndroid) {
        this.restaurantIconAndroid = restaurantIconAndroid;
    }

    public String getGroceryIconIos() {
        return groceryIconIos;
    }

    public void setGroceryIconIos(String groceryIconIos) {
        this.groceryIconIos = groceryIconIos;
    }

    public String getRestaurantIconIos() {
        return restaurantIconIos;
    }

    public void setRestaurantIconIos(String restaurantIconIos) {
        this.restaurantIconIos = restaurantIconIos;
    }

    public List<BannerDetail> getBannerDetails() {
        return bannerDetails;
    }

    public void setBannerDetails(List<BannerDetail> bannerDetails) {
        this.bannerDetails = bannerDetails;
    }

}

