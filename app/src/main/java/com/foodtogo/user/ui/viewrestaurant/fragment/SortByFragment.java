package com.foodtogo.user.ui.viewrestaurant.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.ui.viewrestaurant.interfaces.SortByListener;

import butterknife.BindView;
import butterknife.OnClick;

import static com.foodtogo.user.ui.viewrestaurant.fragment.ItemFilterFragment.SORT_BY;

public class SortByFragment extends BaseFragment {

    private SortByListener sortByListener;

    @BindView(R.id.rdb_atz)
    RadioButton rdbAtZ;

    @BindView(R.id.rdb_zta)
    RadioButton rdbZtA;

    @BindView(R.id.rdb_htl)
    RadioButton rdbHtL;

    @BindView(R.id.rdb_lth)
    RadioButton rdbLtH;

    int sortBy = 0;

    public static SortByFragment newInstance(int sortBy) {
        SortByFragment sortByFragment = new SortByFragment();
        Bundle args = new Bundle();
        args.putInt(SORT_BY, sortBy);
        sortByFragment.setArguments(args);
        return sortByFragment;
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sort_by, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sortBy = getArguments().getInt(SORT_BY, 0);
        setSortBy();
    }

    private void setSortBy() {
        rdbAtZ.setChecked(false);
        rdbZtA.setChecked(false);
        rdbHtL.setChecked(false);
        rdbLtH.setChecked(false);
        switch (sortBy) {
            case 2:
                rdbAtZ.setChecked(true);
                break;
            case 3:
                rdbZtA.setChecked(true);
                break;
            case 4:
                rdbLtH.setChecked(true);
                break;
            case 5:
                rdbHtL.setChecked(true);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onAttachToParentFragment(getParentFragment());
    }

    @OnClick({R.id.rdb_atz, R.id.rdb_zta, R.id.rdb_lth, R.id.rdb_htl})
    public void onRadioButtonClicked(RadioButton radioButton) {
        // Is the button now checked?
        boolean checked = radioButton.isChecked();
        // Check which radio button was clicked
        switch (radioButton.getId()) {
            case R.id.rdb_atz:
                if (checked) {
                    sortBy = 2;
                }
                break;
            case R.id.rdb_zta:
                if (checked) {
                    sortBy = 3;
                }
                break;
            case R.id.rdb_lth:
                if (checked) {
                    sortBy = 4;
                }
                break;
            case R.id.rdb_htl:
                if (checked) {
                    sortBy = 5;
                }
                break;
        }
    }


    public void onAttachToParentFragment(Fragment fragment) {
        try {
            sortByListener = (SortByListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    fragment.toString() + " must implement OnPlayerSelectionSetListener");
        }
    }

    @OnClick(R.id.btn_sort_by)
    public void setShowFilter() {
        if (sortByListener != null) {
            sortByListener.onShowSortBy(sortBy);
        }
    }
}
