package com.foodtogo.user.ui.mycart.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.AppConstants;
import com.foodtogo.user.model.itemdetails.Choice;
import com.foodtogo.user.ui.mycart.interfaces.ChoiceListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChoiceChangeAdapter extends RecyclerView.Adapter<ChoiceChangeAdapter.ChoiceItemHolder> implements AppConstants {

    private List<Choice> choiceList;
    private Context mContext;
    private int itemPosition;
    private ChoiceListener choiceItemListener;


    public ChoiceChangeAdapter(Context context, List<Choice> choiceLists) {
        this.choiceList = choiceLists;
        this.mContext = context;
    }

    public void setChoiceListener(ChoiceListener choiceListener) {
        this.choiceItemListener = choiceListener;
    }

    @NonNull
    @Override
    public ChoiceItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_choice, null);
        return new ChoiceItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoiceItemHolder holder, int i) {
        Choice choice = choiceList.get(i);
        holder.cbChoice.setText(choice.getChoiceName());
        holder.tvPrice.setText(choice.getChoiceCurrency() + SPACE + choice.getChoicePrice());
        if (choice.isChoiceSelected()) {
            holder.cbChoice.setChecked(true);
        } else {
            holder.cbChoice.setChecked(false);
        }

        holder.cbChoice.setOnClickListener(v -> choiceItemListener.onClickChoiceItem(i, holder.cbChoice.isChecked()));
    }


    public void updateItem(int position) {
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return (null != choiceList ? choiceList.size() : 0);
    }

    class ChoiceItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_choice)
        CheckBox cbChoice;

        @BindView(R.id.tv_price)
        TextView tvPrice;

        ChoiceItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}