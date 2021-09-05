package com.foodtogo.user.ui.viewall.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.allrestaurant.AllRestautrantDetail;
import com.foodtogo.user.ui.viewall.interfaces.ItemDetailListener;
import com.foodtogo.user.util.GlideUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.foodtogo.user.base.AppConstants.AVAILABLE;

public class ViewAllDataAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private ItemDetailListener itemDetailListener;
    private List<AllRestautrantDetail> allRestaurantDetails;
    private List<AllRestautrantDetail> allRestaurantDetailFiltered;
    private Context mContext;
    private boolean isLoadingAdded = false;

    public ViewAllDataAdapters(Context context, List<AllRestautrantDetail> allRestaurants) {
        this.allRestaurantDetails = allRestaurants;
        this.allRestaurantDetailFiltered = allRestaurants;
        this.mContext = context;
    }

    public void setItemDetailsListener(ItemDetailListener itemDetailListener) {
        this.itemDetailListener = itemDetailListener;
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
        View v1 = inflater.inflate(R.layout.list_item_view_all, parent, false);
        viewHolder = new ItemVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AllRestautrantDetail allRestaurantDetail = allRestaurantDetailFiltered.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final ItemVH itemVH = (ItemVH) holder;
                itemVH.tvItemName.setText(allRestaurantDetail.getRestaurantName());
                itemVH.tvFoodCategory.setText(allRestaurantDetail.getCategoryName());
                itemVH.tvFoodTiming.setText(String.format("%s %s", mContext.getString(R.string.delivery_in), allRestaurantDetail.getRestaurantDeliveryTime()));
                itemVH.tvFoodDescription.setText(mContext.getString(R.string.upto_offer, allRestaurantDetail.getRestaurantOffer()+"%"));
               // itemVH.tvFoodDescription.setVisibility(allRestaurantDetail.getRestaurantOffer().equals("0") ? View.GONE : View.VISIBLE);
                itemVH.tvFoodDescription.setVisibility(View.GONE);
                itemVH.tvRating.setText(String.valueOf(allRestaurantDetail.getRestaurantRating()));
                itemVH.tvRating.setBackgroundResource(R.drawable.ic_rating_bubble);
                GlideUtils.showImage(mContext, itemVH.ivFood, R.drawable.image_placeholder_small, allRestaurantDetail.getRestaurantImage());
                itemVH.tvAvailable.setText(allRestaurantDetail.getRestaurantStatus());
                itemVH.tvAvailable.setVisibility(View.GONE);
                itemVH.tvRestaurantTiming.setText(allRestaurantDetail.getTodayWkingTime());
                itemVH.tvRestaurantTiming.setVisibility(allRestaurantDetail.getTodayWkingTime().isEmpty()?View.GONE:View.VISIBLE);
                itemVH.rlRestaurantView.setAlpha(allRestaurantDetail.getRestaurantStatus().equals(AVAILABLE) ? 1f : 0.6f);

                if(!allRestaurantDetail.getRestaurantStatus().equals(AVAILABLE)){
                    itemVH.tvRestaurantTiming.setCompoundDrawablesWithIntrinsicBounds( changeDrawableColor(mContext,R.drawable.ic_available,R.color.light_grey_bg), null,null,null);
                    itemVH.tvFoodTiming.setCompoundDrawablesWithIntrinsicBounds( changeDrawableColor(mContext,R.drawable.ic_delivery_time_svg,R.color.light_grey_bg), null,null,null);
                    itemVH.tvFoodDescription.setCompoundDrawablesWithIntrinsicBounds( changeDrawableColor(mContext,R.drawable.ic_offer_percentage_svg,R.color.light_grey_bg),null,null,null);
                    itemVH.tvRating.setBackground(changeDrawableColor(mContext,R.drawable.ic_rating_bubble,R.color.light_grey_bg));
                    itemVH.tvItemName.setTextColor(mContext.getResources().getColor(R.color.light_grey_bg));
                    itemVH.tvRestaurantTiming.setTextColor(mContext.getResources().getColor(R.color.edit_txt_hint));
                    itemVH.tvFoodTiming.setTextColor(mContext.getResources().getColor(R.color.edit_txt_hint));
                    itemVH.tvFoodDescription.setTextColor(mContext.getResources().getColor(R.color.edit_txt_hint));
                }else{
                    itemVH.tvRestaurantTiming.setCompoundDrawablesWithIntrinsicBounds( changeDrawableColor(mContext,R.drawable.ic_available,R.color.colorAccent), null,null,null);
                    itemVH.tvFoodTiming.setCompoundDrawablesWithIntrinsicBounds(changeDrawableColor(mContext,R.drawable.ic_delivery_time_svg,R.color.colorAccent), null,null,null);
                    itemVH.tvFoodDescription.setCompoundDrawablesWithIntrinsicBounds( changeDrawableColor(mContext,R.drawable.ic_offer_percentage_svg,R.color.colorAccent),null,null,null);
                    itemVH.tvRating.setBackground(changeDrawableColor(mContext,R.drawable.ic_rating_bubble,R.color.grey_bubble));
                    itemVH.tvItemName.setTextColor(mContext.getResources().getColor(R.color.black));
                    itemVH.tvRestaurantTiming.setTextColor(mContext.getResources().getColor(R.color.black));
                    itemVH.tvFoodTiming.setTextColor(mContext.getResources().getColor(R.color.black));
                    itemVH.tvFoodDescription.setTextColor(mContext.getResources().getColor(R.color.black));
                }

                itemVH.itemView.setOnClickListener(v ->
                        itemDetailListener.itemClick(position)
                );
                break;

            case LOADING:
//                Do nothing
                break;
        }

    }
    private static Drawable changeDrawableColor(Context context, int icon, int color) {
        Drawable mDrawable = Objects.requireNonNull(ContextCompat.getDrawable(context, icon)).mutate();
        mDrawable.setColorFilter(new PorterDuffColorFilter(context.getResources().getColor(color), PorterDuff.Mode.SRC_IN));
        return mDrawable;
    }


    @Override
    public int getItemCount() {
        return allRestaurantDetailFiltered == null ? 0 : allRestaurantDetailFiltered.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == allRestaurantDetailFiltered.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(AllRestautrantDetail r) {
        allRestaurantDetailFiltered.add(r);
        notifyItemInserted(allRestaurantDetailFiltered.size() - 1);
    }

    public void addAll(List<AllRestautrantDetail> restautrantDetails) {
        for (AllRestautrantDetail result : restautrantDetails) {
            add(result);
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    allRestaurantDetailFiltered = allRestaurantDetails;
                } else {
                    List<AllRestautrantDetail> filteredList = new ArrayList<>();
                    for (AllRestautrantDetail row : allRestaurantDetails) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getRestaurantName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    allRestaurantDetailFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = allRestaurantDetailFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                allRestaurantDetailFiltered = (ArrayList<AllRestautrantDetail>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new AllRestautrantDetail());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = allRestaurantDetailFiltered.size() - 1;
        AllRestautrantDetail result = getItem(position);

        if (result != null) {
            allRestaurantDetailFiltered.remove(position);
            notifyItemRemoved(position);
        }
    }

    private AllRestautrantDetail getItem(int position) {
        return allRestaurantDetailFiltered.get(position);
    }


    class ItemVH extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_rating)
        TextView tvRating;

        @BindView(R.id.tv_item_name)
        TextView tvItemName;

        @BindView(R.id.tv_food_timing)
        TextView tvFoodTiming;

        @BindView(R.id.tv_food_description)
        TextView tvFoodDescription;

        @BindView(R.id.iv_food)
        ImageView ivFood;

        @BindView(R.id.tv_available)
        TextView tvAvailable;

        @BindView(R.id.rl_restaurant_view)
        RelativeLayout rlRestaurantView;

        @BindView(R.id.tv_food_category)
        TextView tvFoodCategory;

        @BindView(R.id.tv_restaurant_timing)
        TextView tvRestaurantTiming;

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
































