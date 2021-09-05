package com.foodtogo.user.ui.allrestaurant.viewholder;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.foodtogo.user.BaseApplication;
import com.foodtogo.user.R;
import com.foodtogo.user.model.allrestaurant.CategoryList;
import com.foodtogo.user.ui.allrestaurant.interfaces.SelectionListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private SelectionListener selectionListener;

    public CategoryViewHolder(View view, SelectionListener selectionListener) {
        super(view);
        ButterKnife.bind(this, view);
        cbCategoryName.setTypeface(ResourcesCompat.getFont(BaseApplication.getContext(), R.font.helvetica_ultra_light));
        this.selectionListener = selectionListener;
    }


    @BindView(R.id.cb_category_name)
    CheckBox cbCategoryName;

    @BindView(R.id.tv_heading)
    TextView tvHeading;

    @BindView(R.id.view_category)
    View viewCategory;
    private CategoryList categoryList;


    public void onBind(int position, List<CategoryList> categoryLists) {
         categoryList = categoryLists.get(position);
        cbCategoryName.setChecked(categoryList.isSelected());
        cbCategoryName.setText(categoryList.getCategoryName());
        tvHeading.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        viewCategory.setVisibility(position == 0 ? View.VISIBLE : View.GONE);

    }

    @OnCheckedChanged(R.id.cb_category_name)
    void onBreakFast(CompoundButton button, boolean checked) {
        String categoryId=String.valueOf(categoryList.getCategoryId());
        selectionListener.onUpdateKeyValue(getAdapterPosition(), checked,categoryId);

    }


}