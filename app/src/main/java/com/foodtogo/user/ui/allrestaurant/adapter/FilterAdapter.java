package com.foodtogo.user.ui.allrestaurant.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foodtogo.user.R;
import com.foodtogo.user.model.allrestaurant.CategoryList;
import com.foodtogo.user.ui.allrestaurant.interfaces.SelectionListener;
import com.foodtogo.user.ui.allrestaurant.viewholder.CategoryViewHolder;
import com.foodtogo.user.ui.allrestaurant.viewholder.FootTypeViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FilterAdapter extends RecyclerView.Adapter  {

    private final Context mContext;
    private final List<CategoryList> categoryLists;

    //int AVAILABLE_TIME = 1;
    int FOOD_TYPE = 1;
    int CUISINES = 2;

    private SelectionListener selectionListener;
    private JSONObject filterJSON;


    public FilterAdapter(Context context, ArrayList<CategoryList> cityProperties) {
        this.mContext = context;
        this.categoryLists = cityProperties;
    }


    public void setSelectionListener(SelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    public void setFilterJSON(JSONObject filterJSON) {
        this.filterJSON = filterJSON;
    }

    /*  if (AVAILABLE_TIME == viewType) {
               View view = inflater.inflate(R.layout.dialog_available_time, parent, false);
               return new AvailableTimeViewHolder(view, selectionListener, filterJSON);
           } else */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (FOOD_TYPE == viewType) {
            View view = inflater.inflate(R.layout.dialog_food_type, parent, false);
            return new FootTypeViewHolder(view, selectionListener, filterJSON);
        } else {
            View view = inflater.inflate(R.layout.dialog_category, parent, false);
            return new CategoryViewHolder(view, selectionListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (categoryLists.get(position).getCategoryType()) {

            case FOOD_TYPE:
                ((FootTypeViewHolder) holder).onBind(position, categoryLists);
                break;
            case CUISINES:
                ((CategoryViewHolder) holder).onBind(position, categoryLists);
                break;
        }
    }

   /* case AVAILABLE_TIME:
                ((AvailableTimeViewHolder) holder).onBind(position, categoryLists);
                break;*/

    /*  case AVAILABLE_TIME:
               categoryType = 1;
               break;*/
    @Override
    public int getItemViewType(int position) {
        int categoryType = 0;
        switch (categoryLists.get(position).getCategoryType()) {
            case FOOD_TYPE:
                categoryType = 1;
                break;
            case CUISINES:
                categoryType = 2;
                break;
        }
        return categoryType;

    }

    @Override
    public int getItemCount() {
        return categoryLists.size();
    }




}
