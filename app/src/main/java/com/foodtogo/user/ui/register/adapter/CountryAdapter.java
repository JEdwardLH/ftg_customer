package com.foodtogo.user.ui.register.adapter;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.country.CountryDetail;

import java.util.List;

public class CountryAdapter extends ArrayAdapter<String> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<CountryDetail> items;
    private final int mResource;

    public CountryAdapter(@NonNull Context context, @LayoutRes int resource,
                          @NonNull List objects) {
        super(context, resource, 0, objects);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        items = objects;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(mResource, parent, false);
        TextView tvSubCategory = (TextView) view.findViewById(R.id.tv_sub_category);
        CountryDetail countryDetail = items.get(position);
        tvSubCategory.setText(countryDetail.getCountryDial()+" - "+countryDetail.getCountryName());
        return view;
    }
}