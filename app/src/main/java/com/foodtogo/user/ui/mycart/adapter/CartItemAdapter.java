package com.foodtogo.user.ui.mycart.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.mycart.AddedItemDetail;
import com.foodtogo.user.model.mycart.CartChoice;
import com.foodtogo.user.ui.mycart.interfaces.MyCartListener;
import com.foodtogo.user.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.RestaurantItemRowHolder> {

    private List<AddedItemDetail> itemListList;
    private Context mContext;
    private MyCartListener myCartListener;

    public CartItemAdapter(Context context, List<AddedItemDetail> itemLists) {
        this.itemListList = itemLists;
        this.mContext = context;
    }

    public void setAddCartListener(MyCartListener myCartListener) {
        this.myCartListener = myCartListener;
    }

    @NonNull
    @Override
    public RestaurantItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_cart_item, null);

        return new RestaurantItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantItemRowHolder holder, int i) {
        AddedItemDetail addedItemDetail = itemListList.get(i);
        holder.tvItemName.setText(addedItemDetail.getProductName());
        holder.tvFoodPrice.setText(String.format("%s%s", addedItemDetail.getCartCurrency(), addedItemDetail.getCartSubTotal()));
        holder.tvQuantity.setText(String.valueOf(addedItemDetail.getCartQuantity()));
        GlideUtils.showImage(mContext, holder.ivFood, R.drawable.image_placeholder_small, addedItemDetail.getProductImage());
        if (addedItemDetail.getCartHasChoice().equals("Yes")) {
            List<CartChoice> cartChoices = addedItemDetail.getCartChoices();
            for (int jIndex = 0; jIndex < cartChoices.size(); jIndex++) {
                int choiceId = cartChoices.get(jIndex).getChoiceId();
                String choiceName = cartChoices.get(jIndex).getChoiceName();
                addChoiceItem(holder.llChoiceItem, addedItemDetail.getProductId(), addedItemDetail.getCartId(), choiceId, choiceName);
            }
        }

        holder.ivMinus.setOnClickListener(v -> {
            int qty = addedItemDetail.getCartQuantity();
            if (qty > 1) {
                qty = qty - 1;
                holder.tvQuantity.setText("" + qty);
                myCartListener.clickMinusBtn(addedItemDetail.getCartId(), qty);
            }
        });

        holder.ivPlus.setOnClickListener(v -> {
            int qty = addedItemDetail.getCartQuantity();
            if (qty < addedItemDetail.getAvailableStock()) {
                qty = qty + 1;
                holder.tvQuantity.setText("" + qty);
                myCartListener.clickPlusBtn(addedItemDetail.getCartId(), qty);
            } else {
                myCartListener.showErrorMessage(mContext.getString(R.string.order_limit_reached));
            }
        });
        holder.ivRemoveItem.setOnClickListener(v -> myCartListener.clickRemoveCart(addedItemDetail.getCartId()));
        holder.itemView.setOnClickListener(v -> myCartListener.clickItem(addedItemDetail));
    }

    private void addChoiceItem(LinearLayout linearLayout, int productId, int cartId, int choiceId, String choiceName) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View view = inflater.inflate(R.layout.list_item_choice_chips, null);
        TextView tvChoiceId = view.findViewById(R.id.tv_choice_id);
        TextView tvChoiceName = view.findViewById(R.id.tv_choice_name);
        tvChoiceId.setText("" + choiceId);
        tvChoiceName.setText(choiceName);
        ImageView ivChoiceClose = view.findViewById(R.id.iv_choice_close);
        ivChoiceClose.setOnClickListener(v -> myCartListener.clickRemoveChoice(productId, cartId, choiceId));
        linearLayout.addView(view);

    }


    @Override
    public int getItemCount() {
        return (null != itemListList ? itemListList.size() : 0);
    }

    class RestaurantItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_quantity)
        TextView tvQuantity;

        @BindView(R.id.iv_minus)
        ImageView ivMinus;

        @BindView(R.id.iv_plus)
        ImageView ivPlus;

        @BindView(R.id.iv_food)
        ImageView ivFood;

        @BindView(R.id.tv_item_name)
        TextView tvItemName;

        @BindView(R.id.tv_food_price)
        TextView tvFoodPrice;

        @BindView(R.id.ll_choice_item)
        LinearLayout llChoiceItem;

        @BindView(R.id.iv_remove_item)
        ImageView ivRemoveItem;

        RestaurantItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}