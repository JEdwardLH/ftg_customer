package com.foodtogo.user.ui.viewrestaurant.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.ui.viewrestaurant.interfaces.ItemFilterListener;
import com.foodtogo.user.util.DataUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


public class FilterFragment extends BaseFragment {

    @BindView(R.id.cb_top_offers)
    CheckBox cbTopOffers;

    @BindView(R.id.cb_combo)
    CheckBox cbCombo;

    @BindView(R.id.cb_halal)
    CheckBox cbHalal;

    @BindView(R.id.cb_special_offers)
    CheckBox cbSpecialOffers;

    @BindView(R.id.break_fast)
    CheckBox cbBreakFast;

    @BindView(R.id.lunch)
    CheckBox cbLunch;

    @BindView(R.id.dinner)
    CheckBox cbDinner;

    @BindView(R.id.brunch)
    CheckBox cbBrunch;

    @BindView(R.id.supper)
    CheckBox cbSupper;

    @BindView(R.id.rb_veg)
    RadioButton rbVeg;

    @BindView(R.id.rb_non_veg)
    RadioButton rbNonVeg;

    @BindView(R.id.rb_both)
    RadioButton rbBoth;


    String itemType = "";
    JSONObject jsonObject;

    ItemFilterListener itemFilterListener;


    public static FilterFragment newInstance(String filterJson) {
        FilterFragment itemFilterFragment = new FilterFragment();
        Bundle args = new Bundle();
//        args.putString(KEY_FILTER, filterJson);
        itemFilterFragment.setArguments(args);
        return itemFilterFragment;
    }


    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_filter, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cbSpecialOffers.setTypeface(ResourcesCompat.getFont(context, R.font.helvetica_ultra_light));
        cbTopOffers.setTypeface(ResourcesCompat.getFont(context, R.font.helvetica_ultra_light));
        cbCombo.setTypeface(ResourcesCompat.getFont(context, R.font.helvetica_ultra_light));
        cbHalal.setTypeface(ResourcesCompat.getFont(context, R.font.helvetica_ultra_light));

        cbBreakFast.setTypeface(ResourcesCompat.getFont(context, R.font.helvetica_ultra_light));
        cbBrunch.setTypeface(ResourcesCompat.getFont(context, R.font.helvetica_ultra_light));
        cbLunch.setTypeface(ResourcesCompat.getFont(context, R.font.helvetica_ultra_light));
        cbSupper.setTypeface(ResourcesCompat.getFont(context, R.font.helvetica_ultra_light));
        cbDinner.setTypeface(ResourcesCompat.getFont(context, R.font.helvetica_ultra_light));
       // String filterJSON = getArguments().getString(KEY_FILTER);
        //setFilter(filterJSON);
    }

    @OnCheckedChanged(R.id.cb_top_offers)
    void onTopOffer(CompoundButton button, boolean checked) {
        updateItem(DataUtils.KEY_TOP_OFFER, checked);
    }

    @OnCheckedChanged(R.id.cb_combo)
    void onComBo(CompoundButton button, boolean checked) {
        updateItem(DataUtils.KEY_COMBO, checked);
    }

    @OnCheckedChanged(R.id.cb_halal)
    void onHalal(CompoundButton button, boolean checked) {
        updateItem(DataUtils.KEY_HALAL, checked);
    }


    @OnClick({R.id.rb_veg, R.id.rb_non_veg, R.id.rb_both})
    public void onRadioButtonClicked(RadioButton radioButton) {
        // Is the button now checked?
        boolean checked = radioButton.isChecked();
        // Check which radio button was clicked
        switch (radioButton.getId()) {
            case R.id.rb_veg:
                if (checked) {
                    updateItemType(DataUtils.KEY_ITEM_TYPE, "1");
                }
                break;
            case R.id.rb_non_veg:
                if (checked) {
                    updateItemType(DataUtils.KEY_ITEM_TYPE, "2");
                }
                break;
            case R.id.rb_both:
                if (checked) {
                    itemType = "";
                    updateItemType(DataUtils.KEY_ITEM_TYPE, "");
                }
                break;
        }
    }


    @OnCheckedChanged(R.id.cb_special_offers)
    void onSpecialOffers(CompoundButton button, boolean checked) {
        updateItem(DataUtils.KEY_SPECIAL_OFFER, checked);
    }

    @OnCheckedChanged(R.id.break_fast)
    void onBreakFast(CompoundButton button, boolean checked) {
        updateItem(DataUtils.KEY_BREAK_FAST, checked);
    }

    @OnCheckedChanged(R.id.brunch)
    void onBrunch(CompoundButton button, boolean checked) {
        updateItem(DataUtils.KEY_BRUNCH, checked);
    }

    @OnCheckedChanged(R.id.lunch)
    void onLunch(CompoundButton button, boolean checked) {
        updateItem(DataUtils.KEY_LUNCH, checked);
    }

    @OnCheckedChanged(R.id.supper)
    void onSupper(CompoundButton button, boolean checked) {
        updateItem(DataUtils.KEY_SUPPER, checked);
    }

    @OnCheckedChanged(R.id.dinner)
    void onDinner(CompoundButton button, boolean checked) {
        updateItem(DataUtils.KEY_DINNER, checked);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onAttachToParentFragment(getParentFragment());
    }

    public void onAttachToParentFragment(Fragment fragment) {
        try {
            itemFilterListener = (ItemFilterListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    fragment.toString() + " must implement OnPlayerSelectionSetListener");
        }
    }

    @OnClick(R.id.btn_show_filter)
    public void setShowFilter() {
        if (itemFilterListener != null) {
            itemFilterListener.onShowItem();
        }
    }

    public void updateItem(String key, boolean value) {
        if (itemFilterListener != null) {
            itemFilterListener.onUpdateFilter(key, value);
        }
    }

    public void updateItemType(String key, String value) {
        if (itemFilterListener != null) {
            itemFilterListener.onUpdateFilter(key, value);
        }
    }


  /*  private void setFilter(String filterJSON) {
        try {
            jsonObject = new JSONObject(filterJSON);
            cbTopOffers.setChecked(jsonObject.getBoolean(DataUtils.KEY_TOP_OFFER));
            cbSpecialOffers.setChecked(jsonObject.getBoolean(DataUtils.KEY_SPECIAL_OFFER));
            cbCombo.setChecked(jsonObject.getBoolean(DataUtils.KEY_COMBO));
            cbHalal.setChecked(jsonObject.getBoolean(DataUtils.KEY_HALAL));

            cbBreakFast.setChecked(jsonObject.getBoolean(DataUtils.KEY_BREAK_FAST));
            cbBrunch.setChecked(jsonObject.getBoolean(DataUtils.KEY_BRUNCH));
            cbLunch.setChecked(jsonObject.getBoolean(DataUtils.KEY_LUNCH));
            cbDinner.setChecked(jsonObject.getBoolean(DataUtils.KEY_DINNER));
            cbSupper.setChecked(jsonObject.getBoolean(DataUtils.KEY_SUPPER));
            switch (jsonObject.getString(DataUtils.KEY_ITEM_TYPE)) {
                case "":
                    rbBoth.setChecked(true);
                    break;
                case "2":
                    rbNonVeg.setChecked(true);
                    break;
                case "1":
                    rbVeg.setChecked(true);
                    break;
            }


        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }
*/

}
