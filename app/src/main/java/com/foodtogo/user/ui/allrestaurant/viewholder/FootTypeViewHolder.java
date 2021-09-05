package com.foodtogo.user.ui.allrestaurant.viewholder;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.allrestaurant.CategoryList;
import com.foodtogo.user.ui.allrestaurant.interfaces.SelectionListener;
import com.foodtogo.user.util.DataUtils;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FootTypeViewHolder extends RecyclerView.ViewHolder {

    private SelectionListener footTypeSelectionListener;
    private JSONObject filterJSON;

    public FootTypeViewHolder(View view, SelectionListener _footTypeSelectionListener, JSONObject filterJSON) {
        super(view);
        ButterKnife.bind(this, view);
        this.filterJSON = filterJSON;
        this.footTypeSelectionListener = _footTypeSelectionListener;
    }


    @BindView(R.id.rb_veg)
    RadioButton rbVeg;

    @BindView(R.id.rb_non_veg)
    RadioButton rbNonVeg;

    @BindView(R.id.rb_both)
    RadioButton rbBoth;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    public void onBind(int position, List<CategoryList> categoryLists) {
        CategoryList categoryList = categoryLists.get(position);
        tvTitle.setText(categoryList.getCategoryName());
        setFilter();
    }

    private void setFilter() {
        try {
            String itemType = filterJSON.getString(DataUtils.KEY_ITEM_TYPE);
            switch (itemType) {
                case "1":
                    rbVeg.setChecked(true);
                    break;
                case "2":
                    rbNonVeg.setChecked(true);
                    break;
                case "12":
                    rbBoth.setChecked(true);
                    break;
            }
        } catch (Exception e) {

        }
    }


    @OnClick({R.id.rb_veg, R.id.rb_non_veg, R.id.rb_both})
    public void onRadioButtonClicked(RadioButton radioButton) {
        // Is the button now checked?
        boolean checked = radioButton.isChecked();
        // Check which radio button was clicked
        switch (radioButton.getId()) {
            case R.id.rb_veg:
                if (checked) {
                    footTypeSelectionListener.onUpdateKeyValue(DataUtils.KEY_ITEM_TYPE, "1");
                }
                break;
            case R.id.rb_non_veg:
                if (checked) {
                    footTypeSelectionListener.onUpdateKeyValue(DataUtils.KEY_ITEM_TYPE, "2");
                }
                break;
            case R.id.rb_both:
                if (checked) {
                    footTypeSelectionListener.onUpdateKeyValue(DataUtils.KEY_ITEM_TYPE, "12");
                }
                break;
        }
    }


}