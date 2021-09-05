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
import com.foodtogo.user.util.DataUtils;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class AvailableTimeViewHolder extends RecyclerView.ViewHolder {

    private SelectionListener selectionListener;
    private JSONObject filterJSON;

    public AvailableTimeViewHolder(View view, SelectionListener selectionListener, JSONObject filterJSON) {
        super(view);
        ButterKnife.bind(this, view);
        cbBreakFast.setTypeface(ResourcesCompat.getFont(BaseApplication.getContext(), R.font.helvetica_ultra_light));
        cbLunch.setTypeface(ResourcesCompat.getFont(BaseApplication.getContext(), R.font.helvetica_ultra_light));
        cbDinner.setTypeface(ResourcesCompat.getFont(BaseApplication.getContext(), R.font.helvetica_ultra_light));
        this.filterJSON = filterJSON;
        this.selectionListener = selectionListener;
        setFilter();
    }

    private void setFilter() {
        try {
            cbBreakFast.setChecked(filterJSON.getBoolean(DataUtils.KEY_BREAK_FAST));
            cbLunch.setChecked(filterJSON.getBoolean(DataUtils.KEY_LUNCH));
            cbDinner.setChecked(filterJSON.getBoolean(DataUtils.KEY_DINNER));
        } catch (Exception e) {

        }
    }


    @BindView(R.id.break_fast)
    CheckBox cbBreakFast;

    @BindView(R.id.lunch)
    CheckBox cbLunch;

    @BindView(R.id.dinner)
    CheckBox cbDinner;

    @BindView(R.id.tv_time_of_day)
    TextView tvTimeOfDay;

    public void onBind(int position, List<CategoryList> categoryLists) {
        CategoryList categoryList = categoryLists.get(position);
        tvTimeOfDay.setText(categoryList.getCategoryName());
    }

    @OnCheckedChanged(R.id.break_fast)
    void onBreakFast(CompoundButton button, boolean checked) {
        selectionListener.onUpdateKeyValue(DataUtils.KEY_BREAK_FAST, checked);
    }


    @OnCheckedChanged(R.id.lunch)
    void onLunch(CompoundButton button, boolean checked) {
        selectionListener.onUpdateKeyValue(DataUtils.KEY_LUNCH, checked);
    }


    @OnCheckedChanged(R.id.dinner)
    void onDinner(CompoundButton button, boolean checked) {
        selectionListener.onUpdateKeyValue(DataUtils.KEY_DINNER, checked);
    }


}