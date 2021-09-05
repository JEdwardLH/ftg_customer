package com.foodtogo.user.ui.viewrestaurant.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.restaurant.ItemList;
import com.foodtogo.user.ui.viewrestaurant.interfaces.AddCartListener;
import com.foodtogo.user.util.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.foodtogo.user.base.AppConstants.AVAILABLE;
import static com.foodtogo.user.base.AppConstants.YES;

public class CategoryBasedItemAdapter extends RecyclerView.Adapter<CategoryBasedItemAdapter.RestaurantItemRowHolder>
        implements Filterable {

    private List<ItemList> itemLists;
    private List<ItemList> itemListsFiltered;
    private Context mContext;
    private AddCartListener addCartListener;

    public CategoryBasedItemAdapter(Context context, List<ItemList> itemLists) {
        this.itemLists = itemLists;
        this.itemListsFiltered = itemLists;
        this.mContext = context;
    }

    public void setAddCartListener(AddCartListener addCartListener) {
        this.addCartListener = addCartListener;
    }

    @NonNull
    @Override
    public RestaurantItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_category_item, null);
        return new RestaurantItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantItemRowHolder holder, int i) {
         ArrayList<String> itemInclude;
        ItemList itemList = itemListsFiltered.get(i);
        itemInclude=checkItemInclude(itemList);
        //TODO set combo/halal image
        if(itemList.getItemApiIncludes().size()>0){
         holder.gluctenImg.setVisibility(itemInclude.contains("1")?View.VISIBLE:View.GONE);
         holder.lactosImg.setVisibility(itemInclude.contains("2")?View.VISIBLE:View.GONE);
         holder.nutsImg.setVisibility(itemInclude.contains("3")?View.VISIBLE:View.GONE);
         holder.chilliImg.setVisibility(itemInclude.contains("4")?View.VISIBLE:View.GONE);
        }

        holder.tvItemName.setText(itemList.getItemName());
        holder.tvFoodPrice.setText(itemList.getItemHasDiscount().equals(YES) ? (itemList.getItemCurrency() + itemList.getItemDiscountPrice()) : (itemList.getItemCurrency() + itemList.getItemOriginalPrice()));

        holder.tvFoodDescription.setText(itemList.getItemDesc());
        holder.tvOutOffStock.setVisibility(itemList.getItemAvailablity().equals(AVAILABLE) ? View.GONE : View.VISIBLE);
        holder.tvRating.setText(itemList.getItemRating() == 0 ? "" : "" + itemList.getItemRating());
        holder.tvRating.setBackgroundResource(itemList.getItemRating() == 0 ? R.drawable.ic_rating_with_star : R.drawable.ic_rating_bubble_color);
        GlideUtils.showCatImage(mContext, holder.ivFood,0, itemList.getItemImage());
        if (itemList.getItemType().equals("Veg")) {
            holder.ivVegNonveg.setImageResource(R.drawable.ic_veg);
        } else {
            holder.ivVegNonveg.setImageResource(R.drawable.ic_nonveg);
        }

       // holder.offerTillText.setVisibility(itemList.getItemSpecialOffer().equals(YES)?View.VISIBLE:View.GONE);
      //  offerCountDown(holder.offerTillText,itemList);


        holder.btnAdd.setVisibility(itemList.getItemAvailablity().equals(AVAILABLE)?View.VISIBLE:View.GONE);
        holder.btnAdd.setOnClickListener(v -> addCartListener.addCartClick(i));
        holder.itemView.setOnClickListener(v -> addCartListener.addCartClick(i));
    }


    private ArrayList<String> checkItemInclude(ItemList itemList){
      ArrayList<String> itemInclude=new ArrayList<>();
      if(itemList.getItemApiIncludes().size()>0)
      for(int i=0;i<itemList.getItemApiIncludes().size();i++){
          itemInclude.add(itemList.getItemApiIncludes().get(i).getItem_inc_id());
      }
      return  itemInclude;
    }

  /*  private void offerCountDown(TextView offerTv,ItemList itemList){

        if(itemList.getDiscountRemainingTime()!=0){
            new CountDownTimer(itemList.getDiscountRemainingTime(), 1000) { // adjust the milli seconds here

                public void onTick(long millisUntilFinished) {
                    final long day = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);

                    final long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                            - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished));

                    final long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));

                    final long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                  String time;
                      if(day>0){
                          time= formatTwoDigit(day)+":"+formatTwoDigit(hours)+":"+formatTwoDigit(minutes)+":"+formatTwoDigit(seconds);
                      }else{
                          time= formatTwoDigit(hours)+":"+formatTwoDigit(minutes)+":"+formatTwoDigit(seconds);

                      }
                    offerTv.setText(time);

                }

                public void onFinish() {
                    offerTv.setVisibility(View.GONE);
                }
            }.start();

        }else{
            offerTv.setVisibility(View.GONE);
        }

    }*/

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    itemListsFiltered = itemLists;
                } else {
                    List<ItemList> filteredList = new ArrayList<>();
                    for (ItemList row : itemLists) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getItemName().toLowerCase().contains(charString.toLowerCase()) || row.getItemName().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    itemListsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = itemListsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemListsFiltered = (ArrayList<ItemList>) filterResults.values;
                notifyDataSetChanged();
                if (itemListsFiltered.size() == 0) {
                    addCartListener.noItems();
                } else {
                    addCartListener.showItems();
                }
            }
        };
    }

    @Override
    public int getItemCount() {
        return (null != itemListsFiltered ? itemListsFiltered.size() : 0);
    }

    class RestaurantItemRowHolder extends RecyclerView.ViewHolder {

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

        @BindView(R.id.offer_till)
        TextView offerTillText;

        @BindView(R.id.iv_veg_nonveg)
        ImageView ivVegNonveg;

        @BindView(R.id.glucten_img)
        ImageView gluctenImg;

        @BindView(R.id.lactos_img)
        ImageView lactosImg;

        @BindView(R.id.nuts_img)
        ImageView nutsImg;

        @BindView(R.id.chilli_img)
        ImageView chilliImg;

        @BindView(R.id.tv_out_off_stock)
        ImageView tvOutOffStock;

        @BindView(R.id.btn_add)
        Button btnAdd;





        RestaurantItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}