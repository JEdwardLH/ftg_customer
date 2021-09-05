package com.foodtogo.user.ui.allrestaurant.fragment;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseDialogFragment;
import com.foodtogo.user.model.allrestaurant.CategoryList;
import com.foodtogo.user.ui.allrestaurant.adapter.FilterAdapter;
import com.foodtogo.user.ui.allrestaurant.interfaces.OnApplyFilter;
import com.foodtogo.user.ui.allrestaurant.interfaces.SelectionListener;
import com.foodtogo.user.util.DataUtils;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.foodtogo.user.base.AppConstants.EXCEPTION;
import static com.foodtogo.user.ui.allrestaurant.enums.CategoryType.CUISINES;


public class RestaurantFilter extends BaseDialogFragment implements SelectionListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public OnApplyFilter mListener;
    public final static String KEY_CATEGORY_LIST = "categoryList";
    public final static String KEY_OTHER_FILTER = "otherFilter";
    private ArrayList<CategoryList> categoryListArrayList;

    JSONObject filterJSON = null;
    ArrayList<String> categoryIds=new ArrayList<>();

    public static RestaurantFilter newInstance(ArrayList<CategoryList> categoryLists, String filterJSON) {
        RestaurantFilter itemFilterFragment = new RestaurantFilter();
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_CATEGORY_LIST, categoryLists);
        args.putString(KEY_OTHER_FILTER, filterJSON);
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
            this.mListener = (OnApplyFilter) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }


    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_all_restaurant_filter, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryListArrayList = getArguments().getParcelableArrayList(KEY_CATEGORY_LIST);
        filterJSON = DataUtils.convertString2JSON(getArguments().getString(KEY_OTHER_FILTER));
        FilterAdapter adapter = new FilterAdapter(getActivity(), categoryListArrayList);
        adapter.setSelectionListener(this);
        adapter.setFilterJSON(filterJSON);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        getCategoryIds();
    }
    void getCategoryIds(){
        if(categoryListArrayList!=null && categoryListArrayList.size()>0) {
            for (int i = 0; i < categoryListArrayList.size(); i++) {
                if(categoryListArrayList.get(i).getCategoryType().equals(CUISINES) && categoryListArrayList.get(i).isSelected() ){
                    categoryIds.add(String.valueOf(categoryListArrayList.get(i).getCategoryId()));
                }
            }
        }
    }



    @OnClick(R.id.btn_show_filter)
    public void setShowFilter() {
        dismiss();
        mListener.onComplete(categoryListArrayList, filterJSON.toString(),categoryIds);
    }

    @Override
    public void onUpdateKeyValue(String key, String value) {
        try {
            filterJSON.put(key, value);
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    @Override
    public void onUpdateKeyValue(String key, boolean value) {
        try {
            filterJSON.put(key, value);
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }

    }

    @Override
    public void onUpdateKeyValue(int position, boolean value,String categoryId) {
        try {
            categoryListArrayList.get(position).setSelected(value);
            if(value){
                if(!categoryIds.contains(categoryId)){
                    categoryIds.add(categoryId);
                }
            }else{
                categoryIds.remove(categoryId);
            }
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }
}
