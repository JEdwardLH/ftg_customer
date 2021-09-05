package com.foodtogo.user.ui.wishlist.adapter;

import android.content.Context;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.wishlist.ProductWishList;
import com.foodtogo.user.ui.wishlist.interfaces.MyWishListener;
import com.foodtogo.user.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.foodtogo.user.base.AppConstants.YES;

public class WishListItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<ProductWishList> productWishLists;
    private Context mContext;
    private boolean isLoadingAdded = false;
    private MyWishListener myWishListener;

    public WishListItemAdapter(Context context, List<ProductWishList> orderArrayList) {
        this.productWishLists = orderArrayList;
        this.mContext = context;
    }

    public void setWishListener(MyWishListener myWishListener) {
        this.myWishListener = myWishListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.list_item_wish_list, parent, false);
        viewHolder = new ItemVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductWishList productWishList = productWishLists.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final ItemVH itemVH = (ItemVH) holder;
                itemVH.tvItemName.setText(productWishList.getProductTitle());
                itemVH.tvDesc.setText(productWishList.getProductDesc());
                String discountInfo = productWishList.getProductCurrencyCode() + productWishList.getProductDiscountPrice();
                String priceInfo = productWishList.getProductCurrencyCode() + productWishList.getProductOriginalPrice();
                if (productWishList.getProHasDiscount().equals(YES)) {
                    itemVH.tvDiscountPrice.setText(discountInfo);
                    itemVH.tvFoodPrice.setText(priceInfo);
                    itemVH.tvFoodPrice.setVisibility(View.VISIBLE);
                    itemVH.tvFoodPrice.setPaintFlags(itemVH.tvFoodPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    itemVH.tvFoodPrice.setVisibility(View.GONE);
                    itemVH.tvDiscountPrice.setText(priceInfo);
                }
                itemVH.tvItemAvailable.setText(productWishList.getAvailablity());
                GlideUtils.showImage(mContext, itemVH.ivFood, R.drawable.image_placeholder_small, productWishList.getProductImage());
                itemVH.ivRemoveItem.setOnClickListener(v -> myWishListener.removeFavourites(position, productWishList.getProductId()));
                itemVH.cardView.setOnClickListener(v -> myWishListener.viewFavourites(position));
                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return productWishLists == null ? 0 : productWishLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == productWishLists.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(ProductWishList r) {
        productWishLists.add(r);
        notifyItemInserted(productWishLists.size() - 1);
    }

    public void addAll(List<ProductWishList> restautrantDetails) {
        for (ProductWishList result : restautrantDetails) {
            add(result);
        }
    }

    public void removedItem(int position) {
        productWishLists.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, productWishLists.size());
    }


    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new ProductWishList());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = productWishLists.size() - 1;
        ProductWishList result = getItem(position);

        if (result != null) {
            productWishLists.remove(position);
            notifyItemRemoved(position);
        }
    }

    private ProductWishList getItem(int position) {
        return productWishLists.get(position);
    }


    class ItemVH extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_food)
        ImageView ivFood;

        @BindView(R.id.tv_item_name)
        TextView tvItemName;

        @BindView(R.id.tv_discount_price)
        TextView tvFoodPrice;

        @BindView(R.id.tv_food_price)
        TextView tvDiscountPrice;

        @BindView(R.id.tv_item_available)
        TextView tvItemAvailable;

        @BindView(R.id.tv_des)
        TextView tvDesc;

        @BindView(R.id.iv_remove_item)
        ImageView ivRemoveItem;

        @BindView(R.id.card_view)
        CardView cardView;

        ItemVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private class LoadingVH extends RecyclerView.ViewHolder {

        private LoadingVH(View itemView) {
            super(itemView);
        }
    }
}