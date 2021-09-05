
package com.foodtogo.user.model.wishlist;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavouriteResponse {

    @SerializedName("product_wish_list")
    @Expose
    private List<ProductWishList> productWishList = null;

    public List<ProductWishList> getProductWishList() {
        return productWishList;
    }

    public void setProductWishList(List<ProductWishList> productWishList) {
        this.productWishList = productWishList;
    }

}
