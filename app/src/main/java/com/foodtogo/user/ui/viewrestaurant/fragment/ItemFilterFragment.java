package com.foodtogo.user.ui.viewrestaurant.fragment;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseDialogFragment;
import com.foodtogo.user.ui.viewrestaurant.adapter.FilterMenuAdapter;
import com.foodtogo.user.ui.viewrestaurant.interfaces.ItemFilterListener;
import com.foodtogo.user.ui.viewrestaurant.interfaces.OnCompleteListener;
import com.foodtogo.user.ui.viewrestaurant.interfaces.SortByListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.foodtogo.user.base.AppConstants.EXCEPTION;
import static com.foodtogo.user.util.DataUtils.KEY_BREAK_FAST;
import static com.foodtogo.user.util.DataUtils.KEY_BRUNCH;
import static com.foodtogo.user.util.DataUtils.KEY_COMBO;
import static com.foodtogo.user.util.DataUtils.KEY_DINNER;
import static com.foodtogo.user.util.DataUtils.KEY_HALAL;
import static com.foodtogo.user.util.DataUtils.KEY_ITEM_TYPE;
import static com.foodtogo.user.util.DataUtils.KEY_LUNCH;
import static com.foodtogo.user.util.DataUtils.KEY_SPECIAL_OFFER;
import static com.foodtogo.user.util.DataUtils.KEY_SUPPER;
import static com.foodtogo.user.util.DataUtils.KEY_TOP_OFFER;


public class ItemFilterFragment extends BaseDialogFragment implements ItemFilterListener, SortByListener {

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    FilterMenuAdapter filterMenuAdapter;

    public OnCompleteListener mListener;

   // public final static String KEY_FILTER = "filter";
    public final static String SORT_BY = "sortBy";
    private JSONObject jsonObject;
    private int sortBy = 0;

    public static ItemFilterFragment newInstance( int sortBy) {
        ItemFilterFragment itemFilterFragment = new ItemFilterFragment();
        Bundle args = new Bundle();
       // args.putString(KEY_FILTER, filterJson);
        args.putInt(SORT_BY, sortBy);
        itemFilterFragment.setArguments(args);
        return itemFilterFragment;
    }


    @OnClick(R.id.iv_close)
    public void closeDialog() {
        dismiss();
    }

    // make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public void onShowItem() {
        dismiss();
        try {
            int orderByTopOffers = jsonObject.getBoolean(KEY_TOP_OFFER) ? 1 : 0;
            int orderBySplOffer = jsonObject.getBoolean(KEY_SPECIAL_OFFER) ? 1 : 0;
            int searchCombo = jsonObject.getBoolean(KEY_COMBO) ? 1 : 0;
            int searchHalal = jsonObject.getBoolean(KEY_HALAL) ? 1 : 0;

            ArrayList<String> arrayList = new ArrayList<>();
            arrayList = addTime(jsonObject.getBoolean(KEY_BREAK_FAST), "1", arrayList);
            arrayList = addTime(jsonObject.getBoolean(KEY_BRUNCH), "2", arrayList);
            arrayList = addTime(jsonObject.getBoolean(KEY_LUNCH), "3", arrayList);
            arrayList = addTime(jsonObject.getBoolean(KEY_SUPPER), "4", arrayList);
            arrayList = addTime(jsonObject.getBoolean(KEY_DINNER), "5", arrayList);

            String itemType = jsonObject.getString(KEY_ITEM_TYPE);

            mListener.onComplete(jsonObject, String.valueOf(orderBySplOffer), String.valueOf(orderByTopOffers),
                    String.valueOf(searchCombo), String.valueOf(searchHalal), itemType, arrayList);

        } catch (JSONException e) {
            Log.e(EXCEPTION, e.toString());
        }


    }

    private ArrayList<String> addTime(boolean isChecked, String data, final ArrayList<String> arrayList) {
        ArrayList<String> availableTimes = new ArrayList<>();
        availableTimes.addAll(arrayList);
        if (isChecked)
            availableTimes.add(data);

        return availableTimes;
    }

    @Override
    public void onUpdateFilter(String key, boolean value) {
        try {
            jsonObject.put(key, value);
        } catch (JSONException e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    @Override
    public void onUpdateFilter(String key, String value) {
        try {
            jsonObject.put(key, value);
        } catch (JSONException e) {
            Log.e(EXCEPTION, e.toString());
        }
    }


    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_item_filter, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // String filterJSON = getArguments().getString(KEY_FILTER);
        sortBy = getArguments().getInt(SORT_BY, 0);
       // setFilter(filterJSON);
        filterMenuAdapter = new FilterMenuAdapter(getChildFragmentManager());
       // filterMenuAdapter.addFragment(getString(R.string.filter), FilterFragment.newInstance(filterJSON));
        filterMenuAdapter.addFragment(getString(R.string.sort_by), SortByFragment.newInstance(sortBy));
        viewPager.setAdapter(filterMenuAdapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_orange));
        setCustomFont();
    }


    private void setFilter(String filterJSON) {
        try {
            jsonObject = new JSONObject(filterJSON);
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    public void setCustomFont() {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    //Put your font in assests folder
                    //assign name of the font here (Must be case sensitive)
                    ((TextView) tabViewChild).setTypeface(ResourcesCompat.getFont(getActivity(), R.font.helvetica_semi_bold));
                    ((TextView) tabViewChild).setAllCaps(false);
                    ((TextView) tabViewChild).setTextSize(17f);
                }
            }
        }
    }


    @Override
    public void onShowSortBy(int sortBy) {
        dismiss();
        mListener.onSortBy(sortBy);
    }
}
