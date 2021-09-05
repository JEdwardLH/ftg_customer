package com.foodtogo.user.ui.viewitemdetails.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.itemdetails.RelatedItem;
import com.foodtogo.user.ui.viewitemdetails.interfaces.AddRelatedItemCartListener;
import com.foodtogo.user.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.foodtogo.user.base.AppConstants.AVAILABLE;
import static com.foodtogo.user.base.AppConstants.NOT_FAVOURITE;
import static com.foodtogo.user.base.AppConstants.VEG;
import static com.foodtogo.user.base.AppConstants.YES;

public class RelatedRestaurantDataAdapter extends RecyclerView.Adapter<RelatedRestaurantDataAdapter.RelatedRowHolder> {

    private List<RelatedItem> relatedItems;
    private AddRelatedItemCartListener relatedItemCartListener;
    private Context mContext;

    public RelatedRestaurantDataAdapter(Context context, List<RelatedItem> relatedItems) {
        this.relatedItems = relatedItems;
        this.mContext = context;
    }

    public void setAddCartListener(AddRelatedItemCartListener relatedItemCartListener) {
        this.relatedItemCartListener = relatedItemCartListener;
    }

    @NonNull
    @Override
    public RelatedRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_related_food, null);
        return new RelatedRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedRowHolder holder, int i) {
        RelatedItem relatedItem = relatedItems.get(i);
        holder.tvItemName.setText(relatedItem.getItemName());
        holder.tvFoodPrice.setText(relatedItem.getItemHasDiscount().equals(YES) ? (relatedItem.getItemCurrency() + relatedItem.getItemDiscountPrice()) : (relatedItem.getItemCurrency() + relatedItem.getItemOriginalPrice()));
        holder.tvFoodDescription.setText(relatedItem.getItemDesc());
        holder.tvRating.setText(relatedItem.getItemRating() == 0 ? "" : "" + relatedItem.getItemRating());
        holder.tvRating.setBackgroundResource(relatedItem.getItemRating() == 0 ? R.drawable.ic_with_star : R.drawable.ic_without_star);
        GlideUtils.showImage(mContext, holder.ivFood, R.drawable.image_placeholder_small, relatedItem.getItemImage());
        if (relatedItem.getItemType().equals(VEG)) {
            holder.ivVegNonveg.setImageResource(R.drawable.ic_veg);
        } else {
            holder.ivVegNonveg.setImageResource(R.drawable.ic_nonveg);
        }
        holder.ivFav.setEnabled(relatedItem.getItemAvailablity().equals(AVAILABLE) ? true : false);
        if (relatedItem.getItemIsFavourite().equals(NOT_FAVOURITE)) {
            holder.ivFav.setChecked(false);
        } else {
            holder.ivFav.setChecked(true);
        }

        holder.btnAdd.setOnClickListener(v -> {
            relatedItemCartListener.addCartClick(i);
        });

        holder.ivFav.setOnClickListener(v -> relatedItemCartListener.itemFavourites(i));
        holder.itemView.setOnClickListener(v -> relatedItemCartListener.addCartClick(i));
    }

    @Override
    public int getItemCount() {
        return (null != relatedItems ? relatedItems.size() : 0);
    }

    public void updateValues(int position) {
        notifyItemChanged(position);
    }

    class RelatedRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_rating)
        TextView tvRating;

        @BindView(R.id.tv_item_name)
        TextView tvItemName;

        @BindView(R.id.tv_food_price)
        TextView tvFoodPrice;

        @BindView(R.id.tv_food_description)
        TextView tvFoodDescription;

        @BindView(R.id.iv_food)
        ImageView ivFood;

        @BindView(R.id.iv_veg_nonveg)
        ImageView ivVegNonveg;

        @BindView(R.id.btn_add)
        Button btnAdd;

        @BindView(R.id.iv_fav)
        CheckBox ivFav;


        RelatedRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}