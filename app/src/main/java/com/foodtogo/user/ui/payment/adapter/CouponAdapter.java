package com.foodtogo.user.ui.payment.adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.payment.CouponList;
import com.foodtogo.user.ui.payment.interfaces.OnApplyItemListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ItemRowHolder> {

    private List<CouponList> itemListList;
    private OnApplyItemListener onApplyListener;
    private String couponId;

    public CouponAdapter(List<CouponList> itemLists,String mCouponId) {
        this.itemListList = itemLists;
        this.couponId=mCouponId;
    }

    public void setOnApplyListener(OnApplyItemListener onApplyListener) {
        this.onApplyListener = onApplyListener;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_coupon, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, int i) {
        CouponList couponList = itemListList.get(i);
        holder.tvTitle.setText(couponList.getCouponName());
       // holder.tvDescription.setText(couponList.getCouponDesc());
        holder.tvCoupon.setText(couponList.getCouponCode());
        if(!couponId.equals("")){
            if(couponId.equals(couponList.getCouponId())){
                holder.btnApply.setText("Applied");
                holder.btnApply.setClickable(false);
                holder.btnApply.setEnabled(false);
            }
        }
        holder.btnApply.setOnClickListener(v -> onApplyListener.onClickItemApply(i));
    }


    @Override
    public int getItemCount() {
        return (null != itemListList ? itemListList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_description)
        TextView tvDescription;

        @BindView(R.id.tv_coupon_code)
        TextView tvCoupon;

        @BindView(R.id.btn_apply)
        Button btnApply;

        ItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}